
package adventure;
import java.util.*;
import scheme.*;

/* 
 * Class Computer
 *
 * A thing that can transform unfinished homeworks into completed
 * homeworks
 *
 */

public class Computer extends Thing implements DualUsable {

    protected Computer (String name, Container location) {
        super(name,location);
    }

    public static Computer create (String name, Container location) {
        Computer obj =  new Computer(name,location);
        obj.install();
        return obj;
    }


    /*
   public void use (Person user) {
      // get all unfinished homeworks in the user of the computer
      LinkedList<Thing> unfinished = user.hasA (UnfinishedHomework.isA());
      if (unfinished.isEmpty()) {
         user.say ("Aargh! This computer is near useless!");
      } else {
         // convert each homework
         for (Thing t : unfinished) {
            Homework hw = (Homework) t;
            user.say("Okay, time to finish " + hw.name());
            CompletedHomework.create(hw.coreName().toString(),
                  user.location());
            // make sure we destroy the unfinished homework here
            hw.destroy();
         }
      }

   }*/

    public void use (Person user, MobileThing hw) {
        if (!(hw instanceof UnfinishedHomework || hw instanceof CompletedHomework))
            user.say("Don't know how to use computer on " + hw.name());
        else {
            Scheme.startEditor((Homework)hw);
            if (hw instanceof UnfinishedHomework) {
                CompletedHomework.create(((Homework)hw).coreName(), user.location(),
                                ((Homework)hw).getAssignment(),
                                ((Homework)hw).getTests()).setCode(((Homework)hw).getCode());
                hw.destroy();
            }
        }
    }

}
