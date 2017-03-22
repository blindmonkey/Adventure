
package adventure;
import java.util.*;

/*
 * Class AutonomousPerson
 *
 * Persons that can walk around on their own 
 * Movement is implemented via a registered callback
 *
 */

public class AutonomousPerson extends Person {  

    // chance of movement at every turn
    private int restlessness;
    // chance of taking something at every turn
    private int miserly;
    // identifier for the callback (in order to remove it)
    private int cbRecord;
    // good or evil in a person
    // all people start in the middle and by turn change
    // good < 50 < evil
    private static int initialIntent = 50;
    private int intent;

    protected AutonomousPerson (String name, 
                    Place birthplace,
                    int restlessness, 
                    int miserly) {
        super(name,birthplace);
        this.restlessness = restlessness;
        this.miserly = miserly;
        this.intent = initialIntent;
    }

    public static AutonomousPerson create (String name,
                    Place birthplace,
                    int r, int m) {
        AutonomousPerson obj = new AutonomousPerson(name,birthplace,r,m);
        obj.install();
        return obj;
    }

    public void install () {
        super.install();
        this.cbRecord = Clock.registerCallback(new MoveAndTakeStuffCallback(this));
    }


    // the callback wraps the moveAndTakeStuff method
    private static class MoveAndTakeStuffCallback implements Callback {

        private AutonomousPerson person;

        public MoveAndTakeStuffCallback (AutonomousPerson p) {
            this.person = p;
        }

        public void action () {
            this.person.moveAndTakeStuff();
            //mutateIntent();
            //checkIntent();
        }

        
        public void mutateIntent () {
            if (this.person.intent > 50) {
                this.person.mutateIntent(2);
            }
            else this.person.mutateIntent(-2);
        }
        
        public void checkIntent() {
            if (person.getIntent() < 40) {
                World.tellRoom(person.location(), "I need to help someone out.");
            }
            if (40 < person.getIntent() && person.getIntent() < 60) { 
                World.tellRoom(person.location(), "I don't know" +
                		" how I feel about you.");
            } else {
                World.tellRoom(person.location(), "I feel like hurting someone.");
            }
        }
    }

    public void moveAndTakeStuff () {
        if (World.random(this.restlessness) == 1)
            this.moveSomewhere();
        if (World.random(this.miserly) == 1)
            this.takeSomething();
    }

    public int getIntent () {
        return this.intent;
    }
    
    public void mutateIntent ( int i ) {
        intent += i;
    }

    public void changeRestlessness (int newRestlessness) {
        this.restlessness = newRestlessness;
    }

    public void die () {
        Clock.removeCallback(this.cbRecord);
        this.say("SHREEEEEK! I, uh, suddenly feel very faint...");
        super.die();
    }

    public void moveSomewhere () {
        Exit exit = ((Place) this.location()).randomExit();
        if (exit!=null)
            this.goExit(exit);
    }

    public void takeSomething () {
        //openSomething();
        LinkedList<Thing> pickFrom = this.stuffAround();
        pickFrom.addAll(this.peekAround());
        if (!pickFrom.isEmpty())
            this.take(World.pickRandom(pickFrom));
    }

    public void openSomething () {
        LinkedList<Thing> pickFrom = this.stuffAround();
        for (Thing t : pickFrom) {
            if (t instanceof Chest) { 
                Chest c = new Chest (t.name(), t.location());
                c = (Chest)t;
                LinkedList<Thing> contents = new LinkedList<Thing> ();
                contents = c.things();
                for (Thing m : contents)
                    this.take(m);
            }
        }
    }

    public static Predicate isA () {
        return new AutonomousPersonPredicate();
    }

    private static class AutonomousPersonPredicate implements Predicate {
        public AutonomousPersonPredicate () {}
        public boolean check (Thing t) {
            return (t instanceof AutonomousPerson);
        }
    }

}
