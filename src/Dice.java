import java.util.ArrayList;

/**
 * class to represent a collection of Die.
 */
public class Dice
{
    /** ArrayList of Die. */
    private ArrayList<Die> dice;
    /** constant int default number of Die in a set of Dice. */
    private static final int DEF_CAP = 5;

    /**
     * Specific Constructor for Dice.
     * @param num the number of Die that the ArrayList should be initialized to.
     */
    public Dice(int num)
    {
        dice = new ArrayList<Die>(num);
    }

    /**
     * Default constructor for Dice.
     * Initializes ArrayList to size DEF_CAP.
     */
    public Dice()
    {
        this(DEF_CAP);
    }

    /**
     * Add a Die to the Dice.
     * @param d Die to add to the collection.
     */
    public void addDie(Die d)
    {
        dice.add(d);
    }

    /**
     * Get the number of dice in the collection.
     * @return number of dice in the ArrayList.
     */
    public int getNumDice()
    {
        return dice.size();
    }

    /**
     * Get Die at index i.
     * @param i index to get Die at.
     * @return return Die at index i.
     */
    public Die getDie(int i)
    {
        return dice.get(i);
    }

    /**
     * Remove Die at index i.
     * @param i index to remove Die at.
     */
    public void removeDie(int i)
    {
        dice.remove(i);
    }

    /**
     * Count the number of dice with value val.
     * @param val value to count.
     * @return return number of dice with specified value.
     */
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

    /**
     * Get the sum of the dice.
     * @return return sum of all dice.
     */
    public int sum()
    {
        int sum = 0;
        for(Die d : dice)
        {
            sum += d.getValue();
        }
        return sum;
    }

    /**
     * Check if Dice contains a Die with specified value.
     * @param val value to check for.
     * @return true if Dice contains a Die with specified value.
     */
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

    /**
     * Get String representation of the Dice.
     * @return return String representation of the Dice.
     */
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
