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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

/**
 * This is a superclass to allow objects to fire {@link PropertyChangeEvent}s easily. It is not further discussed in
 * this tutorial. Refer to the JDK doc and tutorials for more info on PropertyChangeEvents.
 *
 * @author Irrisor
 */
public abstract class PropertyChangeSource {

    protected void firePropertyChange( String propertyName, Object oldValue, Object newValue ) {
        if ( propertyChangeSupport != null ) {
            singlePropertyChangeEvent.setPropertyName( propertyName );
            singlePropertyChangeEvent.setOldValue( oldValue );
            singlePropertyChangeEvent.setNewValue( newValue );
            firePropertyChange( singlePropertyChangeEvent );
        }
    }

    private MutablePropertyChangeEvent singlePropertyChangeEvent = new MutablePropertyChangeEvent();

    private class MutablePropertyChangeEvent extends PropertyChangeEvent {
        private String propertyName;
        private Object oldValue;
        private Object newValue;

        @Override
        public Object getNewValue() {
            return newValue;
        }

        public void setNewValue( Object newValue ) {
            this.newValue = newValue;
        }

        @Override
        public Object getOldValue() {
            return oldValue;
        }

        public void setOldValue( Object oldValue ) {
            this.oldValue = oldValue;
        }

        @Override
        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName( String propertyName ) {
            this.propertyName = propertyName;
        }

        public MutablePropertyChangeEvent() {
            super( PropertyChangeSource.this, null, null, null );
        }
    }


    private transient PropertyChangeSupport propertyChangeSupport;

    /**
     * Sends an event to all previously subscribed listeners (synchronously).
     *
     * @param evt what to send
     */
    public void firePropertyChange( PropertyChangeEvent evt ) {
        if ( propertyChangeSupport != null ) {
            propertyChangeSupport.firePropertyChange( evt );
        }
    }

    /**
     * Get the propertyChangeSupport attribute of the PropertyChangeInterface object
     *
     * @return The propertyChangeSupport value
     */
    public PropertyChangeSupport getPropertyChangeSupport() {
        if ( propertyChangeSupport == null ) {
            propertyChangeSupport = new PropertyChangeSupport( this );
        }
        return propertyChangeSupport;
    }
}

/*
 * $Log$
 */

