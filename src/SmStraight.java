/**
 * Category representation of a Small Straight.
 */
public class SmStraight extends Category
{
    /**
     * A small straight is any four dice in sequential order.
     * @param d Dice object to evaluate the Category with.
     * @return 30 points if four dice are in sequential order.
     */
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        //1234 or 2345 or 3456
        if(d.contains(1) && d.contains(2) && d.contains(3) && d.contains(4))
            return 30;
        else if(d.contains(2) && d.contains(3) && d.contains(4) && d.contains(5))
            return 30;
        else if(d.contains(3) && d.contains(4) && d.contains(5) && d.contains(6))
            return 30;
        else
            return 0;
    }
}
