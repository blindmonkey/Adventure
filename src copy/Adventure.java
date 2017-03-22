
import java.util.*;
import java.io.*;
import adventure.*;

/*
 * Main class for the game
 *
 */

public class Adventure {

    /*
     * A helpful auxiliary method to connect places on the map
     *
     */
    public static void connect (Place from, String direction, 
                    String reverseDirection, Place to) {
        Exit.create (from,to,direction);
        Exit.create (to,from,reverseDirection);
    }


    public static void createWorld (String myName) {

        Place wvh328 = World.registerRoom(Place.create("riccardo-office"));
        Place wvh108 = World.registerRoom(Place.create("wvh-108"));
        Place wvh1st = World.registerRoom(Place.create("wvh-first-floor"));
        Place wvhlab = World.registerRoom(Place.create("wvh-computer-lab"));
        Place wvh2nd = World.registerRoom(Place.create("wvh-second-floor"));
        Place wvh202 = World.registerRoom(Place.create("cs-office"));
        Place wvh3rd = World.registerRoom(Place.create("wvh-third-floor"));
        Place wvhprl = World.registerRoom(Place.create("prl-lab"));
        Place matthiaslair = World.registerRoom(Place.create("matthias-lair"));
        Place currycenter = World.registerRoom(Place.create("curry-center"));
        Place marinocenter = World.registerRoom(Place.create("marino-center"));
        Place abp = World.registerRoom(Place.create("au-bon-pain"));
        Place centennial = World.registerRoom(Place.create("centennial-common"));
        Place snell = World.registerRoom(Place.create("snell-library"));

        /* added 3 rooms per assignment 0 */
        Place ruggles = World.registerRoom(Place.create("ruggles"));
        Place ryder = World.registerRoom(Place.create("ryder"));
        Place vampirelair = World.registerRoom(Place.create("vampirelair"));

        Place wvg = World.registerRoom(Place.create("wvg"));
        Place willis = World.registerRoom(Place.create("willis-hall"));
        Place behrakis = World.registerRoom(Place.create("behrakis"));
        Place lake = World.registerRoom(Place.create("lake-hall"));
        Place knowles = World.registerRoom(Place.create("knowles-center"));
        Place shillman = World.registerRoom(Place.create("shillman-hall"));
        Place ell = World.registerRoom(Place.create("ell-hall"));
        Place krentzman = World.registerRoom(Place.create("krentzman-quad"));
        Place speare = World.registerRoom(Place.create("speare-hall"));

        /* Lake Dungeon */
        Place lakedungeon = World.registerDungeonRoom(Dungeon.create("lake-dungeon"));
        Place laked1r1 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-1-rm-1"));
        Place laked2r1 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-2-rm-1"));
        Place laked2r2 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-2-rm-2"));
        Place laked2r3 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-2-rm-3"));
        Place laked2r4 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-2-rm-4"));
        Place laked2r5 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-2-rm-5"));
        Place laked2r6 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-2-rm-6"));
        Place laked3r1 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-1"));
        Place laked3r2 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-2"));
        Place laked3r3 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-3"));
        Place laked3r4 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-4"));
        Place laked3r5 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-5"));
        Place laked3r6 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-6"));
        Place laked3r7 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-7"));
        Place laked3r8 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-8"));
        Place laked3r9 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-9"));

        //rescue the professor here
        Place laked3r10 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-10"));

        Place laked3r11 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-11"));
        Place laked3r12 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-12"));
        Place laked3r13 = World.registerDungeonRoom(Dungeon.create("lake-dungeon-lvl-3-rm-13"));

        /* placed three rooms in map */
        connect(ryder, "down", "up", vampirelair);
        connect(ruggles, "east", "west", shillman);
        connect(ruggles, "west", "east", ryder);
        connect(centennial, "north", "south", ruggles);

        connect(wvh328, "south", "north", wvh3rd);
        connect(wvh3rd, "south", "north", wvhprl);
        connect(wvhprl, "south", "north", matthiaslair);
        connect(wvh3rd, "down", "up", wvh2nd);
        connect(wvh2nd, "east", "west", wvh202);
        connect(wvh2nd, "down", "up", wvh1st);
        connect(wvh1st, "south", "north", wvh108);
        connect(wvh1st, "west", "east", wvhlab);
        connect(wvh1st, "north", "south", knowles);
        connect(knowles, "east", "west", lake);
        connect(lake, "south", "north", wvg);
        connect(lake, "down", "up", lakedungeon);
        connect(wvg, "east", "west", behrakis);
        connect(behrakis, "north", "south", willis);
        connect(lake, "east", "west", willis);
        connect(wvh1st, "east", "west", wvg);
        connect(knowles, "north", "south", currycenter);
        connect(knowles, "west", "east", marinocenter);
        connect(snell, "west", "east", currycenter);
        connect(centennial, "west", "east", snell);
        connect(centennial, "south", "north", shillman);
        connect(marinocenter, "south", "north", abp);
        connect(currycenter, "west", "east", ell);
        connect(ell, "west", "east", krentzman);
        connect(krentzman, "west", "east", speare);
        connect(speare, "south", "north", marinocenter);

        /* Lake Dungeon */
        connect(lakedungeon, "down", "up", laked1r1);
        connect(laked1r1, "down", "up", laked2r1);
        connect(laked2r1, "west", "east", laked2r2);
        connect(laked2r2, "west", "east", laked2r3);
        connect(laked2r2, "south", "north", laked2r4);
        connect(laked2r3, "south", "north", laked2r5);
        connect(laked2r5, "east", "west", laked2r6);
        connect(laked2r5, "down", "up", laked3r1);
        connect(laked3r1, "south", "north", laked3r2);
        connect(laked3r1, "west", "east", laked3r6);
        connect(laked3r2, "south", "north", laked3r3);
        connect(laked3r3, "south", "west", laked3r5);
        connect(laked3r3, "east", "west", laked3r4);
        connect(laked3r3, "west", "east", laked3r12);
        connect(laked3r6, "west", "east", laked3r7);
        connect(laked3r6, "south", "north", laked3r10);
        connect(laked3r10, "south", "north", laked3r12);
        connect(laked3r12, "west", "east", laked3r11);
        connect(laked3r7, "west", "east", laked3r8);
        connect(laked3r7, "south", "north", laked3r9);
        connect(laked3r8, "south", "north", laked3r5);
        connect(laked3r9, "west", "east", laked3r13);


        Avatar me = Avatar.create(myName, World.pickRandom(World.rooms()));
        World.registerAvatar(me);

        Computer.create("hal-9000", wvhlab);
        Computer.create("johnny-5", wvhprl);
        Thing.create("blackboard", wvh108);
        Thing.create("lovely-trees", centennial);

        /* Creating 5 things per assignment 0 */
        Thing.create("casket", vampirelair);
        Thing.create("dunkin-donuts", ruggles);
        MobileThing.create("vampire-book", ryder);
        MobileThing.create("smelly-sock", marinocenter);
        MobileThing.create("zork-manual", centennial);

        MobileThing.create("cs-book", snell);
        MobileThing.create("math-book", snell);
        MobileThing.create("htdp", matthiaslair);


        GPSTracker.create("tx200", me);

        String[] chests = {
                        "cabinet",
                        "desk-1",
                        "backpack",
                        "toolbox",
                        "file-cabinet",
                        "dufflebag",
                        "chest",
                        "locker-1",
                        "locker-2",
                        "locker-3",
                        "desk-2",
                        "desk-3"
        };

        for (String chest : chests)
            World.registerChest(Chest.create(chest, World.pickRandom(World.rooms())));

        /*
         * Create dungeon chests
         * 
         * 
         */

        for (int i = 0; i<6; i++) {
            World.registerChest(Chest.create("lake-chest-" + i+1, 
                            World.pickRandom(World.dungeon())));
        }

        /*
         * Create Vampires in dungeons
         * 
         */

        String [] vampires = { "armand", "claudia", "lestat", "louie", "marius",
                        "enkil", "diego", "maharit", "jacque", "akiva", "akisha", 
                        "ariel", "azriel", "altarib"}; 

        for (String vampire: vampires)
            Vampire.create(vampire,
                            World.pickRandom(World.dungeon()), 
                            World.randomNumber(3), 
                            World.randomNumber(3));

        /* 
         * Create professor to be rescued
         * 
         */

        Professor.create("casey", laked3r10, 1, 1);

        /*
         * Create weapons, armor, shields, rings
         * 
         */


        String [] headers = {"aaron's", "sergey's", "dark-elf", "steel",
                        "wooden", "silver", "bleak", "dunmare"};

        String [] weapons = {"sword", "battleaxe", "mace", "bow", "broadsword",
                        "staff", "dagger"};

        String [] armor = {"plate-mail", "chain-mail", "breastplate"};

        String [] shield = {"shield"};

        String [] rings = {"ring"};

        String temp;
        for (String w : weapons){

            temp = headers[(World.randomNumber(headers.length))-1]
                           + "-" + w;
            System.out.println("Creating " + temp);
            Items.createWeapon
            (temp,
                     World.pickRandom(World.chests()),
                     World.randomNumber(8),
                     World.randomNumber(6), 
                     World.randomNumber(7));
        }
        for (String a : armor){
            temp = headers[(World.randomNumber(headers.length))-1]
                           + "-" + a;
            System.out.println("Creating " + temp);
                Items.createArmor
                (temp,
                         World.pickRandom(World.chests()),
                         World.randomNumber(8),
                         World.randomNumber(6), 
                         World.randomNumber(7));
        }
        for (String s : shield)
                Items.createShield
                (headers[(World.randomNumber(headers.length))-1]
                         + " " + s,
                         World.pickRandom(World.chests()),
                         World.randomNumber(3),
                         World.randomNumber(4), 
                         World.randomNumber(3));

        for (String r : rings){
            temp = headers[(World.randomNumber(headers.length))-1]
                           + "-" + r; 
            System.out.println("Creating " + temp);
                Items.createRing
                (temp,
                         World.pickRandom(World.chests()),
                         World.randomNumber(8),
                         World.randomNumber(6), 
                         World.randomNumber(7));
        }



        String[] homeworks = {"hw-1",
                        "hw-2",
                        "hw-3",
                        "hw-4",
                        "hw-5",
                        "hw-6",
        "hw-7"};

        for (String homework : homeworks)
            UnfinishedHomework.create(homework,
                            World.pickRandom(World.rooms()));

        String[] students = {"joe-junior",
                        "sophie-sophomore",
        "cedric-senior"};

        for (String student : students) 
            AutonomousPerson.create(student,
                            World.pickRandom(World.rooms()),
                            World.randomNumber(5), World.randomNumber(5));

        String[] graders = {"alec", 
        "bryan"};


        for (String grader : graders)
            Grader.create(grader,
                            World.pickRandom(World.rooms()),
                            World.randomNumber(5),
                            World.randomNumber(5));

        String[] professors = {"riccardo"};

        for (String professor : professors)
            Professor.create(professor,
                            World.pickRandom(World.rooms()),
                            World.randomNumber(5),
                            World.randomNumber(5));

        String[] prlGrads = {"sam"};

        for (String prlGrad : prlGrads)
            PrlGrad.create(prlGrad,
                            World.pickRandom(World.rooms()),
                            World.randomNumber(5), World.randomNumber(5));

        String[] trolls = {"polyphemus",
        "matthias"};

        for (String troll : trolls)
            Troll.create(troll,
                            World.pickRandom(World.rooms()),
                            World.randomNumber(3), World.randomNumber(3));

    }


