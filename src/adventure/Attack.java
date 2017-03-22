package adventure;

public class Attack extends Verb {

    protected Attack () {
        super("attack");
    }

    public static Verb create () {
        return new Attack();
    }

    public Result action (Thing defendant) {
        if (defendant instanceof Person) {
            Person person = (Person) defendant;
            World.me().attack(person);
        } else
            World.me().say ("I feel weird attacking something of " + 
                            defendant.name());
        return Result.STOP;
    }
}
