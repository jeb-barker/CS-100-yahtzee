/**
 * Abstract Class that represents a scoring category.
 */
public abstract class Category
{
    /** The current score of the Category. */
    private int score;
    /** Whether the category has been used yet. */
    private boolean used;

    /**
     * Abstract method to evaluate a scoring Category.
     * @param d Dice object to evaluate the Category with.
     * @return the value of this Category given a set of Die.
     */
    public abstract int evaluate(Dice d);

    /**
     * Score a category.
     * Uses abstract evaluate method and sets used to true.
     * @param d the set of Die to score with.
     */
    public void addValue(Dice d)
    {
        score += evaluate(d);
        used = true;

    }

    /**
     * Get the current value in score.
     * @return the current score of the Category.
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Get whether the Category has been used.
     * @return true if the Category has been used.
     */
    public boolean getUsed()
    {
        return used;
    }
}
