
package adventure;
import java.util.*;

/*
 * Class Clock
 *
 * Implements a clock for the game
 * All methods and fields are static (essentially a singleton class)
 *
 */

public class Clock {

  // the current time
  private static int time = 0;
      
  // this field holds all the callbacks that have been registered
  private static LinkedList<IDCallback> callbacks = 
      new LinkedList<IDCallback>();

  // temporary field that holds removed callbacks during iteration
  private static LinkedList<IDCallback> removedCB = new LinkedList<IDCallback>();

  // this field holds the next callback identifier returned when
  // a callback is registered
  private static int nextID = 1;


  // extend a callback with an identifier for later recall
  private static class IDCallback implements Callback {
    private Callback content;
    private int id;
    public IDCallback (Callback cb, int i) {
      this.content = cb;
      this.id = i;
    }
    public int getID () {
      return this.id;
    }
    public void action () {
      this.content.action();
    }
  }
    

  // return current time
  public static int time() {
    return time;
  }

  // make clock move forward once, triggering all callbacks
  public static void tick () { 
    // first, increment the time
    time++;
    // then, copy the list so that modifications to callbacks do not
    // affect the iteration
    LinkedList<IDCallback> backup = new LinkedList<IDCallback>(callbacks);
    removedCB = new LinkedList<IDCallback>();
    // finally, perform all the actions in the list of callbacks
    for (IDCallback cb : backup)
      if (!removedCB.contains(cb))
	cb.action();
  }


  // register a callback with the clock, returning a new identifier
  // for that callback
  public static int registerCallback (Callback cb) {
    int id = nextID++;
    IDCallback idcb = new IDCallback(cb,id);
    callbacks.add(idcb);
    return id;
  }

  // remove a registered callback, using the identifier for the
  // callback gotten at registration
  public static void removeCallback (int id) { 
    IDCallback found = null;
    for (IDCallback cb : callbacks)
      if (cb.getID() == id) {
	removedCB.add(cb);
	found = cb;
      }
    if (found!=null) 
      callbacks.remove(found);
  }



}
