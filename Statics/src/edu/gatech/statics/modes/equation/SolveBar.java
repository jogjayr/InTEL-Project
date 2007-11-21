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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class SolveBar extends Toolbar {
    
    private Map<String, BLabel> unknownMap = new HashMap<String, BLabel>();
    private Map<String, Vector> unknownVMap = new HashMap<String, Vector>();
    private List<String> unknownList = new ArrayList<String>();
    private EquationSystem system;
    private EquationWorld world;
    
    private BButton backButton;
    private BButton solveButton;
    
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
        
        backButton = new BButton("Return",listener,"return");
        solveButton = new BButton("Solve!",listener,"solve");
        if(!isSolvable() && unknownMap.size() != 0) {
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
        
        // add the return button if our system has already been solved.
        if(unknownMap.size() == 0) {
            BButton returnButton = new BButton("Return to Main",new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    StaticsApplication.getApp().loadExercizeWorld();
                }
            },"return");
            add(returnButton);
        }
    }
    
    private void collectUnknowns(EquationMath math) {
        
        for(EquationMath.Term term : math.allTerms()) {
            Vector vector = term.getVector();
            if(vector.isSymbol() && !vector.isSolved()) {
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
            
            // update the solutions with units
            String units = "";
            if(unknownVMap.get(term) instanceof Moment)
                units = StaticsApplication.getApp().getUnits().getMoment();
            else if(unknownVMap.get(term) instanceof Force)
                units = StaticsApplication.getApp().getUnits().getForce();
            
            String text = solution.get(term) + " " + units;
            
            unknownMap.get(term).setText(text);
        }
        
        Map<Vector, Float> values = new Hashtable<Vector, Float>();
        for(String term : solution.keySet())
            values.put( unknownVMap.get(term), solution.get(term) );
        
        world.performSolve(values);
        
        solveButton.setEnabled(false);
        BButton returnButton = new BButton("Return to Main",new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                StaticsApplication.getApp().loadExercizeWorld();
            }
        },"return");
        add(returnButton);
    }
    
    private void processEquations() {
        
        system = new EquationSystem(3);
        
        process(system, 0, world.sumFx);
        process(system, 1, world.sumFy);
        process(system, 2, world.sumMp);
        
        system.process();
    }
    
    private void process(EquationSystem system, int row, EquationMath math) {
        for(EquationMath.Term term : math.allTerms()) {
            
            Vector vector = term.getVector();
            if(vector.isSymbol() && !vector.isSolved()) {
                system.addTerm(row, term.coefficientValue, vector.getName());
            } else {
                system.addTerm(row, vector.getMagnitude() * term.coefficientValue, null);
            }
        }
    }
    
}
