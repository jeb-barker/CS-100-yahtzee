public class ThreeOfAKind extends Category
{
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