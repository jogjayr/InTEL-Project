/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.tasks;

import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.bodies.TwoForceMember;

/**
 * A task for graded problems specifically for 2FMss
 * @author Jimmy Truesdell
 */
public class Solve2FMTask extends SolveConnectorTask {

    private TwoForceMember tfm;

    /**
     * For persistence, do not use.
     * @param name
     * @deprecated
     */
    @Deprecated
    public Solve2FMTask(String name) {
        super(name);
    }

    public Solve2FMTask(String name, TwoForceMember tfm, Connector connector) {
        super(name, connector);
        this.tfm = tfm;
    }

    public TwoForceMember getMember() {
        return tfm;
    }

    @Override
    public String getDescription() {
        return "Solve the two force member " +
        //return "Solve the 2FM " +
                tfm.getConnector1().getAnchor().getLabelText() +
                tfm.getConnector2().getAnchor().getLabelText();
    }
}
