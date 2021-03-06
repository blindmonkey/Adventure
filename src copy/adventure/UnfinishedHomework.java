
package adventure;

/*
 * Class UnfinishedHomework
 * 
 * A homework ready to be completed...
 *
 */

public class UnfinishedHomework extends Homework {

  //  the core name from which the actual homework name is derived
  private String core;

  /* Note how the name of the unfinished homework is derived
   * from the core name
   */
  protected UnfinishedHomework (String n, Container l) {
    super ("unfinished-" + n,l);
    core = World.noSpaces (n);
  }

  public static UnfinishedHomework create (String n, Container l) {
    UnfinishedHomework obj = new UnfinishedHomework (n,l);
    obj.install ();
    return obj;
  }

  public String coreName () {
    return this.core;
  }

  public static Predicate isA () {
    return new UnfinishedHomeworkPredicate();
  }

  private static class UnfinishedHomeworkPredicate implements Predicate {
    public UnfinishedHomeworkPredicate () {}
    public boolean check (Thing t) {
      return (t instanceof UnfinishedHomework);
    }
  }


}

