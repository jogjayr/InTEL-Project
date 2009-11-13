/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.state.ExerciseState;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.distributed.objects.ConstantDistributedForce;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import edu.gatech.statics.modes.distributed.objects.QuarterEllipseDistributedForce;
import edu.gatech.statics.modes.distributed.objects.TriangularDistributedForce;
import edu.gatech.statics.modes.truss.zfm.PotentialZFM;
import edu.gatech.statics.modes.truss.zfm.ZeroForceMember;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.ConstantObject;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.bodies.Cable;
import edu.gatech.statics.objects.bodies.Plate;
import edu.gatech.statics.objects.bodies.PointBody;
import edu.gatech.statics.objects.bodies.Potato;
import edu.gatech.statics.objects.bodies.Pulley;
import edu.gatech.statics.tasks.CompleteFBDTask;
import edu.gatech.statics.tasks.Solve2FMTask;
import edu.gatech.statics.tasks.SolveConnectorTask;
import edu.gatech.statics.tasks.SolveFBDTask;
import edu.gatech.statics.tasks.SolveZFMTask;
import edu.gatech.statics.util.Buildable;
import edu.gatech.statics.util.Builder;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.ExceptionListener;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.Statement;
import java.beans.XMLEncoder;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

            @Override
            protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
                Vector v = (Vector) oldInstance;
                if (v.isSymbol()) {
                    out.writeStatement(new Statement(v, "setSymbol", new Object[]{v.getSymbolName()}));
                }
                out.writeStatement(new Statement(v, "setDiagramValue", new Object[]{v.getDiagramValue()}));
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

        setPersistenceDelegate(DiagramType.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                DiagramType type = (DiagramType) oldInstance;
                return new Expression(oldInstance, DiagramType.class, "getType", new Object[]{type.getName()});
            }
        });

        setPersistenceDelegate(BodySubset.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                BodySubset bodySubset = (BodySubset) oldInstance;
                return new Expression(oldInstance, BodySubset.class, "new", new Object[]{new ArrayList(bodySubset.getBodies())});
            }
        });

        PersistenceDelegate namedPersistenceDelegate = new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                ResolvableByName resolvable = (ResolvableByName) oldInstance;
                return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{resolvable.getName()});
            }
        };

        setPersistenceDelegate(PointBody.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                PointBody pb = (PointBody) oldInstance;
                return new Expression(oldInstance, PointBody.class, "new", new Object[]{pb.getName(), pb.getAnchor()});
            }
        });

        setPersistenceDelegate(Point.class, namedPersistenceDelegate);
        setPersistenceDelegate(Body.class, namedPersistenceDelegate);

        setPersistenceDelegate(ConstantObject.class, namedPersistenceDelegate);

        // we should have some general approach so that this nonsense is not necessary
        setPersistenceDelegate(Beam.class, namedPersistenceDelegate);
        setPersistenceDelegate(Cable.class, namedPersistenceDelegate);
        setPersistenceDelegate(Bar.class, namedPersistenceDelegate);
        setPersistenceDelegate(Pulley.class, namedPersistenceDelegate);
        setPersistenceDelegate(Potato.class, namedPersistenceDelegate);
        setPersistenceDelegate(Plate.class, namedPersistenceDelegate);
        setPersistenceDelegate(ZeroForceMember.class, namedPersistenceDelegate);
        setPersistenceDelegate(PotentialZFM.class, namedPersistenceDelegate);
        //setPersistenceDelegate(Point.class, new DefaultPersistenceDelegate(new String[]{"name"}));
        //setPersistenceDelegate(Body.class, new DefaultPersistenceDelegate(new String[]{"name"}));

        PersistenceDelegate distributedForcePersistenceDelegate = new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                DistributedForce dl = (DistributedForce) oldInstance;
                //String name, Beam surface, Point startPoint, Point endPoint, Vector peak
                return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{
                            dl.getName(), dl.getSurface(), dl.getStartPoint(), dl.getEndPoint(), dl.getPeak()});
            }
        };

        setPersistenceDelegate(ConstantDistributedForce.class, distributedForcePersistenceDelegate);
        setPersistenceDelegate(QuarterEllipseDistributedForce.class, distributedForcePersistenceDelegate);
        setPersistenceDelegate(TriangularDistributedForce.class, distributedForcePersistenceDelegate);


        PersistenceDelegate distributedForceObjectPersistenceDelegate = new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                DistributedForceObject dlObj = (DistributedForceObject) oldInstance;
                // DistributedLoad dl, String suffix
                return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{
                            dlObj.getDistributedForce(), dlObj.getSuffix()});
            }
        };

        setPersistenceDelegate(DistributedForceObject.class, distributedForceObjectPersistenceDelegate);

        // set up the delegates for tasks.
        setPersistenceDelegate(CompleteFBDTask.class, namedPersistenceDelegate);
        setPersistenceDelegate(SolveFBDTask.class, namedPersistenceDelegate);
        setPersistenceDelegate(SolveZFMTask.class, namedPersistenceDelegate);
        setPersistenceDelegate(Solve2FMTask.class, namedPersistenceDelegate);
        setPersistenceDelegate(SolveConnectorTask.class, namedPersistenceDelegate);

        setPersistenceDelegate(ExerciseState.class, new ExerciseStatePersistenceDelegate());


        // set up an exception listener
        setExceptionListener(new ExceptionListener() {

            public void exceptionThrown(Exception e) {
                Logger.getLogger("Statics").log(Level.WARNING, "Persistence failed!", e);
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

        try {
            super.writeObject(o);
        } catch (RuntimeException ex) {
            // pick up an exception that might have been thrown and adds some logging
            Logger.getLogger("Statics").log(Level.WARNING, "Persistence of "+o.getClass().getName()+" ("+o+") caused an exception...");
            throw ex;
        }
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
