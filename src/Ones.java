public class Ones extends Category
{
    public int evaluate(Dice d)
    {
        if(getUsed())
        {
            return 0;
        }
        return d.count(1); //count number of ones
    }
}
