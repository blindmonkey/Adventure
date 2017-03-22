
package adventure;
import java.util.*;

/*
 * Class Root
 *
 * The top of the artifacts hierarchy
 * Implements the name() functionality
 *
 */

public class Root {

  private String name;

  protected Root (String n) {
    super ();
    this.name = World.noSpaces(n);
  }
    
  public static Root create (String name) {
    Root root = new Root(name);
    root.install();
    return root;
  }

  public String name () {
    return this.name;
  }

  public void install () {
  }

  public void destroy () {
  }

}
