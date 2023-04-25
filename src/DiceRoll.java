public class DiceRoll extends Dice
{
    private static final int NUM_DIE = 5;

    public DiceRoll()
    {
        super(NUM_DIE);
        for(int i = 0; i < NUM_DIE; i++)
        {
            super.addDie(new Die());
        }
    }

    public void toss()
    {
        for(int i = 0; i < super.getNumDice(); i++)
        {
            super.getDie(i).roll();
        }
    }
}
