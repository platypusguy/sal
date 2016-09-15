/***
 *  Sal: Simple Actor Library (available free at sal.pz.org)
 *
 *  Sal is (c) Copyright 2009 Pacific Data Works LLC. All Rights Reserved.
 *  Licensed under Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0.html)
 */

package org.pz.sal;

/**
 * The basic message sent between actors. The destination is the name of the actor to
 * send the payload to.
 *
 * @author alb
 */
public class Message implements IMessage
{
    private String sender;
    private String destination;
    private Object content;

    public Message( final String fromWhom, final String toWhom, Object what )
    {
        sender = fromWhom;
        destination = toWhom;
        content = what;
    }

    public Message( final String whereTo, Object what )
    {
        this( null, whereTo, what );
    }

    public Object getContent()
    {
        return( content );
    }

    public String getDestination()
    {
        return( destination );
    }

    public String getSender()
    {
        return( sender );
    }
}
