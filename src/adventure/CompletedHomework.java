
package adventure;

/*
 * Class CompletedHomework
 *
 * A homework ready to be graded...
 *
 */

public class CompletedHomework extends Homework {

  //  the core name from which the actual homework name is derived
  private String core;

  /* Note how the name of the completed homework is derived
   * from the core name
   */
  protected CompletedHomework (String n, Container l, String assignment, String tests) {
    super ("completed-" + n,l,assignment,tests);
    core = World.noSpaces(n);
  }

  public static CompletedHomework create (String n, Container l, String assignment, String tests) {
    CompletedHomework obj = new CompletedHomework (n,l,assignment,tests);
    obj.install ();
    return obj;
  }

  public String coreName () {
    return this.core;
  }

  public static Predicate isA () {
    return new CompletedHomeworkPredicate();
  }

  private static class CompletedHomeworkPredicate implements Predicate {
    public CompletedHomeworkPredicate () {}
    public boolean check (Thing t) {
      return (t instanceof CompletedHomework);
    }
  }


}

