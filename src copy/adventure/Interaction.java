
package adventure;
import java.io.*;

/*
 * Class defining the interactive loop
 *
 */

public class Interaction{

    /*
     * Read a string from the user, and split it into words
     * at spaces
     *
     */

  private static String[] getPlayerInput () {
    
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String response = "";
    
    System.out.print("\nWhat is thy bidding? ");
    try {
      response = br.readLine();
      if (response==null) {
	System.out.println ("\nPlease use 'quit' to leave");
	response="";
      } else 
	response = response.trim();
    } catch (IOException ioe) {
      System.out.println("IO error reading from terminal\n");
      System.exit(1);
    }
    return response.split ("\\s++");
  }
  
/**
  private static String[] getPlayerInput () {
      Scanner s = new Scanner(System.in);
      s.useDelimiter("[a-z ]");
    String response = "";
    boolean over = false;
    
    while (!over) {
        if (s.hasNextByte())
            System.out.print((char)s.nextByte());
    }
    System.out.println("I got to the end!");
    return response.split("\\s++");
  }
**/
  
    /*
     * The main loop of the game
     *
     */

  public static void mainLoop () {
    while (true) {

      System.out.println ("");
      World.me().lookAround();
      
      while (true) {
	
	String[] response = getPlayerInput ();
	if (!response[0].equals (""))
	  try {
	    Verb verb = World.findVerb (response[0]);
	    Verb.Result actResult;
	    switch (response.length) {
	    case 1 : 
	      actResult = verb.action();
	      break;
	    case 2: 
	      Thing obj1 = World.thingNamed (response[1]);
	      actResult = verb.action (obj1);
	      break;
	    case 3: 
	      Thing obj2 = World.thingNamed(response[1]);
	      Thing obj3 = World.thingNamed(response[2]);
	      actResult = verb.action (obj2,obj3);
	      break;
	    default: 
	      System.out.println ("Sentence too complex");
	      actResult = Verb.Result.STOP;
	    }
	    if (actResult==Verb.Result.STOP) 
	      continue;
	    // World.tellWorld ("OK");
	    break;
	    
	  } catch (World.ObjNotFoundException e) {
	    System.out.println("I do not understand this word: " + 
			       e.getMessage());
	  }
      }
      
      Clock.tick();
    }
  }

}

class MyInputStream extends InputStream {

    @Override
    public int read() throws IOException {
        return System.in.read();
    }
    
    public void write(char ch) {
        System.out.print(ch);
    }
    
}