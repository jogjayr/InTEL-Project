/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BComponent;
import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.border.LineBorder;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.EquationState.Builder;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMath;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMath;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMath;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.ui.applicationbar.ApplicationBar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jayraj
 */
public class Equation3DModePanel extends EquationModePanel {

    //private BContainer equationBarContainer;
    //private EquationBar activeEquation;
    //private BContainer solutionContainer;
    private Map<EquationMath, EquationUIData> ui3DMap = new HashMap<EquationMath, EquationUIData>();
    
    public Equation3DModePanel() {
        super();
        System.out.println("Equation3DModePanel was created");
    }
    
    @Override
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
            } else if (activeEquation instanceof Moment3DEquationBar) {
                if (((Moment3DEquationMathState) activeEquation.getMath().getState()).getTerms().containsKey(load.getAnchoredVector())) {
                    activeEquation.focusOnTerm(load.getAnchoredVector());
                } else {
                    // otherwise, add it.
                    ((Moment3DEquationBar) activeEquation).performAdd(load.getAnchoredVector());
                }
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
        for (EquationUIData data : ui3DMap.values()) {
            data.equationBar.highlightVector(load == null ? null : load.getAnchoredVector());
        }
    }

    /**
     * Returns true if enough unknowns have been solved for.
     * @return
     */
    private boolean enoughSolved() {
        int numberSolved = 0;
        for (EquationMath math : ui3DMap.keySet()) {
            if (math.isLocked()) {
                //allSolved = false;
                numberSolved++;
            }
        }

        return numberSolved >= getDiagram().getNumberUnknowns();
    }

    /**
     * Called when the diagram state changes. This causes the EquationBars to refresh
     */
    @Override
    public void stateChanged() {
        //super.stateChanged(); As part of a terrible hack, this line has been replaced with the code in the grandparent class's stateChanged method to sidestep
        //stateChanged method of the parent class.
        //I also changed the visibility of the appbar updateUndoRedoState to public from non-mentioned
        //System.out.println("Diagram state has changed in Equation3DModePanel and ui3DMap is empty is " + ui3DMap.isEmpty());
        ApplicationBar appBar = InterfaceRoot.getInstance().getApplicationBar();
        appBar.updateUndoRedoState();

        getDiagram().getWorksheet().updateEquations();

        // make sure that uiMap is consistent with our equation states
        // ie, remove rows that are no longer present.
        // rows are added elsewhere, though. (Should that get moved here?)
        List<EquationMath> toRemove = new ArrayList<EquationMath>();
        for (EquationMath equationMath : ui3DMap.keySet()) {
            if (!getDiagram().getWorksheet().getEquationNames().contains(equationMath.getName())) {
                // the entry in uiMap is NOT present in the worksheet list of names, so mark it for removal.
                toRemove.add(equationMath);
            }
        }
        for (EquationMath equationMath : toRemove) {
            EquationUIData data = ui3DMap.get(equationMath);
            // remove data
            removeEquationData(data);
            ui3DMap.remove(equationMath);
        }

        for (EquationUIData data : ui3DMap.values()) {
            data.equationBar.stateChanged();
        }
        // see if anything in the worksheet is present that is not present in uiMap.keySet()
        // then, add new bar, and equationuidata
    }

    private void removeEquationData(EquationUIData data) {

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

    @Override
    public void onHover(Load load) {
        for (EquationUIData data : ui3DMap.values()) {
            data.equationBar.highlightVector(load == null ? null : load.getAnchoredVector());
        }
    }
    @Override
    public EquationBar getActiveEquation() {
        return activeEquation;
    }

    @Override
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
    protected void addMoment3DEquationRow(Moment3DEquationMath math) {
        

        final EquationUIData data = new EquationUIData();

        data.equationBar = new Moment3DEquationBar(math, this);
        data.checkButton = new BButton("check", new ActionListener() {
        
            public void actionPerformed(ActionEvent event) {
                check(data.equationBar); 
            }
        }, "check");

        addEquationData(math, data);
    }

    @Override
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

        ui3DMap.put(math, data);

        if (math.isLocked()) {
            data.equationBar.setLocked();
            setCheckIcon(data.equationBar);
        }

        if (ui3DMap.size() == 1) {
            setActiveEquation(data.equationBar);
        }
    }


    @Override
    protected void addTermEquationRow(TermEquationMath math) {
        final EquationUIData data = new EquationUIData();

        data.equationBar = new TermEquationBar(math, this);
        data.checkButton = new BButton("check", new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                check(data.equationBar);
            }
        }, "check");

        if(math.getState().isMoment()) {
            data.equationBar.setEnabled(false);
            data.checkButton.setEnabled(false);
            //data.addButton.setEnabled(false);
        }
        addEquationData(math, data);
    }


    private void setCheckIcon(EquationBar bar) {

        EquationUIData ui3DData = ui3DMap.get(bar.getMath());
        if(ui3DData == null)
            System.out.println("ui3DData is null");
        if(ui3DData.checkButton == null)
            System.out.println("ui3DData.checkButton is null");
        ui3DData.checkButton.setEnabled(false);

        ImageIcon icon = null;
        try {
            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/checkmark.png")));
        } catch (IOException e) {
        }

        ui3DData.checkButton.setIcon(icon);
        ui3DData.checkButton.setText("");
    }
    private void check(EquationBar bar) {
        boolean success = bar.getMath().check();
        if (success) {
            System.out.println("Equation check passed");
            getDiagram().equationSolved();

            bar.setLocked();

            setCheckIcon(bar); 
            if(bar instanceof Moment3DEquationBar)
                activateMomentTermEquations();
            performSolve(true);
        }
        else
            System.out.println("Equation check failed");
    }

    @Override
    protected void clear() {
        for (EquationUIData eqdata : ui3DMap.values()) {
            eqdata.equationBar.clear();
        }

        equationBarContainer.removeAll();
        //equationButtonContainer.removeAll();
        solutionContainer.removeAll();
        ui3DMap.clear();
    }
    @Override
    public void activate() {
        //super.activate();

        /**
         * The following two lines of code duplicate what is written in the ApplicationModePanel.activate()
         * This is because there is no way to reference that method using super without invoking
         * EquationModePanel.activate (the intermediate class which is the parent of this class)
         *
         * Similar comment has been addded to ApplicationModePanel
         *
         * Ultimate solution may be to make Equation3DModePanel a child of ApplicationModePanel instead of EquationModePanel
         *
         * Start code from ApplicationModePanel.activate()
         */
        active = true;
        stateChanged();

        /* End code from ApplicationModePanel.activate() */

        if (!ui3DMap.isEmpty()) {
            clear();
        }

        EquationDiagram diagram = (EquationDiagram) getDiagram();
        //System.out.println("The type of diagram being used is: " + diagram.getClass().getName());

        if (diagram.getBodySubset().getSpecialName() != null) {
            //getTitleLabel().setText("My Diagram: " + diagram.getBodySubset().getSpecialName());
        } else {
            //getTitleLabel().setText("My Diagram: " + diagram.getBodySubset());
        }

        diagram.getWorksheet().updateEquations();

        for (String mathName : diagram.getWorksheet().getEquationNames()) {

            EquationMath math = diagram.getWorksheet().getMath(mathName);
            if (math instanceof Moment3DEquationMath) {
                //System.out.println("MomentEquationBar created");
                addMoment3DEquationRow((Moment3DEquationMath) math);
            } else if (math instanceof TermEquationMath) {
                //System.out.println("TermEquationBar created");
                addTermEquationRow((TermEquationMath) math);
                
            } else if (math instanceof ArbitraryEquationMath) {
                addArbitraryEquationRow((ArbitraryEquationMath) math);
            } else {
                throw new IllegalArgumentException("Unknown math type: " + math);
            }
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

    private void activateMomentTermEquations() {
        for (EquationUIData data : ui3DMap.values()) {
            if(!data.equationBar.isEnabled())
            data.equationBar.setEnabled(true);
            //data.addButton.setEnabled(true);
            data.checkButton.setEnabled(true);
        }
    }

   
}
