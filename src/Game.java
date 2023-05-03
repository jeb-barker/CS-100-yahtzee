import java.util.*;

/**
 * Class to represent a single Game of Yahtzee.
 */
public class Game
{
    /** Scorecard for player 1 */
    private Scorecard player1;
    /** Scorecard for player 2 */
    private Scorecard player2;
    /** representation of which turn it is. true for player 1, false for player 2. */
    private boolean turn;

    /** Representation of the dice to be rerolled. */
    private DiceRoll dice;
    /** Representation of the dice to be saved. */
    private Dice savedDie;

    /**
     * Default constructor for a Game.
     */
    public Game()
    {
        turn = true; //player1 turn.
        player1 = new Scorecard();
        player2 = new Scorecard();
        dice = new DiceRoll();
        savedDie = new Dice();
        //gameLoop();
    }

    /**
     * Method to play a game with an AI yn.
     * @param yn the YahtzeeNet to play the single game with.
     * @param suppress whether to print output of the game.
     * @return Scorecard at the end of the game.
     */
    public Scorecard gameLoopSinglePlayer(YahtzeeNet yn, boolean suppress)
    {
        for(int i = 0; i < 13; i++)
        {
            //re-initialize variables.
            dice = new DiceRoll();
            savedDie = new Dice();
            AITurn(yn, suppress);
        }
        if(!suppress)
            System.out.println(player2);
        int zeros = 0;
        for(CategoryValue cv : CategoryValue.values())
        {
            if(player2.getCategoryScore(cv) == 0)
            {
                zeros++;
            }
        }
        return player2;
    }

    /**
     * Method to play a game Player vs AI.
     * @param ai the YahtzeeNet to play against.
     */
    public void gameLoopVersusAI(YahtzeeNet ai)
    {
        for(int i = 0; i < 26; i++)
        {
            //re-initialize variables.
            dice = new DiceRoll();
            savedDie = new Dice();
            if(turn)
            {
                turn();
            }
            else
            {
                AITurn(ai, false);
                System.out.println(player2);
                turn = !turn;
            }

        }
        System.out.println(player1);
        System.out.println("***********\n" + player2);
    }

    /**
     * Basic game Player vs Player.
     */
    public void gameLoop()
    {
        for(int i = 0; i < 26; i++)
        {
            //re-initialize variables.
            dice = new DiceRoll();
            savedDie = new Dice();
            turn();
        }
        System.out.println(player1);
        System.out.println("***********\n" + player2);
    }

    /**
     * Basic turn for player vs player.
     */
    private void turn()
    {
        Scanner input = new Scanner(System.in);
        int rerolls = 3;
        //roll the dice once at the start of the turn.
        dice.toss();
        //depends on turn
        System.out.println(turn ? "Player 1:" : "Player 2:");

        while(rerolls > 0)
        {
            if(rerolls == 1)
            {
                for(int i = 0; i < savedDie.getNumDice(); i++)
                {
                    dice.addDie(savedDie.getDie(i));
                }
                System.out.print("Final roll:\n" + dice);
                score(true);
                rerolls--;
            }
            else
            {
                //print scorecard
                System.out.println(turn ? player1 : player2);
                //Prompt user for next action:
                String userInput = getUserAction(input);
                //guaranteed to be valid.
                if (userInput.equalsIgnoreCase("roll"))
                {
                    //roll the dice
                    dice.toss();
                    rerolls--;
                }
                else if (userInput.equalsIgnoreCase("score"))
                {
                    score(false);
                    rerolls = 0;
                }
                else if (userInput.charAt(0) == '[' && userInput.charAt(userInput.length() - 1) == ']')
                {
                    String[] indices = userInput.substring(1, userInput.length() - 1).split(" ");
                    Arrays.sort(indices, Collections.reverseOrder()); //sort in reverse order.
                    for (String s : indices)
                    {
                        int i = Integer.parseInt(s); //all of these have been confirmed valid.
                        savedDie.addDie(dice.getDie(i - 1));
                        dice.removeDie(i - 1);
                    }
                }
            }
        }
        //it is now the other player's turn.
        turn = !turn;
    }

