package scheme;

import operations.*;

import java.util.*;

import scheme.Bindings.BindingNotFoundException;

abstract class Atom implements Item {

   public abstract Item clone();
   
   public static Atom getAtom(String input) {
      if (isNumber(input))
         return SchemeNumber.getNumberInstance(input);
      else if (isSymbol(input))
         return SchemeSymbol.getSymbolInstance(input);
      else if (isString(input))
         return SchemeString.getStringInstance(input);
      else if (isBoolean(input))
         return SchemeBoolean.getBooleanInstance(input);
      else
         return SchemeName.getNameInstance(input);
      //throw new RuntimeException("Unknown atom syntax.  Given \"" + input + "\".");
   }
   
   public static Atom getAtom(boolean input) {
      return SchemeBoolean.getBooleanInstance(input);
   }

   abstract public String getValue();
   abstract public Number getNumber();
   abstract public Atom setValue(String v); 

   public static boolean isAtom(String v) {
      //System.out.println("Identifying string as atom \"" + v + "\"");
      //System.out.println("isNumber: " + isNumber(v));
      //System.out.println("isSymbol: " + isSymbol(v));
      //System.out.println("isString: " + isString(v));
      return isNumber(v) || isSymbol(v) || isString(v) || isBoolean(v);
   }
   public boolean isNumber() { return false; }
   public boolean isString() { return false; }
   public boolean isSymbol() { return false; }
   public boolean isName() { return false; }
   public boolean isList() { return false; }
   public boolean isBoolean() { return false; }

   public static boolean isNumber(String v) {
      String input = v.trim();
      if (input.equals("-") || input.equals("")) return false;
      int start;
      if (input.charAt(0)=='-') start = 1; else start = 0;
      for (int i=start ; i<input.length() ; i++)
         if ((input.charAt(i)<'0' || input.charAt(i)>'9') && input.charAt(i)!='.')
            return false;
      return true;
   }
   public static boolean isString(String v) {
      String input = v.trim();
      if (input.equals("")) return false;
      if (input.charAt(0) != '\"' ||
            input.charAt(input.length()-1) != '\"')
         return false;
      return true;
   }
   public static boolean isSymbol(String v) {
      String input = v.trim();
      if (input.equals("")) return false;
      if (StringOperations.inStr(input, " ")<0 && input.charAt(0)=='\'')
         return true;
      return false;
   }
   public static boolean isBoolean(String v) {
      return v.equals("true") || v.equals("false") || v.equals("#f") || v.equals("#t");
   }

   public Item evaluate(Bindings bindings, Stack<Binding> temp) {
      return this;
   }

   public String toString() {
      return this.getValue();
   }
   
   public abstract boolean equals(Object obj);

   public static class SchemeNumber extends Atom {
      private String value;

      private SchemeNumber() {}

      public Number getNumber() {
         //Number num;
         if (operations.StringOperations.inStr(value, ".")>=0)
            return Double.valueOf(value);
         else {
            return Long.valueOf(value);
         }
      }

      public String getValue() {
         return value.toString();
      }

      public Number addNumber(SchemeNumber n) {
         if (operations.StringOperations.inStr(value, ".")>=0)
            if (operations.StringOperations.inStr(n.getValue(), ".")>=0)
               return Double.valueOf(value) + Double.valueOf(n.getValue());
            else
               return Double.valueOf(value) + Long.valueOf(n.getValue());
         else
            if (operations.StringOperations.inStr(n.getValue(), ".")>=0)
               return Long.valueOf(value) + Double.valueOf(n.getValue());
            else
               return Long.valueOf(value) + Long.valueOf(n.getValue());
      }

      public Number subtractNumber(SchemeNumber n) {
         if (operations.StringOperations.inStr(value, ".")>=0)
            if (operations.StringOperations.inStr(n.getValue(), ".")>=0)
               return Double.valueOf(value) - Double.valueOf(n.getValue());
            else
               return Double.valueOf(value) - Long.valueOf(n.getValue());
         else
            if (operations.StringOperations.inStr(n.getValue(), ".")>=0)
               return Long.valueOf(value) - Double.valueOf(n.getValue());
            else
               return Long.valueOf(value) - Long.valueOf(n.getValue());
      }

