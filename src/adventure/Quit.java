
package adventure;

/*
 * Verb QUIT
 *
 * QUIT
 *
 */

public class Quit extends Verb {

    protected Quit () {
	super("quit");
    }

    public static Verb create () {
	return new Quit();
    }

    public Result action () {
	System.out.println("Goodbye");
	System.exit(0);
	return Result.STOP;
    }

}
