package edu.gatech.statics.modes.frame;

import edu.gatech.statics.modes.select.SelectAction;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.modes.select.SelectState;
import edu.gatech.statics.modes.select.SelectState.Builder;
import edu.gatech.statics.modes.truss.zfm.PotentialZFM;
import edu.gatech.statics.modes.truss.zfm.ZeroForceMember;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Background;
import edu.gatech.statics.objects.bodies.TwoForceMember;
import edu.gatech.statics.util.SelectionFilter;
import java.util.ArrayList;
import java.util.List;

public class FrameSelectDiagram extends SelectDiagram {

    private static final SelectionFilter selectDiagramFilter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Body && !(obj instanceof Background) && !(obj instanceof TwoForceMember);
        }
    };

    @Override
    protected List<SimulationObject> getBaseObjects() {
        //return super.getBaseObjects();
        List<SimulationObject> objects = new ArrayList<SimulationObject>();
        for (SimulationObject obj : super.getBaseObjects()) {
            if (obj instanceof PotentialZFM) {
                // discard
            } else {
                objects.add(obj);
            }
        }
        return objects;
    }

    @Override
    public void update() {

        for (SimulationObject obj : super.getBaseObjects()) {
            if (obj instanceof ZeroForceMember) {
                //System.out.println("************* " + obj + " " + obj.getClass());
                obj.setDisplayGrayed(true);
            }
        }

        super.update();
    }

    public FrameSelectDiagram() {
        super();
    }

    @Override
    public SelectionFilter getSelectionFilter() {
        return selectDiagramFilter;
    }

    /**
     * Returns a special select action for frame problems.
     * This prevents the user from selecting multiple bodies. If the are
     * to select multiple bodies, we force them to use the "Select All" button.
     */
    @Override
    protected SelectAction createSelectAction(SimulationObject obj) {
        return new SelectAction(obj) {

            @Override
            public SelectState performAction(SelectState oldState) {
                Builder builder = oldState.getBuilder();
                boolean removed = builder.getCurrentlySelected().remove(getClicked());
                builder.clear();
                if (!removed && getClicked() != null) {
                    builder.toggle(getClicked());
                }
                return builder.build();
            }
        };
    }
}
