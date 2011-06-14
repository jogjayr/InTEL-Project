/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMath;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMathState;
import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Quantity;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.expressionparser.Parser;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.arbitrary.AnchoredVectorNode;
import edu.gatech.statics.modes.equation.arbitrary.EquationNode;
import edu.gatech.statics.modes.equation.arbitrary.OperatorNode;
import edu.gatech.statics.modes.equation.arbitrary.SymbolNode;
import edu.gatech.statics.modes.equation.solver.EquationSystem;
import edu.gatech.statics.modes.equation.solver.EquationTerm.Constant;
import edu.gatech.statics.modes.equation.solver.EquationTerm.Polynomial;
import edu.gatech.statics.modes.equation.solver.EquationTerm.Symbol;
import edu.gatech.statics.modes.equation.solver.LinearEquationSystem;
import edu.gatech.statics.modes.equation.solver.NonlinearEquationSystem;
import edu.gatech.statics.objects.ConstantObject;
import edu.gatech.statics.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Calvin Ashmore
 */
public class Worksheet {

    final private Map<String, EquationMath> equations = new TreeMap<String, EquationMath>();
//    final private EquationSystem equationSystem;
    final private EquationDiagram diagram;
    private Map<Quantity, Float> solution = null;
    private boolean solved = false;

    /**
     * 
     * @param diagram 
     */
    public Worksheet(EquationDiagram diagram) {
        this.diagram = diagram;
//        equationSystem = new NonlinearEquationSystem();
    }

    /**
     * 
     * @return 
     */
    public Map<String, EquationMath> getEquations() {
        return Collections.unmodifiableMap(equations);
    }

    /**
     * 
     * @return 
     */
    public List<String> getEquationNames() {
        ArrayList<String> equationNames = new ArrayList<String>(equations.keySet());
        System.out.println("getEquationName called");
        return equationNames;
    }

    /**
     * 
     * @return 
     */
    public List<String> getEquationStateNames() {
        return new ArrayList(diagram.getCurrentState().getEquationStates().keySet());
    }

    /**
     * 
     * @param name
     * @return 
     */
    public EquationMath getMath(String name) {
        return equations.get(name);
    }

    /**
     * 
     */
    public void updateEquations() {
        //add math present in state but not the equation list
        for (String mathName : getEquationStateNames()) {
            if (equations.containsKey(mathName)) {
                continue;
            }
            EquationMathState mathState = diagram.getCurrentState().getEquationStates().get(mathName);

            if (mathState instanceof TermEquationMathState) {
                TermEquationMathState state = (TermEquationMathState) mathState;
                if (!state.isMoment()) {
                    equations.put(mathName, new EquationMathForces(mathName, diagram));
                } else if (state.isMoment()) {
                    equations.put(mathName, new EquationMathMoments(mathName, diagram));
                } else {
                    throw new IllegalArgumentException("Unknown equation math state type! " + diagram.getCurrentState().getEquationStates().get(mathName));
                }
            } else if (diagram.getCurrentState().getEquationStates().get(mathName) instanceof ArbitraryEquationMathState) {
                equations.put(mathName, new ArbitraryEquationMath(mathName, diagram));
            } else if (diagram.getCurrentState().getEquationStates().get(mathName) instanceof Moment3DEquationMathState) {

                //MomentEquationMathState state = (MomentEquationMathState) mathState;
                equations.put(mathName, new Moment3DEquationMath(mathName, diagram));

            } else {
                throw new IllegalArgumentException("Unknown equation math state type! " + diagram.getCurrentState().getEquationStates().get(mathName));
            }
        }

        //remove math not present in state but present in the equation list
        ArrayList<String> equationNames = (ArrayList<String>) getEquationNames();
        for (String names : equationNames) {
            if (!diagram.getCurrentState().getEquationStates().containsKey(names)) {
                equations.remove(names);
            }
        }
    }

