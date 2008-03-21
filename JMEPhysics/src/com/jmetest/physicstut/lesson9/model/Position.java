/*
 * Copyright (c) 2005-2007 jME Physics 2
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of 'jME Physics 2' nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jmetest.physicstut.lesson9.model;

import java.io.Serializable;

import com.jme.math.Vector3f;

/**
 * To have proper encapsulation of positions in our model we need a Position class that cannot be altered from
 * outside the model.
 */
public final class Position implements Serializable {
    /**
     * actual values - package local to allow alteration by the Item class.
     */
    float x;
    /**
     * actual values - package local to allow alteration by the Item class.
     */
    float y;
    /**
     * actual values - package local to allow alteration by the Item class.
     */
    float z;

    /**
     * @return x coordinate of this position.
     */
    public float getX() {
        return x;
    }

    /**
     * @return y coordinate of this position.
     */
    public float getY() {
        return y;
    }

    /**
     * @return z coordinate of this position.
     */
    public float getZ() {
        return z;
    }

    public Vector3f copyTo( Vector3f vector ) {
        vector.x = x;
        vector.y = y;
        vector.z = z;
        return vector;
    }

    @Override
    public String toString() {
        return "Position[" + x + "; " + y + "; " + z + "]";
    }
}

/*
 * $Log$
 */

