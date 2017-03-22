
package adventure;

/*
 * Class MobileThing
 *
 * Things that have location that can change
 *
 */

public class MobileThing extends Thing {

  private Container mobileLocation;

  protected MobileThing (String n, Container l) {
    super(n,l);
    this.mobileLocation = l;
  }

  public static MobileThing create (String n, Container l) {
    MobileThing obj = new MobileThing(n,l);
    obj.install();
    return obj;
  }

  public Container location () {
    return this.mobileLocation;
  }

  public void changeLocation (Container l) {
    this.mobileLocation.delThing(this);
    l.addThing(this);
    this.mobileLocation = l;
  }

  public void destroy () {
    super.destroy();
    // move the destroyed object to Limbo
    this.mobileLocation=Limbo.getInstance();
  }

  public void enterRoom () {
  }

  public void leaveRoom () {
  }

  // can access the location where the artifact was initially created
  public Container creationSite () {
    return super.location ();
  }

  public static Predicate isA () {
    return new MobileThingPredicate();
  }

  private static class MobileThingPredicate implements Predicate {
    public MobileThingPredicate () {}
    public boolean check (Thing t) {
      return (t instanceof MobileThing);
    }
  }


}

