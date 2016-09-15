/***
 *  Sal: Simple Actor Library (available free at sal.pz.org)
 *
 *  Sal is (c) Copyright 2009 Pacific Data Works LLC. All Rights Reserved.
 *  Licensed under Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0.html)
 */

package org.pz.sal;

/**
 * Contains special values used by Sal commands for admin purposes. They all begin and end with
 * underscores, so this format should be avoided in messages sent by actors.
 *
 * @author alb
 */
public class Commands
{
    public static final String SHUTDOWN = "_shutdown_";

    /* === commands for the registry === */

    public static final String REMOVE_ACTOR         = "_registry_remove_actor_";
    public static final String REGISTRY_SHUTDOWN    = "_registry_shutdown_";
}
