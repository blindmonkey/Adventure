
package adventure;
import java.util.*;

/*
 * Interface Container
 *
 * Implemented by artifacts that can contain stuff
 *
 */

public interface Container {

  public String name ();
  public LinkedList<Thing> things ();
  public boolean haveThing (Thing t);
  public void addThing (Thing t);
  public void delThing (Thing t);

}
