/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.findpoints.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BTextField;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.modes.findpoints.FindPointsState;
import edu.gatech.statics.modes.findpoints.actions.SetCoordinateAction;
import edu.gatech.statics.modes.findpoints.actions.SetCoordinateAction.Coord;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.ui.components.NextButton;

/**
 *
 * @author Calvin
 */
public class PointBar extends BContainer {

    private Point point;
    private BTextField xValue, yValue, zValue;
    private BButton checkButton;
    private FindPointsModePanel modePanel;

    public PointBar(final FindPointsModePanel modePanel, final Point point) {
        this.point = point;
        this.modePanel = modePanel;

        setLayoutManager(GroupLayout.makeHoriz(GroupLayout.LEFT));
//        add(new BLabel("point: "+point.getName()), BorderLayout.WEST);

        add(new BLabel(point.getName()));

        add(new BLabel("x: "));
        add(xValue = new CoordTextField(Coord.x));
        add(new BLabel("y: "));
        add(yValue = new CoordTextField(Coord.y));
        add(new BLabel("z: "));
        add(zValue = new CoordTextField(Coord.z));

        FindPointsState state = modePanel.getDiagram().getCurrentState();

        xValue.setText(state.getX(point));
        yValue.setText(state.getY(point));
        zValue.setText(state.getZ(point));

        xValue.setPreferredWidth(100);
        yValue.setPreferredWidth(100);
        zValue.setPreferredWidth(100);

        xValue.setStyleClass("textfield_appbar");
        yValue.setStyleClass("textfield_appbar");
        zValue.setStyleClass("textfield_appbar");

        add(checkButton = new NextButton("Check", new ActionListener() {

            public void actionPerformed(ActionEvent event) {
//                onCheck();
                if(modePanel.getDiagram().checkPoint(point, xValue.getText(), yValue.getText(), zValue.getText()))
                    lockBar();
            }
        }, "check"));
        checkButton.setStyleClass("");

        if (state.isLocked(point)) {
            lockBar();
        }
    }

    private void lockBar() {

        xValue.setEnabled(false);
        yValue.setEnabled(false);
        zValue.setEnabled(false);
        checkButton.setEnabled(false);
    }

    private class CoordTextField extends BTextField {

        SetCoordinateAction.Coord coord;

        public CoordTextField(Coord coord) {
            this.coord = coord;
        }

        @Override
        protected void lostFocus() {
            super.lostFocus();
            SetCoordinateAction action = new SetCoordinateAction(point, coord, getText());

            //DIAGRAM
            modePanel.getDiagram().performAction(action);
        }
    }
}
