
package adventure;

/*
 * Verb TAKE
 * 
 * TAKE thing
 *
 */

public class Take extends Verb {

  protected Take () {
    super("take");
  }
  
  public static Verb create () {
    return new Take();
  }
  
  public Result action (Thing obj) {
    World.me().take(obj);
    return Result.STOP;
  }


}
