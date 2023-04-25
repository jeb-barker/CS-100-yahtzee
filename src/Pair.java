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

    @Override
    public int compareTo(Object o) {
        Pair other = (Pair)o;
        if(value - other.value > 0)
        {
            return -1;
        }
        else if(value - other.value < 0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
