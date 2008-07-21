/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.tasks;

import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.bodies.TwoForceMember;

/**
 *
 * @author Trudetski
 */
public class Solve2FMTask extends SolveJointTask{
    TwoForceMember tfm;
    Connector joint1, joint2;
    public Solve2FMTask(TwoForceMember tfm, Connector joint1, Connector joint2) {
        super(joint1);
        this.tfm = tfm;
        this.joint1 = joint1;
        this.joint2 = joint2;
    }

    @Override
    public String getDescription() {
        return "Solve the two force member " + joint1.getAnchor().getLabelText() + joint2.getAnchor().getLabelText();
    }

    @Override
    public boolean isSatisfied() {
        if(super.isSatisfied()){
            return true;
        } else {
            return joint2.isSolved();
        }
    }
}
