import java.util.Random;

public class Die
{
    private int value;
    private static final int SIDES = 6;
    private static Random r = new Random(System.currentTimeMillis());

    public Die()
    {
        value = r.nextInt(SIDES) + 1; // + 1 since nextInt(SIDES) returns between 0 (inclusive) and SIDES (exclusive).
    }

    public void roll()
    {
        value = r.nextInt(SIDES) + 1;
    }

    public int getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return "" + value;
    }
}
