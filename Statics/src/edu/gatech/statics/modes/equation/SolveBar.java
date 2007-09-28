/*
 * SolveBar.java
 *
 * Created on August 1, 2007, 4:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.application.ui.Toolbar;
import edu.gatech.statics.modes.equation.solver.EquationSystem;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class SolveBar extends Toolbar {
    
    private Map<String, BLabel> unknownMap = new HashMap();
    private Map<String, Vector> unknownVMap = new HashMap();
    private List<String> unknownList = new ArrayList();
    private EquationSystem system;
    private EquationWorld world;
    
    /** Creates a new instance of SolveBar */
    public SolveBar(EquationWorld world, final EquationInterface iface) {
        super(GroupLayout.makeHoriz(GroupLayout.LEFT));
        this.world = world;
        
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(event.getAction().equals("return")) {
                    iface.setPalette(null);
                }
                if(event.getAction().equals("solve")) {
                    solve();
                }
            }
        };
        
        collectUnknowns(world.sumFx);
        collectUnknowns(world.sumFy);
        collectUnknowns(world.sumMp);
        processEquations();
        
        BButton backButton = new BButton("Return",listener,"return");
        BButton solveButton = new BButton("Solve!",listener,"solve");
        if(!isSolvable()) {
            solveButton.setEnabled(false);
            StaticsApplication.getApp().setAdvice(
                    java.util.ResourceBundle.getBundle("rsrc/Strings").getString("equation_warning_unsolvable"));
        }
        add(backButton);
        add(solveButton);
        
        GroupLayout systemLayout = GroupLayout.makeVert(GroupLayout.TOP);
        GroupLayout solveLayout = GroupLayout.makeVert(GroupLayout.TOP);
        systemLayout.setOffAxisJustification(GroupLayout.LEFT);
        solveLayout.setOffAxisJustification(GroupLayout.LEFT);
        
        BContainer systemContainer = new BContainer(systemLayout);
        BContainer solveContainer = new BContainer(solveLayout);
        
        add(systemContainer);
        add(solveContainer);
        
        systemContainer.add(fillSystemMath(world.sumFx));
        systemContainer.add(fillSystemMath(world.sumFy));
        systemContainer.add(fillSystemMath(world.sumMp));
        
        for(String unknown : unknownMap.keySet()) {
            BContainer unknownRow = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
            unknownRow.add(new BLabel("@=b#0000FF("+unknown + ") = "));
            unknownRow.add(unknownMap.get(unknown));
            solveContainer.add(unknownRow);
        }
    }
    
    private void collectUnknowns(EquationMath math) {
        
        for(EquationMath.Term term : math.allTerms()) {
            Vector vector = term.getVector();
            if(vector.isSymbol()) {
                String symbol = vector.getName();
                
                if(!unknownMap.containsKey(symbol)) {
                    unknownMap.put(symbol, new BLabel("???"));
                    unknownList.add(symbol);
                    unknownVMap.put(symbol, vector);
                }
            }
        }
    }
    
    private BContainer fillSystemMath(EquationMath math) {
        BContainer container = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
        
        boolean first = true;
        for(EquationMath.Term term : math.allTerms()) {
            if(!first)
                container.add(new BLabel(" + "));
            first = false;
            
            String coeffcient = term.getCoefficient();
            String vectorLabel = term.getVector().getLabelTextNoUnits();
            
            if(term.getVector().isSymbol())
                vectorLabel = "@=b#0000FF("+vectorLabel+")";
            
            if(coeffcient.length() == 0)
                container.add(new BLabel(vectorLabel));
            else if(coeffcient.equals("-"))
                container.add(new BLabel("-"+vectorLabel));
            else container.add(new BLabel(coeffcient+"*"+vectorLabel));
        }
        container.add(new BLabel(" = 0    "));
        
        return container;
    }

    private boolean isSolvable() {
        //return unknownMap.size() == 3;
        return system.isSolvable();
    }
    
    private void solve() {
        Map<String, Float> solution = system.solve();
        for(String term : solution.keySet()) {
            String units = "";
            if(unknownVMap.get(term) instanceof Moment)
                units = StaticsApplication.getApp().getUnits().getMoment();
            else if(unknownVMap.get(term) instanceof Force)
                units = StaticsApplication.getApp().getUnits().getForce();
            
            String text = solution.get(term) + " " + units;
            
            unknownMap.get(term).setText(text);
        }
    }
    
    private void processEquations() {
        
        system = new EquationSystem(3);
        
        process(system, 0, world.sumFx);
        process(system, 1, world.sumFy);
        process(system, 2, world.sumMp);
        
        system.process();
        
        /*Matrix3f matrix = new Matrix3f();
        Vector3f values = new Vector3f();
        matrix.zero();
        
        values.x = -process(matrix, 0, world.sumFx);
        values.y = -process(matrix, 1, world.sumFy);
        values.z = -process(matrix, 2, world.sumMp);
        
        Matrix3f inverse = matrix.invert();
        Vector3f solution = inverse.mult(values);
        
        unknownMap.get(unknownList.get(0)).setText(""+solution.x);
        unknownMap.get(unknownList.get(1)).setText(""+solution.y);
        unknownMap.get(unknownList.get(2)).setText(""+solution.z);*/
    }
    
    private void process(EquationSystem system, int row, EquationMath math) {
        for(EquationMath.Term term : math.allTerms()) {
            
            Vector vector = term.getVector();
            if(vector.isSymbol()) {
                system.addTerm(row, term.coefficientValue, vector.getName());
            } else {
                system.addTerm(row, vector.getMagnitude() * term.coefficientValue, null);
            }
        }
    }
    
}
