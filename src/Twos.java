public class Twos extends Category
{
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return 2 * d.count(2); //2 points for every 2.
    }
}