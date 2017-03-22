package adventure;

import java.util.Random;

public class Dice {
    
    private int sides;
    
    protected Dice (int s) { 
        sides = s;
    }
    
    public static Dice create (int s){
       Dice d = new Dice (s);
       return d;
    }
    
    public int roll (){
        Random r = new Random();
        int num = r.nextInt(this.sides());
        return num;
    }
    
    public int sides () {
        return this.sides;
    }
}
