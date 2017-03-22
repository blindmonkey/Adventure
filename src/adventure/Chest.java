package adventure;

import java.util.LinkedList;

public class Chest extends Thing implements Container {

    private Container contents;

    protected Chest(String n, Container l) {
        super(n, l);
        this.contents = ContainerProxy.make();
    }

    public static Chest create (String n, Container l) {
        Chest obj = new Chest (n, l);
        obj.install();
        return obj;
    }

    public void addThing (Thing obj) {
        if (obj instanceof Thing) contents.addThing(obj);
        else World.tellWorld("Cannot add " + obj.name() + " to chest.");
    }

    public void delThing (Thing obj) {
        if (obj instanceof MobileThing) contents.delThing(obj);
        else World.tellRoom(this.location(), "Cannot take " + obj.name());
    }

    public boolean haveThing(Thing t) {
        for (Thing th : contents.things()){
            if (th.name().equals(t.name())) return true;
        }
        return false; 
    }

    public LinkedList<Thing> things() {
        return this.contents.things();
    }
}
