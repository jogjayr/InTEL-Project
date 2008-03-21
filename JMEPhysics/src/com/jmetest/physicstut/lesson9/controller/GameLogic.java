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
package com.jmetest.physicstut.lesson9.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jmetest.physicstut.lesson9.model.Actor;
import com.jmetest.physicstut.lesson9.model.Item;
import com.jmetest.physicstut.lesson9.model.Platform;
import com.jmetest.physicstut.lesson9.model.World;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.PhysicsSpace;

/**
 * This class is instantiated to initialize the games logic (behaviour) and should take care of all model changes.
 * E.g. physics is one of its domains. As physics already need the scenegraph it is also created in this class
 * (see {@link #getScenegraph()}.
 * <p/>
 * An important thing for the GameLogic is that its {@link #update} method must be called by the application frequently.
 *
 * @author Irrisor
 */
public class GameLogic {

    public GameLogic( World world ) {
        this.world = world;

        // first create the root Node of the scenegraph
        scenegraph = new Node( "World" );

        // and initialize the physics
        physicsSpace = PhysicsSpace.create();

        // initalize handlers for different types of items
        itemHandlers.put( Actor.class, new ActorHandler( this ) );
        itemHandlers.put( Platform.class, new PlatformHandler( this ) );

        // then we have a look if there already is some stuff in the world
        for ( Item item : world.getItems() ) {
            registerItem( item );
        }

        // finally subscribe a listener for stuff added to world (and removed from the world)
        world.getPropertyChangeSupport().addPropertyChangeListener( World.ITEMS_PROPERTY, new ItemsListener() );
    }

    public void update( float time ) {
        getPhysicsSpace().update( time );
    }

    /**
     * the world this game logic operates on.
     */
    private final World world;

    /**
     * @return the world this game logic operates on
     */
    public World getWorld() {
        return world;
    }

    /**
     * the scenegraph.
     */
    private final Node scenegraph;

    /**
     * @return the scenegraph
     */
    public Node getScenegraph() {
        return scenegraph;
    }

    /**
     * physics space.
     */
    private final PhysicsSpace physicsSpace;

    /**
     * @return physics space
     */
    public PhysicsSpace getPhysicsSpace() {
        return physicsSpace;
    }

    /**
     * Small class for listening to the items association of our world.
     */
    private class ItemsListener implements PropertyChangeListener {
        /**
         * called when an item has been added to / removed from the world
         *
         * @param evt evt.getNewValue() contains and added item, evt.getOldValue() a removed one
         */
        public void propertyChange( PropertyChangeEvent evt ) {
            if ( evt.getNewValue() instanceof Item ) {
                registerItem( (Item) evt.getNewValue() );
            }
            if ( evt.getOldValue() instanceof Item ) {
                unregisterItem( (Item) evt.getNewValue() );
            }
        }
    }

    /**
     * We call this method for all items we find in the world.
     * <br> (it is package local to allow direct access from the listener)
     *
     * @param item new item
     */
    void registerItem( Item item ) {
        // here defer to a seperate class for each kind of Item we have
        ItemHandler handler = itemHandlers.get( item.getClass() );
        if ( handler != null ) {
            PhysicsNode itemPhysics = handler.createPhysicsFor( item );
            // add it to the scenegraph
            getScenegraph().attachChild( itemPhysics );
            // and associate it with the item
            item.setNode( itemPhysics );
            // apply current position
            item.getPosition().copyTo( itemPhysics.getLocalTranslation() );
            // and listen to position changes - the listener for this can
            item.getPropertyChangeSupport().addPropertyChangeListener( Item.POSITION_PROPERTY, positionUpdater );
        } else {
            throw new IllegalArgumentException( "Unknown Item class: " + item.getClass() );
        }
    }

    public interface ItemHandler {
        PhysicsNode createPhysicsFor( Item item );
    }

    private Map<Class, ItemHandler> itemHandlers = new HashMap<Class, ItemHandler>();

    private final PositionUpdater positionUpdater = new PositionUpdater();

    private static class PositionUpdater implements PropertyChangeListener {
        public void propertyChange( PropertyChangeEvent evt ) {
            Item item = (Item) evt.getSource();
            item.getPosition().copyTo( item.getNode().getLocalTranslation() );
        }
    }

    /**
     * We call this method for all items that get removed from the world.
     * <br> (it is package local to allow direct access from the listener)
     *
     * @param item removed item
     */
    void unregisterItem( Item item ) {
        // undo the things we have done in registerItem
        Spatial spatial = item.getNode();
        if ( spatial instanceof PhysicsNode ) {
            PhysicsNode node = (PhysicsNode) spatial;
            node.setActive( false );
        }
        item.setNode( null );
        item.getPropertyChangeSupport().removePropertyChangeListener( Item.POSITION_PROPERTY, positionUpdater );
    }

}

/*
 * $Log$
 */

