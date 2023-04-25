public class FiveOfAKind extends Category
{
    public int evaluate(Dice d)
    {
        for(int val = 1; val <= 6; val++)
        {
            if(d.count(val) == 5)
            {
                if(getUsed() && getScore() != 0)
                {
                    return 100; //yahtzee bonus only applies if previous score wasn't zero.
                }
                else if(getUsed() && getScore() == 0)
                {
                    return 0;
                }
                else
                {
                    return 50;
                }
            }
        }
        return 0;
    }

}
