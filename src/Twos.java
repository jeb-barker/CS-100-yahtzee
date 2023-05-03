/**
 * Category Representation of Twos
 */
public class Twos extends Category
{
    /**
     * Twos counts the value of all dice with value 2.
     * @param d Dice object to evaluate the Category with.
     * @return 2 times the number of dice with value 2.
     */
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return 2 * d.count(2); //2 points for every 2.
    }
}