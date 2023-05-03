/**
 * Category to represent Fives.
 */
public class Fives extends Category
{
    /**
     * Fives counts the value of all dice with value 5.
     * @param d Dice object to evaluate the Category with.
     * @return 5 times the number of dice with value 5.
     */
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return 5 * d.count(5); //5 points for every 5.
    }
}