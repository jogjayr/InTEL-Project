/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.connectors.ContactPoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Jimmy Truesdell
 */
public class FrictionEquationRecognizer extends EquationRecognizer {

    /**
     * Map keys are: "f", "mu", and "N"
     * Representing exactly what they ought to
     * @param state
     * @return
     */
    protected Map<String, EquationNode> interpret(ArbitraryEquationMathState state) {
        // attempt to fill in values of the form f = mu * N
        // where equation sides may be reversed, and multiplication order may be reversed.
        // return null if something is incorrect.

        Map<String, EquationNode> nodeMap = new HashMap<String, EquationNode>();
        if (state.getLeftSide() instanceof AnchoredVectorNode && ((AnchoredVectorNode) state.getLeftSide()).getAnchoredVector().getSymbolName().charAt(0) == 'f') {
            //if left side is valid format
            nodeMap.put("f", state.getLeftSide());

            if (state.getRightSide() instanceof OperatorNode) {
                //if right side suggests valid format
                OperatorNode opNode = (OperatorNode) state.getRightSide();

                if (opNode.getLeftNode() instanceof SymbolNode) {
                    //if first element of right side is valid
                    nodeMap.put("mu", opNode.getLeftNode());

                    if (opNode.getRightNode() instanceof AnchoredVectorNode && ((AnchoredVectorNode) opNode.getRightNode()).getAnchoredVector().getSymbolName().charAt(0) == 'N') {
                        //if second element of right side is valid
                        nodeMap.put("N", opNode.getRightNode());

                    } else {
                        StaticsApplication.logger.info("check: improper friction equation");
                        StaticsApplication.logger.info("check: FAILED");

                        StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_friction_equation_validate");
                        return null;
                    }
                } else if (opNode.getLeftNode() instanceof AnchoredVectorNode && ((AnchoredVectorNode) opNode.getLeftNode()).getAnchoredVector().getSymbolName().charAt(0) == 'N') {
                    //if first element of right side is valid but reversed
                    nodeMap.put("N", opNode.getLeftNode());

                    if (opNode.getRightNode() instanceof SymbolNode) {
                        //if second element of right side is valid but reversed
                        nodeMap.put("mu", opNode.getRightNode());

                    } else {
                        StaticsApplication.logger.info("check: improper friction equation");
                        StaticsApplication.logger.info("check: FAILED");

                        StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_friction_equation_validate");
                        return null;
                    }
                } else {
                    StaticsApplication.logger.info("check: improper friction equation");
                    StaticsApplication.logger.info("check: FAILED");

                    StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_friction_equation_validate");
                    return null;
                }
            } else {
                StaticsApplication.logger.info("check: improper friction equation");
                StaticsApplication.logger.info("check: FAILED");

                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_friction_equation_validate");
                return null;
            }
        } else if (state.getLeftSide() instanceof OperatorNode) {
            //if left side is valid but reversed
            OperatorNode opNode = (OperatorNode) state.getLeftSide();

            if (opNode.getLeftNode() instanceof SymbolNode) {
                //if first element of right side is valid
                nodeMap.put("mu", opNode.getLeftNode());

                if (opNode.getRightNode() instanceof AnchoredVectorNode && ((AnchoredVectorNode) opNode.getRightNode()).getAnchoredVector().getSymbolName().charAt(0) == 'N') {
                    //if second element of right side is valid
                    nodeMap.put("N", opNode.getRightNode());

                } else {
                    StaticsApplication.logger.info("check: improper friction equation");
                    StaticsApplication.logger.info("check: FAILED");

                    StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_friction_equation_validate");
                    return null;
                }
            } else if (opNode.getLeftNode() instanceof AnchoredVectorNode && ((AnchoredVectorNode) opNode.getLeftNode()).getAnchoredVector().getSymbolName().charAt(0) == 'N') {
                //if first element of right side is valid but reversed
                nodeMap.put("N", opNode.getLeftNode());

                if (opNode.getRightNode() instanceof SymbolNode) {
                    //if second element of right side is valid but reversed
                    nodeMap.put("mu", opNode.getRightNode());

                } else {
                    StaticsApplication.logger.info("check: improper friction equation");
                    StaticsApplication.logger.info("check: FAILED");

                    StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_friction_equation_validate");
                    return null;
                }
            } else {
                StaticsApplication.logger.info("check: improper friction equation");
                StaticsApplication.logger.info("check: FAILED");

                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_friction_equation_validate");
                return null;
            }
            if (state.getRightSide() instanceof AnchoredVectorNode && ((AnchoredVectorNode) state.getRightSide()).getAnchoredVector().getSymbolName().charAt(0) == 'f') {
                //if right side is valid format but reversed
                nodeMap.put("f", state.getRightSide());
            }
        } else {
            StaticsApplication.logger.info("check: improper friction equation");
            StaticsApplication.logger.info("check: FAILED");

            StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_friction_equation_validate");
            return null;
        }
        return nodeMap;
    }

    private boolean isCloseEnough(Vector3bd v1, Vector3bd v2) {
        // use a flat comparison here.
        return v1.distance(v2) < .001;
    }

    /**
     * Ensure that the equation's assertion is true. That is to say, a == bu.
     * @param state
     * @return
     */
    public boolean isValid(ArbitraryEquationMathState state, FreeBodyDiagram d) {

        Map<String, EquationNode> interpretation = interpret(state);
        if (interpretation == null) {
            return false;
        }

        AnchoredVectorNode fNode = (AnchoredVectorNode) interpretation.get("f");
        AnchoredVectorNode NNode = (AnchoredVectorNode) interpretation.get("N");
        SymbolNode muNode = (SymbolNode) interpretation.get("mu");
        List pList = d.getConnectorsAtPoint(fNode.getAnchoredVector().getAnchor());
        ContactPoint cp = null;

        //is this point a ContactPoint?
        for (Object p : pList)//Object p : cp){
        {
            if (((Connector) p) instanceof ContactPoint) {
                cp = ((ContactPoint) p);
                break;
            }
        }

        if (cp == null) {
            return false;
        }

        if (!muNode.getSymbol().equals(cp.getFrictionCoefficient().getName())) {
            StaticsApplication.logger.info("check: wrong friction coefficient selected. Expected: " + cp.getFrictionCoefficient());
            StaticsApplication.logger.info("check: FAILED");

            StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_friction_equation_coefficient");
            return false;
        }

        //are N and f at the same point?
        if (fNode.getAnchoredVector().getAnchor() != NNode.getAnchoredVector().getAnchor()) {
            return false;
        }

        //is NNode pointed the right way?
        if (!isCloseEnough(NNode.getAnchoredVector().getVectorValue(), cp.getNormalDirection())) {
            return false;
        }

        //is fNode pointed the right way?
        if (!isCloseEnough(fNode.getAnchoredVector().getVectorValue(), cp.getFrictionDirection())) {
            return false;
        }

        return true;
    }
}
