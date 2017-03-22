
package adventure;
import java.util.*;

/*
 * Class Person
 *
 * A person is a mobile thing with some special methods
 *
 */

public class Person extends MobileThing implements Container, Attackable, Equipable {

    private static int initialHealth = 5;
    private static int initialStrength = 10;
    private static int initialLevel = 1;
    private static int initialMana = 20;
    private static int initialDexterity = 10;
    private static int initialIntelligence = 10;
    private static int initialXP = 0;
    private static int initialGeld = 15;
    private static int initialKills = 0;

    // what the person carries
    private Container content;
    // what the person has equipped
    private Container equipped;
    // the health of the person
    private int health;
    // level of the person
    private int level;
    // dexterity of person
    private int dex;
    // mana of person
    private int mana;
    // strength of person
    private int str;
    // intelligence of person
    private int in;
    // experience points
    private int xp;
    // moneys
    private int geld;
    // # of things killed by person
    private int kills;


    protected Person (String name, Place birthplace) {
        super(name,birthplace);
        this.content = ContainerProxy.make();
        this.equipped = ContainerProxy.make();
        this.health = initialHealth;
        this.dex = initialDexterity;
        this.level = initialLevel;
        this.str = initialStrength;
        this.mana = initialMana;
        this.in = initialIntelligence;
        this.xp = initialXP;
        this.geld = initialGeld;
        this.kills = initialKills;
    }

    public static Person create (String name, Place birthplace) {
        Person obj = new Person(name,birthplace);
        obj.install();
        return obj;
    }

    public int health () {
        return this.health;
    }

    public void mutateHealth (int i) {
        this.health += i;
    }

    public void resetHealth () {
        this.health = initialHealth;
    }

    public int dexterity () {
        return this.dex;
    }

    public int strength () {
        return this.str;
    }

    public int mana () {
        return this.mana;
    }

    public int level () {
        return this.level;
    }

    public int intelligence () {
        return this.in;
    }

    public int kills () {
        return this.kills;
    }

    public void mutateKills () {
        kills++;
    }

    public int experiencePoints () {
        return this.xp;
    }

    public int geld () {
        return this.geld;
    }

    public void mutateGeld (int g) {
        geld += g;
    }

    /**
     * Change the xp of this person
     * Check if level increase has been attained and if so
     * level-up this person
     * @param x Number to increase xp by
     */
    public void mutateXP (int x) {
        xp += x;
        if (xp >= this.level()*20) this.levelUp();
    }

    /**
     * Level up this person
     * Give new attributes
     * 
     */
    public void levelUp () {
        level++;
        str += 5;
        mana += 7;
        dex += 4;
        health += 5;
        in += 4;
    }

    /**
     * Method to increase Attributes 
     * 
     * String must be one of: level, mana, strength, dexterity, intelligence
     * @param s Attribute to increase
     * @param n Amount to increase by
     * 
     */
    public void increaseAtt (String s, int n) throws RuntimeException {
        try {
            if (s.equals("level")) level += n;
            if (s.equals("mana")) mana += n;
            if (s.equals("strength")) str += n;
            if (s.equals("dexterity")) dex += n;
            if (s.equals("intelligence")) in += n;
        } catch (RuntimeException e) { 
            World.tellWorld("Cannot increase " +  s); 
        }
    }

    // write to the screen that the person said something
    public void say (String arg) {
        World.tellRoom ((Place) this.location(),
                        "At " + this.location().name() + " " + this.name() + " says -- " + arg);
    }

    /**
     * Write to the screen this information
     * @param arg String to be written to the screen
     */
    public void write (String arg) {
        World.tellRoom((Place) this.location(), arg);
    }

    public void haveFit () {
        this.say("Yaaaaah! I am upset!");
    }


    // return people in the same location as the person
    public LinkedList<Person> peopleAround () {
        LinkedList<Person> result = new LinkedList<Person>();
        for (Thing t : ((Place) this.location()).things())
            if (t instanceof Person &&
                            (Person) t != this)
                result.add ((Person) t);
        return result;
    }

