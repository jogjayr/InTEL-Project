package edu.gatech.statics.modes.frame;

import edu.gatech.statics.modes.select.SelectAction;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.modes.select.SelectState;
import edu.gatech.statics.modes.select.SelectState.Builder;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Background;
import edu.gatech.statics.objects.bodies.TwoForceMember;
import edu.gatech.statics.util.SelectionFilter;

public class FrameSelectDiagram extends SelectDiagram {

    private static final SelectionFilter selectDiagramFilter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Body && !(obj instanceof Background) && !(obj instanceof TwoForceMember);
        }
    };

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
                if (!removed) {
                    builder.toggle(getClicked());
                }
                return builder.build();
            }
        };
    }
}
