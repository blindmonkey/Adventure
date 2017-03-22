package scheme;


public class Binding {
   private Atom.SchemeName name;
   private Item value;
   
   private Binding(Atom.SchemeName name, Item value) {
      this.name = name;
      this.value = value;
   }
   
   public static Binding getBinding(Atom.SchemeName name, Item value) {
      return new Binding(name, value);
   }
   
   public Atom.SchemeName getName() {
      return name;
   }
   
   public Item getValue() {
      return value;
   }
   
   public String toString() {
      return name.getValue() + " : " + value.toString();
   }
}
