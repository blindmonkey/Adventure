
package adventure;

/*
 * Class Exit
 *
 * Implements exits for rooms
 * An exit is really just a direction along with two places,
 * one where the exit comes from, and one where it goes
 *
 */

public class Exit extends Root {

  private Place from;
  private Place to;
  private String direction;

  protected Exit (Place f, Place t, String d) {
    super(d);
    this.from = f;
    this.to = t;
    this.direction = d;
  }

  public static Exit create (Place f, Place t, String d) {
    Exit obj = new Exit(f,t,d);
    obj.install();
    return obj;
  }

  // creating an exit automatically adds it to the from() room
  public void install () {
    super.install();
    if (this.from()!=null) 
      this.from().addExit(this);
  }
  
  public Place from () {
    return this.from;
  }

  public Place to () {
    return this.to;
  }

  public String direction () {
    return this.direction;
  }

  // using an exit amounts to moving a thing through the exit
  // mostly used by autonomouspersons moving through the world
  public boolean use (MobileThing whom) {
    whom.leaveRoom();
    World.tellRoom((Place) whom.location(),
		   whom.name() + " moves from " + 
		   whom.location().name() + " to " + 
		   this.to.name());
    whom.changeLocation(this.to);
    whom.enterRoom();
    return true;
  }

    public String toString () {
	return "Exit from " + this.from().name() + " to " + this.to().name() + " via " + this.direction();
    }

}
