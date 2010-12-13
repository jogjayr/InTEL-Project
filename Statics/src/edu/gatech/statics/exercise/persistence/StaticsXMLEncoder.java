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
import edu.gatech.statics.modes.equation.arbitrary.AnchoredVectorNode;
import edu.gatech.statics.modes.equation.arbitrary.EmptyNode;
import edu.gatech.statics.modes.equation.arbitrary.OperatorNode;
import edu.gatech.statics.modes.equation.arbitrary.SymbolNode;
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
import edu.gatech.newbeans.DefaultPersistenceDelegate;
import edu.gatech.newbeans.Encoder;
import edu.gatech.newbeans.ExceptionListener;
import edu.gatech.newbeans.Expression;
import edu.gatech.newbeans.PersistenceDelegate;
import edu.gatech.newbeans.Statement;
import edu.gatech.newbeans.XMLEncoder;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.centroid.CentroidBody;
import edu.gatech.statics.modes.distributed.objects.TrapezoidalDistributedForce;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.objects.connectors.ContactPoint;
import edu.gatech.statics.objects.connectors.Fix2d;
import edu.gatech.statics.objects.connectors.Hinge;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.connectors.Pin2dKnownDirection;
import edu.gatech.statics.objects.connectors.Roller2d;
import edu.gatech.statics.objects.connectors.Roller3d;
import edu.gatech.statics.util.Pair;
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

        // specialized delegate for Class objects
        setPersistenceDelegate(Class.class, new ClassPersistenceDelegate());

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
        setPersistenceDelegate(Vector3bd.UnmodifiableVector3bd.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                Vector3bd v = (Vector3bd) oldInstance;
                return new Expression(oldInstance, Vector3bd.UnmodifiableVector3bd.class, "new", new Object[]{v.getX(), v.getY(), v.getZ()});
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

        // bodies
        setPersistenceDelegate(Point.class, namedPersistenceDelegate);
        setPersistenceDelegate(Body.class, namedPersistenceDelegate);
        setPersistenceDelegate(ConstantObject.class, namedPersistenceDelegate);
        setPersistenceDelegate(Beam.class, namedPersistenceDelegate);
        setPersistenceDelegate(Cable.class, namedPersistenceDelegate);
        setPersistenceDelegate(Bar.class, namedPersistenceDelegate);
        setPersistenceDelegate(Pulley.class, namedPersistenceDelegate);
        setPersistenceDelegate(Potato.class, namedPersistenceDelegate);
        setPersistenceDelegate(Plate.class, namedPersistenceDelegate);
        setPersistenceDelegate(ZeroForceMember.class, namedPersistenceDelegate);
        setPersistenceDelegate(PotentialZFM.class, namedPersistenceDelegate);
        setPersistenceDelegate(CentroidBody.class, namedPersistenceDelegate);

//        setPersistenceDelegate(CentroidPart.class, namedPersistenceDelegate);

        // connectors
        setPersistenceDelegate(Connector2ForceMember2d.class, namedPersistenceDelegate);
        setPersistenceDelegate(ContactPoint.class, namedPersistenceDelegate);
        setPersistenceDelegate(Fix2d.class, namedPersistenceDelegate);
        setPersistenceDelegate(Hinge.class, namedPersistenceDelegate);
        setPersistenceDelegate(Pin2d.class, namedPersistenceDelegate);
        setPersistenceDelegate(Pin2dKnownDirection.class, namedPersistenceDelegate);
        setPersistenceDelegate(Roller2d.class, namedPersistenceDelegate);
        setPersistenceDelegate(Roller3d.class, namedPersistenceDelegate);

//        PersistenceDelegate distributedForcePersistenceDelegate = new DefaultPersistenceDelegate() {
//
//            @Override
//            protected Expression instantiate(Object oldInstance, Encoder out) {
//                DistributedForce dl = (DistributedForce) oldInstance;
//                //String name, Beam surface, Point startPoint, Point endPoint, Vector peak
//                return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{
//                            dl.getName(), dl.getSurface(), dl.getStartPoint(), dl.getEndPoint(), dl.getPeak()});
//            }
//        };

        setPersistenceDelegate(ConstantDistributedForce.class, namedPersistenceDelegate);
        setPersistenceDelegate(QuarterEllipseDistributedForce.class, namedPersistenceDelegate);
        setPersistenceDelegate(TriangularDistributedForce.class, namedPersistenceDelegate);
        setPersistenceDelegate(TrapezoidalDistributedForce.class, namedPersistenceDelegate);


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


        // set up delegates for Arbitrary equation nodes
        setPersistenceDelegate(EmptyNode.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                //return super.instantiate(oldInstance, out);
                EmptyNode node = (EmptyNode) oldInstance;
                return new Expression(oldInstance, EmptyNode.class, "new", new Object[]{node.getParent()});
            }
        });
        setPersistenceDelegate(AnchoredVectorNode.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                //return super.instantiate(oldInstance, out);
                AnchoredVectorNode node = (AnchoredVectorNode) oldInstance;
                return new Expression(oldInstance, AnchoredVectorNode.class, "new", new Object[]{node.getParent(), node.getAnchoredVector()});
            }
        });
        setPersistenceDelegate(SymbolNode.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                //return super.instantiate(oldInstance, out);
                SymbolNode node = (SymbolNode) oldInstance;
                return new Expression(oldInstance, SymbolNode.class, "new", new Object[]{node.getParent(), node.getSymbol()});
            }
        });
        setPersistenceDelegate(OperatorNode.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                //return super.instantiate(oldInstance, out);
                OperatorNode node = (OperatorNode) oldInstance;
                // operatornode takes left and right, but we give it nulls to start with
                // so that the persistence delegate will actually fill in the left and right for us.
                // we can't provide them here, because the left and right nodes need to be built using this operator.
                return new Expression(oldInstance, OperatorNode.class, "new", new Object[]{node.getParent(), null, null});
            }
        });

        setPersistenceDelegate(Pair.class, new DefaultPersistenceDelegate() {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                Pair pair = (Pair) oldInstance;
                return new Expression(oldInstance, Pair.class, "new", new Object[]{pair.getLeft(), pair.getRight()});
            }
        });

        // set up an exception listener
        setExceptionListener(new ExceptionListener() {

            public void exceptionThrown(Exception e) {
                StaticsApplication.logger.log(Level.WARNING, "Persistence failed!", e);
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
            StaticsApplication.logger.log(Level.WARNING, "Persistence of " + o.getClass().getName() + " (" + o + ") caused an exception...");
            throw ex;   
        }
    }

    @Override
    public Object get(Object oldInstance) {
        return super.get(oldInstance);
    }

    @Override
    public Object getValue(Expression exp) {
        if (exp != null && exp.getTarget() == Class.class &&
                exp.getMethodName().equals("forName") &&
                !(exp instanceof ClassPersistenceDelegate.ClassExpression)) {
            try {
                return Class.forName((String) exp.getArguments()[0]);//ClassFinder.findClass((String) exp.getArguments()[0]);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StaticsXMLEncoder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return super.getValue(exp);
    }

    /**
     * Here we force instances of DiagramState to use the DiagramStatePersistenceDelegate.
     * The reason why is because DiagramState is an interface, and cannot be used to determine a regular
     * persistence delegate. This still requires the DiagramStatePersistenceDelegate to be set, though.
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
