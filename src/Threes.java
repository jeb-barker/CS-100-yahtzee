public class Threes extends Category
{
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return 3 * d.count(3); //3 points for every 3.
    }
}