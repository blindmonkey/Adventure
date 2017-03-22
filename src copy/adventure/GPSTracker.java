package adventure;

import java.util.LinkedList;

/**
 * GPSTracker
 * @author Aaron Gooch
 * 
 * Implemented as part of assignment 1
 * 
 * Returns the location of all people in the game
 * 
 */
public class GPSTracker extends MobileThing implements Usable{

    protected GPSTracker(String n, Container l) {
        super("TX200", l);
    }

    public static GPSTracker create (String n, Container l) {
        GPSTracker obj = new GPSTracker (n,l);
        obj.install ();
        return obj;
    }

    public static Predicate isA () {
        return new GPSTrackerPredicate();
    }

    private static class GPSTrackerPredicate implements Predicate {
        public GPSTrackerPredicate () {}
        public boolean check (Thing t) {
            return (t instanceof GPSTracker);
        }
    }

    public void use(Person user) {
        // check if user has a the GPS
        LinkedList<Thing> GPS = (user.hasA (GPSTracker.isA()));
        if (GPS.isEmpty()){
            System.out.println("I don't have a GPS!");
        } else {
           // standard header sentence
            String header = "At " + user.location().name() + " " + user.name() 
            + " says --- "; 
            System.out.println(header + "I turn on TX200.");
            
        //return location of all people in the game
            /* create a list of all people in the game */
            LinkedList<Person> people = new LinkedList<Person>();
            /* go through each room in the world and get the people in them */
            for (int i = 0; i < World.rooms().size(); i++){
                Place p = World.rooms().get(i);
                for (int j = 0; j < p.things().size(); j++ ) {
                    Thing th = p.things().get(j);
                    /* check if thing is an instance of person */
                    if (th instanceof Person) people.add((Person)th);
                }
            }
            /* print to screen name and location of persons */
            for (int i = 0; i < people.size(); i ++) {
                Person p = people.get(i);
                System.out.println(header + p.name() + " is in " 
                                + p.location().name() + ".");
            }
        }

    }
}
