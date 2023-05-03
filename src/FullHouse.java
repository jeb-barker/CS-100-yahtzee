/**
 * Category to represent a Full House.
 */
public class FullHouse extends Category
{
    /**
     * Full House scores 25 points.
     * It's ok for the two dice to be the same value, since that can either be
     * scored as Full House, Three of a Kind, Four of a Kind, or Yahtzee.
     * @param d Dice object to evaluate the Category with.
     * @return 25 points if three of one value and 2 of another are present.
     */
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        for(int val = 1; val <= 6; val++)
        {
            if(d.count(val) == 3)
            {
                //three of one die found, look for 2 of another.
                for(int val2 = 1; val2 <= 6; val2++)
                {
                    if(d.count(val2) == 2)
                    {
                        return 25;
                    }
                }
            }
        }
        return 0;
    }
}
