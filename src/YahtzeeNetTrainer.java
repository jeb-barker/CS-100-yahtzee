import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to train a set of YahtzeeNets to play Yahtzee.
 */
public class YahtzeeNetTrainer
{
    /** Collection of YahtzeeNets */
    private ArrayList<YahtzeeNet> nets;
    /** The scores that each YahtzeeNet achieved */
    private ArrayList<Integer> scores;

    /** How many games each YahtzeeNet should play per generation. */
    private static final int BATCH_SIZE = 5;
    /** How many YahtzeeNets should be created each generation. */
    private static final int POP_SIZE = 50;
    /** number of generations to train for. */
    private static final int GENERATION_NUM = 100;

    /**
     * Default constructor.
     */
    public YahtzeeNetTrainer()
    {
        nets = new ArrayList<YahtzeeNet>(POP_SIZE);
        scores = new ArrayList<>(POP_SIZE);
        for(int i = 0; i < POP_SIZE; i++)
        {
            nets.add(new YahtzeeNet());
            scores.add(0);
        }
    }

    /**
     * Specific Constructor to start from existing weights.
     * @param a filename for weight file.
     * @throws FileNotFoundException
     */
    public YahtzeeNetTrainer(String a) throws FileNotFoundException {
        nets = new ArrayList<YahtzeeNet>(POP_SIZE);
        scores = new ArrayList<>(POP_SIZE);
        Scanner infile = new Scanner(new File(a));

        double[][] arrays = new double[13][];
        int index = 0;
        while(infile.hasNext())
        {
            String arrayIn = infile.nextLine();
            int startPos = arrayIn.indexOf('[');
            arrayIn = arrayIn.substring(startPos+1, arrayIn.length()-1);
            String[] array = arrayIn.split(",");
            ArrayList<Double> tempList = new ArrayList<Double>();
            for(String s : array)
            {
                if(s != "")
                    tempList.add(Double.parseDouble(s));
            }
            Object[] tempArray = tempList.toArray();
            double[] newArray = new double[tempArray.length];
            for(int i = 0; i < tempArray.length; i++)
            {
                newArray[i] = (double)tempArray[i];
            }
            arrays[index] = newArray;
            index++;
        }
        infile.close();
        for(int i = 0; i < POP_SIZE; i++)
        {
            //Add a new YahtzeeNet with the arrays read in from the weight file.
            nets.add(new YahtzeeNet(arrays[0],arrays[1],arrays[2],arrays[3],arrays[4],arrays[5],arrays[6],arrays[7],arrays[8],arrays[9],arrays[10]));
            scores.add(0);
        }
    }

    /**
     * Have each YahtzeeNet play one game.
     * Adds the scores they received to scores.
     */
    public void playOneGame()
    {
        for(int i = 0; i < nets.size(); i++)
        {
            YahtzeeNet yn = nets.get(i);
            //start single player game.
            Game g = new Game();
            Scorecard score = g.gameLoopSinglePlayer(yn, true);
            //System.out.println(score);
            int zeros = 0;
            for(CategoryValue cv : CategoryValue.values())
            {
                if(score.getCategoryScore(cv) == 0)
                {
                    zeros++;
                }
            }
            scores.set(i, score.score() + scores.get(i));
        }
    }

    /**
     * Train the YahtzeeNets using the Genetic Pruning algorithm (sort of)
     * This algorithm relies on the assumption that crossing the weights of two "good" YahtzeeNets will result in a better one.
     * @return the best YahtzeeNet after GENERATION_NUM generations of training.
     * See comments within this method for further explanation of the algorithm.
     */
    public YahtzeeNet train()
    {
        YahtzeeNet best = new YahtzeeNet();
        int bestAverageScore = 0;
        //int bestGeneration = 0;
        for(int generation = 1; generation <= GENERATION_NUM; generation++)
        {
            for(int i = 0; i < BATCH_SIZE; i++)
            {
                playOneGame();
            }
            //find the top three scores and discard the rest of the networks.
            //use the top three to create 10 offspring that are slightly different from their parents.
            int maxIndex=0;
            int maxValue=-150;
            int secondIndex = 0;
            int thirdIndex = 0;
            for(int i = 1; i < nets.size(); i++)
            {
                if(scores.get(i) > maxValue)
                {
                    thirdIndex = secondIndex;
                    secondIndex = maxIndex;
                    maxValue = scores.get(i);
                    maxIndex = i;
                    //keep track of the best three nets per generation.
                }
            }
            if(maxValue > bestAverageScore)
            {
                bestAverageScore = maxValue;
                best = nets.get(maxIndex);
                //keep track of the overall best net for all generations. This is susceptible to a single "lucky" generation.
            }
            YahtzeeNet parent1 = nets.get(maxIndex);
            YahtzeeNet parent2 = nets.get(secondIndex);
            YahtzeeNet parent3 = nets.get(thirdIndex);

            nets = parent1.cross(parent2, POP_SIZE/2);
            ArrayList<YahtzeeNet> p2p3 = parent1.cross(parent3, POP_SIZE/2);
            nets.addAll(p2p3);
            //replace nets with children between parent 1/2 and parent 1/3
            //nets has children between top 3 YahtzeeNets.
            //The goal of the algorithm is to discard the worst nets every generation and hopefully get even better ones
            //by crossing the best of every generation.
            //Over hundreds of generations one would hope that this AI could play Yahtzee as well as or better than a human.
            int sum = 0;
            for(Integer i : scores)
            {
                sum += i;
            }
            //the number after the modulus can be changed to only print out every 5,10,20,etc. generations.
            //Especially useful if there are thousands of generations.
            if(generation % 1 == 0)
            {
                //print out the stats for the current generation.
                System.out.println(generation + ", " + (sum / (POP_SIZE*BATCH_SIZE)) + ", " + maxValue/BATCH_SIZE);
                Game g = new Game();
                if(generation % 100 == 0)
                {
                    // if suppress is set to false here, an example game will be played every 100 generations.
                    //Useful if you want to monitor how the AI is training.
                    g.gameLoopSinglePlayer(nets.get(maxIndex), true);
                }

            }
            for(int i = 0; i < scores.size(); i++)
            {
                scores.set(i, 0);
            }

        }
        return best;
    }

    /**
     * Return the YahtzeeNet from the given input file.
     * @return YahtzeeNet from input file given in constructor.
     */
    public YahtzeeNet read()
    {
        return nets.get(0);
    }
}
