
package adventure;

/*
 * Class Limbo
 *
 * Limbo is a special place that has no exit
 * It is a singleton class (implements the singleton pattern)
 * 
 */

public class Limbo extends Place {

  protected Limbo () {
    super("limbo");
  }

  private static Limbo limbo = new Limbo();
  
  public static Limbo getInstance () {
    return limbo;
  }

}
