package scheme;

import java.util.*;
import java.io.*;
import adventure.*;

import operations.*;
import scheme.Atom.SchemeName;

/**
 * The main Scheme class.
 * 
 * @author Sergey Grabkovsky
 */
public class Scheme {
    /**
     * The exception that represents a syntax error.
     * @author Sergey Grabkovsky
     */
    public static class SyntaxException extends RuntimeException {
        public SyntaxException() {
            super();
        }
        public SyntaxException(String s) {
            super(s);
        }
    }

    /**
     * Starts editing the given homework.
     */
    public static void startEditor(adventure.Homework hw) {
        System.out.println(World.me().name() + " starts up the computer.");
        System.out.println("Welcome to DrScheme 1.0");
        System.out.println("Type in (help) for a list of supported commands");
        System.out.println("or (exit) when you finish.");
        System.out.println(hw.getAssignment());      

        String line="",total="";
        Bindings bindings = new Bindings();
        bindings.addBinding(Binding.getBinding((Atom.SchemeName)Atom.SchemeName.getNameInstance("empty"), SchemeList.getSchemeList(new ArrayList<Item>())));
        while (true) {
            try {
                System.out.print("> ");
                line = new BufferedReader(new InputStreamReader(System.in)).readLine();
            } catch (IOException io) {}
            if (line.toLowerCase().equals("(exit)")) {
                hw.setCode(total); return;
            } else if (line.toLowerCase().equals("(help)")) {
                System.out.println("list : Items -> [ListOf TypeOf Items]" +
                                "+ : Numbers -> Number" +
                                "- : Numbers -> Number" +
                                "/ : Numbers -> Number" +
                                "* : Numbers -> Number" +
                                "^ : Numbers -> Number" +
                                "sqrt : Number -> Number" +
                                "lambda : (Variables) (Function code) -> Function" +
                                "define : (Name Variables) (Function code) -> Lambda Function" +
                                "bindings : Lists the current bindings" +
                                "cond : ((Boolean) Code) ((Boolean) Code)...etc -> Code" +
                                "cons : Item List -> List" +
                                "symbol? : Atom -> Boolean" +
                                "number? : Atom -> Boolean" +
                                "boolean? : Atom -> Boolean" +
                                "string? : Atom -> Boolean" +
                                "symbol=? : Symbol Symbol -> Boolean" +
                                "number=? : Number Number -> Boolean" +
                                "boolean=? : Boolean Boolean -> Boolean" +
                                "string=? : String String -> Boolean" +
                                "equals? : Atom Atom -> Boolean" +
                                "empty? : List -> Boolean" +
                                "cons? : List -> Boolean" +
                                "= : Number Number -> Boolean" +
                                "> : Number Number -> Boolean" +
                                "< : Number Number -> Boolean" +
                                ">= : Number Number -> Boolean" +
                                "<= : Number Number -> Boolean" +
                                "!= : Number Number -> Boolean" +
                                "first : List -> Item" +
                                "rest : List -> List" +
                                "and : Booleans -> Boolean" +
                "or : Booleans -> Boolean"); 
            } else if (line.toLowerCase().equals("(clear)"))
                total = "";
            else if (line.toLowerCase().equals("(test)"))  
                try {
                    System.out.println("running");
                    System.out.println(evaluate(total + "\n" + hw.getTests(), bindings));
                } catch (Scheme.SyntaxException se) {
                    System.out.println(se.getMessage());
                }
                else
                    total = total + " \n " + line;
        }
    }

    public static String evaluate(String input) throws SyntaxException {
        Bindings bindings = new Bindings();
        bindings.addBinding(Binding.getBinding((Atom.SchemeName)Atom.SchemeName.getNameInstance("empty"), SchemeList.getSchemeList(new ArrayList<Item>())));
        return evaluate(input, bindings);
    }
    
    /**
     * Evaluates one or more scheme expressions.
     * @param input The scheme expression(s) to be evaluated.
     * @param bindings Some bindings we want to start with (like empty for example).
     * @return The output of every expression.
     * @throws SyntaxException if it has a problem with the code.
     */
    public static String evaluate(String input, Bindings bindings) throws SyntaxException {
        input = input.trim();
        if (!correctSyntax(input))
            throw new SyntaxException("Incorrect syntax");

        ArrayList<String> output = getListElements(input);
        ArrayList<Item> items;
        String out = "";

        items = getItems(output);
        output = interpret(items, bindings);
        for (int i=0 ; i<output.size() ; i++)
            if (!output.get(i).equals(""))
                if (out.equals(""))
                    out = output.get(i);
                else
                    out = out + "\n" + output.get(i);
        return out;
    }

