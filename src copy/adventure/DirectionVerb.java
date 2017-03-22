
package adventure;

/*
 * Verbs for going in some directions
 *
 * <DIRECTION>
 *
 */

public class DirectionVerb extends Verb {

    protected DirectionVerb (String w) {
	super(w);
    }

    public static Verb create (String w) {
	return new DirectionVerb(w);
    }

    public Result action () {
	if (World.me().go(this.word()))
	    return Result.CONTINUE;
	else 
	    return Result.STOP;
    }


}
