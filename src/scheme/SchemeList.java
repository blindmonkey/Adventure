package scheme;

import java.util.*;

import scheme.Atom.SchemeBoolean;
import scheme.Atom.SchemeName;
import scheme.Atom.SchemeNumber;
import scheme.Bindings.BindingNotFoundException;



import com.sun.org.apache.xerces.internal.impl.dv.xs.SchemaDateTimeException;

class SchemeList implements Item {
   private ArrayList<Item> items;

   private SchemeList() {}

   public static boolean isSchemeList(String v) {
      String input = v.trim();
      return ((input.charAt(0)=='(' && input.charAt(input.length()-1)==')') ||
            (input.charAt(0)=='[' && input.charAt(input.length()-1)==']'));
   }

   public Item clone() {
      SchemeList n = getSchemeList();
      for (int i=0 ; i<items.size() ; i++) 
         n.items.add(items.get(i).clone());
      return n;
   }

   public Item addItem(Item i) {
      items.add(i);
      return this;
   }

   public Item removeItem(int index) {
      return items.remove(index);
   }

   public Item get(int index) {
      return items.get(index);
   }

   public Item set(int index, Item item) {
      return items.set(index, item);
   }

   public Item insert(int index, Item item) {
      ArrayList<Item> out = new ArrayList<Item>();
      for (int i=0 ; i<=items.size() ; i++) {
         if (i<index)
            out.add(items.get(i));
         else if (i==index)
            out.add(item);
         else
            out.add(items.get(i-1));
      }
      items = out;
      return this;
   }

   public int getSize() {
      return items.size();
   }

   public boolean isEmpty() {
      return items.size()==0;
   }

   public static Item getItem(String v) {
      Item out;
      String input = v.trim();
      ArrayList<String> temp;
      if (isSchemeList(input)) {
         out = getSchemeList();
         temp = Scheme.getListElements(input.substring(1,input.length() -1));
         for (int i=0 ; i<temp.size() ; i++)
            ((SchemeList)out).items.add(getItem(temp.get(i)));
      } else {
         out = Atom.getAtom(v);
      }
      return out;
   }

   public static SchemeList getSchemeList() {
      SchemeList out = new SchemeList();
      out.items = new ArrayList<Item>();
      return out;
   }
   public static SchemeList getSchemeList(ArrayList<Item> items) {
      SchemeList out = getSchemeList();
      out.items = items;
      return out;
   }

   public String getValue() {
      String out = "(quote";
      for (int i=0 ; i<items.size() ; i++)
         out = out + " " + items.get(i).getValue();
      return out + ")";
   }

   public String toString() {
      String out = "(";
      for (int i=0 ; i<items.size() ; i++)
         if (i==0)
            out = out + items.get(i);
         else
            out = out + " " + items.get(i);
      out = out + ")";
      return out;
   }

