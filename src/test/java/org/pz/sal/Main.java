/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saltest;

import org.pz.sal.*;
import org.pz.sal.commonActors.*;

/**
 *
 * @author alb
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        int procs = Runtime.getRuntime().availableProcessors();
        System.out.println( "Number of processors: " + procs );
        
        Sal sal = new Sal();
        sal.initialize();
        sal.addActor( new Stdout( "stdout" ));
//        sal.addActor( new Log( "log" ));
//        sal.addActor( new Shutdown( "monitor" ));

        sal.send( new Message( "stdout", "SalTest v. 0.0.1" ));

        for( Integer i = 1; i <= 503; i++ ) {
            sal.addActor( new CalcAndPass( i.toString(), sal ));
        }

        sal.send( new Message( "1", 1 ));

    }
}