    // return things in the same location as the person
    public LinkedList<Thing> stuffAround () {
        LinkedList<Thing> result = new LinkedList<Thing>();
        for (Thing t : ((Place) this.location()).things())
            if (!(t instanceof Person))
                result.add(t);
        return result;
    }

    // returns things carried by anyone in the same location as the person
    public LinkedList<Thing> peekAround () {
        LinkedList<Thing> result = new LinkedList<Thing>();
        for (Person p : this.peopleAround())
            result.addAll (p.things());
        return result;
    }


    public Thing take (Thing t) {
        if (this.haveThing(t)) {
            this.say("I am already carrying " + t.name());
            return null;
        }
        if (t instanceof Person || 
                        !(t instanceof MobileThing)) {
            this.say("I try but cannot take " + t.name());
            return null;
        }

        MobileThing m = (MobileThing) t;

        Container owner = m.location();
        this.say("I take " + m.name() + " from " + owner.name());
        if (owner instanceof Person)
            ((Person) owner).lose(m,this);
        else
            m.changeLocation(this);
        return m;
    }

    public void lose (Thing t, Container loseTo) {
        if (t instanceof MobileThing) {
            MobileThing m = (MobileThing) t;
            this.say("I lose " + m.name());
            this.haveFit();
            m.changeLocation(loseTo);
        }
        else
            throw new RuntimeException ("Cannot lose a non-mobile thing - this should not happen");
    }

    public void drop (MobileThing t) {
        this.say ("I drop " + t.name() + " at " + this.location().name());
        t.changeLocation(this.location());
    }

    public boolean goExit (Exit exit) {
        return exit.use(this);
    }

    public boolean go (String direction) {
        Exit exit = ((Place) this.location()).exitTowards(direction);
        if (exit!=null) 
            return this.goExit(exit);
        World.tellRoom(this.location(),
                        "No exit in " + direction + " direction");
        return false;
    }

    public void suffer (int hits) {
        this.say("Ouch! " + hits + " hits is more than I want!");
        this.health = this.health - hits;
        this.xp += 1;
        if (this.health < 0)
            this.die ();
        else
            this.say("My health is now " + this.health);
    }

    public void die () {
        for (Thing item : this.things())
            this.lose(item,this.location());
        World.tellWorld("An earth-shattering, soul-piercing scream is heard...");
        this.destroy();
    }

    public void ask () {
        this.say ("Don't bug me, I'm very busy right now!");
    }

    public void enterRoom () {
        LinkedList<Person> people = this.peopleAround();
        if (!people.isEmpty())
            this.say("Hi " + World.getNames(people));
    }

    public LinkedList<Thing> things () {
        return content.things();
    }

    public boolean haveThing (Thing t) {
        return content.haveThing(t);
    }

    public void addThing (Thing t) {
        content.addThing(t);
    }

    public void delThing (Thing t) {
        content.delThing(t);
    }

    /**
     * Gives a list of all items that are equipped
     * @return a list of equipped things
     */
    public LinkedList<Thing> equippedThings () {
        return equipped.things();
    }

    /**
     * Checks to see if an item is equipped
     * @param t Item to check
     * @return
     */
    public boolean haveEquipped (Thing t) {
        for (Thing thing : equipped.things())
            if (t.equals(thing))
                return true;
        return false;
    }

    /**
     * Adds thing t to list of equipped items
     * @param t thing to equip
     */
    public void addEquippedThing (Thing t) {
        if (t instanceof Items) equipped.addThing(t);
        else World.tellRoom(this.location(), "I cannot equip " + t.name());
    }

    /**
     * Removes thing t from equipped status
     * @param t Thing to delete
     */
    public void delEquippedThing (Thing t) {
        if (t instanceof Items) equipped.delThing(t);
    }

