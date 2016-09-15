/***
 *  Sal: Simple Actor Library (available free at sal.pz.org)
 *
 *  Sal is (c) Copyright 2009 Pacific Data Works LLC. All Rights Reserved.
 *  Licensed under Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0.html)
 */

package org.pz.sal;

/**
 * Start-up and shutdown routines for the Simple Actor Library
 *
 * @author alb
 */
public class Sal
{
    private ActorRegistry registry = null;

    /**
     * Creates a registry (which is implemented as an actor) for
     * the actors in this program and starts it up on its own thread.
     *
     * @return the registry
     */
    public ActorRegistry initialize()
    {
        registry = new ActorRegistry();
        Thread registryThread = new Thread( registry );
        registryThread.start();
        return( registry );
    }

    /**
     * Registers a new actor with the registry and starts it up.
     *
     * @param actor the new actor
     */
    public void addActor( Actor actor )
    {
        registry.addActor( actor.getName(), actor.getQueue() );
        actor.setRegistry( registry );
        new Thread( actor ).start();
    }

    /**
     * Closes down Sal, by closing down the registry.
     * TODO: should place a shutdown message in all actor queues.
     */
    public void shutdown()
    {
        if( registry != null ) {
            registry.shutdown();
        }
    }
}
