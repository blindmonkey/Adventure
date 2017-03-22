package adventure;

public class Inventory extends Verb {

    /*
     * Verb Inventory 
     * 
     * Returns user's inventory and attributes
     * 
     * 
     */
    protected Inventory () {
        super("inventory");
    }

    public static Verb create () {
        return new Inventory ();
    }

    public Result action (Thing obj) {
        if (obj instanceof Person) {
            Person person = (Person) obj;
            World.me().write("\n" + person.name() + "'s Inventory: \n" +
                            "Health is: " + person.health() +
                            "\nStrength is: " + person.strength() + 
                            "\nMana is: " + person.mana() +
                            "\nDexterity is: " + person.dexterity() +
                            "\nLevel is: " + person.level() +
                            "\nXP: " + person.experiencePoints() +
                            "\nXP to lvl-" + (person.level() + 1) + ": " +
                            (person.level()*20 - person.experiencePoints()) +
                            "\nGeld: " + person.geld() +
                            "\n" + "Backpack: " +
                            World.getNames(person.things()));
        } else 
            World.me().say (obj.name() + " can't tell me anything.");
        return Result.STOP;
    }

}
