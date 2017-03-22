
package adventure;

/*
 * Verb DEBUG
 *
 * DEBUG
 *
 */

public class Debug extends Verb {

    protected Debug () {
	super("debug");
    }

    public static Verb create () {
	return new Debug();
    }

    public Result action () {
      World.setDebugFlag (!World.isDebug());
      World.tellWorld ("Setting debug mode to " + World.isDebug());
      return Result.STOP;
    }

}