    /**
     * Basic turn for an AI.
     * @param yn the YahtzeeNet to play the turn.
     * @param suppress whether to print output.
     */
    private void AITurn(YahtzeeNet yn, boolean suppress)
    {
        int rerolls = 3;
        dice.toss();
        if(!suppress)
            System.out.println("Start of turn:\n");
        while(rerolls > 0)
        {
            double[] inputs = new double[19];
            //feed forward: get double[18] - five dice, 13 scoring categories
            DiceRoll fullDice = new DiceRoll();
            for (int k = 0; k < 5; k++)
            {
                fullDice.removeDie(0);
            }
            for (int i = 0; i < savedDie.getNumDice(); i++) {
                fullDice.addDie(savedDie.getDie(i));
            }
            for (int i = 0; i < dice.getNumDice(); i++) {
                fullDice.addDie(dice.getDie(i));
            }

            for(int i = 0; i < 6; i++)
            {
                //add dice value-1 / 5.
                //inputs[i] = (fullDice.getDie(i).getValue()-1)/5.0;
                inputs[i] = (fullDice.count(i+1))/5.0;
            }
            for(int i = 6; i < 19; i++)
            {
                inputs[i] = Math.tanh(player2.getEvaluation(CategoryValue.values()[i-6], fullDice));
            }
            yn.feedForward(inputs);
            ArrayList<Integer> holds = yn.getHolds();
            //holds is the number of each type of die to try to hold.
            ArrayList<Pair> scoreCategory = yn.getScoreCategory();
            scoreCategory.sort(Pair::compareTo);

            if(!suppress)
                System.out.println("Dice: \n" + fullDice);
            if(!suppress)
                System.out.println("Hold: \n" + holds);
            if(holds.size() > 0)
            {
                savedDie = new Dice();
                for(int i = 0; i < holds.size(); i++)
                {
                    try
                    {
                        int addedTotal = 0;
                        for(int j = 0; j < fullDice.getNumDice(); j++)
                        {
                            //for each die in dice, check type and attempt to add it.
                            if(addedTotal < holds.get(i) && i+1 == fullDice.getDie(j).getValue())
                            {
                                savedDie.addDie(fullDice.getDie(j));
                                fullDice.removeDie(j);
                                j--;
                                addedTotal++;
                            }
                        }
                    }
                    catch(IndexOutOfBoundsException e)
                    {
                        //System.out.println("AI attempted illegal move, continuing...");
                        i++;
                    }
                }
                dice = fullDice;
            }
            if(!suppress)
                System.out.println("HeldDice: \n" + savedDie);
            //attempt to score.
            if(rerolls == 1)
            {
                boolean continueLoop = true;
                for (Pair p : scoreCategory) {
                    if (!player2.checkScored(CategoryValue.values()[p.getIndex()]) && continueLoop)
                    {
                        rerolls = 0;
                        Dice df = new Dice();
                        for (int i = 0; i < savedDie.getNumDice(); i++)
                        {
                            df.addDie(savedDie.getDie(i));
                        }
                        for (int i = 0; i < dice.getNumDice(); i++)
                        {
                            df.addDie(dice.getDie(i));
                        }
                        if(!suppress)
                            System.out.println("Chose: " + CategoryValue.values()[p.getIndex()].toString());
                        if(!suppress)
                            System.out.println("Value: " + player2.getEvaluation(CategoryValue.values()[p.getIndex()],df));
                        player2.choose(CategoryValue.values()[p.getIndex()], df);
                        continueLoop = false;
                    }
                }
                rerolls--;
            }
            else
            {
                dice.toss();
                rerolls--;
            }
        }
    }

