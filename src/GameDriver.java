public class GameDriver
{
    public static void main(String[] args)
    {

        Game g = new Game();
        YahtzeeNetTrainer yn = new YahtzeeNetTrainer();
        YahtzeeNet ai = yn.train();
        g.gameLoopSinglePlayer(ai, false);
//        g.gameLoopSinglePlayer(ai, false);
//        g.gameLoopSinglePlayer(ai, false);
    }
}
