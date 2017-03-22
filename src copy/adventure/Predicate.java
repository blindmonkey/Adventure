
package adventure;

/*
 * Interface Predicate
 *
 * Implemented by a predicate suitable for passing to the Person.hasA
 * method
 *
 */

public interface Predicate {

  public boolean check (Thing obj);

}
