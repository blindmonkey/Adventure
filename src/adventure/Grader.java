package adventure;

import java.util.LinkedList;



public class Grader extends AutonomousPerson {


    protected Grader (String name,
                    Place birthplace,
                    int speed,
                    int miserly) {
        super(name,birthplace,speed,miserly);
    }

    public static Grader create (String name,
                    Place birthplace,
                    int speed,
                    int miserly) {
        Grader obj = new Grader(name,birthplace,speed,miserly);
        obj.install();
        return obj;
    }

    /* when asked, checks to see if he/she has a graded homework
     * if so, grade the homework
     */
    public void ask () {
        // get all finished homeworks from the user
        LinkedList<Thing> finished = this.hasA (CompletedHomework.isA());
        if (finished.isEmpty()) {
            this.say ("You need to finish your homework!");
        } else {
            // convert each homework
            for (Thing t : finished) {
                Homework hw = (Homework) t;
                String output;
                this.say("Okay, time to grade " + hw.name());
                output = scheme.Scheme.evaluate(hw.getCode() + "\n" + hw.getTests());
                if (output.charAt(output.length()-1)=='t') {
                    this.say("You completed the homework correctly.");
                    GradedHomework.create(hw.coreName().toString(),
                                    this.location(), hw.getAssignment(), hw.getTests());
                    // make sure we destroy the unfinished homework here
                    hw.destroy();
                } else {
                    this.say("You did " + hw.name() + " wrong.  My computer said:\n" + output);
                }
            }
        }
    }

    public static Predicate isA () {
        return new GraderPredicate();
    }

    private static class GraderPredicate implements Predicate {
        public GraderPredicate () {}
        public boolean check (Thing t) {
            return (t instanceof Grader);
        }
    }

}