      public Number multiplyNumber(SchemeNumber n) {
         if (operations.StringOperations.inStr(value, ".")>=0)
            if (operations.StringOperations.inStr(n.getValue(), ".")>=0)
               return Double.valueOf(value) * Double.valueOf(n.getValue());
            else
               return Double.valueOf(value) * Long.valueOf(n.getValue());
         else
            if (operations.StringOperations.inStr(n.getValue(), ".")>=0)
               return Long.valueOf(value) * Double.valueOf(n.getValue());
            else
               return Long.valueOf(value) * Long.valueOf(n.getValue());
      }

      public Number divideNumber(SchemeNumber n) {
         //System.out.println("Dividing " + value + " by " + n.getValue());
         return Double.valueOf(value) / Double.valueOf(n.getValue());
      }

      public Number powerNumber(SchemeNumber n) {
         return Math.pow(Double.valueOf(value), Double.valueOf(n.getValue()));
      }


      public boolean isNumber() { return true; }

      public Atom setValue(String v) {
         String input = v;
         int start=0,end=v.length()-1;

         for (int i=0 ; i<input.length() ; i++)
            if (!(input.charAt(i)=='0')) {
               start = i; break;
            }

         if (operations.StringOperations.inStr(input, ".")>=0)
            for (int i=input.length()-1 ; i>=0 ; i--) {
               if (!(input.charAt(i)=='0')) {
                  end = i+1; break;
               }
            }
         else
            end = input.length();

         value = input.substring(start,end);
         if (value.charAt(value.length()-1)=='.')
            value = value.substring(0,value.length()-1);
         if (value.equals(""))
            value = "0";

         return this;
      }

      public static Atom getNumberInstance() {
         return new SchemeNumber();
      }
      public static Atom getNumberInstance(String input) {
         return (new SchemeNumber()).setValue(input);
      }

      @Override
      public boolean equals(Object obj) {
         if (obj instanceof SchemeNumber)
            return ((SchemeNumber)obj).getNumber().equals(this.getNumber());
         else return false;
      }
      
      public boolean lessThan(SchemeNumber n) {
         if (operations.StringOperations.inStr(value, ".")>=0)
            if (operations.StringOperations.inStr(n.getValue(), ".")>=0)
               return Double.valueOf(value) < Double.valueOf(n.getValue());
            else
               return Double.valueOf(value) < Long.valueOf(n.getValue());
         else
            if (operations.StringOperations.inStr(n.getValue(), ".")>=0)
               return Long.valueOf(value) < Double.valueOf(n.getValue());
            else
               return Long.valueOf(value) < Long.valueOf(n.getValue());
      }
      
      public boolean greaterThan(SchemeNumber n) {
         System.out.println("Comparing (>) " + getValue() + " and " + n.getValue());
         if (operations.StringOperations.inStr(value, ".")>=0)
            if (operations.StringOperations.inStr(n.getValue(), ".")>=0)
               return Double.valueOf(value) > Double.valueOf(n.getValue());
            else
               return Double.valueOf(value) > Long.valueOf(n.getValue());
         else
            if (operations.StringOperations.inStr(n.getValue(), ".")>=0)
               return Long.valueOf(value) > Double.valueOf(n.getValue());
            else
               return Long.valueOf(value) > Long.valueOf(n.getValue());
      }
      public Item clone() {
         return getNumberInstance(this.value);
      }
   }

   public static class SchemeString extends Atom {
      private String value;

      private SchemeString() {}

      public Number getNumber() {
         throw new RuntimeException("String " + getValue() + " is not a number");
      }

      public String getValue() {
         return "\"" + value + "\"";
      }

      public boolean isString() {
         return true;
      }

      @Override
      public Atom setValue(String v) {
         String input = v.trim();
         if (input.charAt(0) != '\"' ||
               input.charAt(input.length()-1) != '\"')
            throw new RuntimeException("String values must begin and end with double quotes.  Given " + input);
         value = input.substring(1,input.length()-1);
         return this;
      }
      public static Atom getStringInstance() {
         return new SchemeString();
      }
      public static Atom getStringInstance(String input) {
         return (new SchemeString()).setValue(input);
      }

