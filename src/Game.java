import java.util.*;

public class Game
{
    private Scorecard player1;
    private Scorecard player2;
    private boolean turn;
    private boolean singlePlayer;

    private DiceRoll dice;
    private Dice savedDie;

    public Game()
    {
        turn = true; //player1 turn.
        player1 = new Scorecard();
        player2 = new Scorecard();
        dice = new DiceRoll();
        savedDie = new Dice();
        singlePlayer = false;
        //gameLoop();
    }

    public Game(YahtzeeNet yn)
    {
        turn = true; //player1 turn.
        player1 = new Scorecard();
        player2 = new Scorecard();
        dice = new DiceRoll();
        savedDie = new Dice();
        this.singlePlayer = true;
//        gameLoopSinglePlayer(yn);
    }

    public int gameLoopSinglePlayer(YahtzeeNet yn, boolean suppress)
    {
        for(int i = 0; i < 13; i++)
        {
            //re-initialize variables.
            dice = new DiceRoll();
            savedDie = new Dice();
            AITurn(yn, suppress);
        }
        if(!suppress)
            System.out.println(player1);
        return player1.score();
    }

    private void gameLoop()
    {
        for(int i = 0; i < 26; i++)
        {
            //re-initialize variables.
            dice = new DiceRoll();
            savedDie = new Dice();
            turn();
        }
    }

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

    private void AITurn(YahtzeeNet yn, boolean suppress)
    {
        int rerolls = 3;
        dice.toss();
        if(!suppress)
            System.out.println("Start of turn:\n");
        while(rerolls > 0)
        {
            double[] inputs = new double[18];
            //feed forward: get double[18] - five dice, 13 scoring categories
            Dice fullDice = new Dice();
            for (int i = 0; i < savedDie.getNumDice(); i++) {
                fullDice.addDie(savedDie.getDie(i));
            }
            for (int i = 0; i < dice.getNumDice(); i++) {
                fullDice.addDie(dice.getDie(i));
            }

            for(int i = 0; i < fullDice.getNumDice(); i++)
            {
                //add dice value-1 / 5.
                //inputs[i] = (fullDice.getDie(i).getValue()-1)/5.0;
                inputs[i] = (fullDice.count(i))/5.0;
            }
            for(int i = 5; i < 18; i++)
            {
                inputs[i] = Math.tanh(player1.getEvaluation(CategoryValue.values()[i-5], fullDice));
            }
            yn.feedForward(inputs);
            ArrayList<Integer> holds = yn.getHolds();
            holds.sort(Integer::compareTo);
            Collections.reverse(holds);
            ArrayList<Pair> scoreCategory = yn.getScoreCategory();
            double rerollVal = yn.getReroll();
            if(!suppress)
                System.out.println("Dice: \n" + dice);
            if(!suppress)
                System.out.println("Hold: \n" + holds);
            if(holds.size() > 0)
            {
                for(int i = 0; i < holds.size(); i++)
                {
                    try
                    {
                        savedDie.addDie(dice.getDie(holds.get(i)));
                        dice.removeDie(holds.get(i));
                    }
                    catch(IndexOutOfBoundsException e)
                    {
                        //System.out.println("AI attempted illegal move, continuing...");
                        i++;
                    }
                }
            }
            if(!suppress)
                System.out.println("HeldDice: \n" + savedDie);
            //attempt to score.
            if(rerolls == 1)
            {
                boolean continueLoop = true;
                for (Pair p : scoreCategory) {
                    if (!player1.checkScored(CategoryValue.values()[p.getIndex()]) && continueLoop)
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
                            System.out.println("Value: " + player1.getEvaluation(CategoryValue.values()[p.getIndex()],df));
                        player1.choose(CategoryValue.values()[p.getIndex()], df);
                        continueLoop = false;
                    }
                }
                rerolls--;
            }
            else if(rerollVal > -1)
            {

                dice.toss();
                rerolls--;
            }
        }
    }

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
            int choice = getScoreChoice();
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
