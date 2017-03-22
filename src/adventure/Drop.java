
package adventure;

/* 
 * Verb DROP
 *
 * DROP thing
 *
 */

public class Drop extends Verb {

  protected Drop () {
    super("drop");
  }
  
  public static Verb create () {
    return new Drop();
  }
  
  public Result action (Thing obj) {
    if (!World.me().haveThing(obj)) 
      World.me().say("I am not carrying " + obj.name());
    else 
      // If I'm carrying the object, then it must be a MobileThing
      World.me().drop((MobileThing) obj);
    return Result.STOP;
  }


}
