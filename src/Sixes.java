/**
 * Category representation of Sixes.
 */
public class Sixes extends Category
{
    /**
     * Sixes counts the value of all dice with value 6.
     * @param d Dice object to evaluate the Category with.
     * @return 6 times the number of dice with value 6.
     */
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return 6 * d.count(6); //6 points for every 6.
    }
}