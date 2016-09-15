/***
 *  Sal: Simple Actor Library (available free at sal.pz.org)
 *
 *  Sal is (c) Copyright 2009 Pacific Data Works LLC. All Rights Reserved.
 *  Licensed under Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0.html)
 */

package org.pz.sal;

/**
 *
 * @author alb
 */
public interface IActor
{
    public void processMessage( Object msg );

    public void run();

    public void shutdown();
}
