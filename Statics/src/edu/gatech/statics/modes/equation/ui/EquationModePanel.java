/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.ui;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BButton;
import com.jmex.bui.BComponent;
import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.border.LineBorder;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.KeyEvent;
import com.jmex.bui.event.KeyListener;
import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.math.Quantity;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.EquationMode;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.actions.RemoveRow;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMath;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMath;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.connectors.ContactPoint;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationModePanel extends ApplicationModePanel<EquationDiagram> {

    public static final String panelName = "equation";
    protected Map<EquationMath, EquationUIData> uiMap = new HashMap<EquationMath, EquationModePanel.EquationUIData>();
    protected BContainer equationBarContainer; //Previously private
    //private BContainer equationButtonContainer;
    protected BContainer solutionContainer; //Previously private
    private BScrollPane equationScrollPane;
    protected EquationBar activeEquation; //Previously private
    //private BButton momentSelectButton;
    protected static final ColorRGBA regularBackgroundColor = new ColorRGBA(0, 69f / 255, 95f / 255, 1); //ColorRGBA.black;
    //private static final ColorRGBA regularBorderColor = ColorRGBA.darkGray;
    protected static final ColorRGBA activeBackgroundColor = ColorRGBA.darkGray;
    protected static final ColorRGBA activeBorderColor = ColorRGBA.white;

    public void onClick(Load load) {
        if (activeEquation == null || load == null) {
            return;
        }
        // add the term if the equation is not locked
        if (!activeEquation.isLocked()) {
            //if (activeEquation.getMath().getState() instanceof ArbitraryEquationMathState) {
            if (activeEquation instanceof ArbitraryEquationBar) {
                //do nothing
                //} else if (activeEquation.getMath().getState() instanceof TermEquationMathState) {
            } else if (activeEquation instanceof TermEquationBar) {
                // if the term has already been added, select it.
                if (((TermEquationMathState) activeEquation.getMath().getState()).getTerms().containsKey(load.getAnchoredVector())) {
                    activeEquation.focusOnTerm(load.getAnchoredVector());
                } else {
                    // otherwise, add it.
                    ((TermEquationBar) activeEquation).performAdd(load.getAnchoredVector());
                }

            } else {
                // ??? illegal state
                throw new IllegalStateException("we have invalid math: ");
            }
        }

        // highlight the load in the equations
        for (EquationUIData data : uiMap.values()) {
            data.equationBar.highlightVector(load == null ? null : load.getAnchoredVector());
        }
    }

    public void onHover(Load load) {
        for (EquationUIData data : uiMap.values()) {
            data.equationBar.highlightVector(load == null ? null : load.getAnchoredVector());
        }
    }

    /**
     * Called when the diagram state changes. This causes the EquationBars to refresh
     */
    @Override
    public void stateChanged() {
        super.stateChanged();
        getDiagram().getWorksheet().updateEquations();

        // make sure that uiMap is consistent with our equation states
        // ie, remove rows that are no longer present.
        // rows are added elsewhere, though. (Should that get moved here?)
        List<EquationMath> toRemove = new ArrayList<EquationMath>();
        ArrayList<String> equationNames = (ArrayList<String>) getDiagram().getWorksheet().getEquationNames();
        for (EquationMath equationMath : uiMap.keySet()) {
            if (!equationNames.contains(equationMath.getName())) {
                // the entry in uiMap is NOT present in the worksheet list of names, so mark it for removal.
                toRemove.add(equationMath);
            }
        }
        for (EquationMath equationMath : toRemove) {
            EquationUIData data = uiMap.get(equationMath);
            // remove data
            removeEquationData(data);
            uiMap.remove(equationMath);
        }

        for (EquationUIData data : uiMap.values()) {
            data.equationBar.stateChanged();
        }
        // see if anything in the worksheet is present that is not present in uiMap.keySet()
        // then, add new bar, and equationuidata
    }

    public EquationBar getActiveEquation() {
        return activeEquation;
    }

    void setActiveEquation(EquationBar bar) {
        if (activeEquation != null) {
            activeEquation.setBackground(new TintedBackground(regularBackgroundColor));
            //activeEquation.setBorder(new LineBorder(regularBorderColor));
            activeEquation.setBorder(null);
        }
        this.activeEquation = bar;
        activeEquation.setBackground(new TintedBackground(activeBackgroundColor));
        activeEquation.setBorder(new LineBorder(activeBorderColor));
    }

    public EquationModePanel() {
        super();
        BContainer fullEquationContainer = new BContainer(new BorderLayout());
        //add(fullEquationContainer, BorderLayout.CENTER);
        //System.out.println("EquationModePanel was created");
        GroupLayout equationLayout = GroupLayout.makeVert(GroupLayout.CENTER);
        equationLayout.setOffAxisJustification(GroupLayout.LEFT);
        equationBarContainer = new BContainer(equationLayout);

        equationScrollPane = new BScrollPane(equationBarContainer, true, true);
        equationScrollPane.setShowScrollbarAlways(false);
        //equationScrollPane.setSize(equationScrollPane.getWidth(), 9000);
        fullEquationContainer.add(equationScrollPane, BorderLayout.CENTER);


//        equationButtonContainer = new BContainer(GroupLayout.makeVert(GroupLayout.CENTER));
//        fullEquationContainer.add(equationButtonContainer, BorderLayout.WEST);

        // the solution container is to the right of the mode panel, and
        // will contain the solution to the equations.
        GroupLayout solutionLayout = GroupLayout.makeVert(GroupLayout.CENTER);
        solutionLayout.setOffAxisJustification(GroupLayout.LEFT);
        BScrollPane newPane = new BScrollPane(fullEquationContainer, true, false);
        newPane.setShowScrollbarAlways(false);
        add(newPane, BorderLayout.CENTER);

        solutionContainer = new BContainer(solutionLayout);
        solutionContainer.setPreferredSize(200, -1);
        add(solutionContainer, BorderLayout.EAST);
    }

    void refreshRows() {
        equationScrollPane.layout();
    }

    protected void removeEquationData(EquationUIData data) {

        // look through the equation bar container for the container that has our
        // check button and equation bar.
        BContainer toRemove = null;
        for (int i = 0; i < equationBarContainer.getComponentCount(); i++) {
            BComponent component = equationBarContainer.getComponent(i);
            if (component instanceof BContainer) {
                // should always be an instance of BContainer, but put this check in just for safety sake
                // this should be the barAndButtonContainer that is used below.
                BContainer container = (BContainer) component;
                // go through children, because order may be unclear
                for (int j = 0; j < container.getComponentCount(); j++) {
                    if (container.getComponent(j) == data.checkButton) {
                        // found match
                        toRemove = container;
                    }
                }
            }
        }

        if (toRemove != null) {
            equationBarContainer.remove(toRemove);
        }
    }

    protected void addEquationData(EquationMath math, final EquationUIData data) {

        //data.checkButton.setStyleClass("smallcircle_button");

        data.equationBar.addListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent event) {
                setActiveEquation(data.equationBar);
            }
        });

        //equationBarContainer.add(data.equationBar);
        //equationButtonContainer.add(data.checkButton);

        BContainer barAndButtonContainer = new BContainer(new BorderLayout(5, 0));
        barAndButtonContainer.add(data.checkButton, BorderLayout.WEST);
        barAndButtonContainer.add(data.equationBar, BorderLayout.CENTER);
        equationBarContainer.add(barAndButtonContainer);

        uiMap.put(math, data);

        if (math.isLocked()) {
            data.equationBar.setLocked();
            setCheckIcon(data.equationBar);
        }

        if (uiMap.size() == 1) {
            setActiveEquation(data.equationBar);
        }
    }

    protected void addTermEquationRow(TermEquationMath math) {
        final EquationUIData data = new EquationUIData();

        data.equationBar = new TermEquationBar(math, this);

        StaticsApplication.logger.info("Equation bar created" + math.getName());
        data.checkButton = new BButton("check", new ActionListener() {
        
            public void actionPerformed(ActionEvent event) {
                check(data.equationBar);
            }
        }, "check");

        addEquationData(math, data);
    }

    protected void addArbitraryEquationRow(ArbitraryEquationMath math) {
        final EquationUIData data = new EquationUIData();

        data.equationBar = new ArbitraryEquationBar(math, this);
        data.checkButton = new BButton("check", new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                check(data.equationBar);
            }
        }, "check");

        // allow arbitrary rows to be deletable
        // key listener doesn't seem to work. Try adding a button instead?
        data.equationBar.addListener(new KeyListener() {

            public void keyPressed(KeyEvent event) {
//                System.out.println("*** Key pressed " + event);
                if ((event.getKeyCode() == 211 /*java.awt.event.KeyEvent.VK_DELETE*/ || event.getKeyCode() == 14 /*java.awt.event.KeyEvent.VK_BACK_SPACE*/)) {
                    RemoveRow removeRowAction = new RemoveRow(data.equationBar.getMath().getName());
                    getDiagram().performAction(removeRowAction);
                }
            }

            public void keyReleased(KeyEvent event) {
            }
        });

        addEquationData(math, data);
    }

    protected void addRowCreator() {
        final EquationUIData data = new EquationUIData();
        data.addButton = new BButton("Add new equation", new ActionListener() {

            //Random rand = new Random();
            public void actionPerformed(ActionEvent event) {

                // TODO: Revise this so that it uses Actions rather than working between the
                // state, math, and the worksheet

                getDiagram().getWorksheet().updateEquations();
                Map<String, EquationMath> equations = new HashMap<String, EquationMath>();

                ArbitraryEquationMath math = new ArbitraryEquationMath("arbitraryEquation# " + UUID.randomUUID(), getDiagram());
                equations.putAll(getDiagram().getWorksheet().getEquations());
                equations.put(math.getName(), math);

                EquationState.Builder builder = new EquationState.Builder(equations);
                getDiagram().pushState(builder.build());

                getDiagram().getWorksheet().updateEquations();
                equationBarContainer.remove(data.addButton);

                addArbitraryEquationRow((ArbitraryEquationMath) getDiagram().getWorksheet().getMath(math.getName()));
                addRowCreator();
                stateChanged();
                performSolve(false);

                refreshRows();
                invalidate();
            }
        }, "add");

        equationBarContainer.add(data.addButton);
    }

    private void setCheckIcon(EquationBar bar) {

        EquationUIData data = uiMap.get(bar.getMath());
        data.checkButton.setEnabled(false);

        ImageIcon icon = null;
        try {
            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/checkmark.png")));
        } catch (IOException e) {
        }

        data.checkButton.setIcon(icon);
        data.checkButton.setText("");
    }

    private void check(EquationBar bar) {
        boolean success = bar.getMath().check();
        if (success) {
            getDiagram().equationSolved();

            bar.setLocked();

            setCheckIcon(bar);
            //InterfaceRoot.getInstance().setAdvice("yay, it worked!");

            performSolve(true);
            //boolean allSolved = true;
//            int numberSolved = 0;
//            for (EquationMath math : uiMap.keySet()) {
//                if (math.isLocked()) {
//                    //allSolved = false;
//                    numberSolved++;
//                }
//            }
//
//            if (numberSolved >= getDiagram().getNumberUnknowns()) {
//                //if (allSolved) {
//                performSolve(true);
//            }
        }
    }

    /**
     * Returns true if enough unknowns have been solved for.
     * @return
     */
    private boolean enoughSolved() {
        int numberSolved = 0;
        for (EquationMath math : uiMap.keySet()) {
            if (math.isLocked()) {
                //allSolved = false;
                numberSolved++;
            }
        }

        return numberSolved >= getDiagram().getNumberUnknowns();
    }

    protected void performSolve(boolean updateDiagram) {

        if (!enoughSolved()) {
            return;
        }

        EquationDiagram diagram = (EquationDiagram) getDiagram();
        //boolean firstTime = !diagram.getCurrentState().isLocked();
        //!diagram.getWorksheet().isSolved();
        StaticsApplication.logger.info("Attempting to solve the equations...");
        Map<Quantity, Float> solution = diagram.getWorksheet().solve();
        if (solution != null) {

            // attempt to update the diagram if the flag is passed
            if (updateDiagram || !diagram.isLocked()) {
                diagram.performSolve(solution);
            }

            solutionContainer.removeAll();
            for (Map.Entry<Quantity, Float> entry : solution.entrySet()) {
                Quantity q = entry.getKey();
                q = new Quantity(q);
                q.setDiagramValue(BigDecimal.valueOf(entry.getValue()));

                BLabel entryLabel = new BLabel(
                        "@=b(@=#ff0000(" + q.getSymbolName() + ")"
                        + " = " + q.getDiagramValue() + //entry.getValue() +
                        " " + q.getUnit().getSuffix() + ")");
                solutionContainer.add(entryLabel);
            }

        }
//        else {
//            boolean allSolved = true;
//            for (EquationMath math : uiMap.keySet()) {
//                if (!math.isLocked()) {
//                    allSolved = false;
//                }
//            }
//            if (allSolved) {
//                InterfaceRoot.getInstance().setStaticsFeedback("There is nothing more to solve");
//            }
//        }

        // do a little check here for submission.
        if (solution == null) {
            StaticsApplication.logger.info("system solve: no result (incomplete or no solution)");
        } else {
            StaticsApplication.logger.info("system solve: PASSED!");
            //StaticsApplication.getApp().setStaticsFeedbackKey("equation_system_solved");
            if (Exercise.getExercise().isExerciseFinished()) {
                StaticsApplication.getApp().setStaticsFeedbackKey("equation_system_solved_done");
            } else {
                StaticsApplication.getApp().setStaticsFeedbackKey("equation_system_solved_not_done");
            }
        }
    }

    /**
     * Only add the row creator if there are contact points in this diagram.
     * @return
     */
    protected boolean shouldAddRowCreator() {
        for (SimulationObject obj : getDiagram().allObjects()) {
            if (obj instanceof ContactPoint) {
                return true;
            }
        }
        return false;
    }

    protected class EquationUIData {

        public EquationBar equationBar;
        public BButton checkButton;
        public BButton addButton;
    }

