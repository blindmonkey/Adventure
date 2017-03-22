
package adventure;

/*
 * Verb WAIT
 *
 * WAIT
 *
 */

public class Wait extends Verb {

  protected Wait () {
    super("wait");
  }
  
  public static Verb create () {
    return new Wait();
  }
  
  public Result action () {
    return Result.CONTINUE;
  }
  
}
