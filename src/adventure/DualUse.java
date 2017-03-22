package adventure;

import adventure.Verb.Result;

public class DualUse extends Verb {

   protected DualUse () {
     super("use");
   }
   
   public static Verb create () {
     return new DualUse();
   }
   
   public Result action (Thing obj) {
       if (obj instanceof Usable) {
         // can only use a "Usable" artifact
         Usable usableObj = (Usable) obj;
         usableObj.use(World.me());
       } else
         World.me().say ("I try but cannot use " + obj.name());
       return Result.STOP;
     }
   
   public Result action (Thing obj, Thing indObj) {
     // first some error checking
     if (!World.me().haveThing(obj)) 
       World.tellWorld("Not carrying " + obj.name());
     else if (!(indObj instanceof Computer)) {
       World.tellWorld("Target " + indObj.name() + 
            " not a computer");
     } else {
       Computer target = (Computer) indObj;
       // If we carry the object, then it must be a MobileThing
       MobileThing mobileObj = (MobileThing) obj;
       target.use(World.me(), mobileObj);
     }
     return Result.STOP;
   }


}
