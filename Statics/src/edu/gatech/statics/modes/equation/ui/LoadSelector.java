package edu.gatech.statics.modes.equation.ui;

import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.arbitrary.AnchoredVectorNode;
import edu.gatech.statics.modes.equation.arbitrary.ChangeArbitraryNode;
import edu.gatech.statics.modes.equation.arbitrary.EquationNode;
import edu.gatech.statics.modes.equation.arbitrary.SymbolNode;
import edu.gatech.statics.objects.ConstantObject;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.manipulators.Tool;
import edu.gatech.statics.util.SelectionFilter;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class LoadSelector extends Tool {

    private EquationBar activeEquation;
    private EquationNode toReplace;

    /** Creates a new instance of LoadSelector */
    public LoadSelector(EquationBar activeEquation, EquationNode toReplace) {
        this.activeEquation = activeEquation;
        this.toReplace = toReplace;
    }
    private static final SelectionFilter filter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Load || obj instanceof ConstantObject;
        }
    };

    @Override
    public SelectionFilter getSelectionFilter() {
        return filter;
    }

    @Override
    protected void onActivate() {
        StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_loadSelect");
    }

    @Override
    public void onClick(SimulationObject obj) {

        if (obj == null || !(obj instanceof Load || obj instanceof ConstantObject)) {
            return;
        }

        Logger.getLogger("Statics").info("Selected... " + obj);

        // when we have selected the object, attempt to add it
        EquationNode toReplaceWith;
        if (obj instanceof Load) {
            toReplaceWith = new AnchoredVectorNode(toReplace.getParent(), ((Load) obj).getAnchoredVector());
        } else {
            toReplaceWith = new SymbolNode(toReplace.getParent(), ((ConstantObject) obj).getQuantity().getSymbolName());
        }
        
        ChangeArbitraryNode action = new ChangeArbitraryNode(toReplace, activeEquation.getMath().getName(), toReplaceWith);
        EquationDiagram diagram = (EquationDiagram) StaticsApplication.getApp().getCurrentDiagram();
        diagram.performAction(action);

        finish();
    }

    @Override
    protected void onCancel() {
    }

    @Override
    protected void onFinish() {
    }
}
