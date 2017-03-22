
package adventure;
import java.util.*;

/*
 * Class Troll
 *
 * Person that walks around eating people
 *
 */

public class Troll extends AutonomousPerson {

  private int hunger;
  private int cbRecord;

  private static int initialMiserly = 10;

  protected Troll (String name,
		   Place birthplace,
		   int speed,
		   int hunger) {
    super(name,birthplace,speed,initialMiserly);
    this.hunger = hunger;
  }

  public static Troll create (String name,
			      Place birthplace,
			      int speed,
			      int hunger) {
    Troll obj = new Troll(name,birthplace,speed,hunger);
    obj.install();
    return obj;
  }

  public void install () {
    super.install();
    this.cbRecord = Clock.registerCallback(new EatPeopleCallback(this));
  }

  /* 
   * Callback for the troll
   * A wrapper around the eatPeople() method
   */
  private static class EatPeopleCallback implements Callback {
    private Troll troll;
    public EatPeopleCallback (Troll t) {
      this.troll=t;
    }
    public void action () {
      this.troll.eatPeople();
    }
  }

  /* if the troll is hungry, pick a random person around
   * and inflict a random amount of damage (using suffer()) method
   */
  public void eatPeople () {
    if (World.random(this.hunger) == 0) {
      LinkedList<Person> people = this.peopleAround();
      if (!people.isEmpty()) {
	Person victim = World.pickRandom(people);
	this.emit(this.name() + " takes a bite out of " + 
		  victim.name());
	victim.suffer(World.randomNumber(3));
      } else {
	this.emit(this.name() + "'s belly rumbles");
      }
    }
  }

  public void die () {
    Clock.removeCallback(this.cbRecord);
    super.die();
  }

  public static Predicate isA () {
    return new TrollPredicate();
  }

  private static class TrollPredicate implements Predicate {
    public TrollPredicate () {}
    public boolean check (Thing t) {
      return (t instanceof Troll);
    }
  }



}