    /**
     * Used to attack
     * @param defendant Person to attack
     * 
     */
    public void attack(Person defendant) {
        /* 
         * Crappy battle algorithm
         * add mana, strength, dexterity and 5 times level
         * then divide that by 7
         * this gives the value for this dice size
         * then we roll the dice to get the value
         */
        int diceSize = (Math.round(this.strength() + this.mana() + 
                        this.dexterity() + (5 * this.level())) / 7);
        Dice d = new Dice(diceSize);
        int offenseVal = d.roll();
        World.tellRoom(this.location(), "" + this.name() + " rolls " +
                        offenseVal);

        //find values for attack
        int diceSize2 = (Math.round(defendant.strength() + defendant.mana() + 
                        defendant.dexterity() + (5 * defendant.level())) / 7);
        Dice d1 = new Dice(diceSize2);
        int defenseVal = d1.roll();
        World.tellRoom(this.location(), "" + defendant.name() + " rolls " +
                        defenseVal);

        // deduct health and kill
        if (offenseVal > defenseVal) {
            defendant.suffer(offenseVal - defenseVal);
            this.mutateHealth(1);
            // if defender dies, gain xp and kills 
            if (defendant.health() <= 0) {
                this.mutateXP(11);
                this.mutateKills();
            }
            if (this instanceof Vampire)
                this.mutateHealth(offenseVal - defenseVal);
            if (defendant instanceof Troll  && 
                            defendant.health() > 0) this.attack(defendant);
        }
        else {
            World.tellRoom(this.location(), defendant.name() + " says " +
            "Ha ha is that all you have??");
            // if this person is a troll and wants to fight back
            if (defendant instanceof Troll) {
                defendant.attack(this);
            }
        }
    }

    /** 
     * A linked list of things a person has
     * @param pred
     * @return List of contents 
     */
    public LinkedList<Thing> hasA (Predicate pred) {
        LinkedList<Thing> out = new LinkedList<Thing>();
        for (Thing t : content.things() )
            if (pred.check(t)) 
                out.add(t);
        return out;
    }

    /**
     * Equip an item to a person
     * Add items attributes to person
     */
    public void equip(Thing i) {
        LinkedList<Thing> th = this.hasA(Thing.isA());
        boolean b = false;
        for (int x=0; x < th.size(); x++){
            if (th.get(x).name().equals(i.name()))

            {
                b = true;
                Items it = new Items(i.name(),
                                i.location(), 0, 0, 0, null);
                it = (Items) i;
                
                // check if item of this type is already equipped
                // and then unequip previously equipped item
                LinkedList<Thing> equipped = new LinkedList <Thing> ();
                equipped =  this.content.things();
                for (int y = 0; y < equipped.size(); y++){
                    if (equipped.get(y) instanceof Items){
                        Items s = new Items(it.name(), it.location()
                                        , 0, 0, 0, null);
                        s = (Items) equipped.get(y);
                        if (s.name().equals(it.name())) this.unequip(s);
                    }
                }
                
                // equip item
                this.addEquippedThing(it);
                this.increaseAtt("dexterity", it.getItemDexterity());
                this.increaseAtt("mana", it.getItemMana());
                this.increaseAtt("strength", it.getItemStrength());
                World.me().say("I have equipped " + it.name() + ".");
            }  
        }
        if (!b) World.me().say("I don't have this " +
                        i.name() + " in my possesion");
    }

    /**
     * Unequip an item from a person
     * Take off items attributes from person
     */
    public void unequip(Thing i) {
        LinkedList<Thing> it = this.equippedThings();
        for (int x = 0; x < it.size(); x++){
            if(!(it.get(x).name().equals(i.name())))
                World.me().say("I don't have this equipped.");
            else {
                Items s = new Items(i.name(),
                                i.location(), 0, 0, 0, null);
                s = (Items) i;
                this.delEquippedThing(i);
                this.increaseAtt("dexterity", -s.getItemDexterity());
                this.increaseAtt("mana", -s.getItemMana());
                this.increaseAtt("strength", -s.getItemStrength());
                World.me().say("I have unequipped " + s.name() + ".");
            }
        }
    }

    public static Predicate isA () {
        return new PersonPredicate();
    }

    private static class PersonPredicate implements Predicate {
        public PersonPredicate () {}
        public boolean check (Thing t) {
            return (t instanceof Person);
        }
    }
}

