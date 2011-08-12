/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulatableexercises;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.manipulatable.ManipulatableDiagram;
import edu.gatech.statics.manipulatable.ManipulatableExercise;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.UnitUtils;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Joint;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.joints.Fix2d;
import edu.gatech.statics.objects.joints.Pin2d;
import edu.gatech.statics.objects.joints.Roller2d;
import edu.gatech.statics.objects.representations.ImageRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Manipulate01 extends ManipulatableExercise {

    @Override
    public void initExercise() {
        setName("What is the joint at A?");

        setDescription("The tree is being blown by the wind with the force shown." +
                "What type of connection does the tree have with the ground at point A?");
        Unit.setUtils(new UnitUtils() {

            @Override
            public String getSuffix(Unit unit) {
                switch (unit) {
                    case angle:
                        return "°";
                    case distance:
                        return " m";
                    case force:
                        return " N";
                    case moment:
                        return " N*m";
                    case none:
                        return "";
                    default:
                        throw new IllegalArgumentException("Unrecognized unit: " + unit);
                }
            }
        });
    }
    private Point A,  B,  C, G;
    private Body tree;
    private Joint joint;

    @Override
    public void setJoint(String selectedValue) {

        ManipulatableDiagram diagram = (ManipulatableDiagram) StaticsApplication.getApp().getCurrentDiagram();

        if (joint != null) {
            diagram.remove(joint);
            tree.removeObject(joint);
            joint = null;
        }

        if (selectedValue.equals("pin")) {
            joint = new Pin2d(A);
        } else if (selectedValue.equals("fix")) {
            joint = new Fix2d(A);
        } else if (selectedValue.equals("roller")) {
            joint = new Roller2d(A);
        }

        if (joint != null) {
            joint.createDefaultSchematicRepresentation();
            joint.attachToWorld(tree);
            diagram.add(joint);
        }

        StaticsApplication.getApp().setAdvice("Selected joint: " + selectedValue);
    }

    @Override
    public float getDrawScale() {
        //return super.getDrawScale();
        return 1f/10;
    }

    @Override
    public void loadExercise() {

        Schematic schematic = getSchematic();
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 1));

        Vector3f base = new Vector3f(0, -0, 0);
        float length = 1;
        float angle = (float)Math.PI/3;
        float forceApplication = .8f;
        float mass = 1;
        Vector3f forceVector = new Vector3f(10f, 0, 0);
        //Vector3f forceVector = new Vector3f(0, 9.8f, 0);
        
        Vector3f rodDirection = new Vector3f((float)Math.cos(angle), (float)Math.sin(angle), 0);
        
        A = new Point(base);
        B = new Point(base.add(rodDirection.mult(forceApplication)));
        G = new Point(base.add(rodDirection.mult(length/2)));
        C = new Point(base.add(rodDirection.mult(length)));
        A.setName("A");
        G.setName("G");

        tree = new Beam(A, C);
        tree.setName("tree");
        tree.getWeight().setValue(mass);
        tree.setCenterOfMassPoint(G);
        // I am aware of the contradiction in units here.
        // this needs to be fixed.
        
        tree.getWeight().setValue(1);
        //tree.setCenterOfMassPoint(new Point(new Vector3f()));
        
        ImageRepresentation imageRep = new ImageRepresentation(tree, loadTexture("manipulatableexercises/tree.png"));
        imageRep.setScale(1.4f, 1.4f);
        imageRep.setTranslation(-.025f, .05f, 0);
        
        Matrix3f quadRotation = new Matrix3f();
        quadRotation.fromStartEndVectors(Vector3f.UNIT_Y, Vector3f.UNIT_Z);
        
        //Matrix3f tilt = new Matrix3f();
        //tilt.fromAngleAxis((float)Math.PI/30, Vector3f.UNIT_Z);
        
        //quadRotation.multLocal(tilt);
        
        imageRep.getQuad().setLocalRotation(quadRotation);
        imageRep.setSynchronizeRotation(true);
        tree.addRepresentation(imageRep);

        tree.addObject(B);
        tree.createDefaultSchematicRepresentation();

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();

        Force f = new Force(B, forceVector);
        f.createDefaultSchematicRepresentation();
        tree.addObject(f);

        schematic.add(tree);

    }
}
