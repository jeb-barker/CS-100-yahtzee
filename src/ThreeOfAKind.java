/**
 * Category representation of Three of a Kind.
 */
public class ThreeOfAKind extends Category
{
    /**
     * Three of a kind scores the value of every dice.
     * @param d Dice object to evaluate the Category with.
     * @return the sum of all dice if three of them are the same.
     */
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        for(int val = 1; val <= 6; val++)
        {
            if(d.count(val) >= 3)
            {
                return d.sum();
            }
        }
        return 0;
    }
}