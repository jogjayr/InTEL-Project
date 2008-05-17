/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.ui;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.border.LineBorder;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.modes.equation.worksheet.EquationMathMoments;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationModePanel extends ApplicationModePanel {

    public static final String panelName = "equation";
    private Map<EquationMath, EquationUIData> uiMap = new HashMap<EquationMath, EquationModePanel.EquationUIData>();
    private BContainer equationBarContainer;
    private BContainer equationButtonContainer;
    private BContainer solutionContainer;
    private BScrollPane equationScrollPane;
    private EquationBar activeEquation;
    //private BButton momentSelectButton;
    
    private static final ColorRGBA regularBackgroundColor = ColorRGBA.black;
    private static final ColorRGBA regularBorderColor = ColorRGBA.black;
    private static final ColorRGBA activeBackgroundColor = ColorRGBA.darkGray;
    private static final ColorRGBA activeBorderColor = ColorRGBA.white;

    public void onClick(Load load) {
        if (activeEquation == null || load == null) {
            return;
        }
        activeEquation.addTerm(load.getVector());
        //activeEquation.highlightVector(load.getVector());
        for (EquationUIData data : uiMap.values()) {
            data.equationBar.highlightVector(load == null ? null : load.getVector());
        }
    }

    public void onHover(Load load) {
        //if (activeEquation == null) {
        //    return;
        //}
        //activeEquation.highlightVector(load == null ? null : load.getVector());
        for (EquationUIData data : uiMap.values()) {
            data.equationBar.highlightVector(load == null ? null : load.getVector());
        }
    }

    /**
     * This lets the equation bars know that the moment point is changed or set.
     * Right now it is called only by EquationDiagram when the diagram's moment point is set.
     * @param momentPoint
     */
    public void setMomentPoint(Point momentPoint) {
        for (EquationUIData data : uiMap.values()) {
            if (data.equationBar.getMath() instanceof EquationMathMoments) {
                ((EquationMathMoments) data.equationBar.getMath()).setObservationPoint(momentPoint.getPosition());
                data.equationBar.setMomentCenter(momentPoint);
            }
        }
    }

    public EquationBar getActiveEquation() {
        return activeEquation;
    }

    private void setActiveEquation(EquationBar bar) {
        if (activeEquation != null) {
            activeEquation.setBackground(new TintedBackground(regularBackgroundColor));
            activeEquation.setBorder(new LineBorder(regularBorderColor));
        }
        this.activeEquation = bar;
        activeEquation.setBackground(new TintedBackground(activeBackgroundColor));
        activeEquation.setBorder(new LineBorder(activeBorderColor));

        // show the select moment warning popup
        /*if (bar.getMath() instanceof EquationMathMoments) {
            EquationDiagram diagram = (EquationDiagram) getDiagram();
            if (diagram.getMomentPoint() == null) {
                ChooseMomentPopup popup = new ChooseMomentPopup(diagram);
                popup.popup(0, 0, true);
                popup.center();
            }
        }*/
    }

    @Override
    public String getPanelName() {
        return panelName;
    }

    public EquationModePanel() {
        super();

        /*momentSelectButton = new BButton("choose\nmoment\npoint", new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                selectMomentPoint();
            }
        }, "momentSelect");
        add(momentSelectButton, BorderLayout.WEST);*/

        BContainer fullEquationContainer = new BContainer(new BorderLayout());
        add(fullEquationContainer, BorderLayout.CENTER);

        GroupLayout equationLayout = GroupLayout.makeVert(GroupLayout.CENTER);
        equationLayout.setOffAxisJustification(GroupLayout.LEFT);
        equationBarContainer = new BContainer(equationLayout);

        equationScrollPane = new BScrollPane(equationBarContainer, false, true);
        equationScrollPane.setShowScrollbarAlways(false);
        fullEquationContainer.add(equationScrollPane, BorderLayout.CENTER);


        equationButtonContainer = new BContainer(GroupLayout.makeVert(GroupLayout.CENTER));
        fullEquationContainer.add(equationButtonContainer, BorderLayout.WEST);

        // the solution container is to the right of the mode panel, and 
        // will contain the solution to the equations.
        GroupLayout solutionLayout = GroupLayout.makeVert(GroupLayout.CENTER);
        solutionLayout.setOffAxisJustification(GroupLayout.LEFT);
        solutionContainer = new BContainer(solutionLayout);
        
        add(solutionContainer, BorderLayout.EAST);
        solutionContainer.setPreferredSize(200, -1);
    }

    void refreshRows() {
        equationScrollPane.layout();
    }

    private void addEquationRow(EquationMath math) {

        final EquationUIData data = new EquationUIData();
        data.equationBar = new EquationBar(math, this);

        data.checkButton = new BButton("check", new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                check(data.equationBar);
            }
        }, "check");
        data.checkButton.setStyleClass("smallcircle_button");

        data.equationBar.addListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent event) {
                setActiveEquation(data.equationBar);
            }
        });

        equationBarContainer.add(data.equationBar);
        equationButtonContainer.add(data.checkButton);

        uiMap.put(math, data);

        if (math.isLocked()) {
            data.equationBar.setLocked();
            setCheckIcon(data.equationBar);
        }

        // check our bar
        //check(data.equationBar);

        if (uiMap.size() == 1) {
            setActiveEquation(data.equationBar);
        }
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
            bar.setLocked();

            setCheckIcon(bar);
            //InterfaceRoot.getInstance().setAdvice("yay, it worked!");

            boolean allSolved = true;
            for (EquationMath math : uiMap.keySet()) {
                if (!math.isLocked()) {
                    allSolved = false;
                }
            }

            if (allSolved) {
                performSolve();
            }
        }
    }

    private void performSolve() {
        EquationDiagram diagram = (EquationDiagram) getDiagram();
        boolean firstTime = !diagram.getWorksheet().isSolved();
        Map<Vector, Float> solution = diagram.getWorksheet().solve();
        if (solution != null) {

            for (Map.Entry<Vector, Float> entry : solution.entrySet()) {
                BLabel entryLabel = new BLabel(
                        "@=b(@=#ff0000(" + entry.getKey().getSymbolName() + ")" +
                        " = " + entry.getValue() + " " + entry.getKey().getUnit().getSuffix() + ")");
                solutionContainer.add(entryLabel);
            }

            if (firstTime) {
                // this is our first time solving the system.

                diagram.performSolve(solution);
            }

        } else {
            InterfaceRoot.getInstance().setAdvice("Your system is not solvable!");
        }
    }

    /*private void selectMomentPoint() {
        PointSelector selector = new PointSelector((EquationDiagram) getDiagram());
        selector.activate();
    }*/

    private class EquationUIData {

        public EquationBar equationBar;
        public BButton checkButton;
    }

    @Override
    protected ApplicationTab createTab() {
        return new ApplicationTab("Solve");
    }

    private void clear() {
        equationBarContainer.removeAll();
        equationButtonContainer.removeAll();
        solutionContainer.removeAll();
        uiMap.clear();
    }

    @Override
    public void activate() {
        if (!uiMap.isEmpty()) {
            clear();
        }

        EquationDiagram diagram = (EquationDiagram) getDiagram();
        getTitleLabel().setText("My Diagram: " + diagram.getBodySubset());

        for (EquationMath math : diagram.getWorksheet().getEquations()) {
            addEquationRow(math);
        }

        if (diagram.getWorksheet().isSolved()) {
            performSolve();
        }
        
        Exercise.getExercise().enableTabs(diagram.getBodySubset());
        
        refreshRows();
        invalidate();
    }
}
