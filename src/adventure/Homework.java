
package adventure;

/*
 * Class Homework
 *
 * Abstract class from which all homeworks derive
 *
 * This is useful for PrlGrad, which will take *any* homework
 * and destroy it
 *
 */

public abstract class Homework extends MobileThing {
   private String assignment;
   private String tests;
   private String code;
   
   protected Homework (String n, Container l, String assignment, String tests) {
      super(n,l);
      this.code = "";
      this.assignment = assignment;
      this.tests = tests;
   }

   public String getAssignment() {
      return assignment;
   }
   
   public String getTests() {
      return tests;
   }
   
   public Homework setCode(String c) {
      this.code = c;
      return this;
   }
   
   public String getCode() {
      return code;
   }
   
   public abstract String coreName ();

   public static Predicate isA () {
      return new HomeworkPredicate();
   }
   

   private static class HomeworkPredicate implements Predicate {
      public HomeworkPredicate () {}
      public boolean check (Thing t) {
         return (t instanceof Homework);
      }
   }


}

