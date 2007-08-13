/*
 * SolveBar.java
 *
 * Created on August 1, 2007, 4:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.application.ui.AppWindow;
import edu.gatech.statics.objects.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class SolveBar extends AppWindow {
    
    private Map<String, BLabel> unknownMap = new HashMap();
    private List<String> unknownList = new ArrayList();
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
        
        BButton backButton = new BButton("Return",listener,"return");
        BButton solveButton = new BButton("Solve!",listener,"solve");
        if(!isSolvable()) {
            solveButton.setEnabled(false);
            StaticsApplication.getApp().setAdvice(
                    "WARNING: You have more than three unknowns in your equation! " +
                    "You won't be able to solve for them.");
        }
        add(backButton);
        add(solveButton);
        
        BContainer systemContainer = new BContainer(GroupLayout.makeVert(GroupLayout.TOP));
        BContainer solveContainer = new BContainer(GroupLayout.makeVert(GroupLayout.TOP));
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
        return unknownMap.size() == 3;
    }
    
    private void solve() {
        Matrix3f matrix = new Matrix3f();
        Vector3f values = new Vector3f();
        matrix.zero();
        
        values.x = -process(matrix, 0, world.sumFx);
        values.y = -process(matrix, 1, world.sumFy);
        values.z = -process(matrix, 2, world.sumMp);
        
        Matrix3f inverse = matrix.invert();
        Vector3f solution = inverse.mult(values);
        
        unknownMap.get(unknownList.get(0)).setText(""+solution.x);
        unknownMap.get(unknownList.get(1)).setText(""+solution.y);
        unknownMap.get(unknownList.get(2)).setText(""+solution.z);
    }
    
    private float process(Matrix3f matrix, int row, EquationMath math) {
        
        float sumConstant = 0;
        for(EquationMath.Term term : math.allTerms()) {
            
            Vector vector = term.getVector();
            if(vector.isSymbol()) {
                String symbol = vector.getName();
                float value = term.coefficientValue;
                
                // hopefully this won't cause trouble
                int column = unknownList.indexOf(symbol);
                matrix.set(row, column, value + matrix.get(row, column));
                
            } else {
                
                // just a regular vector, add its magnitude in
                sumConstant += vector.getMagnitude() * term.coefficientValue;
            }
        }
        return sumConstant;
    }
}
