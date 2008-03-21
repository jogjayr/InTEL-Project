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
package com.jmetest.physics;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import com.jme.system.PropertiesIO;
import com.jme.system.lwjgl.LWJGLPropertiesDialog;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.util.SimplePhysicsGame;

public abstract class SimplePhysicsTest extends SimplePhysicsGame {
    private URL logo = getClass().getResource( "resources/logo300.png" );
    private static final String PHYSICS_PROPERTIES_KEY = "PHYSICS";

    protected SimplePhysicsTest() {
        if ( logo != null ) {
            setDialogBehaviour( ALWAYS_SHOW_PROPS_DIALOG, logo );
        } else {
            setDialogBehaviour( ALWAYS_SHOW_PROPS_DIALOG );
        }
    }

    @Override
    protected void getAttributes() {
        properties = new PropertiesIO( "properties.cfg" );
        properties.load();

        LWJGLPropertiesDialog dialog = new LWJGLPropertiesDialog( properties, logo );
        dialog.setVisible( false );
        dialog.setModal( true );

        final JComboBox implBox = new JComboBox();
        final Set<String> availableImplementations = PhysicsSpace.getAvailableImplementations();
        for ( String implName : availableImplementations ) {
            implBox.addItem( implName );
        }
        String chosenImpl = properties.get( PHYSICS_PROPERTIES_KEY );
        if ( chosenImpl == null || !availableImplementations.contains( chosenImpl ) )
        {
            chosenImpl = "ODE";
        }
        implBox.setSelectedItem( chosenImpl );
        implBox.addItemListener( new ItemListener() {
            public void itemStateChanged( ItemEvent e ) {
                if ( e.getStateChange() == ItemEvent.SELECTED )
                {
                    // FIX ME: does not work due to prop.clear() in PropertiesIO.save
                    properties.set( PHYSICS_PROPERTIES_KEY, (String) e.getItem() );
                }
            }
        } );

        final JComponent centerPanel = (JComponent) ((JComponent)dialog.getContentPane().getComponent( 0 )).getComponent( 0 );
        JComponent optionsPanel = (JComponent) centerPanel.getComponent( 1 );
        optionsPanel.add( implBox );

        dialog.pack();
        dialog.setVisible( true );

        if ( dialog.isCancelled() ) {
            //System.exit(0);
            finish();
        }

        chosenImpl = (String) implBox.getSelectedItem();
        if ( chosenImpl != null )
        {
            PhysicsSpace.chooseImplementation( chosenImpl );
        }
    }
}

/*
 * $Log: SimplePhysicsTest.java,v $
 * Revision 1.1  2007/11/26 10:28:35  irrisor
 * some improvents on choosing the physics implementation, impl specific debug view possible, readme updated
 *
 */

