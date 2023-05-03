/**
 * Category to represent Fours.
 */
public class Fours extends Category
{
    /**
     * Fours counts the value of all dice with value 4.
     * @param d Dice object to evaluate the Category with.
     * @return 4 times the number of dice with value 4.
     */
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return 4 * d.count(4); //4 points for every 4.
    }
}