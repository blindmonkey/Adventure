package adventure;
/**
 * Verb enter to enter portals and dungeons 
 * @author Aaron
 *
 */

public class Enter extends Verb {

    protected Enter () {
        super("enter");
    }

    public static Verb create () {
        return new Enter();
    }

    public Result action () {
        if (World.me().go(this.word()))
            return Result.CONTINUE;
        return Result.STOP;
    }


}
