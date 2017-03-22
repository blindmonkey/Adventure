
package adventure;

/*
 * Class Thing
 *
 * Artifacts that have a location
 *
 * Creating a Thing adds it to the location's content
 *
 */

public class Thing extends Root {

  private Container location;

  protected Thing (String n, Container l) {
    super (n);
    location = l;
  }

  public static Thing create (String n, Container l) {
    Thing obj =  new Thing (n,l);
    obj.install ();
    return obj;
  }

  public void install () {
    super.install();
    this.location().addThing(this);
    if (World.isDebug())
      this.emit(this.name() + " is created");
  }

  public Container location () {
    return this.location;
  }

  public void destroy () {
    this.location().delThing(this);
    super.destroy();
    if (World.isDebug())
      this.emit(this.name() + " is destroyed");
  }

  public void emit (String text) {
    World.tellRoom (this.location(),
		    "At " + this.location().name() + " " + text);
  }
  
  /**
   * Change the location of this item
   * @param l new location to be sent to
   */
  public Container updateLocation (Container l) {
      this.location = l;
      return location;
  }

  public static Predicate isA () {
    return new ThingPredicate();
  }

  private static class ThingPredicate implements Predicate {
    public ThingPredicate () {}
    public boolean check (Thing t) {
      return true;
    }
  }

}
