/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
 * This is a utility class used for holding pairs of objects.
 * The pair is described by <code>A</code> and <code>B</code>
 * @author Calvin Ashmore
 */
public class Pair<A, B> {

    private A a;
    private B b;

    /** 
     * Creates a new instance of UpdatePair
     */
    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Getter
     * @return
     */
    public A getLeft() {
        return a;
    }

    /**
     * Getter
     * @return
     */
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
