
package adventure;

/* Verb USE
 *
 * USE thing
 *
 */

public class Use extends Verb {

  protected Use () {
    super("use");
  }
  
  public static Verb create () {
    return new Use();
  }
  

  public Result action (Thing obj) {
    if (obj instanceof Usable) {
      // can only use a "Usable" artifact
      Usable usableObj = (Usable) obj;
      usableObj.use(World.me());
    } else
      World.me().say ("I try but cannot use " + obj.name());
    return Result.STOP;
  }

}
