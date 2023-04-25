import java.util.ArrayList;

public class Scorecard
{
    private ArrayList<Category> scorecard;
    private int yahtzeeBonus;
    private static final int NUM_CATS = 13;

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

    public void choose(CategoryValue cv, Dice d)
    {
        scorecard.get(cv.getValue()).addValue(d);
    }

    public int getEvaluation(CategoryValue cv, Dice d)
    {
        return scorecard.get(cv.getValue()).evaluate(d);
    }

    public boolean checkScored(CategoryValue cv)
    {
        return scorecard.get(cv.getValue()).getUsed();
    }

    public int getCategoryScore(CategoryValue cv)
    {
        return scorecard.get(cv.getValue()).getScore();
    }

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

        return sum;
    }

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

    public int score()
    {
        return scoreTop() + scoreBottom();
    }

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
