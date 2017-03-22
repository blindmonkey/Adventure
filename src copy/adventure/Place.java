
package adventure;
import java.util.*;

/* 
 * Class Place
 *
 * Implements a room (which can have a content)
 *
 */

public class Place extends Root implements Container {

  // the content of the room
  private Container content;
  // the exits to the room
  private LinkedList<Exit> exits;

  protected Place (String n) {
    super(n);
    this.content = ContainerProxy.make();
    this.exits = new LinkedList<Exit>();
  }

  public static Place create (String n) {
    Place obj  = new Place(n);
    obj.install();
    return obj;
  }

  public LinkedList<Exit> exits () {
    return this.exits;
  }

  /* checks whether there is an exit in a particular direction
   * returns it if so
   */
  private Exit findExitInDirection(String direction) {
    for (Exit exit : this.exits)
      if (direction.equals(exit.direction()))
	return exit;
    return null;
  }

  public Exit exitTowards (String direction) {
    return this.findExitInDirection(direction);
  }
  
  /* add an exit to the list of exits
   */
  public void addExit (Exit exit) {
      // World.tellWorld (exit.toString());
    String direction = exit.direction();
    if (this.exitTowards(direction)!=null)
      throw new RuntimeException (this.name() + " already has exit " + 
				  direction);
    else
      this.exits.add (exit);
    // World.tellWorld (exits.toString());
  }

  public LinkedList<Thing> things () {
    return content.things();
  }

  public boolean haveThing (Thing t) {
    return content.haveThing(t);
  }

  public void addThing (Thing t) {
    content.addThing(t);
  }

  public void delThing (Thing t) {
    content.delThing(t);
  }

  /*
   * returns a random exit from the list of exits
   *
   * useful for AutonomousPerson movement
   *
   */
  public Exit randomExit () {
    if (exits.isEmpty()) return null;
    //    Exit[] exitsArray = (Exit[]) this.exits.toArray();
    int index = World.random(exits.size());
    return exits.get(index);
  }

}
