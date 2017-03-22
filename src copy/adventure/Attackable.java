package adventure;

/*
 * Interface Attackable
 *
 * Implemented by artifact that provide an attack() method
 *
 */

public interface Attackable {

    public String name ();
    public void attack (Person user);

}


