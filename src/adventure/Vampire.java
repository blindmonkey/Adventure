package adventure;

import java.util.LinkedList;

public class Vampire extends Troll {

    private int cbRecord;

    private static int initialDeviance = 10;

    protected Vampire (String name,
                    Dungeon birthplace,
                    int speed,
                    int hunger) {
        super(name,birthplace,speed,initialDeviance);
        this.increaseAtt("strength", 5);
        this.increaseAtt("dexterity", 15);
        this.increaseAtt("mana", -7);
        this.mutateHealth(3);
    }

    public static Vampire create (String name,
                    Dungeon birthplace,
                    int speed,
                    int hunger) {
        Vampire obj = new Vampire(name,birthplace,speed,hunger);
        obj.install();
        return obj;
    }

    public void install () {
        super.install();
        this.cbRecord = Clock.registerCallback(new BitePeopleCallback(this));
    }

    /* 
     * Callback for the Vampire
     * A wrapper around the eatPeople() method
     */
    private static class BitePeopleCallback implements Callback {
        private Vampire vampire;
        public BitePeopleCallback (Vampire t) {
            this.vampire=t;
        }
        public void action () {
            this.vampire.bitePeople();
        }
    }

    /* 
     * bite person in the room
     * and inflict a random amount of damage (using suffer()) method
     */
    public void bitePeople () {
        LinkedList<Person> people = this.peopleAround();
        if (!people.isEmpty()) {
            for (Person p : people)
                if (!(p instanceof Vampire))this.attack(p);
        }
    }
    
    /* 
     * Overwrite this method so Vampires don't randomly bite 
     */
    public void eatPeople () {
    }
    
    /*
     * Overwrite this method so vampires won't move around
     * or else all hell will break loose
     * @see adventure.AutonomousPerson#moveAndTakeStuff()
     */
    public void moveAndTakeStuff () {  }

    public void die () {
        Clock.removeCallback(this.cbRecord);
        super.die();
    }

    public static Predicate isA () {
        return new VampirePredicate();
    }

    private static class VampirePredicate implements Predicate {
        public VampirePredicate () {}
        public boolean check (Thing t) {
            return (t instanceof Vampire);
        }
    }
}