    /**
     * Converts an ArrayList of Strings to an ArrayList of items recursively.
     * @param elements The ArrayList that is to be evaluated
     * @return an ArrayList of Items
     */
    private static ArrayList<Item> getItems(ArrayList<String> elements) {
        ArrayList<Item> out = new ArrayList<Item>();
        for (int i=0 ; i<elements.size() ; i++) {
            out.add(SchemeList.getItem(elements.get(i)));
        }
        return out;
    }

    /**
     * Parses a scheme list (sans the outside parens) into its elements.
     * @param input A scheme list without the outermost parens.
     * @return The elements of the scheme list.
     */
    public static ArrayList<String> getListElements(String input) {
        int parensOpen = 0;
        ArrayList<String> out = new ArrayList<String>();
        boolean inString = false;
        int start=0, end=0;
        boolean inAtom = false;
        input = input.trim() + " ";
        for (int a=0 ; a<input.length() ; a++) {
            if (input.charAt(a)=='\"') {
                if (!inString)
                    start = a;
                else {
                    end = a;
                    out.add(input.substring(start,end+1));
                }
                inString=!inString;
            } else if (!inString) {
                if (input.charAt(a)=='(' || input.charAt(a)=='[') {
                    parensOpen++;
                    if (parensOpen==1) {
                        start = a;
                    }
                } else if (input.charAt(a)==')' || input.charAt(a)==']') {
                    parensOpen--;
                    if (parensOpen==0) {
                        end = a;
                        out.add(input.substring(start,end+1));
                    }
                } else if (input.charAt(a)!=' ' && input.charAt(a)!='\n' && input.charAt(a)!='\r') {
                    if (parensOpen==0 && !inString) {
                        if (!inAtom) {
                            inAtom=true;
                            start = a;
                            end = a;
                        } else
                            end = a;
                    }
                } else if (input.charAt(a)==' ' || input.charAt(a)=='\n' || input.charAt(a)=='\r')
                    if (inAtom) {
                        out.add(input.substring(start,end+1));
                        inAtom=false;
                    }
            }
        }
        return out;
    }

    /**
     * Determines whether a given syntax looks correct on the surface.
     * @param input Scheme code.
     */
    private static boolean correctSyntax(String input) {
        String parensOpen = "";
        input = input.trim();
        for (int a=0 ; a<input.length() ; a++) {
            if (input.charAt(a)=='(' || input.charAt(a)=='[') {
                parensOpen = parensOpen + input.charAt(a);
            } else if (input.charAt(a)==')') {
                if (parensOpen.equals("")) return false;
                if (parensOpen.charAt(parensOpen.length()-1)=='(') {
                    parensOpen = parensOpen.substring(0, parensOpen.length()-1);
                } else if (parensOpen.charAt(parensOpen.length()-1)=='[') {
                    return false; }
            } else if (input.charAt(a)==']') {
                if (parensOpen.equals("")) return false;
                if (parensOpen.charAt(parensOpen.length()-1)=='[') {
                    parensOpen = parensOpen.substring(0, parensOpen.length()-1);
                } else if (parensOpen.charAt(parensOpen.length()-1)=='(') {
                    return false; }
            }
        }
        return parensOpen.equals("");
    }

    /**
     * Evaluates an ArrayList of Items. 
     * @param input ArrayList of Items.
     * @param bindings Bindings of variable names to scheme code.
     */
    public static ArrayList<String> interpret(ArrayList<Item> input, Bindings bindings) {
        ArrayList<String> out = new ArrayList<String>();
        for (int i=0 ; i<input.size() ; i++) {
            out.add(input.get(i).evaluate(bindings, new Stack<Binding>()).toString());
        }
        return out;
    }

    /**
     * I used this to test the scheme interpreter.
     * @param args
     */
    public static void main(String[] args) {      
        String line="",total="";
        Bindings bindings = new Bindings();
        bindings.addBinding(Binding.getBinding((Atom.SchemeName)Atom.SchemeName.getNameInstance("empty"), SchemeList.getSchemeList(new ArrayList<Item>())));
        //bindings.addBinding(Binding.getBinding((Atom.SchemeName)Atom.SchemeName.getNameInstance("factorial"), SchemeList.getSchemeList(items)))
        evaluate("(define (factorial n) (cond [(<= n 0) 1] [else (* n (factorial (- n 1)))]))", bindings);
        while (true) {
            try {
                System.out.print("> ");
                line = new BufferedReader(new InputStreamReader(System.in)).readLine();
            } catch (IOException io) {}
            if (line.toLowerCase().equals("exit"))
                System.exit(0);
            else if (line.toLowerCase().equals("run"))  
                try {
                    System.out.println("running");
                    System.out.println(evaluate(total, bindings));
                    total = "";
                } catch (Scheme.SyntaxException se) {
                    System.out.println(se.getMessage());
                }
                else
                    total = total + " " + line;
        }
    }
}