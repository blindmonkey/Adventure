
package adventure;

/*
 * Class Homework
 *
 * Abstract class from which all homeworks derive
 *
 * This is useful for PrlGrad, which will take *any* homework
 * and destroy it
 *
 */

public abstract class Homework extends MobileThing {

  protected Homework (String n, Container l) {
    super(n,l);
  }

  public abstract String coreName ();

  public static Predicate isA () {
    return new HomeworkPredicate();
  }

  private static class HomeworkPredicate implements Predicate {
    public HomeworkPredicate () {}
    public boolean check (Thing t) {
      return (t instanceof Homework);
    }
  }


}

