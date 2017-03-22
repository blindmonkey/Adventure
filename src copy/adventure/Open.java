package adventure;

import java.util.LinkedList;

/**
 * Verb for opening chests
 * @author Aaron
 *
 */
public class Open extends Verb {

    protected Open () {
        super("open");
    }

    public static Verb create () {
        return new Open();
    }

    public Result action (Thing obj) {
        if (!(obj instanceof Chest)) {
            World.me().say("I feel weird trying to open " + obj.name());
        }
        Chest c = (Chest) obj;
        LinkedList<Thing> contents = new LinkedList<Thing> ();
        contents = c.things();
        if (contents.isEmpty()) World.tellRoom(c.location(), 
                        "You find nothing.");
        /* 
         * "drop" the contents onto the ground
         * we do this by changing the location of each object in the contents
         *
         */
        for (Thing t : contents) {
            if (t instanceof Items) {
                Items it = new Items (t.name(), t.location(), 0, 0, 0, null);
                it = (Items) t;
                if (it.getType() == Items.type.WEAPON) 
                    Items.createWeapon(it.name(), c.location(),
                                    it.getItemStrength(), 
                                    it.getItemMana(),
                                    it.getItemDexterity());
                if (it.getType() == Items.type.ARMOR)
                    Items.createArmor(it.name(), c.location(),
                                    it.getItemStrength(), 
                                    it.getItemMana(),
                                    it.getItemDexterity());
                if (it.getType() == Items.type.SHIELD)
                    Items.createShield(it.name(), c.location(),
                                    it.getItemStrength(), 
                                    it.getItemMana(),
                                    it.getItemDexterity());
                if (it.getType() == Items.type.RING)
                    Items.createRing(it.name(), c.location(),
                                it.getItemStrength(), 
                                it.getItemMana(),
                                it.getItemDexterity());
                it.destroy();
            }
            World.tellRoom(c.location(), t.name() + " lays on the floor.");
        }
        return Result.STOP;
    }
}
