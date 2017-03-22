
package adventure;

/* 
 * Class Verb
 *
 * Every verb defining some vocabulary must subclass this
 *
 */

public abstract class Verb {

  // the text of the verb to use during parsing
  private String word;
  
  public static enum Result { CONTINUE, STOP };
  
  protected Verb (String w) {
    // remove spaces from the word
    this.word = World.noSpaces(w);
  }
  
  public String word () {
    return this.word;
  }
  

    /* 
     * Three possible actions per verb, depending on how
     * many arguments we supply
     *
     * By default they return errors
     *
     */

  public Result action () {
    System.out.println ("I do not know how to " + this.word());
    return Result.STOP;
  }
  
  public Result action (Thing obj) {
    System.out.println ("I do not know how to " + this.word() + " on " + 
			obj.name());
    return Result.STOP;
  }
  
  public Result action (Thing obj, Thing indObj) {
    System.out.println ("I do not know how to " + this.word() + " on " + 
			obj.name() + " and " + 
			indObj.name());
    return Result.STOP;
  }

}



