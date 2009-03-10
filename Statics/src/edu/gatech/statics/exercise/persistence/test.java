/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.ExceptionListener;
import java.beans.Expression;
import java.beans.Statement;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class test {

    public static class A {

        private B b;

        public A() {
            b = new B();
        }

        public B getB() {
            return b;
        }
    }

    public static class B {

        private int toast;

        public int getToast() {
            return toast;
        }

        public void setToast(int toast) {
            this.toast = toast;
        }
    }

    public static class ADelegate extends DefaultPersistenceDelegate {

        @Override
        protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
            A a = (A) oldInstance;


            Expression bExpression = new Expression(a.getB(), a, "getB", new Object[0]);
            Statement outStatement = new ExpressionStatement(bExpression, "setToast", new Object[]{a.getB().toast});


//            try {
//
//                Method m = Encoder.class.getDeclaredMethod("cloneStatement", new Class<?>[]{Statement.class});
//                m.setAccessible(true);
//                Object newStatement = m.invoke(out, outStatement);
//                System.out.println("new statement: " + newStatement);
//
//            } catch (Exception ex) {
//                //Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
//                ex.printStackTrace();
//            }

            //Object newA = out.get(a);
            //Object newB = out.get(a.getB());
            //System.out.println("new B: "+newB);

            System.out.println("writing...");
            //out.writeExpression(bExpression);
            //XMLEncoder xmlOut = (XMLEncoder) out;
            //xmlOut.writeObject(a.getB());
            out.writeStatement(new Statement(a, "getB", new Object[]{}));
            out.writeStatement(new Statement(a.getB(), "setToast", new Object[]{a.getB().getToast()}));
            //out.writeStatement(outStatement);
            System.out.println("outStatement: " + outStatement + " target:" + outStatement.getTarget());

        //super.initialize(type, oldInstance, newInstance, out);
        }

        @Override
        protected boolean mutatesTo(Object oldInstance, Object newInstance) {
            //return true;
            System.out.println("mutatesTo(" + oldInstance + ", " + newInstance + ")");
            return super.mutatesTo(oldInstance, newInstance);
        }
    }

    /**
     * 
     */
    private static class ExpressionStatement extends Statement {

        private Expression target;

        public ExpressionStatement(Expression target, String methodName, Object[] arguments) {
            super(null, methodName, arguments);
            this.target = target;
        }

        @Override
        public Object getTarget() {
            //return super.getTarget();
            //System.out.println("in getTarget");
            try {
                Object result = target.getValue();
                System.out.println("result: " + result);
                return result;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        public void execute() throws Exception {
            System.out.println("executing... " + this);
            try {
                super.execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(bOut) {

            @Override
            public void writeObject(Object o) {
                System.out.println("writing object: "+o);
                super.writeObject(o);
            }

            @Override
            public void writeExpression(Expression oldExp) {
                System.out.println("writing expression: "+oldExp);
                super.writeExpression(oldExp);
            }

            @Override
            public void writeStatement(Statement oldStm) {
                System.out.println("writing statement: "+oldStm);
                super.writeStatement(oldStm);
            }
            
        };
        encoder.setExceptionListener(new ExceptionListener() {

            public void exceptionThrown(Exception e) {
                e.printStackTrace();
            }
        });

        A a = new A();
        a.getB().setToast(1234);

        encoder.setPersistenceDelegate(A.class, new ADelegate());
        encoder.writeObject(a);

        encoder.close();

        System.out.println(new String(bOut.toByteArray()));
    }
}
