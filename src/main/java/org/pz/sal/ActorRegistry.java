/***
 *  Sal: Simple Actor Library (available free at sal.pz.org)
 *
 *  Sal is (c) Copyright 2009 Pacific Data Works LLC. All Rights Reserved.
 *  Licensed under Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0.html)
 */

package org.pz.sal;

import java.util.concurrent.*;
import java.util.*;

/**
 * The main communication point. Consists of a hash map containing the name of the
 * actors and the location of their message queues.
 *
 * Implemented as an actor. (We eat our own dogfood!)
 *
 * @author alb
 */
@SuppressWarnings("unchecked")
public class ActorRegistry extends Actor
{
    /** the table that maps actor names to their queues */
    private HashMap<String, LinkedBlockingQueue<Object>> lookupTable;

    ActorRegistry()
    {
        super( "_sal_registry_" );
        lookupTable = new HashMap<String, LinkedBlockingQueue<Object>>();
    }

    /**
     * Accepts a message and puts it in the main queue from which it will be
     * sent to the queue of the destination actor
     *
     * @param msg message to be sent to the actor
     */
    public void send( final IMessage msg )
    {
        if( msg !=  null ) {
                try {
                    queue.put( msg );
                }
                catch ( InterruptedException ie ) {
                    System.out.println( "Interrupted Exception in ActorRegistry.send()" );
                }

//            System.out.println( "msg added. Dest: " + msg.getDestination() +
//                    " Content: " + msg.getContent().toString() );
        }
    }

    /**
     * Adds an actor to the registry immediately. Ideally, it would have been nice
     * to make this a message put in the registry's queue, but because of the inherent
     * non-determinism, this step must be done immediately (that is, before a message
     * is sent to the act or.
     *
     * @param actorName  name of actor to add
     * @param actorQueue queue for messages to the actor
     */
    public void addActor( String actorName, LinkedBlockingQueue<Object> actorQueue )
    {
        synchronized( this ) {
            lookupTable.put( actorName, actorQueue );
        }
    }

    /**
     * Removes an actor to the registry by placing a message to do this add in the
     * registry's own queue.
     *
     * @param actorName name of actor to remove
     */
    public void removeActor( final String actorName )
    {
        send( new Message( Commands.REMOVE_ACTOR, actorName ));
    }

    /**
     * The main method, which retrieves messages from its own queue and
     * places them in the correct actor's queues. If it finds messages for
     * itself, it performs them then and there. Consequently, it's helpful
     * to limit the number of registry-specific functions.
     *
     * @param obj The retrieved from the registry's queue and placed in the correct actor's queue
     */
    @Override
    public void processMessage( final Object obj )
    {
        if( obj == null ) {
            return;
        }

        IMessage msg = (IMessage) obj;

        if( msg.getSender() != null ) {
            if( msg.getSender().startsWith( "_registry_" )) {
                processAdminTask( msg );
                return;
            }
        }

        if( msg.getDestination() != null ) {
            if( msg.getDestination().startsWith( "_registry_" )) {
                processAdminTask( msg );
                return;
            }
        }

        addMessageToActorQueue( msg );
    }

    /**
     * The principal function, which puts the message in the correct actor's queue
     *
     * @param msg The message to be added.
     */
    private void addMessageToActorQueue( final IMessage msg )
    {
        LinkedBlockingQueue<Object> actorQueue = lookupTable.get( msg.getDestination() );
        if( actorQueue != null ) {
            synchronized( this ) {
                actorQueue.add( msg.getContent() );
            }
        }
        else {
            System.err.println( "Sal Error: actor " + msg.getDestination() + " not registered" );
        }
    }

    /**
     * Handle registry functions
     *
     * @param msg the message to be executed
     */
    private void processAdminTask( final IMessage msg )
    {
        if( msg.getDestination().equals( Commands.REMOVE_ACTOR )) {
            removeActorFromLookupTable( (String) msg.getContent() );
        }
        else
        if( msg.getDestination().equals( Commands.REGISTRY_SHUTDOWN )) {
            running = false;
        }
    }

    /**
     * The low-level function that adds an actor to the lookup table.
     *
     * @param actor the actor to add
     */
    private void addActorToLookupTable( final Actor actor )
    {
        if( actor == null ) {
            return;
        }

        String actorName = actor.getName();
        LinkedBlockingQueue<Object> actorQueue = actor.getQueue();

        synchronized( this ) {
            lookupTable.put( actorName, actorQueue );
        }
    }

    private void addActorToLookupTable( final String actorName, final Object actorQueueObject )
    {
        if( actorName == null || actorQueueObject == null ) {
            return;
        }

        LinkedBlockingQueue<Object> actorQueue = (LinkedBlockingQueue<Object>) actorQueueObject;

        synchronized( this ) {
            lookupTable.put( actorName, actorQueue );
        }
    }

    /**
     * The low-level function that removes an actor from the lookup table.
     *
     * @param actorName the actor to add
     */
    private void removeActorFromLookupTable( final String actorName )
    {
        if( actorName == null ) {
            return;
        }

        synchronized( this ) {
            lookupTable.remove( actorName );
        }
    }

    @Override
    public void shutdown()
    {
        send( new Message( Commands.REGISTRY_SHUTDOWN, Commands.SHUTDOWN ));
    }

    public int getSize()
    {
        return( lookupTable.size() );
    }
}
