
package adventure;
import java.util.*;

/*
 * Class Avatar
 *
 * The avatar is the character the player controls
 *
 */

public class Avatar extends Person {
  
  protected Avatar (String name, Place birthplace) {
    super(name,birthplace);
    // add extra health to avatar
    mutateHealth (5);
  }

  public static Avatar create (String name, Place birthplace) {
    Avatar obj  = new Avatar(name,birthplace);
    obj.install();
    return obj;
  }

  /*
   * return all the names of things in the list, as a string
   *
   */
  public static String getNames (LinkedList<? extends Root> l) {
    String result = "";
    for (Root obj : l)
      result = result + obj.name() + " ";
    return result;
  }


  public void lookAround () {
    Place place = (Place) this.location();
    LinkedList<Exit> exits = place.exits();
    LinkedList<Person> otherPeople = this.peopleAround();
    LinkedList<Thing> myStuff = this.things();
    LinkedList<Thing> stuff = this.stuffAround();

    World.tellWorld ("You are in " + place.name());

    if (myStuff.isEmpty())
	World.tellWorld ("You are not holding anything");
    else
	World.tellWorld ("You are holding: " + World.getNames (myStuff));

    if (stuff.isEmpty())
	World.tellWorld ("There is no stuff in the room");
    else
	World.tellWorld ("You see stuff in the room: " + 
			 World.getNames (stuff)); 

    if (otherPeople.isEmpty())
	World.tellWorld ("There are no other people around you");
    else
	World.tellWorld ("You see other people: " + 
			 World.getNames (otherPeople)); 
    
    if (exits.isEmpty())
	World.tellWorld ("There are no exits");
    else
	World.tellWorld ("The exits are in directions: " + 
			 World.getNames (exits));

  }


  public boolean go (String direction) {
    boolean success = super.go(direction);
    return success;
  }


  public void die () {
    this.say ("I am slain!");
    super.die();
    World.tellWorld("(I am afraid this game is over for you)");
    System.exit(0);
  }

  public static Predicate isA () {
    return new AvatarPredicate();
  }

  private static class AvatarPredicate implements Predicate {
    public AvatarPredicate () {}
    public boolean check (Thing t) {
      return (t instanceof Avatar);
    }
  }


}