    /**
     * 
     * @return 
     */
    public Map<Quantity, Float> solve() {
        if (!solved) {
            Map<String, Quantity> vectorNames = new HashMap<String, Quantity>();
            EquationSystem equationSystem = new NonlinearEquationSystem();
            prepareSystem(equationSystem, vectorNames);

            // try again with the linear system:
            // occasionally the nonlinear solver fumbles with rounding errors.
            // the linear solver may be better at solving it.
            if (!equationSystem.isSolvable()) {
                vectorNames.clear();
                EquationSystem linearEquationSystem = new LinearEquationSystem();
                try {
                    prepareSystem(linearEquationSystem, vectorNames);
                    equationSystem = linearEquationSystem;
                } catch (IllegalArgumentException ex) {
                    // do nothing...
                    // this cannot be solved linearly.
                }
            }

            if (!equationSystem.isSolvable()) {
                solution = null;
            } else {
                //solution = equationSystem.solve();
                Map<String, Float> nameSolution = equationSystem.solve();

                if (nameSolution.size() < diagram.getNumberUnknowns()) {
                    // if this is the case, then the solution is incomplete. The user may have been able
                    // to solve for one or two terms, but not all the unknowns. To prevent problems
                    // with partial solutions, we reject a solution in this case, even if it could be solved
                    return null;
                } else {
                    // if we get here, then all the unknowns are solved for.
                    solved = true;
                    solution = new HashMap<Quantity, Float>();
                    for (Map.Entry<String, Float> entry : nameSolution.entrySet()) {
                        solution.put(vectorNames.get(entry.getKey()), entry.getValue());
                    }
                }
            }
        }

        return solution;
    }

    /**
     * 
     * @param equationSystem
     * @param vectorNames
     * @throws IllegalArgumentException 
     */
    private void prepareSystem(EquationSystem equationSystem, Map<String, Quantity> vectorNames) throws IllegalArgumentException {
        equationSystem.resetTerms();
        //int numberEquations = equations.size();
        // go through each row
        int row = 0;
        for (EquationMath math : equations.values()) {
            // now go through each term in the equation for the row
            //EquationMath math = equations.get(row);
            EquationMathState mathState = math.getState();
            if (!mathState.isLocked()) {
                continue;
            }
            if (mathState instanceof TermEquationMathState) {
                for (Map.Entry<AnchoredVector, String> entry : ((TermEquationMathState) mathState).getTerms().entrySet()) {
                    AnchoredVector load = entry.getKey();
                    String coefficient = entry.getValue();
                    AffineQuantity affineCoefficient = Parser.evaluateSymbol(coefficient);
                    // work with the term's quantity
                    Quantity q = load.getVector().getQuantity();
                    if (q.isSymbol() && !q.isKnown()) {
                        // the vector represented by the term is an unknown symbol
                        //equationSystem.addTerm(row, affineCoefficient.getConstant().floatValue(), q.getSymbolName());
                        equationSystem.addTerm(row, new Symbol(affineCoefficient.getConstant().floatValue(), q.getSymbolName()));
                        vectorNames.put(q.getSymbolName(), q);
                    } else {
                        // the vector represented by this term is a constant
                        if (affineCoefficient.isSymbolic()) {
                            equationSystem.addTerm(row, new Constant(q.doubleValue() * affineCoefficient.getConstant().floatValue()));
                            equationSystem.addTerm(row, new Symbol(q.doubleValue() * affineCoefficient.getConstant().floatValue(), affineCoefficient.getSymbolName()));
                            Quantity measureQuantity = new Quantity(Unit.distance, affineCoefficient.getSymbolName());
                            vectorNames.put(measureQuantity.getSymbolName(), measureQuantity);
                        } else {
                            equationSystem.addTerm(row, new Constant(q.doubleValue() * affineCoefficient.getConstant().floatValue()));
                        }
                    }
                }
            } else if (mathState instanceof ArbitraryEquationMathState) {
                //throw new IllegalArgumentException("Arbitrary equation math states are not yet implemented in Worksheet.");
                ArbitraryEquationMathState arbitraryState = (ArbitraryEquationMathState) mathState;
                EquationNode leftSide = arbitraryState.getLeftSide();
                EquationNode rightSide = arbitraryState.getRightSide();
                // right now, equation nodes are multiplicative ONLY, so, we take advantage of this.
                // should they change, then we will need to modify this algorithm.
                Pair<Float, List<String>> leftSymbols = extractSymbolsFromEquationNode(leftSide);
                Pair<Float, List<String>> rightSymbols = extractSymbolsFromEquationNode(rightSide);
                // add both sides of the equation as polynomial terms
                equationSystem.addTerm(row, new Polynomial(leftSymbols.getLeft(), leftSymbols.getRight().toArray(new String[0])));
                equationSystem.addTerm(row, new Polynomial(-rightSymbols.getLeft(), rightSymbols.getRight().toArray(new String[0])));
                // add constant symbols to the name list
                for (String string : leftSymbols.getRight()) {
                    ConstantObject constant = Exercise.getExercise().getSymbolManager().getConstant(string);
                    if (constant != null) {
                        vectorNames.put(string, constant.getQuantity());
                    }
                }
                for (String string : rightSymbols.getRight()) {
                    ConstantObject constant = Exercise.getExercise().getSymbolManager().getConstant(string);
                    if (constant != null) {
                        vectorNames.put(string, constant.getQuantity());
                    }
                }
            } else if (mathState instanceof Moment3DEquationMathState) {
                // skip
            } else {
                throw new IllegalArgumentException("Unknown equation math state type! " + mathState + " (" + mathState.getClass() + ")");
            }
            // increment our row count.
            row++;
        }
    }

