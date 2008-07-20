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
public class Pair<A, B> {

    private A a;
    private B b;

    /** Creates a new instance of UpdatePair */
    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getLeft() {
        return a;
    }

    public B getRight() {
        return b;
    }

    @Override
    public String toString() {
        return "Pair(" + a + "," + b + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<A, B> other = (Pair<A, B>) obj;
        if (this.a != other.a && (this.a == null || !this.a.equals(other.a))) {
            return false;
        }
        if (this.b != other.b && (this.b == null || !this.b.equals(other.b))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.a != null ? this.a.hashCode() : 0);
        hash = 97 * hash + (this.b != null ? this.b.hashCode() : 0);
        return hash;
    }
}
