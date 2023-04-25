import java.util.ArrayList;

public class Dice
{
    private ArrayList<Die> dice;
    private static final int DEF_CAP = 5;

    public Dice(int num)
    {
        dice = new ArrayList<Die>(num);
    }

    public Dice()
    {
        this(DEF_CAP);
    }

    public void addDie(Die d)
    {
        dice.add(d);
    }

    public int getNumDice()
    {
        return dice.size();
    }

    public Die getDie(int i)
    {
        return dice.get(i);
    }

    public void removeDie(int i)
    {
        dice.remove(i);
    }

    public int count(int val)
    {
        int total = 0;
        for(Die d : dice)
        {
            if(d.getValue() == val)
            {
                total++;
            }
        }
        return total;
    }

    public int sum()
    {
        int sum = 0;
        for(Die d : dice)
        {
            sum += d.getValue();
        }
        return sum;
    }

    public boolean contains(int val)
    {
        for(Die d : dice)
        {
            if(d.getValue() == val)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString()
    {
        String ret = "";
        for(int i = 0; i < dice.size(); i++)
        {
            ret += "\n" + (i+1) + ":\tvalue " + dice.get(i).getValue();
        }
        return ret;
    }
}
