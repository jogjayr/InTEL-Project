/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.ui;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.objects.Load;
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

    public void onClick(Load load) {
        if (activeEquation == null || load == null) {
            return;
        }
        activeEquation.addTerm(load.getVector());
        activeEquation.highlightVector(load.getVector());
    }

    public void onHover(Load load) {
        if (activeEquation == null) {
            return;
        }
        activeEquation.highlightVector(load == null ? null : load.getVector());
    }

    private void setActiveEquation(EquationBar bar) {
        if (activeEquation != null) {
            activeEquation.setBackground(new TintedBackground(ColorRGBA.black));
        }
        this.activeEquation = bar;
        activeEquation.setBackground(new TintedBackground(ColorRGBA.darkGray));
    }

    @Override
    public String getPanelName() {
        return panelName;
    }

    public EquationModePanel() {
        super();

        BContainer fullEquationContainer = new BContainer(new BorderLayout());
        add(fullEquationContainer, BorderLayout.CENTER);

        GroupLayout equationLayout = GroupLayout.makeVert(GroupLayout.CENTER);
        equationLayout.setOffAxisJustification(GroupLayout.LEFT);
        equationBarContainer = new BContainer(equationLayout);

        equationScrollPane = new BScrollPane(equationBarContainer, false, true);
        equationScrollPane.setShowScrollbarAlways(false);
        fullEquationContainer.add(equationScrollPane, BorderLayout.CENTER);


        equationButtonContainer = new BContainer(GroupLayout.makeVert(GroupLayout.CENTER));
        fullEquationContainer.add(equationButtonContainer, BorderLayout.EAST);

        // the solution container is to the right of the mode panel, and 
        // will contain the solution to the equations.
        solutionContainer = new BContainer(GroupLayout.makeVert(GroupLayout.CENTER));
        add(solutionContainer, BorderLayout.EAST);
        solutionContainer.setPreferredSize(200, -1);
    }

    void refreshRows() {
        //equationScrollPane.invalidate();
        equationScrollPane.layout();
    }

    private void addEquationRow(EquationMath math) {

        final EquationUIData data = new EquationUIData();
        data.equationBar = new EquationBar(math, this);
        //data.checkButton = new BButton("check");
        //ImageIcon icon = null;
        //try {
        //    icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/circle_white.png")));
        //} catch (IOException e) {
        //}
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

        if (uiMap.size() == 1) {
            setActiveEquation(data.equationBar);
        }

    //data.solutionLabel = new BLabel(_tiptext)
    }

    private void check(EquationBar bar) {
        boolean success = bar.getMath().check();
        if (success) {
            bar.setLocked();
            EquationUIData data = uiMap.get(bar.getMath());
            data.checkButton.setEnabled(false);

            ImageIcon icon = null;
            try {
                icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/checkmark.png")));
            } catch (IOException e) {
            }

            data.checkButton.setIcon(icon);
            data.checkButton.setText("");
            InterfaceRoot.getInstance().setAdvice("yay, it worked!");
        }
    }

    private class EquationUIData {

        public EquationBar equationBar;
        public BButton checkButton;
    }

    @Override
    protected ApplicationTab createTab() {
        return new ApplicationTab("Solve");
    }

    @Override
    public void activate() {
        EquationDiagram diagram = (EquationDiagram) getDiagram();
        getTitleLabel().setText("My Diagram: " + diagram.getBodySubset());

        for (EquationMath math : diagram.getWorksheet().getEquations()) {
            addEquationRow(math);
        }
    }
}