    /**
     * Helper method to get the user's input.
     * @param input Scanner to use for input.
     * @return validated input from the user.
     */
    private String getUserAction(Scanner input)
    {
        //print the dice to reroll.
        System.out.print("Dice to reroll:");
        System.out.println(dice + "\n");
        //print the dice to be saved (if they exist)
        if(savedDie.getNumDice() > 0)
        {
            System.out.print("Dice to save");
            System.out.println(savedDie + "\n");
        }
        System.out.println("Save dice with [ ] filled with index values separated by spaces\n'roll' to reroll, 'score' to score");
        String userInput = input.nextLine();
        //validation
        if(userInput.equalsIgnoreCase("roll"))
        {
            return userInput;
        }
        else if(userInput.equalsIgnoreCase("score"))
        {
            return userInput;
        }
        else if(userInput.charAt(0) == '[' && userInput.charAt(userInput.length()-1) == ']')
        {
            DiceRoll d = dice; //make shallow copies for this method.
            Dice sd = savedDie;
            String[] indices = userInput.substring(1,userInput.length()-1).split(" ");
            for(String s : indices)
            {
                try
                {
                    int i = Integer.parseInt(s);
                    d.getDie(i-1);
                }
                catch(NumberFormatException | InputMismatchException | IndexOutOfBoundsException e)
                {
                    System.out.println("Please enter a valid die number, roll, or score");
                    return getUserAction(input);
                }
            }
            return userInput;
        }
        else
        {
            System.out.println("Please enter a valid die number, roll, or score");
            return getUserAction(input);
        }
    }

    /**
     * Score a player's scorecard based on their input.
     * @param combined whether the dice have been combined already or not.
     */
    private void score(boolean combined)
    {
        Dice fullDice;
        if(!combined)
        {
            fullDice = new Dice();
            for (int i = 0; i < savedDie.getNumDice(); i++) {
                fullDice.addDie(savedDie.getDie(i));
            }
            for (int i = 0; i < dice.getNumDice(); i++) {
                fullDice.addDie(dice.getDie(i));
            }
            //fullDice has all die in it.
        }
        else
        {
            fullDice = dice;
        }
        System.out.println("\nSelect a category that you have not scored in yet:");
        if(turn)
        {
            for (CategoryValue cv : CategoryValue.values())
            {
                if(!player1.checkScored(cv))
                {
                    System.out.printf("\n%d: %s, %d points", cv.getValue() + 1, cv.toString(), player1.getEvaluation(cv, fullDice));
                }
            }

            int choice = getScoreChoice();
            while(choice < 1 || choice > 13)
            {
                choice = getScoreChoice();
            }
            for (CategoryValue cv : CategoryValue.values())
            {
                if(choice == cv.getValue() + 1)
                {
                    System.out.printf("\nYou scored %d points in %s\n\n\n*****************************************************\n",
                            player1.getEvaluation(cv, fullDice), cv.toString());
                    player1.choose(cv, fullDice);
                }
            }
        }
        else
        {
            for (CategoryValue cv : CategoryValue.values())
            {
                if(!player2.checkScored(cv))
                {
                    System.out.printf("\n%d: %s, %d points", cv.getValue() + 1, cv.toString(), player2.getEvaluation(cv, fullDice));
                }
            }
            int choice = -1;
            while(choice < 1 || choice > 13)
            {
                choice = getScoreChoice();
            }
            for (CategoryValue cv : CategoryValue.values())
            {
                if(choice == cv.getValue() + 1)
                {
                    System.out.printf("You scored %d points in %s\n\n\n*****************************************************\n",
                            player2.getEvaluation(cv, fullDice), cv.toString());
                    player2.choose(cv, fullDice);
                }
            }
        }
    }

    /**
     * Gets the user's valid score choice.
     * @return valid integer scoring choice.
     */
    private int getScoreChoice()
    {
        System.out.println("");
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();
        try
        {
            return Integer.parseInt(userInput);
        }
        catch(InputMismatchException | NumberFormatException e)
        {
            System.out.println("Please enter a valid choice:");
            return getScoreChoice();
        }
    }
}
