/***
 *  Sal: Simple Actor Library (available free at sal.pz.org)
 *
 *  Sal is (c) Copyright 2009 Pacific Data Works LLC. All Rights Reserved.
 *  Licensed under Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0.html)
 */

package org.pz.sal;

import org.pz.sal.commonActors.Stdout;

/**
 * Simple example using Hello World
 *
 * @author alb
 */
public class Main
{
    /**
     * @param args the command-line arguments
     */
    public static void main(String[] args)
    {
        Sal sal = new Sal();
        ActorRegistry central = sal.initialize();

        sal.addActor( new Stdout( "stdout" ));

        central.send( new Message( "stdout", "hello, world!" ));
        central.send( new Message( "stdout", "hello again, world" ));
        central.send( new Message( "stdout", Commands.SHUTDOWN ));

        sal.shutdown();
    }

}