      @Override
      public boolean equals(Object obj) {
         if (obj instanceof SchemeString)
            return ((SchemeString)obj).getValue().equals(getValue());
         else return false;
      }
      
      public Item clone() {
         return getStringInstance(this.value);
      }
   }

   public static class SchemeSymbol extends Atom {
      private String value;

      public Number getNumber() {
         throw new RuntimeException("Symbol " + getValue() + " is not a number");
      }

      public String getValue() {
         return "'" + value;
      }

      public boolean isSymbol() {
         return true;
      }

      @Override
      public Atom setValue(String v) {
         String input = v.trim();
         if (input.charAt(0) != '\'')
            throw new RuntimeException("Symbols must begin with a single quote.  Given " + input);
         value = input.substring(1,input.length());
         return this;
      }
      public static Atom getSymbolInstance() {
         return new SchemeSymbol();
      }
      public static Atom getSymbolInstance(String input) {
         return (new SchemeSymbol()).setValue(input);
      }

      @Override
      public boolean equals(Object obj) {
         if (obj instanceof SchemeSymbol)
            return ((SchemeSymbol)obj).getValue().equals(getValue());
         else return false;
      }
      
      public Item clone() {
         return getSymbolInstance(this.value);
      }
   }

   public static class SchemeName extends Atom {
      private String value;

      private SchemeName() {}

      public Item evaluate(Bindings bindings, Stack<Binding> temporaryBindings) {
         Item out;
         //System.out.println("Looking for binding for " + this);
         try {
            out = Bindings.getBinding((Atom.SchemeName)this, temporaryBindings).clone();
            //System.out.println("Found in temporary bindings : " + temporaryBindings);
         } catch (Bindings.BindingNotFoundException bnfe) {
            try {
               out = bindings.getBinding((Atom.SchemeName)this).clone();
               //System.out.println("Found in permanent bindings");
            } catch (Bindings.BindingNotFoundException bnfe2) {
               out = this;
               //System.out.println("Not found");
            }
         }
         //System.out.println("\tReturning " + out + " for " + this);
         return out;
      }
      public static Atom getNameInstance() {
         return new SchemeName();
      }

      public static Atom getNameInstance(String input) {
         return (new SchemeName()).setValue(input);
      }

      public Number getNumber() {
         throw new RuntimeException("Atom is not a number");
      }

      @Override
      public String getValue() {
         return value;
      }

      @Override
      public Atom setValue(String v) {
         value = v;
         return this;
      }

      @Override
      public boolean isName() {
         return true;
      }
      
      public boolean equals(Object obj) {
         if (obj instanceof SchemeName)
            return this.getValue().equals(((SchemeName)obj).getValue());
         else return false;
      }
      
      public Item clone() {
         return getNameInstance(this.value);
      }
   }

   public static class SchemeBoolean extends Atom {
      private boolean value;

      private SchemeBoolean() {}

      public static Atom getBooleanInstance() {
         return new SchemeBoolean();
      }

      public static Atom getBooleanInstance(String input) {
         return (new SchemeBoolean()).setValue(input);
      }
      
      public static Atom getBooleanInstance(boolean input) {
         if (input)
            return (new SchemeBoolean()).setValue("#t");
         else
            return (new SchemeBoolean()).setValue("#f");
      }
      
      public boolean getBooleanValue() {
         return value;
      }

      public Number getNumber() {
         throw new RuntimeException("Atom is not a number");
      }

      @Override
      public String getValue() {
         if (value) return "#t"; else return "#f";
      }

      @Override
      public Atom setValue(String v) {
         if (v.equals("true") || v.equals("#t"))
            value = true;
         else if (v.equals("false") || v.equals("#f"))
            value = false;
         else
            throw new RuntimeException(v + " is not a valid boolean value");
         return this;
      }

      @Override
      public boolean isBoolean() {
         return true;
      }
      
      public boolean equals(Object obj) {
         if (obj instanceof SchemeBoolean)
            return this.getValue().equals(((SchemeBoolean)obj).getValue());
         else return false;
      }
      
      public Item clone() {
         return getBooleanInstance(this.value);
      }
   }
}