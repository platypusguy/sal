/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saltest;

import java.util.concurrent.LinkedBlockingQueue;
import org.pz.sal.*;

/**
 *
 * @author alb
 */
public class CalcAndPass extends Actor
{
    static int numberOfPasses = 0;
    LinkedBlockingQueue<Object> nextActorQueue = null;

    public CalcAndPass( final String name, Sal sal )
    {
        super( name );

        if( ! name.equals( "1" )) {
            int num = Integer.valueOf(name);
            Integer prevActor = num - 1;
            nextActorQueue = sal.getActorQueue(prevActor.toString());
        }
    }

    @Override
    public void processMessage( Object msg )
    {
        Integer nextActor = 0;
        final int MAX_REPS = 10000000;

        if( ! ( msg instanceof Integer )) {
            return;
        }
        
        numberOfPasses = (Integer) msg;

        if( this.getName().equals( "1" )) {
            numberOfPasses += 1;

            if( numberOfPasses > MAX_REPS) {
                sal.send( new Message( "log", "shutdown signal sent" ));
                sal.send( new Message( "monitor", "shutdown" ));
                return;
            }
            else
            if( numberOfPasses % ( MAX_REPS / 1000 ) == 0 ) {
                   sal.send( new Message( "log",
                        "actor: " + getName() +
                        " at loop: " + numberOfPasses ));
            }
        }

        // now send the message
        if( nextActorQueue != null ) {
             synchronized( this ) {
                nextActorQueue.add( numberOfPasses );
            }
        }
        else {
            // determine address of next actor.
            nextActor = Integer.valueOf( this.getName() );
            nextActor++;
            if( nextActor > 503 ) {
                nextActor = 1;
            }
            sal.send( new Message( nextActor.toString(),
                            numberOfPasses ));
        }
    }
}