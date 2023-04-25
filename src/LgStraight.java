public class LgStraight extends Category
{
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        //12345 or 23456
        if(d.contains(1) && d.contains(2) && d.contains(3) && d.contains(4) && d.contains(5))
            return 40;
        else if(d.contains(2) && d.contains(3) && d.contains(4) && d.contains(5) && d.contains(6))
            return 40;
        else
            return 0;
    }
}