   public Item evaluate(Bindings bindings, Stack<Binding> temporaryBindings) throws Scheme.SyntaxException {
      ArrayList<Item> eval = new ArrayList<Item>();
      String command;
      Item output = this;
      boolean boolOut;
      if (items.size()==0) return this;
      eval.add(items.get(0).evaluate(bindings, temporaryBindings));
      if (!eval.get(0).getValue().equals("cond") && !eval.get(0).getValue().equals("define") && !eval.get(0).getValue().equals("lambda")) {
         for (int i=1 ; i<items.size() ; i++) {
            eval.add(items.get(i).evaluate(bindings, temporaryBindings));
         }
      }
      else {
         for (int i=1 ; i<items.size() ; i++) {
            eval.add(items.get(i));
         }
      }

      if (eval.get(0).isName()) {
         command = eval.get(0).getValue();
         if (command.equals("list")) {
            output = SchemeList.getSchemeList();
            for (int i=1 ; i<eval.size() ; i++)
               ((SchemeList)output).addItem(eval.get(i));
         } else if (command.equals("+")) {
            if (eval.size()==1) throw new Scheme.SyntaxException("+ function must have at least one parameter.");
            if (!eval.get(1).isNumber()) throw new Scheme.SyntaxException("+ function can only take numbers.  Given " + eval.get(1));
            output = Atom.SchemeNumber.getNumberInstance();
            ((Atom.SchemeNumber)output).setValue(((Atom.SchemeNumber)eval.get(1)).getValue());
            for (int i=2 ; i<eval.size() ; i++)
               if (eval.get(i).isNumber()) {
                  ((Atom.SchemeNumber)output).setValue(((Atom.SchemeNumber)output).addNumber((Atom.SchemeNumber)eval.get(i)).toString()); 
               } else throw new Scheme.SyntaxException("+ function can only take numbers.  Given " + eval.get(i));
         } else if (command.equals("-")) {
            if (eval.size()==1) throw new Scheme.SyntaxException("- function must have at least one parameter.");
            if (!eval.get(1).isNumber()) throw new Scheme.SyntaxException("- function can only take numbers.  Given " + eval.get(1));
            output = Atom.SchemeNumber.getNumberInstance();
            ((Atom.SchemeNumber)output).setValue(((Atom.SchemeNumber)eval.get(1)).getValue());
            for (int i=2 ; i<eval.size() ; i++)
               if (eval.get(i).isNumber()) {
                  ((Atom.SchemeNumber)output).setValue(((Atom.SchemeNumber)output).subtractNumber((Atom.SchemeNumber)eval.get(i)).toString()); 
               } else throw new Scheme.SyntaxException("= function can only take numbers.  Given " + eval.get(i));
         } else if (command.equals("/")) {
            if (eval.size()==1) throw new Scheme.SyntaxException("/ function must have at least one parameter.");;
            if (!eval.get(1).isNumber()) throw new Scheme.SyntaxException("/ function can only take numbers.  Given " + eval.get(1));
            output = Atom.SchemeNumber.getNumberInstance();
            ((Atom.SchemeNumber)output).setValue(((Atom.SchemeNumber)eval.get(1)).getValue());
            for (int i=2 ; i<eval.size() ; i++)
               if (eval.get(i).isNumber()) {
                  ((Atom.SchemeNumber)output).setValue(((Atom.SchemeNumber)output).divideNumber((Atom.SchemeNumber)eval.get(i)).toString()); 
               } else throw new Scheme.SyntaxException("/ function can only take numbers.  Given " + eval.get(i));
         } else if (command.equals("*")) {
            if (eval.size()==1) throw new Scheme.SyntaxException("* function must have at least one parameter.");;
            if (!eval.get(1).isNumber()) throw new Scheme.SyntaxException("* function can only take numbers.  Given " + eval.get(1));
            output = Atom.SchemeNumber.getNumberInstance();
            ((Atom.SchemeNumber)output).setValue(((Atom.SchemeNumber)eval.get(1)).getValue());
            for (int i=2 ; i<eval.size() ; i++)
               if (eval.get(i).isNumber()) {
                  ((Atom.SchemeNumber)output).setValue(((Atom.SchemeNumber)output).multiplyNumber((Atom.SchemeNumber)eval.get(i)).toString()); 
               } else throw new Scheme.SyntaxException("* function can only take numbers.  Given " + eval.get(i));
         } else if (command.equals("^")) {
            if (eval.size()==1) throw new Scheme.SyntaxException("^ function must have at least one parameter.");;
            if (!eval.get(1).isNumber()) throw new Scheme.SyntaxException("^ function can only take numbers.  Given " + eval.get(1));
            output = Atom.SchemeNumber.getNumberInstance();
            ((Atom.SchemeNumber)output).setValue(((Atom.SchemeNumber)eval.get(1)).getValue());
            for (int i=2 ; i<eval.size() ; i++)
               if (eval.get(i).isNumber()) {
                  ((Atom.SchemeNumber)output).setValue(((Atom.SchemeNumber)output).powerNumber((Atom.SchemeNumber)eval.get(i)).toString()); 
               } else throw new Scheme.SyntaxException("^ function can only take numbers.  Given " + eval.get(i));
         } else if (command.equals("sqrt")) {
            if (eval.size()!=2) throw new Scheme.SyntaxException("sqrt functions must take only one parameter.  Given " + (eval.size()-1));
            if (!eval.get(1).isNumber()) throw new Scheme.SyntaxException("The sqrt function's parameter must be of type number.  Given " + eval.get(1));
            return Atom.getAtom(((Double)Math.sqrt(((Atom.SchemeNumber)eval.get(1)).getNumber().doubleValue())).toString());
         } else if (command.equals("lambda")) {
            if (eval.size()==2)
               return eval.get(1);
            else if (eval.size()==3)
               return this;
            else
               throw new Scheme.SyntaxException("Lambda function takes in 2 or 3 parameters.  Given " + this);
         } else if (command.equals("define")) {
            return getLambda(eval, bindings);
         } else if (command.equals("bindings")) {
            return Atom.getAtom(bindings.toString() + "\n" + temporaryBindings.toString());
         } else if (command.equals("cond")) {
            for (int i=1 ; i<eval.size() ; i++) {
               if (eval.get(i).isList()) {
                  ((SchemeList)eval.get(i)).set(0, ((SchemeList)eval.get(i)).get(0).evaluate(bindings, temporaryBindings));
                  if (((SchemeList)eval.get(i)).get(0).isBoolean()) {
                     if (((SchemeList)eval.get(i)).get(0).equals(Atom.SchemeBoolean.getBooleanInstance("#t")))
                        return ((SchemeList)eval.get(i)).get(1).evaluate(bindings, temporaryBindings);
                  } else if (((SchemeList)eval.get(i)).get(0).isName()) {
                     if (((SchemeList)eval.get(i)).get(0).getValue().equals("else"))
                        return ((SchemeList)eval.get(i)).get(1).evaluate(bindings, temporaryBindings);
                  } else
                     throw new Scheme.SyntaxException("cond statements must take in booleans.  Given " + ((SchemeList)eval.get(i)).get(0));
               } else
                  throw new Scheme.SyntaxException("cond branches must not be atoms.  Given " + eval.get(i));
            }
            throw new Scheme.SyntaxException("The cond statement has no true branches.  Given " + this);
         } else if (command.equals("cons")) {
            if (eval.size()!=3) throw new Scheme.SyntaxException("cons functions must take only two parameters.  Given " + (eval.size()-1));
            if (!eval.get(2).isList()) throw new Scheme.SyntaxException("The second parameter of the cons function must be of type list.  Given " + eval.get(2));
            return ((SchemeList)eval.get(2)).insert(0, eval.get(1));
         } else if (command.equals("symbol?")) {
            if (eval.size()!=2) throw new Scheme.SyntaxException("symbol? must only take 1 parameter.  Given " + (eval.size()-1));
            return Atom.SchemeBoolean.getBooleanInstance(eval.get(1).isSymbol());
         } else if (command.equals("number?")) {
            if (eval.size()!=2) throw new Scheme.SyntaxException("number? must only take 1 parameter.  Given " + (eval.size()-1));
            return Atom.SchemeBoolean.getBooleanInstance(eval.get(1).isNumber());
         } else if (command.equals("boolean?")) {
            if (eval.size()!=2) throw new Scheme.SyntaxException("boolean? must only take 1 parameter.  Given " + (eval.size()-1));
            return Atom.SchemeBoolean.getBooleanInstance(eval.get(1).isBoolean());
         } else if (command.equals("string?")) {
            if (eval.size()!=2) throw new Scheme.SyntaxException("string? must only take 1 parameter.  Given " + (eval.size()-1));
            return Atom.SchemeBoolean.getBooleanInstance(eval.get(1).isString());
         } else if (command.equals("symbol=?")) {
            if (eval.size()!=3) throw new Scheme.SyntaxException("symbol=? must take 2 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isSymbol() || !eval.get(2).isSymbol()) throw new Scheme.SyntaxException("Both symbol=? parameter must be symbols.");
            return Atom.SchemeBoolean.getBooleanInstance(eval.get(1).equals(eval.get(2)));
         } else if (command.equals("number=?")) {
            if (eval.size()!=3) throw new Scheme.SyntaxException("number=? must take 2 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isNumber() || !eval.get(2).isNumber()) throw new Scheme.SyntaxException("Both number=? parameter must be numbers.");
            return Atom.SchemeBoolean.getBooleanInstance(eval.get(1).equals(eval.get(2)));
         } else if (command.equals("boolean=?")) {
            if (eval.size()!=3) throw new Scheme.SyntaxException("boolean=? must take 2 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isBoolean() || !eval.get(2).isBoolean()) throw new Scheme.SyntaxException("Both boolean=? parameter must be booleans.");
            return Atom.SchemeBoolean.getBooleanInstance(eval.get(1).equals(eval.get(2)));
         } else if (command.equals("string=?")) {
            if (eval.size()!=3) throw new Scheme.SyntaxException("string=? must take 2 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isString() || !eval.get(2).isString()) throw new Scheme.SyntaxException("Both string=? parameter must be strings.");
            return Atom.SchemeBoolean.getBooleanInstance(eval.get(1).equals(eval.get(2)));
         } else if (command.equals("equals?")) {
            if (eval.size()!=3) throw new Scheme.SyntaxException("equals? must take 2 parameters.  Given " + (eval.size()-1));
            return Atom.SchemeBoolean.getBooleanInstance(eval.get(1).equals(eval.get(2)));
         } else if (command.equals("empty?")) {
            if (eval.size()!=2) throw new Scheme.SyntaxException("empty? must take 1 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isList()) throw new Scheme.SyntaxException("empty? only takes in lists.  Given " + eval.get(1));
            return Atom.SchemeBoolean.getBooleanInstance(((SchemeList)eval.get(1)).isEmpty());
         } else if (command.equals("cons?")) {
            if (eval.size()!=2) throw new Scheme.SyntaxException("cons? must take 1 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isList()) throw new Scheme.SyntaxException("cons? only takes in lists.  Given " + eval.get(1));
            return Atom.SchemeBoolean.getBooleanInstance(!((SchemeList)eval.get(1)).isEmpty());
         } else if (command.equals("=")) {
            if (eval.size()!=3) throw new Scheme.SyntaxException("= must take 2 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isNumber() || !eval.get(2).isNumber()) throw new Scheme.SyntaxException("= only takes in numbers.  Given " + this);
            return Atom.SchemeBoolean.getBooleanInstance(eval.get(1).equals(eval.get(2)));
         } else if (command.equals(">")) {
            if (eval.size()<3) throw new Scheme.SyntaxException("> must take at least 2 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isNumber()) throw new Scheme.SyntaxException("> only takes in numbers.");
            for (int i=1 ; i<eval.size()-1 ; i++)
               if (eval.get(i+1).isNumber())
                  if (!(((Atom.SchemeNumber)eval.get(i)).greaterThan((Atom.SchemeNumber)eval.get(i+1))))
                     return Atom.SchemeBoolean.getBooleanInstance(false);
            return Atom.SchemeBoolean.getBooleanInstance(true);
         } else if (command.equals("<")) {
            if (eval.size()<3) throw new Scheme.SyntaxException("< must take at least 2 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isNumber()) throw new Scheme.SyntaxException("< only takes in numbers.");
            for (int i=1 ; i<eval.size()-1 ; i++)
               if (eval.get(i+1).isNumber())
                  if (!(((Atom.SchemeNumber)eval.get(i)).lessThan((Atom.SchemeNumber)eval.get(i+1))))
                     return Atom.SchemeBoolean.getBooleanInstance(false);
            return Atom.SchemeBoolean.getBooleanInstance(true); 
         } else if (command.equals(">=")) {
            if (eval.size()<3) throw new Scheme.SyntaxException(">= must take at least 2 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isNumber()) throw new Scheme.SyntaxException(">= only takes in numbers.");
            for (int i=1 ; i<eval.size()-1 ; i++)
               if (eval.get(i+1).isNumber())
                  if (!(((Atom.SchemeNumber)eval.get(i)).greaterThan((Atom.SchemeNumber)eval.get(i+1)) ||
                        (((Atom.SchemeNumber)eval.get(i)).equals((Atom.SchemeNumber)eval.get(i+1)))))
                     return Atom.SchemeBoolean.getBooleanInstance(false);
            return Atom.SchemeBoolean.getBooleanInstance(true);
         } else if (command.equals("<=")) {
            if (eval.size()<3) throw new Scheme.SyntaxException("<= must take at least 2 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isNumber()) throw new Scheme.SyntaxException("<= only takes in numbers. Given " + this);
            for (int i=1 ; i<eval.size()-1 ; i++)
               if (eval.get(i+1).isNumber())
                  if (!(((Atom.SchemeNumber)eval.get(i)).lessThan((Atom.SchemeNumber)eval.get(i+1)) ||
                        (((Atom.SchemeNumber)eval.get(i)).equals((Atom.SchemeNumber)eval.get(i+1)))))
                     return Atom.SchemeBoolean.getBooleanInstance(false);
            return Atom.SchemeBoolean.getBooleanInstance(true);
         } else if (command.equals("!=")) {
            if (eval.size()!=3) throw new Scheme.SyntaxException("!= must take 2 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isNumber() || !eval.get(2).isNumber()) throw new Scheme.SyntaxException("!= only takes in numbers.");
            return Atom.SchemeBoolean.getBooleanInstance(!eval.get(1).equals(eval.get(2)));
         } else if (command.equals("first")) {
            if (eval.size()!=2) throw new Scheme.SyntaxException("first must take 1 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isList()) throw new Scheme.SyntaxException("first only takes in lists.  Given " + eval.get(1));
            return ((SchemeList)eval.get(1)).get(0);
         } else if (command.equals("rest")) {
            if (eval.size()!=2) throw new Scheme.SyntaxException("rest must take 1 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isList()) throw new Scheme.SyntaxException("rest only takes in lists.  Given " + eval.get(1));
            output = SchemeList.getSchemeList();
            for (int i=1 ; i<((SchemeList)eval.get(1)).getSize() ; i++) {
               ((SchemeList)output).addItem(((SchemeList)eval.get(1)).get(i));
            }
            return output;
         } else if (command.equals("and")) {
            if (eval.size()<3) throw new Scheme.SyntaxException("and must take at least 2 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isBoolean()) throw new Scheme.SyntaxException("and only takes in numbers. Given " + this);
            boolOut = ((Atom.SchemeBoolean)eval.get(1)).getBooleanValue();
            for (int i=1 ; i<eval.size() ; i++)
               if (eval.get(i).isBoolean())
                  boolOut = boolOut && ((Atom.SchemeBoolean)eval.get(i)).getBooleanValue();
               else
                  throw new Scheme.SyntaxException("and only takes in numbers. Given " + this);
            return Atom.getAtom(boolOut);
         } else if (command.equals("or")) {
            if (eval.size()<3) throw new Scheme.SyntaxException("and must take at least 2 parameters.  Given " + (eval.size()-1));
            if (!eval.get(1).isBoolean()) throw new Scheme.SyntaxException("and only takes in numbers. Given " + this);
            boolOut = ((Atom.SchemeBoolean)eval.get(1)).getBooleanValue();
            for (int i=1 ; i<eval.size() ; i++)
               if (eval.get(i).isBoolean())
                  boolOut = boolOut || ((Atom.SchemeBoolean)eval.get(i)).getBooleanValue();
               else
                  throw new Scheme.SyntaxException("and only takes in numbers. Given " + this);
            return Atom.getAtom(boolOut);
         }
      } else if (eval.get(0).isList() && !((SchemeList)eval.get(0)).isEmpty()) {
         if (((SchemeList)eval.get(0)).get(0).isName())
            if (((SchemeList)eval.get(0)).get(0).getValue().equals("lambda"))
               if (((SchemeList)eval.get(0)).getSize()==3 &&
                     (eval.size()==((SchemeList)((SchemeList)eval.get(0)).get(1)).getSize()+1))
                  return evalLambda(eval.get(0), eval,bindings,temporaryBindings);
               else
                  throw new Scheme.SyntaxException("Incorrect syntax for lambda.  Given " + this);
      }
      return output;
   }

   private Item evalLambda(Item lambda, ArrayList<Item> call, Bindings bindings, Stack<Binding> temporaryBindings) {
      for (int i=1 ; i<call.size() ; i++) {
         if (((SchemeList)((SchemeList)lambda).get(1)).get(i-1).isName()) {
            temporaryBindings.push(Binding.getBinding((Atom.SchemeName)((SchemeList)((SchemeList)lambda).get(1)).get(i-1), call.get(i).evaluate(bindings, temporaryBindings)));
         }
         else
            return lambda;
      }
      return ((SchemeList)lambda).get(2).evaluate(bindings, temporaryBindings);
   }

   private Item getLambda(ArrayList<Item> define, Bindings bindings) throws Scheme.SyntaxException {
      if (define.size()!=3) throw new Scheme.SyntaxException("Incorrect syntax for define");
      SchemeList out = SchemeList.getSchemeList();
      SchemeList params = SchemeList.getSchemeList();
      if (define.get(1).isList()) {
         if (!((SchemeList)define.get(1)).get(0).isName())
            throw new Scheme.SyntaxException("All parameters for a define statement must be names");
         for (int i=1 ; i<((SchemeList)define.get(1)).getSize() ; i++) {
            if (((SchemeList)define.get(1)).get(i).isName())
               params.addItem(((SchemeList)define.get(1)).get(i));
            else
               throw new Scheme.SyntaxException("All parameters for a define statement must be names");
         }
         out.addItem(Atom.getAtom("lambda"));
         out.addItem(params);
         out.addItem(define.get(2));
         //bindings.addBinding(Binding.getBinding((Atom.SchemeName)((SchemeList)define.get(1)).get(0), define.get(2)));
         bindings.addBinding(Binding.getBinding((Atom.SchemeName)((SchemeList)define.get(1)).get(0), out));
      } else {
         if (!define.get(1).isName())
            throw new Scheme.SyntaxException("All parameters for a define statement must be names");
         out.addItem(Atom.getAtom("lambda"));
         out.addItem(define.get(2));
         bindings.addBinding(Binding.getBinding((Atom.SchemeName)define.get(1),define.get(2)));
      }
      return out;
   }

   private Item evalBindings(Item item, Bindings bindings, Stack<Binding> temporaryBindings) {
      Item out;
      //System.out.println("getting binding for " + item);
      if (item.isName()) {
         try {
            out = Bindings.getBinding((Atom.SchemeName)item, temporaryBindings);
         } catch (Bindings.BindingNotFoundException bnfe) {
            try {
               out = bindings.getBinding((Atom.SchemeName)item);
            } catch (Bindings.BindingNotFoundException bnfe2) {
               out = item;
            }
         }
         //System.out.println("Returning " + out);
      }
      else out = item;
      return out;
   }

   public boolean isList() { return true; }
   public boolean isName() { return false; }
   public boolean isNumber() { return false; }
   public boolean isString() { return false; }
   public boolean isSymbol() { return false; }
   public boolean isBoolean() { return false; }

   public Number getNumber() {
      throw new RuntimeException("List " + getValue() + " is not a number");
   }
}