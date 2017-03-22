
package adventure;

/*
 * Class GradedHomework
 *
 * A homework ready to be handed in
 *
 */

public class GradedHomework extends Homework {
  
  //  the core name from which the actual homework name is derived
  private String core;

  /* Note how the name of the graded homework is derived
   * from the core name
   */
  protected GradedHomework (String n, Container l) {
    super ("graded-" + n,l);
    core = World.noSpaces(n);
  }

  public static GradedHomework create (String n, Container l) {
    GradedHomework obj = new GradedHomework (n,l);
    obj.install ();
    return obj;
  }

  public String coreName () {
    return this.core;
  }

  public static Predicate isA () {
    return new GradedHomeworkPredicate();
  }

  private static class GradedHomeworkPredicate implements Predicate {
    public GradedHomeworkPredicate () {}
    public boolean check (Thing t) {
      return (t instanceof GradedHomework);
    }
  }


}