    /**
     * 
     * @param node
     * @return 
     */
    private Pair<Float, List<String>> extractSymbolsFromEquationNode(EquationNode node) {
        if (node instanceof OperatorNode) {
            OperatorNode opNode = (OperatorNode) node;
            if (opNode.getOperation() != '*') {
                throw new IllegalArgumentException("Cannot handle an operation other than multiplication!");
            }
            List<String> symbols = new ArrayList<String>();

            Pair<Float, List<String>> leftSymbols = extractSymbolsFromEquationNode(opNode.getLeftNode());
            Pair<Float, List<String>> rightSymbols = extractSymbolsFromEquationNode(opNode.getRightNode());
            symbols.addAll(leftSymbols.getRight());
            symbols.addAll(rightSymbols.getRight());

            return new Pair<Float, List<String>>(leftSymbols.getLeft() * rightSymbols.getLeft(), symbols);
        } else if (node instanceof AnchoredVectorNode) {
            AnchoredVectorNode avNode = (AnchoredVectorNode) node;
            AnchoredVector anchoredVector = avNode.getAnchoredVector();
            if (anchoredVector.isKnown()) {
                return new Pair<Float, List<String>>((float) anchoredVector.doubleValue(), Collections.<String>emptyList());
            } else {
                return new Pair<Float, List<String>>(1f, Arrays.asList(anchoredVector.getSymbolName()));
            }
        } else if (node instanceof SymbolNode) {
            SymbolNode symbolNode = (SymbolNode) node;
            String symbol = symbolNode.getSymbol();
            ConstantObject constant = Exercise.getExercise().getSymbolManager().getConstant(symbol);
            if (constant.getQuantity().isKnown()) {
                return new Pair<Float, List<String>>(constant.getQuantity().getDiagramValue().floatValue(), Collections.<String>emptyList());
            } else {
                return new Pair<Float, List<String>>(1f, Arrays.asList(constant.getQuantity().getSymbolName()));
            }
        } else {
            throw new IllegalArgumentException("Unknown node type! " + node);
        }
    }
}
