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
package com.jmetest.physicstut.lesson9.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingSphere;
import com.jme.input.KeyInput;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.system.DisplaySystem;
import com.jmetest.physicstut.lesson9.controller.GameLogic;
import com.jmetest.physicstut.lesson9.model.Actor;
import com.jmetest.physicstut.lesson9.model.Item;
import com.jmetest.physicstut.lesson9.model.Platform;
import com.jmetest.physicstut.lesson9.model.World;
import com.jmex.physics.PhysicsDebugger;

/**
 * @author
 */
public class View extends SimpleGame {
    private World world;
    private final GameLogic gameLogic;

    public View( World world, GameLogic gameLogic ) {
        this.world = world;
        this.gameLogic = gameLogic;
    }

    @Override
    protected void simpleUpdate() {
        // to ease things we call the controller update method from the view here
        // the server application (without view) should do it in an extra thread
        gameLogic.update( tpf );
    }

    protected void simpleInitGame() {
        rootNode.attachChild( gameLogic.getScenegraph() );

        // like in the controller we need to know about any item that gets added to the world here:
        // have a look if there already is some stuff in the world
        for ( Item item : world.getItems() ) {
            registerItem( item );
        }

        // finally subscribe a listener for stuff added to world (and removed from the world)
        world.getPropertyChangeSupport().addPropertyChangeListener( World.ITEMS_PROPERTY, new ItemsListener() );
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
        // we could load some model here depending on e.g. some attribute of the Item
        // for simplicity in this tut we simply create some geom depending on type

        // these should somewhat match the physical representation
        // though I do not recommend to derive physics from visual as you can't have
        // correct behaviour without loading the models (e.g. on a server)

        if ( item instanceof Actor ) {
            item.getNode().attachChild( new Sphere( null, 20, 20, 2 ) );
        }
        else if ( item instanceof Platform ) {
            item.getNode().attachChild( new Box( null, new Vector3f(), 5, 0.25f, 5 ) );
        }
        else {
            throw new IllegalArgumentException( "unknown Item type: " + item.getClass() );
        }
        item.getNode().setModelBound( new BoundingSphere() );
        item.getNode().updateModelBound();
    }

    /**
     * We call this method for all items that get removed from the world.
     * <br> (it is package local to allow direct access from the listener)
     *
     * @param item removed item
     */
    void unregisterItem( Item item ) {
        // nothing to be done as the items node is deleted by the controller
    }

    @Override
    protected void simpleRender() {
        if ( KeyInput.get().isKeyDown( KeyInput.KEY_V ) ) {
            PhysicsDebugger.drawPhysics( gameLogic.getPhysicsSpace(), DisplaySystem.getDisplaySystem().getRenderer() );
        }
    }
}

/*
 * $Log$
 */

