/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.Quantity;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Cable;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.objects.connectors.Fix2d;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.connectors.Roller2d;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDChecker {

    private FreeBodyDiagram diagram;
    //private Joint nextJoint;
    private boolean done = false;
    private boolean verbose = true;

    protected FreeBodyDiagram getDiagram() {
        return diagram;
    }

    public FBDChecker(FreeBodyDiagram diagram) {
        this.diagram = diagram;
    }

    /**
     * Get all of the symbolic measurements in the schematic, for making sure their names
     * do are not being used for loads.
     * @return
     */
    private List<DistanceMeasurement> getSymbolicMeasurements() {
        List<DistanceMeasurement> m = new ArrayList<DistanceMeasurement>();
        for (SimulationObject obj : FreeBodyDiagram.getSchematic().allObjects()) {
            if (obj instanceof DistanceMeasurement && ((DistanceMeasurement) obj).isSymbol()) {
                m.add((DistanceMeasurement) obj);
            }
        }
        return m;
    }

    /**
     * The verbose flag lets the checker know whether to report information on failure.
     * Verbose output will report both information to the logger and to the advice box.
     * @param enable
     */
    public void setVerbose(boolean enable) {
        verbose = enable;
    }

    /**
     * Get all the points in the schematic, to check against for force names.
     * @return
     */
    private List<Point> getAllPoints() {
        List<Point> m = new ArrayList<Point>();
        for (SimulationObject obj : FreeBodyDiagram.getSchematic().allObjects()) {
            if (obj instanceof Point) {
                m.add((Point) obj);
            }
        }
        return m;
    }

    /**
     * Get all the loads added to the free body diagram.
     * @return
     */
    private List<Load> getAddedForces() {
        List<Load> addedForces = new ArrayList<Load>();
        for (SimulationObject obj : diagram.allObjects()) {
            if (!(obj instanceof Load)) {
                continue;
            }

            // this force has been added, and is not a given that could have been selected.
            addedForces.add((Load) obj);
        }
        return addedForces;
    }

    private List<Load> getGivenForces() {
        List<Load> givenForces = new ArrayList<Load>();
        for (Body body : FreeBodyDiagram.getSchematic().allBodies()) {
            if (diagram.getBodySubset().getBodies().contains(body)) {
                for (SimulationObject obj : body.getAttachedObjects()) {
                    if (obj instanceof Load) {
                        givenForces.add((Load) obj);
                    }
                }
            }
        }
        return givenForces;
    }

    private void logInfo(String info) {
        if (verbose) {
            Logger.getLogger("Statics").info(info);
        }
    }

    private void setAdviceKey(String key, Object... parameters) {
        if (verbose) {
            StaticsApplication.getApp().setAdviceKey(key, parameters);
        }
    }

    public boolean checkDiagram() {

        done = false;

        // step 1: assemble a list of all the forces the user has added.
        List<Load> addedForces = getAddedForces();

        logInfo("check: user added forces: " + addedForces);

        if (addedForces.size() <= 0) {
            logInfo("check: diagram does not contain any forces");
            logInfo("check: FAILED");

            setAdviceKey("fbd_feedback_check_fail_add");
            return false;
        }

        // make list for weights, as we will need these later.
        Map<Load, Body> weights = new HashMap<Load, Body>();

        List<Load> givenForces = getGivenForces();

        // step 2: for vectors that we can click on and add, ie, given added forces,
        // make sure that the user has added all of them.
        for (Load external : givenForces) {
            boolean success = false;
            if (external.isSymbol()) {
                // if the force is symbolic, it is allowed to be negated.
                success = testReaction(external, addedForces) ||
                        testReaction(negate(external), addedForces);
            } else {
                success = addedForces.remove(external);
            }

            if (!success) {
                //if the code reaches this point there is a problem. this checks
                //to see if the falure is related to a force added as an external that
                //was wrong (numerically or symbolically)
                for (Load addedExt : addedForces) {
                    if (addedExt.getAnchor() == external.getAnchor()) {
                        if ((addedExt instanceof Force && external instanceof Force) || (addedExt instanceof Moment && external instanceof Moment)) {

                            if (external.isSymbol() && !addedExt.isSymbol()) {
                                //An external value that should be symbolic has been added as numeric
                                logInfo("check: external value should be a symbol at point" + external.getAnchor().getLabelText());
                                logInfo("check: FAILED");

                                setAdviceKey("fbd_feedback_check_fail_given_symbol", addedExt.getLabelText(), addedExt.getAnchor().getLabelText());
                                return false;
                            } else if (!external.isSymbol() && addedExt.isSymbol()) {
                                //An external value that should be numeric has been added as symbolic
                                logInfo("check: external value should be a numeric at point" + external.getAnchor().getLabelText());
                                logInfo("check: FAILED");

                                setAdviceKey("fbd_feedback_check_fail_given_number", addedExt.getLabelText(), addedExt.getAnchor().getLabelText());
                                return false;
                            } else {
                                //An external value was added as numeric, correctly, but was the wrong number
                                //pointing the wrong way
                                logInfo("check: diagram contains incorrect external force " + external);
                                logInfo("check: FAILED");

                                setAdviceKey("fbd_feedback_check_fail_given_value", addedExt.getLabelText(), addedExt.getAnchor().getLabelText());
                                return false;
                            }
                        }
                    }
                }

                //if the above is not the error it is assumed to be because the
                //user has forgotten to add an external force
                logInfo("check: diagram does not contain external force " + external);
                logInfo("check: FAILED");

                setAdviceKey("fbd_feedback_check_fail_given", external.getAnchor().getLabelText());
                return false;
            }
        }

        // step 3: Make sure weights exist, and remove them from our addedForces.
        for (Body body : diagram.getBodySubset().getBodies()) {
            if (body.getWeight().getDiagramValue().floatValue() != 0) {

                Load weight = new Force(
                        body.getCenterOfMassPoint(),
                        Vector3bd.UNIT_Y.negate(),
                        new BigDecimal(body.getWeight().doubleValue()));
                weights.put(weight, body);
                if (addedForces.contains(weight)) {
                    // still using units here....
                    // this should be changed so the test is independent of magnitude.
                    logInfo("check: removing weight for " + body);
                    addedForces.remove(weight);
                } else {
                    //if the code reaches this point there is a problem. this checks
                    //to see if the falure is related to a force added as a weight that
                    //was wrong (numerically or symbolically)
                    for (Load addedWeight : addedForces) {
                        if (addedWeight.getAnchor() == weight.getAnchor()) {
                            //A weight that should be symbolic has been added as numeric
                            if (weight.isSymbol() && !addedWeight.isSymbol()) {
                                logInfo("check: weight should be a symbol at point" + weight.getAnchor().getLabelText());
                                logInfo("check: FAILED");

                                setAdviceKey("fbd_feedback_check_fail_weight_symbol", body.getLabelText());
                                return false;
                            } //A weight that should be numeric has been added as symbolic
                            else if (!weight.isSymbol() && addedWeight.isSymbol()) {
                                logInfo("check: weight should be numeric at point" + weight.getAnchor().getLabelText());
                                logInfo("check: FAILED");

                                setAdviceKey("fbd_feedback_check_fail_weight_number", body.getLabelText());
                                return false;
                            } //A weight was added as numeric, correctly, but was the wrong number
                            else {
                                logInfo("check: diagram contains incorrect weight " + weight);
                                logInfo("check: FAILED");

                                setAdviceKey("fbd_feedback_check_fail_weight_value", body.getLabelText());
                                return false;
                            }
                        }
                    }
                    // weight does not exist in system.
                    logInfo("check: diagram does not contain weight for " + body);
                    logInfo("check: weight is: " + weight);
                    logInfo("check: FAILED");

                    setAdviceKey("fbd_feedback_check_fail_weight", body.getLabelText());
                    return false;
                }
            }
        }

        // Step 4: go through all the border joints connecting this FBD to the external world,
        // and check each force implied by the joint.
        //List<Pair<Joint, Body>> jointsAndBodies = new ArrayList(); // joints between system and external.
//        for (SimulationObject obj : diagram.allObjects()) {
        for (int i = 0; i < diagram.allObjects().size(); i++) {
            SimulationObject obj = diagram.allObjects().get(i);
            if (!(obj instanceof Connector)) {
                continue;
            }

            Connector joint = (Connector) obj;

            // no idea what this next section does.
            // commenting out seems to do no harm for the time being.
            /*for (int ii = i + 1; ii < diagram.allObjects().size(); ii++) {
                SimulationObject obj2 = diagram.allObjects().get(ii);
                if (!(obj2 instanceof Joint)) {
                    if (ii == diagram.allObjects().size() - 1) {
                        done = true;
                    }
                    continue;
                }
                nextJoint = (Joint) obj2;
                break;
            }*/

            Body body = null;
            if (diagram.getBodySubset().getBodies().contains(joint.getBody1())) {
                body = joint.getBody1();
            }
            if (diagram.getBodySubset().getBodies().contains(joint.getBody2())) {
                body = joint.getBody2();
            }

            // ^ is java's XOR operator
            // we want the joint IF it connects a body in the body list
            // to a body that is not in the body list. This means xor.
            if (!(diagram.getBodySubset().getBodies().contains(joint.getBody1()) ^
                    diagram.getBodySubset().getBodies().contains(joint.getBody2()))) {
                continue;
            }

            List<Load> reactions = getReactions(joint, joint.getReactions(body));

            logInfo("check: testing joint: " + joint);

            if (testJoint(joint, addedForces, reactions)) {
                continue;
            } else {
                // joint check has failed,
                // so check some common errors

                //no reaction forces added
                if (getForcesAtPoint(joint.getAnchor(), addedForces).isEmpty()) {
                    logInfo("check: have any forces been added");
                    logInfo("check: FAILED");
                    setAdviceKey("fbd_feedback_check_fail_joint_reaction", connectorType(joint), joint.getAnchor().getLabelText());
                    return false;
                }
                //check to see if the user has wrongly created a pin
                if (!(joint instanceof Pin2d)) {
                    Pin2d testPin = new Pin2d(joint.getAnchor());
                    if (testJoint(testPin, addedForces, reactions)) {
                        logInfo("check: user wrongly created a pin at point " + joint.getAnchor().getLabelText());
                        logInfo("check: FAILED");
                        setAdviceKey("fbd_feedback_check_fail_joint_wrong_type", joint.getAnchor().getLabelText(), "pin", connectorType(joint));
                        return false;
                    }
                }
                //check to see if the user has wrongly created a fix
                if (!(joint instanceof Fix2d)) {
                    Fix2d testFix = new Fix2d(joint.getAnchor());
                    if (testJoint(testFix, addedForces, reactions)) {
                        logInfo("check: user wrongly created a fix at point " + joint.getAnchor().getLabelText());
                        logInfo("check: FAILED");
                        setAdviceKey("fbd_feedback_check_fail_joint_wrong_type", joint.getAnchor().getLabelText(), "fix", connectorType(joint));
                        return false;
                    }
                }

                //check to see if the user has created a cable that is in compression
                if (joint instanceof Connector2ForceMember2d) {
                    for (Load load : reactions) {
                        for (int iii = 0; iii < addedForces.size(); iii++) {
                            if ((joint.getBody1() instanceof Cable || joint.getBody2() instanceof Cable) && addedForces.get(iii).equalsSymbolic(negate(load))) {
                                logInfo("check: user created a cable in compression at point " + joint.getAnchor().getLabelText());
                                logInfo("check: FAILED");
                                setAdviceKey("fbd_feedback_check_fail_joint_cable",
                                        addedForces.get(iii).getAnchor().getLabelText(),
                                        addedForces.get(iii).getLabelText());
                                return false;
                            }
                        }
                    }
                }
                logInfo("check: user simply added reactions to a joint that don't make sense to point " + joint.getAnchor().getLabelText());
                logInfo("check: FAILED");
                setAdviceKey("fbd_feedback_check_fail_joint_wrong", connectorType(joint), joint.getAnchor().getLabelText());
                return false;
            }
        }

        // Step 5: Make sure we've used all the user added forces.
        if (!addedForces.isEmpty()) {
            logInfo("check: user added more forces than necessary: " + addedForces);
            logInfo("check: FAILED");

            setAdviceKey("fbd_feedback_check_fail_additional", addedForces.get(0).getAnchor().getLabelText());
            return false;
        }

        // Step 6: Verify labels
        // verify that all unknowns are symbols
        // these are reaction forces and moments
        // knowns should not be symbols: externals, weights
        // symbols must also not be repeated, unless this is valid somehow? (not yet)

        addedForces = getAddedForces();

        // go through each force that the user has added
        for (Load force : addedForces) {
            if (force.isSymbol()) {

                // ignore solved forces
                if (force.isKnown()) {
                    continue;
                }

                // should force be a symbol?
                // the only forces that should not be symbols now are weights.
                // we probably need some sort of means for identifying this later on...

                if (weights.values().contains(force)) {
                    logInfo("check: force should not be symbol: " + force);
                    logInfo("check: FAILED");

                    setAdviceKey("fbd_feedback_check_fail_symbol", force.getAnchor().getLabelText());
                    return false;
                }

                // here we check against a quantity set in the symbol manager
                Quantity symbolicQuantity = StaticsApplication.getApp().getExercise().getSymbolManager().getSymbol(force);

                if (symbolicQuantity != null) {
                    if (!symbolicQuantity.toString().equalsIgnoreCase(force.getVector().getQuantity().toString())) {
                        setAdviceKey("fbd_feedback_check_fail_not_same_symbol", forceOrMoment(force), force.getName(), force.getAnchor().getLabelText());
                        return false;
                    }
                }

                // make sure that points 
                for (Point p : getAllPoints()) {
                    if (p.getLabelText().equalsIgnoreCase(force.getLabelText())) {
                        logInfo("check: anchors and added force/moments should not share names");
                        logInfo("check: FAILED");
                        setAdviceKey("fbd_feedback_check_fail_duplicate_anchor", forceOrMoment(force), force.getAnchor().getLabelText(), p.getLabelText());
                        return false;
                    }
                }

                // go through our distance measurements and make sure that no labels share names with
                // symbolic distance measurements
                for (DistanceMeasurement d : getSymbolicMeasurements()) {
                    if (d.getLabelText().equalsIgnoreCase(force.getLabelText())) {
                        logInfo("check: force or moment should not share the same name with the unknown measurement: " + d.getLabelText());
                        logInfo("check: FAILED");
                        setAdviceKey("fbd_feedback_check_fail_duplicate_measurement", forceOrMoment(force), force.getAnchor().getLabelText(), d.getLabelText());
                        return false;
                    }
                }

                // loop through our added forces a second time, to check for duplicate names.
                for (Load f : addedForces) {
                    if (f.getLabelText().equalsIgnoreCase(force.getLabelText()) && f != force) {
                        logInfo("check: force or moment have incorrectly duplicate names: " + force);
                        logInfo("check: FAILED");
                        setAdviceKey("fbd_feedback_check_fail_duplicate",
                                forceOrMoment(force),
                                force.getAnchor().getLabelText(),
                                forceOrMoment(f),
                                f.getAnchor().getLabelText());

                        return false;
                    }
                }

                if (symbolicQuantity == null) {
                    for (String symbol : StaticsApplication.getApp().getExercise().getSymbolManager().getSymbols()) {
                        if (symbol.equals(force.getVector().getSymbolName())) {
                            logInfo("check: force or moment have incorrectly duplicate names (with a symbol from another diagram): " + force);
                            logInfo("check: FAILED");
                            setAdviceKey("fbd_feedback_check_fail_duplicate_name", forceOrMoment(force), force.getVector().getSymbolName(), force.getAnchor().getLabelText());
                            return false;
                        }
                    }
                }
            } else {

                // force is numeric.
                // the only forces that WOULD be numeric are weights right now.
                // make sure the value given is equal to the weight of the object.

                Load tLoad = StaticsApplication.getApp().getExercise().getSymbolManager().getLoad(force);

                if (weights.containsKey(force)) {
                    // ignore this case, weights have already been taken care of
                } else if (givenForces.contains(force)) {
                    // OK, do nothing, givens have also been taken care of.
                } else if (tLoad != null) {
                    if (tLoad.getVector().getQuantity().toString().equalsIgnoreCase(force.getVector().getQuantity().toString())) {
                        if (tLoad.getVectorValue().negate().equals(force.getVectorValue())) {
                            logInfo("check: force or moment has been solved before, and is reversed: " + force);
                            logInfo("check: FAILED");
                            setAdviceKey("fbd_feedback_check_fail_reverse", forceOrMoment(force), force.getVector().getQuantity(), force.getAnchor().getLabelText());
                            return false;
                        }
                    } else {
                        logInfo("check: force or moment has been solved before, and has the wrong number reversed: " + force);
                        logInfo("check: FAILED");
                        setAdviceKey("fbd_feedback_check_fail_not_same_number", forceOrMoment(force), force.getVector().getQuantity(), force.getAnchor().getLabelText());
                        return false;
                    }
                } else {
                    logInfo("check: force should not be numeric: " + force);
                    logInfo("check: FAILED");

                    setAdviceKey("fbd_feedback_check_fail_numeric", force.getAnchor().getLabelText());
                    return false;
                }
            }
        }

        // Yay, we've passed the test!
        logInfo("check: PASSED!");
        return true;
    }

    private Load negate(Load reaction) {
        if (reaction instanceof Force) {
            return new Force(reaction.getAnchor(), reaction.getVector().negate());
        } else if (reaction instanceof Moment) {
            return new Moment(reaction.getAnchor(), reaction.getVector().negate());
        } else {
            // ignore this case
            return null;
        }
    }

    private List<Load> getForcesAtPoint(Point p, List<Load> addedForces) {
        List<Load> forcesAtPoint = new ArrayList<Load>();
        for (Load load : addedForces) {
            if (load.getAnchor() == p) {
                forcesAtPoint.add(load);
            }
        }
        return forcesAtPoint;
    }

    private List<Load> getReactions(Connector joint, List<Vector> reactions) {
        List<Load> loads = new ArrayList<Load>();
        for (Vector vector : reactions) {
            if (vector.getUnit() == Unit.force) {
                loads.add(new Force(joint.getAnchor(), vector));
            } else if (vector.getUnit() == Unit.moment) {
                loads.add(new Moment(joint.getAnchor(), vector));
            }
        }
        return loads;
    }

    /**
     * Tests whether the correct user-added loads are present at the joint specified.
     * This method will remove the correct loads from addedForces if the test passes.
     * @param joint
     * @param addedForces all remaining forces that the user has added.
     * @param reactions the loads that should be present at the joint.
     * @return
     */
    private boolean testJoint(Connector joint, List<Load> addedForces, List<Load> reactions) {

        //has the forces that the user added
        List<Load> forcesAtJoint = getForcesAtPoint(joint.getAnchor(), addedForces);

        // see if we can clear them all
        boolean success = true;
        for (Load load : reactions) {
            if (joint.isForceDirectionNegatable()) {
                if (!testReaction(load, forcesAtJoint) && !testReaction(negate(load), forcesAtJoint)) {
                    success = false;
                }
            } else {
                if (!testReaction(load, forcesAtJoint)) {
                    success = false;
                }
            }
        }
        if (!success || !forcesAtJoint.isEmpty()) {
            // return false, without having changed addedForces at all
            return false;
        }

        // otherwise go through and remove these forces 
        //if (done || !nextJoint.getAnchor().getLabelText().equals(joint.getAnchor().getLabelText())) {
            addedForces.removeAll(getForcesAtPoint(joint.getAnchor(), addedForces));
        //}
        return true;
    }

    private boolean testReaction(Load reaction, List<Load> addedForces) {
        return testReaction(reaction, addedForces, true);
    }

    private boolean testReaction(Load reaction, List<Load> addedForces, boolean remove) {

        // the equality check on Load requires that we ignore the symbol name

        Load equivalent = null;
        for (Load candidate : addedForces) {
            if (candidate.equalsSymbolic(reaction)) {
                // okay the direction is correct.
                // the magnitude will get checked at a later stage
                equivalent = candidate;
            }
        }

        if (equivalent != null) {
            if (remove) {
                addedForces.remove(equivalent);
            }
            return true;
        }

        return false;
    }

    private String forceOrMoment(Load load) {
        if (load instanceof Force) {
            return "force";
        } else if (load instanceof Moment) {
            return "moment";
        } else {
            return "unknown?";
        }
    }

    private String connectorType(Connector joint) {
        if (joint instanceof Pin2d || joint instanceof Connector2ForceMember2d) {
            return "pin";
        } else if (joint instanceof Fix2d) {
            return "fix";
        } else if (joint instanceof Roller2d) {
            return "roller";
        } else {
            return "connector";
        }
    }
}
