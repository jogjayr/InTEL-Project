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
 * Copyright (c) 2003-2006 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
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
package edu.gatech.statics.objects.representations;

import java.io.IOException;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.TexCoords;
import com.jme.scene.TriMesh;
import com.jme.util.export.InputCapsule;
import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;
import com.jme.util.export.OutputCapsule;
import com.jme.util.geom.BufferUtils;

/**
 * <code>Torus</code> is um ... a Torus :) The center is by default the
 * origin.
 * 
 * @author Mark Powell
 * @version $Id: Torus.java,v 1.11 2006/06/21 20:32:51 nca Exp $
 */
public class PartialTorus extends TriMesh {

    private static final long serialVersionUID = 1L;
    private int circleSamples;
    private int radialSamples;
    private float innerRadius;
    private float outerRadius;
    private float sweep;

    public PartialTorus() {
    }

    /**
     * Constructs a new Torus. Center is the origin, but the Torus may be
     * transformed.
     *
     * @param name
     *            The name of the Torus.
     * @param circleSamples
     *            The number of samples along the circles.
     * @param radialSamples
     *            The number of samples along the radial.
     * @param innerRadius
     *            The radius of the inner begining of the Torus.
     * @param outerRadius
     *            The radius of the outter end of the Torus.
     */
    public PartialTorus(String name, int circleSamples, int radialSamples,
            float innerRadius, float outerRadius, float sweep) {

        super(name);
        this.circleSamples = circleSamples;
        this.radialSamples = radialSamples;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.sweep = sweep;

        setGeometryData();
        setIndexData();
        setDefaultColor(ColorRGBA.white);

    }

    private void setGeometryData() {
        //TriangleBatch batch = getBatch(0);

        // allocate vertices
        setVertexCount((circleSamples + 1) * (radialSamples + 1));
        setVertexBuffer(BufferUtils.createVector3Buffer(getVertexCount()));

        // allocate normals if requested
        setNormalBuffer(BufferUtils.createVector3Buffer(getVertexCount()));

        // allocate texture coordinates
        getTextureCoords().set(0, new TexCoords(BufferUtils.createVector2Buffer(getVertexCount())));
        //getTextureBuffers().set(0, BufferUtils.createVector2Buffer(getVertexCount()));

        // generate geometry
        float inverseCircleSamples = 1.0f / circleSamples;
        float inverseRadialSamples = 1.0f / radialSamples;
        int i = 0;
        // generate the cylinder itself
        Vector3f radialAxis = new Vector3f(), torusMiddle = new Vector3f(), tempNormal = new Vector3f();
        for (int circleCount = 0; circleCount < circleSamples; circleCount++) {
            // compute center point on torus circle at specified angle
            float circleFraction = circleCount * inverseCircleSamples;
            //float theta = FastMath.TWO_PI * circleFraction;
            float theta = sweep * circleFraction;
            float cosTheta = FastMath.cos(theta);
            float sinTheta = FastMath.sin(theta);
            radialAxis.set(cosTheta, sinTheta, 0);
            radialAxis.mult(outerRadius, torusMiddle);

            // compute slice vertices with duplication at end point
            int iSave = i;
            for (int radialCount = 0; radialCount < radialSamples; radialCount++) {
                float radialFraction = radialCount * inverseRadialSamples;
                // in [0,1)
                float phi = FastMath.TWO_PI * radialFraction;
                float cosPhi = FastMath.cos(phi);
                float sinPhi = FastMath.sin(phi);
                tempNormal.set(radialAxis).multLocal(cosPhi);
                tempNormal.z += sinPhi;
                if (true) {
                    getNormalBuffer().put(tempNormal.x).put(tempNormal.y).put(tempNormal.z);
                } else {
                    getNormalBuffer().put(-tempNormal.x).put(-tempNormal.y).put(-tempNormal.z);
                }

                tempNormal.multLocal(innerRadius).addLocal(torusMiddle);
                getVertexBuffer().put(tempNormal.x).put(tempNormal.y).put(tempNormal.z);

                getTextureCoords().get(0).coords.put(radialFraction).put(circleFraction);
                i++;
            }

            BufferUtils.copyInternalVector3(getVertexBuffer(), iSave, i);
            BufferUtils.copyInternalVector3(getNormalBuffer(), iSave, i);

            getTextureCoords().get(0).coords.put(1.0f).put(circleFraction);

            i++;
        }

        // duplicate the cylinder ends to form a torus
		/*for (int iR = 0; iR <= radialSamples; iR++, i++) {
        BufferUtils.copyInternalVector3(getVertexBuffer(), iR, i);
        BufferUtils.copyInternalVector3(getNormalBuffer(), iR, i);
        BufferUtils.copyInternalVector2(getTextureBuffers().get(0), iR, i);
        getTextureBuffers().get(0).put(i*2+1, 1.0f);
        }*/
        for (int iR = 0; iR <= radialSamples; iR++, i++) {
            BufferUtils.copyInternalVector3(getVertexBuffer(), iR, i);
            BufferUtils.copyInternalVector3(getNormalBuffer(), iR, i);
            BufferUtils.copyInternalVector2(getTextureCoords().get(0).coords, iR, i);
            getTextureCoords().get(0).coords.put(i * 2 + 1, 1.0f);
        }
    }

    private void setIndexData() {
        //TriangleBatch batch = getBatch(0);
        //      allocate connectivity
        //setTriangleQuantity(2 * circleSamples * radialSamples);
        setTriangleQuantity(2 * (circleSamples - 1) * radialSamples);
        setIndexBuffer(BufferUtils.createIntBuffer(3 * getTriangleCount()));
        int i;
        // generate connectivity
        int connectionStart = 0;
        int index = 0;
        //for (int circleCount = 0; circleCount < circleSamples; circleCount++) {
        for (int circleCount = 0; circleCount < (circleSamples - 1); circleCount++) {
            int i0 = connectionStart;
            int i1 = i0 + 1;
            connectionStart += radialSamples + 1;
            int i2 = connectionStart;
            int i3 = i2 + 1;
            for (i = 0; i < radialSamples; i++, index += 6) {
                if (true) {
                    getIndexBuffer().put(i0++);
                    getIndexBuffer().put(i2);
                    getIndexBuffer().put(i1);
                    getIndexBuffer().put(i1++);
                    getIndexBuffer().put(i2++);
                    getIndexBuffer().put(i3++);
                } else {
                    getIndexBuffer().put(i0++);
                    getIndexBuffer().put(i1);
                    getIndexBuffer().put(i2);
                    getIndexBuffer().put(i1++);
                    getIndexBuffer().put(i3++);
                    getIndexBuffer().put(i2++);
                }
            }
        }
    }

    public void write(JMEExporter e) throws IOException {
        super.write(e);
        OutputCapsule capsule = e.getCapsule(this);
        capsule.write(circleSamples, "circleSamples", 0);
        capsule.write(radialSamples, "radialSamples", 0);
        capsule.write(innerRadius, "innerRadius", 0);
        capsule.write(outerRadius, "outerRadius", 0);
        capsule.write(sweep, "sweep", 0);
    }

    public void read(JMEImporter e) throws IOException {
        super.read(e);
        InputCapsule capsule = e.getCapsule(this);
        circleSamples = capsule.readInt("circleSamples", 0);
        radialSamples = capsule.readInt("radialSamples", 0);
        innerRadius = capsule.readFloat("innerRadius", 0);
        outerRadius = capsule.readFloat("outerRaidus", 0);
        sweep = capsule.readFloat("sweep", 0);
    }
}