//    @Override
//    protected ApplicationTab createTab() {
//        return new ApplicationTab("Solve");
//    }
    protected void clear() {
        for (EquationUIData eqdata : uiMap.values()) {
            eqdata.equationBar.clear();
        }

        equationBarContainer.removeAll();
        //equationButtonContainer.removeAll();
        solutionContainer.removeAll();
        uiMap.clear();
    }

    // called during activate()
    protected void setupEquationMath(String mathName) {
        EquationDiagram diagram = (EquationDiagram) getDiagram();
        EquationMath math = diagram.getWorksheet().getMath(mathName);
        if (math instanceof TermEquationMath) {
            addTermEquationRow((TermEquationMath) math);
            //System.out.println("TermEquationRow added for equation: " + math.getName());
        } else if (math instanceof ArbitraryEquationMath) {
            addArbitraryEquationRow((ArbitraryEquationMath) math);
            //System.out.println("ArbitraryEquationRow added for equation: " + math.getName());
        } else {
            throw new IllegalArgumentException("Unknown math type: " + math);
        }
    }

    @Override
    public void activate() {
        super.activate();

        if (!uiMap.isEmpty()) {
            clear();
        }

        EquationDiagram diagram = (EquationDiagram) getDiagram();

//        if (diagram.getBodySubset().getSpecialName() != null) {
//            //getTitleLabel().setText("My Diagram: " + diagram.getBodySubset().getSpecialName());
//        } else {
//            //getTitleLabel().setText("My Diagram: " + diagram.getBodySubset());
//        }

        diagram.getWorksheet().updateEquations();
        ArrayList<String> equationNames = (ArrayList<String>) diagram.getWorksheet().getEquationNames();
        for (String mathName : equationNames) {

            setupEquationMath(mathName);
        }

        // only add the button to create extra rows if the diagram has friction in it.
        if (shouldAddRowCreator()) {
            addRowCreator();
        }

        stateChanged();
        performSolve(false);

        refreshRows();
        invalidate();
    }

    @Override
    public DiagramType getDiagramType() {
        return EquationMode.instance.getDiagramType();
    }
}
