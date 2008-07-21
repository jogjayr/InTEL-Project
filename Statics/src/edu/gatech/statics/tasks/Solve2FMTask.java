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
public class Solve2FMTask extends SolveConnectorTask {

    TwoForceMember tfm;

    public Solve2FMTask(TwoForceMember tfm, Connector connector) {
        super(connector);
        this.tfm = tfm;
    }

    @Override
    public String getDescription() {
        return "Solve the two force member " +
                tfm.getConnector1().getAnchor().getLabelText() +
                tfm.getConnector2().getAnchor().getLabelText();
    }
}
