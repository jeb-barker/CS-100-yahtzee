/**
 * Class to hold an index and a value.
 */
public class Pair implements Comparable
{
    /** integer value */
    private int index;
    /** double value paired with the index. */
    private double value;

    /**
     * Specific constructor
     * @param index the integer index.
     * @param value the double value.
     */
    public Pair(int index, double value)
    {
        this.index = index;
        this.value = value;
    }

    /**
     * Get the index.
     * @return int index.
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * Get the value
     * @return double value.
     */
    public double getValue()
    {
        return value;
    }

    /**
     * Compare two Pair Objects. used with Comparable Interface.
     * @param o the object to be compared.
     * @return negative number or positive number based on which value is greater.
     */
    @Override
    public int compareTo(Object o) {
        Pair other = (Pair)o;
        return -(int)(1000 * (this.value - other.value));
    }
}
