/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.util.Buildable;
import edu.gatech.statics.util.Builder;
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

        //setPersistenceDelegate(DiagramState.class, new DiagramStatePersistenceDelegate());
        setPersistenceDelegate(Buildable.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                // casts the object into a buildable
                Buildable<?> buildable = (Buildable<?>) oldInstance;

                // fetch the builder
                Builder<?> builder = buildable.getBuilder();

                // encode the builder itself.
                Expression expression = new Expression(oldInstance, builder, "build", new String[]{});
                return expression;
            }
        });

        setPersistenceDelegate(AnchoredVector.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                AnchoredVector v = (AnchoredVector) oldInstance;
                return new Expression(oldInstance, AnchoredVector.class, "new", new Object[]{v.getAnchor(), v.getVector()});
            }
        });
        setPersistenceDelegate(Vector.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                Vector v = (Vector) oldInstance;
                if (v.isSymbol()) {
                    return new Expression(oldInstance, Vector.class, "new", new Object[]{v.getUnit(), v.getVectorValue(), v.getSymbolName()});
                } else {
                    return new Expression(oldInstance, Vector.class, "new", new Object[]{v.getUnit(), v.getVectorValue(), v.getDiagramValue()});
                }
            }
        });
        setPersistenceDelegate(Vector3bd.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                Vector3bd v = (Vector3bd) oldInstance;
                return new Expression(oldInstance, Vector3bd.class, "new", new Object[]{v.getX(), v.getY(), v.getZ()});
            }
        });
        setPersistenceDelegate(BigDecimal.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                BigDecimal bd = (BigDecimal) oldInstance;
                return new Expression(oldInstance, BigDecimal.class, "new", new Object[]{bd.toString()});
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
        if (Buildable.class.isAssignableFrom(type) && type != Buildable.class) {
            return getPersistenceDelegate(Buildable.class);
        }
        return super.getPersistenceDelegate(type);
    }
}