    public static void createVocabulary () {
        World.registerVerb (Quit.create());
        World.registerVerb (Debug.create());
        World.registerVerb (Look.create());
        World.registerVerb (Wait.create());
        World.registerVerb (Take.create());
        World.registerVerb (Drop.create());
        World.registerVerb (Give.create());
        World.registerVerb (Use.create());
        World.registerVerb (Ask.create());
        World.registerVerb (Inventory.create());
        World.registerVerb (Attack.create());
        World.registerVerb (Equip.create());
        World.registerVerb (Unequip.create());
        World.registerVerb (Open.create());
        World.registerVerb (DirectionVerb.create("north"));
        World.registerVerb (DirectionVerb.create("south"));
        World.registerVerb (DirectionVerb.create("east"));
        World.registerVerb (DirectionVerb.create("west"));
        World.registerVerb (DirectionVerb.create("up"));
        World.registerVerb (DirectionVerb.create("down"));
    }


    /*
     * Callback that prints a tick whenever the clock moves
     * forward
     *
     */

    private static class PrintTickCallback implements Callback {
        public PrintTickCallback () {}

        public void action () {
            World.tellWorld ("The clock ticks " + Clock.time());
        }
    }



    // 
    // The entry point for adventure
    //

    public static void main (String[] argv) {

        if (argv.length > 0 && argv[0].equals("--debug"))
            World.setDebugFlag(true);

        System.out.println("The CSU 370 Adventure Game, version 1.0 (November 2007)");

        // Register this callback first
        // This will be the first thing executed at every turn
        Clock.registerCallback (new PrintTickCallback());

        // Create the world
        createWorld("me");

        // Create the vocabulary to control artifacts
        createVocabulary();

        // Start up the interactive loop
        Interaction.mainLoop ();
    }

}
