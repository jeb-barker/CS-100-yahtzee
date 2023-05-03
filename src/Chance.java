/**
 * Category to represent chance.
 */
public class Chance extends Category
{
    /**
     * Chance scores the values of every die.
     * @param d Dice object to evaluate the Category with.
     * @return the sum of all the dice.
     */
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return d.sum();
    }
}
