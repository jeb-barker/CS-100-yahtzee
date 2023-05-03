/**
 * Category representation of Ones.
 */
public class Ones extends Category
{
    /**
     * Ones counts the value of all dice with value 1.
     * @param d Dice object to evaluate the Category with.
     * @return 1 times the number of dice with value 1.
     */
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return d.count(1); //count number of ones
    }
}
