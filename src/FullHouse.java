public class FullHouse extends Category
{
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        for(int val = 1; val <= 6; val++)
        {
            if(d.count(val) == 3)
            {
                //three of one die found, look for 2 of another.
                for(int val2 = 1; val2 <= 6; val2++)
                {
                    if(d.count(val2) == 2)
                    {
                        return 25;
                    }
                }
            }
        }
        return 0;
    }
}
