package adventure;

public class Dungeon extends Place implements Container{

    protected Dungeon (String n) {
        super(n);
    }

    public static Dungeon create (String n) {
        Dungeon obj  = new Dungeon(n);
        obj.install();
        return obj;
    }
}
