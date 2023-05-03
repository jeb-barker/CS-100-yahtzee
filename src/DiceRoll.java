/**
 * Class to represent a collection of rollable dice.
 */
public class DiceRoll extends Dice
{
    /** constant number of Die default in the DiceRoll. */
    private static final int NUM_DIE = 5;

    /**
     * Default constructor for DiceRoll.
     * Adds NUM_DIE dice to the collection.
     */
    public DiceRoll()
    {
        super(NUM_DIE);
        for(int i = 0; i < NUM_DIE; i++)
        {
            super.addDie(new Die());
        }
    }

    /**
     * Roll every Die in the collection.
     */
    public void toss()
    {
        for(int i = 0; i < super.getNumDice(); i++)
        {
            super.getDie(i).roll();
        }
    }
}
