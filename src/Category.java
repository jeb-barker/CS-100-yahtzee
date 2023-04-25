public abstract class Category
{
    private int score;
    private boolean used;

    public abstract int evaluate(Dice d);

    public void addValue(Dice d)
    {
        score += evaluate(d);
        used = true;

    }

    public int getScore()
    {
        return score;
    }

    public boolean getUsed()
    {
        return used;
    }
}
