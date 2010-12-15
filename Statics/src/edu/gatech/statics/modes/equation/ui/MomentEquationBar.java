/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.modes.equation.worksheet.EquationMathMoments;
import edu.gatech.statics.modes.equation.worksheet.MomentEquationMathState;
import java.io.IOException;

/**
 *
 * @author Jayraj
 *
 * As explained in MomentEquationMath, this class deals with the
 * Equation Bar for RxF moment equations. For the SigmaMx, SigmaMy and
 * SigmaMz equation bars you should probably look elsewhere (Probably
 * TermEquationBar)
 */
public class MomentEquationBar extends EquationBar {
    private BButton momentButton; //Present only for moment math. Pressing this sets the moment

    //private Equation3DModePanel parent;
    public MomentEquationBar(EquationMath math, Equation3DModePanel parent) {
        super(math, parent);
        this.math = math;
        super.parent = parent;
        setStyleClass("equation_bar");
        System.out.println("MomentEquationBar constructor was called");
        ImageIcon icon;

        try {
            // add sum icon
            BContainer startContainer = makeStartContainer();
            add(startContainer);

            // add = 0 icon
            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/equalsZero.png")));
            add(new BLabel(icon));

        } catch (IOException e) {
            // this is here in case there is a problem with loading the icon.
            e.printStackTrace();
        }
    }
    
    @Override
    void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void stateChanged() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void focusOnTerm(AnchoredVector load) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void setLocked() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void setUnlocked() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void highlightVector(AnchoredVector obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private BContainer makeStartContainer() throws IOException {
        BContainer startContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));

        ImageIcon icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/cross.png")));
        startContainer.add(new BLabel(icon));
        /** TO DO:
         * Handle our math differently
         */
        if (math instanceof EquationMathMoments) {
            // we do special handling for moment math
            startContainer.add(new BLabel("M["));

            Point momentPoint = ((MomentEquationMathState) math.getState()).getMomentPoint(); 
            String pointName = momentPoint == null ? "?" : momentPoint.getName();

            momentButton = new BButton(pointName, new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    PointSelector selector = new PointSelector((EquationDiagram) parent.getDiagram(), math.getState().getName());
                    selector.activate();

                    // activate the bar when the button is pressed.
                    parent.setActiveEquation(MomentEquationBar.this);
                }
            }, "momentpoint");

            startContainer.add(momentButton);
            startContainer.add(new BLabel("]"));
        } else {
            startContainer.add(new BLabel(math.getName()));
        }

        icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/equals.png")));
        startContainer.add(new BLabel(icon));

        return startContainer;
    }
    

}
