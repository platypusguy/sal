/***
 *  Sal: Simple Actor Library (available free at sal.pz.org)
 *
 *  Sal is (c) Copyright 2009 Pacific Data Works LLC. All Rights Reserved.
 *  Licensed under Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0.html)
 */

package org.pz.sal;

/**
 * The messages sent between actors. These messages consist of the name of the sending
 * actor (optional and used in request-receive), the destination actor, and an Object
 * (generally a class) that is the payload passed to the destination actor.
 *
 * @author alb
 */
public interface IMessage
{
    public String getSender();

    public String getDestination();

    public Object getContent();
}
