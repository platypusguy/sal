/***
 *  Sal: Simple Actor Library (available free at sal.pz.org)
 *
 *  Sal is (c) Copyright 2010 Pacific Data Works LLC. All Rights Reserved.
 *  Licensed under Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0.html)
 */

package org.pz.sal.commonActors;

import org.pz.sal.Actor;
import org.pz.sal.Commands;

/**
 * Simple actor that prints text to stderr
 *
 * @author alb
 */
public class Stderr extends Actor
{
    public Stderr( final String assignedName )
    {
        super( assignedName );
    }

    @Override
    public void processMessage( final Object o )
    {
        assert( o != null );

        String msg;

        if( o instanceof String ) {
            msg = (String) o;

            if( msg.isEmpty() ) {
                return;
            }

            if( msg.equals( Commands.SHUTDOWN )) {
                super.shutdown();
            }
            else {
                System.err.println( msg );
            }
        }
    }
}