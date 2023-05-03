/**
 * Category to represent Four of a Kind.
 */
public class FourOfAKind extends Category
{
    /**
     * Four of a Kind scores the value of all dice.
     * @param d Dice object to evaluate the Category with.
     * @return the value of all dice if four dice are matching.
     */
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        for(int val = 1; val <= 6; val++)
        {
            if(d.count(val) >= 4)
            {
                return d.sum();
            }
        }
        return 0;
    }
}