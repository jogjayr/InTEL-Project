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
package com.jmetest.physicstut.lesson9;

import com.jmetest.physicstut.lesson9.controller.GameLogic;
import com.jmetest.physicstut.lesson9.model.Actor;
import com.jmetest.physicstut.lesson9.model.Platform;
import com.jmetest.physicstut.lesson9.model.World;
import com.jmetest.physicstut.lesson9.view.View;

/**
 * This lesson shows an example of building an application with proper design. It makes use of the Model/View/Controller
 * concept. This means the data model is separated from the vizualization (rendered stuff) and the controller (physics
 * and user input). This will notice that this makes the task of creating an initial version of the application quite
 * complex. But it is rewarded by maintainability, extensibility and readability. Additionally distribution or
 * replication of data gets a lot easier and thus allows a client-server or peer-to-peer application.
 * <p/>
 * First we should create our data model. Have a short look into the {@link World} class. In the
 * {@link #createSimpleWorld} method we create some model elements.
 * <p/>
 * The {@link #start} method calls that latter method. Afterwards it creates controller ({@link GameLogic}) and
 * {@link View}.
 *
 * @author Irrisor
 */
public class Lesson9 {

    /**
     * Create a world and some stuff in it - this could also load from file instead.
     * @return the new world :)
     */
    private World createSimpleWorld() {
        World world = new World();
        Actor actor1 = new Actor();
        actor1.setPosition( 0, 3, 0 );
        world.getItems().add( actor1 );
        Actor actor2 = new Actor();
        world.getItems().add( actor2 );
        actor2.setPosition( 4, -1, 0 );
        Platform platform = new Platform();
        platform.setPosition( 0, -4, 0 );
        world.getItems().add( platform );
        return world;
    }

    /**
     * Start our application. Invoked by the main method.
     */
    private void start() {
        // create a model
        World world = createSimpleWorld();
        // create a controller
        // we do this before creating the view - this helps us to mind not to use
        // anything from the view in the controller 
        GameLogic gameLogic = new GameLogic( world );
        // create a view
        View view = new View( world, gameLogic );
        // start the view
        view.start();
    }

    public static void main( String[] args ) {
        new Lesson9().start();
    }
}

/*
 * $Log$
 */

