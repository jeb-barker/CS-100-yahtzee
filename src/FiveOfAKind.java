/**
 * Category to represent Yahtzee (also known as Five of a Kind)
 */
public class FiveOfAKind extends Category
{
    /**
     * Yahtzee scores 50 points.
     * @param d Dice object to evaluate the Category with.
     * @return 50 points if all 5 dice are the same.
     * Special rules apply for the Yahtzee bonus.
     */
    public int evaluate(Dice d)
    {
        for(int val = 1; val <= 6; val++)
        {
            if(d.count(val) == 5)
            {
                if(getUsed() && getScore() != 0)
                {
                    return 100; //yahtzee bonus only applies if previous score wasn't zero.
                }
                else if(getUsed() && getScore() == 0)
                {
                    return 0;
                }
                else
                {
                    return 50;
                }
            }
        }
        return 0;
    }

}
