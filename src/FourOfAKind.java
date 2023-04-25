public class FourOfAKind extends Category
{
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