
package adventure;

/*
 * Verb LOOK
 *
 * LOOK 
 *
 */

public class Look extends Verb {

    protected Look () {
	super("look");
    }

    public static Verb create () {
	return new Look();
    }

    public Result action () {
      World.me().lookAround();
      return Result.STOP;
    }

}
