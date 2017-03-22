
package adventure;

/*
 * Verb ASK
 *
 * ASK person
 * 
 */

public class Ask extends Verb {

  protected Ask () {
    super("ask");
  }
  
  public static Verb create () {
    return new Ask();
  }
  
  public Result action (Thing obj) {
    if (obj instanceof Person) {
      Person person = (Person) obj;
      person.ask();
    } else
      World.me().say ("I feel weird asking something of " + 
		      obj.name());
    return Result.STOP;
  }

}
