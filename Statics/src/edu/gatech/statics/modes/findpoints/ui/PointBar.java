/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.findpoints.ui;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.objects.Point;

/**
 *
 * @author Calvin
 */
public class PointBar extends BContainer {

    private Point point;

    public PointBar(Point point) {
        this.point = point;

        setLayoutManager(new BorderLayout());
        add(new BLabel("point: "+point.getName()), BorderLayout.WEST);
    }
}
