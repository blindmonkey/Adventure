
package adventure;
import java.util.*;
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

    private static String getSimpleInput () {
        String out = "";
        try {
            out = new BufferedReader(new InputStreamReader(System.in)).readLine();
        }
        catch (Exception e) {
            e.notifyAll();
        }
        return out;
    }



    /*
     * The main loop of the game
     *
     */

    public static void mainLoop () {
        ArrayList<String> autoCompleteList;
        Integer sel;
        String in="";
        while (true) {

            System.out.println ("");
            World.me().lookAround();

            while (true) {

                String[] response = getPlayerInput ();
                if (!response[0].equals (""))
                    try {
                        if (response.length > 1)
                            for (int a=1 ; a<response.length ; a++) {
                                autoCompleteList = autoComplete(response[a]);
                                if (autoCompleteList.size() > 0) {
                                    if (!autoCompleteList.get(0).equals(response[a])) {
                                        if (autoCompleteList.size()==1) {
                                            System.out.print("The word \"" + response[a] +
                                                            "\" was not found.  However, autocomplete has determined that\n there is a word called \"" +
                                                            autoCompleteList.get(0) + "\".  Would you like to use that? [y or enter/n] ");
                                            in = getSimpleInput();
                                            if (in.equals("") || in.equals("y") || in.equals("Y"))
                                                response[a] = autoCompleteList.get(0);
                                            else {
                                                System.out.println("in was \"" + in + "\".");
                                                throw new World.ObjNotFoundException(response[a]);
                                            }
                                        } else {
                                            System.out.print("The word \"" + response[a] +
                                                            "\" was not found.  However, autocomplete has determined that\n there are " + 
                                                            autoCompleteList.size() + " words starting with " + response[a] + ".\n" +
                                            "Please type in the number of the word you want to use (0 to cancel).\n");
                                            for (int b=0 ; b<autoCompleteList.size() ; b++)
                                                System.out.println((b+1) + ". " + autoCompleteList.get(b));
                                            System.out.print("Enter selection: ");
                                            sel = Integer.valueOf(getSimpleInput());
                                            if (sel==null || sel<1 || sel>autoCompleteList.size()) {
                                                System.out.println("You did not select a number within the given range.");
                                                //throw new World.ObjNotFoundException(response[a]);
                                            }
                                            else
                                                response[a] = autoCompleteList.get(sel-1);
                                        }/*
                                        if (autoCompleteList.size()==0)
                                            throw new World.ObjNotFoundException(response[a]);*/
                                    }
                                } //else 
                                    //throw new World.ObjNotFoundException(response[a]);
                            }
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

    private static ArrayList<String> autoComplete(String str) {
        ArrayList<String> everything = new ArrayList<String>();      
        for (Thing t : World.me().location().things())
            everything.add(t.name());
        for (Thing t : World.me().things())
            everything.add(t.name());
        //for (Person p : World.me().peopleAround())
        //everything.add(p.name());

        return autoComplete(str, everything);
    }

    private static ArrayList<String> autoComplete(String str, ArrayList<String> everything) {
        return autoComplete(str, everything, new ArrayList<String>());
    }

    private static ArrayList<String> autoComplete(String str, ArrayList<String> everything, ArrayList<String> output) {
        sort(everything);
        for (int c=0 ; c<everything.size() ; c++)
            if (everything.get(c).length() >= str.length())
                if (matches(everything.get(c), str))
                    output.add(everything.get(c));
        return output;
    }

    private static boolean matches(String str1, String str2) {
        String t=str1;
        if (str1.length() > str2.length()) {
            str1 = str2 ; str2 = t;}

        for (int a=0 ; a<str1.length() ; a++)
            if (str1.charAt(a) != str2.charAt(a))
                return false;
        return true;
    }

    private static ArrayList<String> sort(ArrayList<String> things) {
        for (int a=0 ; a<things.size() ; a++)
            for (int b=a+1 ; b<things.size() ; b++)
                if (things.get(a).compareTo(things.get(b)) > 0)
                    things.set(a, things.set(b, things.get(a)));
        return things;
    }
}