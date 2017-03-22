
package adventure;
import java.util.*;

/*
 * Class Professor
 * 
 * A person that turns graded homeworks into passing grades
 *
 */

public class Professor extends AutonomousPerson {

  protected Professor (String name,
		       Place birthplace,
		       int speed,
		       int miserly) {
    super(name,birthplace,speed,miserly);
  }

  public static Professor create (String name,
				  Place birthplace,
				  int speed,
				  int miserly) {
    Professor obj = new Professor(name,birthplace,speed,miserly);
    obj.install();
    return obj;
  }

  /* when asked, checks to see if he/she has a graded homework
   * if so, we pass the class and we're done
   */
  public void ask () {
    LinkedList<Thing> homeworks = this.hasA(GradedHomework.isA());
    if (homeworks.isEmpty()) {
      this.say ("Why are you bothering me now?");
    } else {
      this.say ("Look at that, someone at least will pass this course!");
      this.say ("Congratulations!");
      System.exit(0);
    }
  }

  public static Predicate isA () {
    return new ProfessorPredicate();
  }

  private static class ProfessorPredicate implements Predicate {
    public ProfessorPredicate () {}
    public boolean check (Thing t) {
      return (t instanceof Professor);
    }
  }

}
