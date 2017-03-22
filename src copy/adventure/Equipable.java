package adventure;


    /*
     * Interface Equip
     *
     * Implemented by artifact that provide an equip() method
     *
     */

    public interface Equipable {

      public String name ();
      public void equip (Thing i);
      public void unequip(Thing i);

    }

