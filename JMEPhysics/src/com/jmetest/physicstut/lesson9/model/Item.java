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

import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Logger;

import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jmex.physics.PhysicsSpace;

/**
 * Any item that can reside in our {@link World}. An important property of an Item is its position. The position
 * can only be changed by calling {@link #setPosition(float, float, float)}. Retrieving the position by calling
 * {@link #getPosition()} and alter that one is not possible as {@link Position} cannot be altered from outside this
 * package. This is implemented like this to allow firing PropertyChangeEvents (see {@link PropertyChangeSource}) when
 * the position changes.
 * <p/>
 * Furthermore an Item has an associated {@link Spatial}. To allow navigating from Spatial to Item we introduce the
 * {@link #spatialBacklinks} attribute and the {@link #getItem(Spatial)} method.
 *
 * @author Irrisor
 */
public abstract class Item extends PropertyChangeSource {

    // ----------------------------------- world association -----------------------------------

    /**
     * store reference to world.
     */
    private World world;

    /**
     * @return world that contains this item
     */
    public World getWorld() {
        return world;
    }

    /**
     * Constant for firing property change events for the 'world' links.
     */
    public static final String WORLD_PROPERTY = "world";

    /**
     * Only the {@link World} class calls this when this item gets added to a world.
     * If this can be called from somewhere else the implementation would be more complex to ensure having proper
     * bidirectional links.
     *
     * @param value world that contains this item
     */
    void setWorld( World value ) {
        World oldValue = this.world;
        if ( oldValue != value ) {
            if ( oldValue != null ) {
                oldValue.getItems().remove( this );
            }
            this.world = value;
            firePropertyChange( WORLD_PROPERTY, oldValue, value );
        }
    }

    // ----------------------------------- position attribute -----------------------------------

    /**
     * stores position of this Item.
     */
    private Position position = new Position();

    public static final String POSITION_PROPERTY = "position";

    /**
     * @return the position of this Item (read only)
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Change the position of this object to (x,y,z).
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public void setPosition( float x, float y, float z ) {
        if ( this.position.x != x ||
                this.position.y != y ||
                this.position.z != z ) {
            this.position.x = x;
            this.position.y = y;
            this.position.z = z;
            firePropertyChange( POSITION_PROPERTY, null, this.position );
        }
    }

    // ----------------------------------- spatial association -----------------------------------

    /**
     * store reference to this Items associated scenegraph Spatial.
     */
    private Node node;

    /**
     * As Spatial cannot have a backlink to this Item we store all the backlinks in a Map to allow navigating from
     * Spatial to Item.
     *
     * @see #getItem(Spatial)
     */
    private static final Map<Spatial, Item> spatialBacklinks = new WeakHashMap<Spatial, Item>();

    /**
     * @return the scenegraph Node associated with this Item
     */
    public Node getNode() {
        return node;
    }

    /**
     * Change the associated scenegraph Node.
     *
     * @param node new Spatial
     */
    public void setNode( Node node ) {
        Spatial oldValue = this.node;
        if ( oldValue != node ) {
            this.node = node;
            if ( node != null ) {
                // here we introduce the backlink
                Item oldItem = spatialBacklinks.put( node, this );
                if ( oldItem != null ) {
                    Logger.getLogger( PhysicsSpace.LOGGER_NAME ).warning( "Spatial '" + node + "' was registered for '" + oldItem + "', now registered for '" + this + "'" );
                    oldItem.setNode( null );
                }
            }
            if ( oldValue != null && spatialBacklinks.get( oldValue ) == this ) {
                // here we remove it
                spatialBacklinks.remove( oldValue );
            }
        }
    }

    /**
     * Retrieve the Item that is associated with the given Spatial. If the given spatial itself is not associated with
     * an Item this methods walks up the scenegraph hierarchy to find a Node that is associated with an Item.
     *
     * @param spatial a scenegraph Spatial
     * @return the associated with the Spatial, null if no such Item was found
     */
    public static Item getItem( Spatial spatial ) {
        Item item = null;
        while ( item == null && spatial != null ) {
            item = spatialBacklinks.get( spatial );
            spatial = spatial.getParent();
        }
        return item;
    }
}

/*
 * $Log$
 */

