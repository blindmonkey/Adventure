package scheme;

import java.util.*;


public class Bindings {
   private Stack<Binding> binds;

   public Bindings() {
      binds = new Stack<Binding>();
   }

   public Bindings addBinding(Binding bind) {
      binds.push(bind);
      return this;
   }

   public Item getBinding(Atom.SchemeName name) throws BindingNotFoundException {
      Stack<Binding> temp = (Stack<Binding>)binds.clone();
      while (!temp.isEmpty()) {
         if (temp.peek().getName().equals(name))
            return temp.peek().getValue();
         temp.pop();
      }
      throw new BindingNotFoundException("Binding for \"" + name.getValue() + "\" not found."); 
   }
   
   public static Item getBinding(Atom.SchemeName name, Stack<Binding> bindings) {
      Stack<Binding> temp = (Stack<Binding>)bindings.clone();
      while (!temp.isEmpty()) {
         if (temp.peek().getName().equals(name))
            return temp.peek().getValue();
         temp.pop();
      }
      throw new BindingNotFoundException("Binding for \"" + name.getValue() + "\" not found.");
   }
   
   public String toString() {
      String out = "";
      for (Binding b : binds)
         if (out.equals(""))
            out = b.toString();
         else
            out = out + "\n" + b.toString();
      return out;
   }
   
   public static class BindingNotFoundException extends RuntimeException {
      public BindingNotFoundException() {
         super();
      }
      
      public BindingNotFoundException(String s) {
         super(s);
      }
   }
}
