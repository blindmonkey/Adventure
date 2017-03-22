package adventure;

/**
 * Verb equip allows a person to equip an item
 * @author Aaron
 *
 */
public class Equip extends Verb {

    protected Equip() {
        super("equip");
    }

    public static Verb create () {
        return new Equip();
    }


    public Result action (Thing obj) {
        if (obj instanceof Items) {
            // we know this is a thing artifact
            (World.me()).equip(obj);
        } else
            World.me().say ("I try but cannot equip " + obj.name());
        return Result.STOP;
    }

}
