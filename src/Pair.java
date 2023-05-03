public class Pair implements Comparable
{
    private int index;
    private double value;

    public Pair(int index, double value)
    {
        this.index = index;
        this.value = value;
    }

    public int getIndex()
    {
        return index;
    }
    public double getValue()
    {
        return value;
    }

    @Override
    public int compareTo(Object o) {
        Pair other = (Pair)o;
        return -(int)(1000 * (this.value - other.value));
    }
}
