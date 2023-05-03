import java.util.ArrayList;

/**
 * Representation of a player's Yahtzee scorecard.
 */
public class Scorecard
{
    /** ArrayList of Categories. */
    private ArrayList<Category> scorecard;
    /** Bonus points for additional yahtzees. */
    private int yahtzeeBonus;
    /** Constant int number of categories */
    private static final int NUM_CATS = 13;

    /**
     * Scorecard constructor.
     * Adds one of each type of Category to the scorecard ArrayList.
     */
    public Scorecard()
    {
        scorecard = new ArrayList<Category>(NUM_CATS);
        scorecard.add(new Ones());
        scorecard.add(new Twos());
        scorecard.add(new Threes());
        scorecard.add(new Fours());
        scorecard.add(new Fives());
        scorecard.add(new Sixes());
        scorecard.add(new ThreeOfAKind());
        scorecard.add(new FourOfAKind());
        scorecard.add(new FullHouse());
        scorecard.add(new SmStraight());
        scorecard.add(new LgStraight());
        scorecard.add(new FiveOfAKind());
        scorecard.add(new Chance());
    }

    /**
     * Method to score a Category in the scorecard.
     * @param cv the CategoryValue to score.
     * @param d the dice to calculate the score with.
     */
    public void choose(CategoryValue cv, Dice d)
    {
        int yahtzeeEval = getEvaluation(CategoryValue.YAHTZEE, d);
        //check for yahtzee bonus
        if(yahtzeeEval == 100)
        {
            //Evaluation of YAHTZEE Category will be 100 if yahtzeeBonus applies.
            yahtzeeBonus += yahtzeeEval;
        }
        scorecard.get(cv.getValue()).addValue(d);
    }

    /**
     * Returns the evaluation for a Category.
     * @param cv the CategoryValue to evaluate.
     * @param d the dice to calculate the potential score with
     * @return returns the score this category would receive if chosen.
     */
    public int getEvaluation(CategoryValue cv, Dice d)
    {
        return scorecard.get(cv.getValue()).evaluate(d);
    }

    /**
     * Check if the given Category is scored.
     * @param cv the CategoryValue to check.
     * @return return true if the Category is already scored.
     */
    public boolean checkScored(CategoryValue cv)
    {
        return scorecard.get(cv.getValue()).getUsed();
    }

    /**
     * Get the current score of a Category.
     * @param cv the CategoryValue to check.
     * @return return the current score of Category cv.
     */
    public int getCategoryScore(CategoryValue cv)
    {
        return scorecard.get(cv.getValue()).getScore();
    }

    /**
     * Get the score of the top categories. Ones, Twos, ... , Sixes.
     * Adds a bonus 35 points if the top categories scored above 63 points.
     * @return return the score of the top Categories.
     */
    public int scoreTop()
    {
        int sum = 0;
        //loop can be used here, this is slightly more readable.
        sum += getCategoryScore(CategoryValue.ONES);
        sum += getCategoryScore(CategoryValue.TWOS);
        sum += getCategoryScore(CategoryValue.THREES);
        sum += getCategoryScore(CategoryValue.FOURS);
        sum += getCategoryScore(CategoryValue.FIVES);
        sum += getCategoryScore(CategoryValue.SIXES);
        if(sum > 63)
        {
            sum+= 35;
        }
        return sum;
    }

    /**
     * Get the score of the bottom categories. Three of a Kind, Four of a Kind, ... Chance.
     * Does not include the YahtzeeBonus.
     * @return return the sum of the bottom categories.
     */
    public int scoreBottom()
    {
        int sum = 0;
        //loop can be used here, this is slightly more readable.
        sum += getCategoryScore(CategoryValue.THREE_OF_A_KIND);
        sum += getCategoryScore(CategoryValue.FOUR_OF_A_KIND);
        sum += getCategoryScore(CategoryValue.FULL_HOUSE);
        sum += getCategoryScore(CategoryValue.SM_STRAIGHT);
        sum += getCategoryScore(CategoryValue.LG_STRAIGHT);
        sum += getCategoryScore(CategoryValue.YAHTZEE);
        sum += getCategoryScore(CategoryValue.CHANCE);

        return sum;
    }

    /**
     * Get the full score of the scorecard.
     * Includes the yahtzee bonus.
     * @return the full score.
     */
    public int score()
    {
        return scoreTop() + scoreBottom() + yahtzeeBonus;
    }

    /**
     * Return String representation of the scorecard with current scores for each category.
     * @return String representation of the scorecard.
     */
    @Override
    public String toString()
    {
        return String.format("Current Scorecard:\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d\n%15s: %d",
                CategoryValue.ONES.toString(), getCategoryScore(CategoryValue.ONES), CategoryValue.TWOS.toString(), getCategoryScore(CategoryValue.TWOS),
                CategoryValue.THREES.toString(), getCategoryScore(CategoryValue.THREES),CategoryValue.FOURS.toString(), getCategoryScore(CategoryValue.FOURS),
                CategoryValue.FIVES.toString(), getCategoryScore(CategoryValue.FIVES), CategoryValue.SIXES.toString(), getCategoryScore(CategoryValue.SIXES),
                CategoryValue.THREE_OF_A_KIND.toString(), getCategoryScore(CategoryValue.THREE_OF_A_KIND),
                CategoryValue.FOUR_OF_A_KIND.toString(), getCategoryScore(CategoryValue.FOUR_OF_A_KIND),
                CategoryValue.FULL_HOUSE.toString(), getCategoryScore(CategoryValue.FULL_HOUSE),
                CategoryValue.SM_STRAIGHT.toString(), getCategoryScore(CategoryValue.SM_STRAIGHT),
                CategoryValue.LG_STRAIGHT.toString(), getCategoryScore(CategoryValue.LG_STRAIGHT),
                CategoryValue.YAHTZEE.toString(), getCategoryScore(CategoryValue.YAHTZEE),
                CategoryValue.CHANCE.toString(), getCategoryScore(CategoryValue.CHANCE),
                "Upper Total", scoreTop(), "Lower Total", scoreBottom(), "Total", score());
    }
}
