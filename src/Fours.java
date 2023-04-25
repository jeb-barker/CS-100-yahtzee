public class Fours extends Category
{
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return 4 * d.count(4); //4 points for every 4.
    }
}