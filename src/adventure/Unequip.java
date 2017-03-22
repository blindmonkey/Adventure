package adventure;

/**
 * Verb Unequip allows a person to unequip an item
 * @author Aaron
 *
 */
public class Unequip extends Verb {

    protected Unequip() {
        super("unequip");
        // TODO Auto-generated constructor stub
    }

    public static Verb create () {
        return new Unequip ();
    }

    public Result action (Thing obj) {
        if (obj instanceof Items) {
            // we know obj is an item
            (World.me()).unequip(obj);
        } else
            World.me().say ("I try but cannot unequip " + obj.name());
        return Result.STOP;
    }

}
