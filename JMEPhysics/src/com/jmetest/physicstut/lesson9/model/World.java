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

import java.util.ArrayList;
import java.util.List;

import com.jmex.physics.util.AssocList;

/**
 * This is a central data model class. It represents our entire virtual world. Thus it contains references to all
 * {@link Item}s that are located in it. Like the Item class, this class extends {@link PropertyChangeSource} which
 * is a little helper class to fire {@link java.beans.PropertyChangeEvent}s. E.g. the list of items fires them
 * whenever an Item gets added or removed to/from it.
 * <p/>
 * The most important idea that should be followed when deciding what to put into the model is: The model should contain
 * the whole state of the game - meaning if you save and restore the model your game state should be restored. It should
 * not contain (much) more.
 * <br>
 * This allows easy saving/storing as well as client-server or peer-to-peer applications.
 * <p/>
 * (You can now switch back to the previous file you were watching or read about model implementation here.)
 * <p/>
 * An important concept when designing the model for your application is the concept of <i>associations</i>.
 * Associations between your model classes allow you to create links between model elements and thus make it possible
 * to navigate in your model. Associations can be modelled by simple attributes containing a reference to another
 * object, or a simple list/set of other objects. Though most of the times (if not always) it is better to have more
 * sophisticated association implementations:
 * <p/>
 * First encapsulation is an issue: the fields containing the references should not be directly accessible from outside
 * a class. Instead access methods are used. This allows to have readonly references, firing change events and ensure
 * link integrity. <br>
 * Second I recommend to use bidirectional associations. Meaning each object that is linked to another object also links
 * to that other object. This bidirectional links should always be consistent, that is: there shouldn't be any 'half'
 * links! Thus this should be ensured in the accessmethods already.
 * @author Irrisor
 */
public class World extends PropertyChangeSource {

    /**
     * Define a list of items in this world.
     */
    private final List<Item> items = new AssocList<Item>( new ArrayList<Item>(), new AssocList.ModificationHandler<Item>() {
        public void added( Item element ) {
            element.setWorld( World.this );
            firePropertyChange( ITEMS_PROPERTY, null, element );
        }

        public void removed( Item element ) {
            element.setWorld( null );
            firePropertyChange( ITEMS_PROPERTY, element, null );
        }

        public boolean canAdd( Item element ) {
            return element.getWorld() != World.this;
        }
    } );

    /**
     * Constant for firing property change events for the 'items' links.
     */
    public static final String ITEMS_PROPERTY = "items";

    /**
     * Get the list of items to add/remove or query items of this World.
     *
     * @return list of items
     */
    public List<Item> getItems() {
        return items;
    }
}

/*
 * $Log$
 */

