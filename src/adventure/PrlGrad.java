
package adventure;
import java.util.*;

/*
 * Class PrlGrad
 *
 * A PRL graduate student
 * 
 * Will destroy non-scheme homeworks
 *
 */

public class PrlGrad extends AutonomousPerson {

  private int anger;
  private int cbRecord;

  // make it very unlikely that the PrlGrad grabs something
  private static int initialMiserly = 1000;

  protected PrlGrad (String name,
		     Place birthplace,
		     int speed,
		     int anger) {
    super(name,birthplace,speed,initialMiserly);
    this.anger = anger;
  }

  public static PrlGrad create (String name,
				Place birthplace,
				int speed,
				int anger) {
    PrlGrad obj = new PrlGrad(name,birthplace,speed,anger);
    obj.install();
    return obj;
  }

  public void install () {
    super.install();
    this.cbRecord = Clock.registerCallback(new SnatchHomeworkCallback(this));
  }


  /*
   * callback for PrlGrad is a wrapper around the snatchHomework()
   * method
   */
  private static class SnatchHomeworkCallback implements Callback {
    private PrlGrad prlGrad;
    public SnatchHomeworkCallback (PrlGrad t) {
      this.prlGrad=t;
    }
    public void action () {
      this.prlGrad.snatchHomework();
    }
  }

  /*
   * If it is determined that the graduate student snatches 
   * homeworks, then they take every homework from all people around 
   * them and destroy them
   */
  public void snatchHomework () {
    if (World.random(this.anger) == 0) {
      LinkedList<Thing> stuff = this.stuffAround();
      stuff.addAll (this.peekAround());
      if (!stuff.isEmpty()) {
	for (Thing t : stuff) 
	  if (t instanceof Homework) {
	    this.take(t);
	    this.say("Wait a minute! This is not Scheme...");
	    this.say("Burn, baby, burn!");
	    this.emit(this.name() + " burns " + 
		      t.name());
	    t.destroy();
	  }
      } else {
	this.emit(this.name() + " grumbles something about Scheme");
      }
    }
  }

  public void die () {
    Clock.removeCallback(this.cbRecord);
    super.die();
  }

  public static Predicate isA () {
    return new PrlGradPredicate();
  }

  private static class PrlGradPredicate implements Predicate {
    public PrlGradPredicate () {}
    public boolean check (Thing t) {
      return (t instanceof PrlGrad);
    }
  }



}

