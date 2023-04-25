public class Fives extends Category
{
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return 5 * d.count(5); //5 points for every 5.
    }
}