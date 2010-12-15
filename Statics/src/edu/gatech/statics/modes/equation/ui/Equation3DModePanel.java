/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BImage;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.icon.ImageIcon;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMath;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.modes.equation.worksheet.MomentEquationMath;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMath;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jayraj
 */
public class Equation3DModePanel extends EquationModePanel {

    public Equation3DModePanel() {
        super();
        System.out.println("Equation3DModePanel was created");
    }

    private Map<EquationMath, EquationUIData> ui3DMap = new HashMap<EquationMath, EquationUIData>();

    protected void addMoment3DEquationRow(MomentEquationMath math) {
        //System.out.println("Here we would create the new equation row that has R x F");

        final EquationUIData data = new EquationUIData();

        data.equationBar = new MomentEquationBar(math, this);
        data.checkButton = new BButton("check", new ActionListener() {
        
            public void actionPerformed(ActionEvent event) {
                check(data.equationBar);
            }
        }, "check");

        addEquationData(math, data);
    }
    private void addEquationData(EquationMath math, final EquationUIData data) {

        //data.checkButton.setStyleClass("smallcircle_button");

        data.equationBar.addListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent event) {
                setActiveEquation(data.equationBar);
            }
        });
    }
    private void setCheckIcon(MomentEquationBar bar) {

        EquationUIData ui3DData = ui3DMap.get(bar.getMath());
        ui3DData.checkButton.setEnabled(false);

        ImageIcon icon = null;
        try {
            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/checkmark.png")));
        } catch (IOException e) {
        }

        ui3DData.checkButton.setIcon(icon);
        ui3DData.checkButton.setText("");
    }
    private void check(MomentEquationBar bar) {
        boolean success = bar.getMath().check();
        if (success) {
            getDiagram().equationSolved();

            bar.setLocked();

            setCheckIcon(bar);
          

            performSolve(true);
        }
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
        System.out.println("The type of diagram being used is: " + diagram.getClass().getName());

        if (diagram.getBodySubset().getSpecialName() != null) {
            //getTitleLabel().setText("My Diagram: " + diagram.getBodySubset().getSpecialName());
        } else {
            //getTitleLabel().setText("My Diagram: " + diagram.getBodySubset());
        }

        diagram.getWorksheet().updateEquations();

        for (String mathName : diagram.getWorksheet().getEquationNames()) {

            EquationMath math = diagram.getWorksheet().getMath(mathName);
            if (math instanceof MomentEquationMath) {
                System.out.println("MomentEquationBar created");
                addMoment3DEquationRow((MomentEquationMath) math);
            } else if (math instanceof TermEquationMath) {
                System.out.println("TermEquationBar created");
                addTermEquationRow((TermEquationMath) math);
                //System.out.println("TermEquationRow added for equation: " + math.getName());
            } else if (math instanceof ArbitraryEquationMath) {
                addArbitraryEquationRow((ArbitraryEquationMath) math);
                //System.out.println("ArbitraryEquationRow added for equation: " + math.getName());
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

    private class EquationUIData {

        public MomentEquationBar equationBar;
        public BButton checkButton;
        public BButton addButton;
    }
}
