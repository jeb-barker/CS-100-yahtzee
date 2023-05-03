import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Driver for a game of Yahtzee.
 * Gives options for playing against AI and other players.
 */
public class GameDriver
{
    /** Path to weights for AI. */
    private static final String weightPath = "/Users/jeb/IdeaProjects/CS-110-Yahtzee/src/weights.txt";
    public static void main(String[] args) throws FileNotFoundException
    {
        startUserGameChoice();
//        YahtzeeNetTrainer ynt = new YahtzeeNetTrainer(weightPath);
//        YahtzeeNet ai = ynt.train();
//        ai.printWeights(weightPath);

//        YahtzeeNet ai = yn.read();
//        g.gameLoopVersusAI(ai);
    }

    /**
     * Start a game based off of the user's game choice.
     */
    private static void startUserGameChoice()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("*******************************************************\n" +
                "WELCOME TO YAHTZEE\n" +
                "*******************************************************");
        System.out.println("\nType one of the following options for different games:\npvp - standard player versus player\n" +
                "pve - player versus computer\ntrain - train the AI for 100 generations (WARNING: may take a minute or two)");
        String userIn = input.nextLine();

        Game g = new Game();
        YahtzeeNetTrainer ynt = null;
        try
        {
            ynt = new YahtzeeNetTrainer(weightPath);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Make sure you have the weight file in your directory.");
            System.exit(0);
        }
        if(userIn.equals("pvp"))
        {
            g.gameLoop();
        }
        else if(userIn.equals("pve"))
        {
            YahtzeeNet ai = ynt.read();
            g.gameLoopVersusAI(ai);
        }
        else if(userIn.equals("train"))
        {
            YahtzeeNet ai = ynt.train();
            try
            {
                ai.printWeights(weightPath);
            }
            catch (FileNotFoundException e)
            {
                System.out.println("File is not accessible.");
                System.exit(0);
            }
        }
        else
        {
            System.out.println("You didn't enter one of the valid input options. Exiting...");
            System.exit(0);
        }
    }
}
