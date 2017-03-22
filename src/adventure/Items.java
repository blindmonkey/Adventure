package adventure;

public class Items extends MobileThing  {

    private int str;
    private int mana;
    private int dex;
    protected enum type {WEAPON, SHIELD, ARMOR, RING};
    private type t;
    
    public String core;

    protected Items(String n, Container l, int s, int m, int d, type type) {
        super(n, l);
        core = World.noSpaces(n);
        str = s;
        mana = m;
        dex = d; 
        t = type;
    }

    /**
     * Create a weapon to Equip in the game
     * @param n Name
     * @param l Container
     * @param s Strength gained by equiping
     * @param m Mana gained by equiping
     * @param d Dexterity gained by equiping
     * @param t Type of item
     * @return A new weapon
     */
    public static Items createWeapon (String n, Container l, int s, int m, int d) {
        Items obj = new Items (n,l, s, m, d, type.WEAPON);
        System.out.println("Creating weapon.  Type is " + obj.getType());
        obj.install ();
        return obj;
    }

    /**
     * Create an armor to Equip in the game
     * @param n Name
     * @param l Container
     * @param s Strength gained by equiping
     * @param m Mana gained by equiping
     * @param d Dexterity gained by equiping
     * @param t Type of item
     * @return A new armor
     */
    public static Items createArmor (String n, Container l, int s, int m, int d) {
        Items obj = new Items (n,l, s, m, d, type.ARMOR);
        System.out.println("Creating armor.  Type is " + obj.getType());
        obj.install ();
        return obj;
    }

    /**
     * Create a shield to Equip in the game
     * @param n Name
     * @param l Container
     * @param s Strength gained by equiping
     * @param m Mana gained by equiping
     * @param d Dexterity gained by equiping
     * @param t Type of item
     * @return A new shield
     */
    public static Items createShield (String n, Container l, int s, int m, int d) {
        Items obj = new Items (n,l, s, m, d, type.SHIELD);
        System.out.println("Creating shiel.  Type is " + obj.getType());
        obj.install ();
        return obj;
    }

    /**
     * Create a ring to Equip in the game
     * @param n Name
     * @param l Container
     * @param s Strength gained by equiping
     * @param m Mana gained by equiping
     * @param d Dexterity gained by equiping
     * @param t Type of item
     * @return A new ring
     */
    public static Items createRing (String n, Container l, int s, int m, int d) {
        Items obj = new Items (n,l, s, m, d, type.RING);
        System.out.println("Creating ring.  Type is " + obj.getType());
        obj.install ();
        return obj;
    }

    public static Predicate isA () {
        return new ItemsPredicate();
    }

    private static class ItemsPredicate implements Predicate {
        public ItemsPredicate () {}
        public boolean check (Thing t) {
            return (t instanceof Items);
        }
    }

    public int getItemStrength () {
        return this.str;
    }

    public int getItemDexterity () {
        return this.dex;
    }

    public int getItemMana() {
        return this.mana;
    }

    public type getType () {
        return this.t;
    }
    
}
