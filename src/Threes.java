/**
 * Category representation of Threes.
 */
public class Threes extends Category
{
    /**
     * Threes counts the value of all dice with value 3.
     * @param d Dice object to evaluate the Category with.
     * @return 3 times the number of dice with value 3.
     */
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return 3 * d.count(3); //3 points for every 3.
    }
}