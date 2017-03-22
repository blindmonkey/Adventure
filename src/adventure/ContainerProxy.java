
package adventure;
import java.util.*;

/*
 * Class ContainerProxy
 *
 * A simple proxy class that wraps some simple methods around a
 * LinkedList<Thing> object.
 *
 */

public class ContainerProxy implements Container {

  private LinkedList<Thing> things;

  private ContainerProxy () {
    this.things = new LinkedList<Thing> ();
  }

  public static ContainerProxy make () {
    return new ContainerProxy ();
  }

  public String name () {
    throw new RuntimeException ("Invoking name on container subobject - should never happen");
  }

  // Note that this returns a fresh list with the content
  public LinkedList<Thing> things () {
    return new LinkedList<Thing> (this.things);
  }

  // check if t appears in the content
  public boolean haveThing (Thing t) {
    return things.contains(t);
  }

  // add a thing to the content
  public void addThing (Thing t) {
    things.add(t);
  }

  // remove a thing from the content
  public void delThing (Thing t) {
    things.remove(t);
  }

}
