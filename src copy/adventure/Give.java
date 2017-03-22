
package adventure;

/*
 * Verb GIVE
 *
 * GIVE thing person
 *
 */

public class Give extends Verb {

  protected Give () {
    super("give");
  }
  
  public static Verb create () {
    return new Give();
  }
  
  public Result action (Thing obj, Thing indObj) {
    // first some error checking
    if (!World.me().haveThing(obj)) 
      World.tellWorld("Not carrying " + obj.name());
    else if (!(indObj instanceof Person)) {
      World.tellWorld("Target " + indObj.name() + 
		      " not a person");
    } else {
      Person target = (Person) indObj;
      // If we carry the object, then it must be a MobileThing
      MobileThing mobileObj = (MobileThing) obj;
      // If we have the target just take the object from us, then 
      // our persona will complain that the object was lost
      // let's drop it on the floor first
      World.me().drop(mobileObj);
      target.take(mobileObj);
    }
    return Result.STOP;
  }


}
