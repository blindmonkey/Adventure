
package adventure;
import java.util.*;

/* 
 * Class World
 *
 * A class implementing utility functions for managing the state of the
 * world
 *
 * All fields and methods in the class are static
 *
 */

public class World {

  // these fields are set by the main function
  private static Avatar me;  
  // the list of all rooms registered in the world
  private static LinkedList<Place> rooms = new LinkedList<Place>();
  // the list of all dungeon rooms registered in the world
  private static LinkedList<Dungeon> dungeon = new LinkedList<Dungeon>();
  // lists of chests in the world
  private static LinkedList<Chest> chests = new LinkedList<Chest> ();
  
  // are we in debugging mode?
  private static boolean debug = false;

  // the list of registered verbs
  private static LinkedList<Verb> verbs = new LinkedList<Verb>();

  // a specific exception
  public static class ObjNotFoundException extends RuntimeException {
    
      private String name;
      
      public ObjNotFoundException (String n) {
	  name=n;
      }
      
      public String getMessage () {
	  return this.name;
      }
  }
    
  public static void setDebugFlag (boolean flag) {
    debug = flag;
  }
  
  // find verb corresponding to the given string
  public static Verb findVerb (String w) {
    for (Verb v : verbs) 
      if (v.word().equals(w))
	return v;
    throw new ObjNotFoundException (w);
  }
  
  public static void registerVerb (Verb v) {
    verbs.add(v);
  }
    
  public static boolean isDebug () {
    return debug;
  }

  // write a message on the screen if and only if the player is in the room
  public static void tellRoom (Container room, String msg) {
    if (isDebug())
      System.out.println(msg);
    else if (me.location() == room) {
      System.out.println(msg);
    }
  }

  // write a message on the screen irrespectively of where the player is
  public static void tellWorld (String msg) {
    System.out.println(msg);
  }

  public static void registerAvatar (Avatar newMe) {
    me = newMe;
  }

  public static Place registerRoom (Place room) {
    rooms.add (room);
    return room;
  }
  
  public static Dungeon registerDungeonRoom (Dungeon room) {
      dungeon.add(room);
      return room;
  }
  
  public static Chest registerChest (Chest c) {
      chests.add(c);
      return c;
  }

  public static LinkedList<Place> rooms () {
    return rooms;
  }

  public static LinkedList<Dungeon> dungeon () {
      return dungeon;
  }
  
  public static LinkedList<Chest> chests () {
      return chests;
  }
  
  public static Avatar me () {
    return me;
  }
  
  // get list of names corresponding to list of things as a string
  public static String getNames (LinkedList<? extends Root> l) {
    String result = "";
    for (Root obj : l)
      result = result + obj.name() + " ";
    return result;
  }

  //  seed for getting randomness
  private static Random rnd = new Random();

  // return a random integer between 0 and bound-1
  public static int random(int bound) {
    return rnd.nextInt(bound);
  }

  // return a random integer between 1 and n
  public static int randomNumber(int bound) {
    return rnd.nextInt(bound)+1;
  }

  //  pick an element of a list at random
  public static <U> U pickRandom (LinkedList<U> list) {
    if (list.isEmpty())
      throw new RuntimeException ("pickRandom() on empty list");
    return list.get(random(list.size()));
  }


  //  remove spaces from names (replacing by '-')
  public static String noSpaces (String text) {
    return text.split("\\s++")[0].toLowerCase();
  }


  /* Grab any kind of thing from avatar's location, 
   * given its name.  The thing may be in the possession of
   * the place, or in the possession of a person at the place.
   */
  public static Thing thingNamed (String name) {
    Place here = (Place) me.location();
    LinkedList<Thing> all = new LinkedList<Thing> (here.things());
    all.addAll (me.peekAround());
    all.addAll (me.things());
    for (Thing t : all)
      if (t.name().equals(name))
	return t;
    throw new ObjNotFoundException (name);
  }


}


