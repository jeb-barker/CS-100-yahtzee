public class Sixes extends Category
{
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return 6 * d.count(6); //6 points for every 6.
    }
}