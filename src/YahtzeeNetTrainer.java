import java.util.ArrayList;

public class YahtzeeNetTrainer
{
    private ArrayList<YahtzeeNet> nets;
    private ArrayList<Integer> scores;

    private static final int BATCH_SIZE = 3;

    public YahtzeeNetTrainer()
    {
        nets = new ArrayList<YahtzeeNet>(10);
        scores = new ArrayList<>(10);
        for(int i = 0; i < 10; i++)
        {
            nets.add(new YahtzeeNet());
            scores.add(0);
        }
    }

    public void playOneGame()
    {
        for(int i = 0; i < nets.size(); i++)
        {
            YahtzeeNet yn = nets.get(i);
            //start single player game.
            Game g = new Game(yn);
            int score = g.gameLoopSinglePlayer(yn, true);
            //System.out.println(score);
            scores.set(i, score + scores.get(i));
        }
    }

    public YahtzeeNet train()
    {
        YahtzeeNet best = new YahtzeeNet();
        for(int generation = 1; generation <= 5000; generation++)
        {
            for(int i = 0; i < BATCH_SIZE; i++)
            {
                playOneGame();
            }
            //find the top three scores and discard the rest of the networks.
            //use the top three to create 10 offspring that are slightly different from their parents.
            int maxIndex=0;
            int maxValue=0;
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

                }
            }
            best = nets.get(maxIndex);
            YahtzeeNet parent1 = nets.get(maxIndex);
            YahtzeeNet parent2 = nets.get(secondIndex);
            YahtzeeNet parent3 = nets.get(thirdIndex);

            nets = parent1.cross(parent2, 5);
            ArrayList<YahtzeeNet> p2p3 = parent1.cross(parent3, 5);
            nets.addAll(p2p3);
            //nets has children between top 3 YahtzeeNets.
            int sum = 0;
            for(Integer i : scores)
            {
                sum += i;
            }
            if(generation % 50 == 0)
            {
                System.out.println("generation " + generation + ": average " + (sum / (10*BATCH_SIZE)) + "\nMax: " + maxValue/BATCH_SIZE);
                Game g = new Game(nets.get(maxIndex));
                g.gameLoopSinglePlayer(nets.get(maxIndex), true);

            }
            for(int i = 0; i < scores.size(); i++)
            {
                scores.set(i, 0);
            }

        }
        return best;
    }
}
