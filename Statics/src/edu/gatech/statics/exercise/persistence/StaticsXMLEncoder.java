/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.OutputStream;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsXMLEncoder extends XMLEncoder {

    public StaticsXMLEncoder(OutputStream out) {
        super(out);

        setPersistenceDelegate(DiagramState.class, new DiagramStatePersistenceDelegate());
        //setPersistenceDelegate(NameContainer.class, new DefaultPersistenceDelegate(new String[]{"name", "targetClass"}));

        setPersistenceDelegate(AnchoredVector.class, new DefaultPersistenceDelegate(new String[]{"anchor", "vector"}));
        setPersistenceDelegate(Vector.class, new DefaultPersistenceDelegate(new String[]{"unit", "vectorValue", "diagramValue"}));
        setPersistenceDelegate(BigDecimal.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                BigDecimal bd = (BigDecimal) oldInstance;
                return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{bd.toString()});
            }

            @Override
            protected boolean mutatesTo(Object oldInstance, Object newInstance) {
                return oldInstance.equals(newInstance);
            }
        });
    }

    /**
     * Modify writeObject to handle named objects. 
     * Named objects are ones that are stored by name, but then resolved.
     * @param o
     */
    @Override
    public void writeObject(Object o) {
        //if (o == null) {
        //    super.writeObject(o);
        //} else if (o instanceof ResolvableByName) {
        //    ResolvableByName resolvable = (ResolvableByName) o;
        //    super.writeObject(new NameContainer(resolvable.getName(), o.getClass()));
        //} else {
        //    super.writeObject(o);
        //}

        super.writeObject(o);
    }

    /**
     * Here we force instances of DiagramState to use the DiagramStatePersistenceDelegate.
     * The reason why is because DiagramState is an interface, and cannot be used to determine a regular
     * persistence delgate. This still requires the DiagramStatePersistenceDelegate to be set, though.
     * @param type
     * @return
     */
    @Override
    public PersistenceDelegate getPersistenceDelegate(Class<?> type) {
        if (type == null) {
            return super.getPersistenceDelegate(type);
        }
        if (DiagramState.class.isAssignableFrom(type) && type != DiagramState.class) {
            return getPersistenceDelegate(DiagramState.class);
        }
        return super.getPersistenceDelegate(type);
    }
}
