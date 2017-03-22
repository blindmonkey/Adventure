package operations;
/**
 * 
 * @author Sergey Grabkovsky
 *
 */

public class MathOperations {
    public static double round(double num, int precision) {
        return ((double)Math.round(num*Math.pow(10,precision))) / Math.pow(10,precision);
    }
}
