/*
 * UpdatePair.java
 *
 * Created on June 12, 2005, 6:15 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package edu.gatech.statics.util;
/**
 *
 * @author Calvin Ashmore
 */
public class Pair<A,B>
{
    private A a;
    private B b;
    
    /** Creates a new instance of UpdatePair */
    public Pair(A a, B b)
    {
        this.a = a;
        this.b = b;
    }
    
    public A getLeft() {return a;}
    public B getRight() {return b;}
    
    public String toString() {return "Pair("+a+","+b+")";}
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Pair) {
            Pair x = (Pair)obj;
            return a.equals(x.a) && b.equals(x.b);
        }
        return false;
    }
    public int hashCode() { return a.hashCode() + 3456721*b.hashCode();}
}
