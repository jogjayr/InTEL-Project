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
public class Manipulate02 extends ManipulatableExercise {

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
    private Point A,  B,  C;
    private Body beam1,  beam2;
    private Joint joint;

    @Override
    public void setJoint(String selectedValue) {

        ManipulatableDiagram diagram = (ManipulatableDiagram) StaticsApplication.getApp().getCurrentDiagram();

        if (joint != null) {
            diagram.remove(joint);
            beam1.removeObject(joint);
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
            joint.attachToWorld(beam1);
            diagram.add(joint);
        }

        StaticsApplication.getApp().setAdvice("Selected joint: " + selectedValue);
    }

    @Override
    public float getDrawScale() {
        //return super.getDrawScale();
        return .5f;
    }

    @Override
    public void loadExercise() {

        Schematic schematic = getSchematic();
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 1));

        float mass = 1;
        A = new Point(new Vector3f(-5, 0, 0));
        B = new Point(new Vector3f(0, 7, 0));
        C = new Point(new Vector3f(5, 0, 0));

        A.setName("A");
        B.setName("B");
        C.setName("C");

        beam1 = new Beam(A, B);
        beam1.setName("beam 1");
        beam1.getWeight().setValue(mass);
        beam1.getWeight().setValue(1);

        beam2 = new Beam(B, C);
        beam2.setName("beam 2");
        beam2.getWeight().setValue(mass);
        beam2.getWeight().setValue(1);

        beam1.createDefaultSchematicRepresentation();
        beam2.createDefaultSchematicRepresentation();

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();

        Joint jointB = new Pin2d(B);
        jointB.createDefaultSchematicRepresentation();
        jointB.attach(beam1, beam2);
        schematic.add(jointB);
        
        Joint jointC = new Pin2d(C);
        jointC.createDefaultSchematicRepresentation();
        jointC.attachToWorld(beam2);
        schematic.add(jointC);

        schematic.add(beam1);
        schematic.add(beam2);

    }
}
