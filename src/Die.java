import java.util.Random;

/**
 * Class to represent a single Die.
 */
public class Die
{
    /** The value on the face of the Die. */
    private int value;
    /** constant int number of sides on the Die. */
    private static final int SIDES = 6;
    /** static Random Object shared between all Die. */
    private static Random r = new Random(System.currentTimeMillis());

    /**
     * Default Constructor for Die.
     * Sets value to random integer between 1 and 6 (inclusive).
     */
    public Die()
    {
        value = r.nextInt(SIDES) + 1; // + 1 since nextInt(SIDES) returns between 0 (inclusive) and SIDES (exclusive).
    }

    /**
     * roll the die.
     */
    public void roll()
    {
        value = r.nextInt(SIDES) + 1;
    }

    /**
     * Get the value on the face of the Die.
     * @return value.
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Get String representation of the Die.
     * @return value cast to a String.
     */
    @Override
    public String toString()
    {
        return "" + value;
    }
}
