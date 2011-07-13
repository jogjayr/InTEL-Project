/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.coordinates;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.layout.AbsoluteLayout;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.util.Point;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.representations.CurveUtil;

/**
 * A robust coordinate system window that shows axes in 3D.
 * This may still be used in 2D problems, where we might want to look around a bit.
 * @author Calvin Ashmore
 */
public class CoordinateSystem3DWindow extends CoordinateSystemWindow {

    private MainComponent mainComponent;
    private static final int WINDOW_SIZE = 100;
    /**
     * Constructor. 
     */
    public CoordinateSystem3DWindow() {

        add(mainComponent = new MainComponent(), BorderLayout.CENTER);
        mainComponent.setPreferredSize(WINDOW_SIZE, WINDOW_SIZE);
    }
    /**
     * 
     */
    private class MainComponent extends BContainer {

        private BLabel yLabel;
        private BLabel xLabel;
        /**
         * Constructor
         */
        public MainComponent() {
            setLayoutManager(new AbsoluteLayout());
            xLabel = new BLabel("x");
            yLabel = new BLabel("y");

            add(xLabel, new Point(0, 0));
            add(yLabel, new Point(0, 0));
        }
        /**
         * Renders the axes
         * @param renderer 
         */
        @Override
        public void render(Renderer renderer) {
            super.render(renderer);
            Camera camera = StaticsApplication.getApp().getCamera();

            Vector3f center = camera.getWorldCoordinates(new Vector2f(renderer.getWidth()/2, renderer.getHeight()/2), .5f);

            Vector3f xformZero = camera.getScreenCoordinates(Vector3f.ZERO.add(center));
            Vector3f xformOne = camera.getScreenCoordinates(new Vector3f(1, 1, 0)).subtract(xformZero);
            Vector3f xformX = camera.getScreenCoordinates(Vector3f.UNIT_X.add(center)).subtract(xformZero);
            Vector3f xformY = camera.getScreenCoordinates(Vector3f.UNIT_Y.add(center)).subtract(xformZero);
            Vector3f xformZ = camera.getScreenCoordinates(Vector3f.UNIT_Z.add(center)).subtract(xformZero);
//            Vector3f xformX = camera.getLeft();
//            Vector3f xformY = camera.getUp();
//            Vector3f xformZ = camera.getDirection();
//            Vector3f xformOne = xformX.add(xformY);

            float normalLength = xformOne.length();

            if (normalLength == 0) {
                return;
            }

//            Vector3f direction = camera.getDirection();
//            Vector3f left = camera.getLeft();
//            Vector3f up = camera.getUp();

            int displayLength = 60;

            Vector3f xformXScaled = xformX.mult(displayLength / normalLength);
            Vector3f xformYScaled = xformY.mult(displayLength / normalLength);
            Vector3f xformZScaled = xformZ.mult(displayLength / normalLength);

            float xMin = Math.min(Math.min(xformXScaled.x, xformYScaled.x), 0);
            float yMin = Math.min(Math.min(xformXScaled.y, xformYScaled.y), 0);
            float xMax = Math.max(Math.max(xformXScaled.x, xformYScaled.x), 0);
            float yMax = Math.max(Math.max(xformXScaled.y, xformYScaled.y), 0);

            float xSize = xMax - xMin;
            float ySize = yMax - yMin;

            float xOffset = WINDOW_SIZE / 2 - xMin - xSize / 2;
            float yOffset = WINDOW_SIZE / 2 - yMin - ySize / 2;

            Vector3f root = new Vector3f(xOffset, yOffset, 0);

            xformXScaled.x += xOffset;
            xformXScaled.y += yOffset;
            xformYScaled.x += xOffset;
            xformYScaled.y += yOffset;

            xLabel.setLocation((int) xformXScaled.x, (int) xformXScaled.y);
            yLabel.setLocation((int) xformYScaled.x, (int) xformYScaled.y);

            CurveUtil.renderCircle(renderer, ColorRGBA.green, root, 50, Vector3f.UNIT_Z);
            CurveUtil.renderLine(renderer, ColorRGBA.blue, root, xformXScaled);
            CurveUtil.renderLine(renderer, ColorRGBA.blue, root, xformYScaled);
            //CurveUtil.renderCircle(renderer, ColorRGBA.green, Vector3f.ZERO, 50, direction);
        }
    }
}
