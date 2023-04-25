import java.util.ArrayList;

public class YahtzeeNet
{
    private double[] inputWeights;// = new double[18*9];
    private double[] layer1;// = new double[9];
    private double[] layer1Bias;// = new double[9];
    private double[] layer1Weights;// = new double[9*4];
    private double[] layer2;// = new double[4];
    private double[] layer2Bias;// = new double[4];
    private double[] layer2Weights;// = new double[4*9];
    private double[] layer3;// = new double[9];
    private double[] layer3Bias;// = new double[9];
    private double[] layer3Weights;// = new double[9*19];
    private double[] outputs;// = new double[19];

    private static final double MUTATION_CHANCE = 0.85;
    private static final int INPUT_SIZE = 18;
    private static final int LAYER1_SIZE = 72;
    private static final int LAYER2_SIZE = 36;
    private static final int LAYER3_SIZE = 72;
    private static final int OUTPUT_SIZE = 19;

    public YahtzeeNet()
    {
        inputWeights = new double[INPUT_SIZE*LAYER1_SIZE];
        layer1 = new double[LAYER1_SIZE];
        layer1Bias = new double[LAYER1_SIZE];
        layer1Weights = new double[LAYER1_SIZE*LAYER2_SIZE];
        layer2 = new double[LAYER2_SIZE];
        layer2Bias = new double[LAYER2_SIZE];
        layer2Weights = new double[LAYER2_SIZE*LAYER3_SIZE];
        layer3 = new double[LAYER3_SIZE];
        layer3Bias = new double[LAYER3_SIZE];
        layer3Weights = new double[LAYER3_SIZE*OUTPUT_SIZE];
        outputs = new double[OUTPUT_SIZE];

        for(int i = 0; i < inputWeights.length; i++)
        {
            inputWeights[i] = -1 + 2*Math.random();
        }
        for(int i = 0; i < layer1Weights.length; i++)
        {
            layer1Weights[i] = -1 + 2*Math.random();
        }
        for(int i = 0; i < layer2Weights.length; i++)
        {
            layer2Weights[i] = -1 + 2*Math.random();
        }
        for(int i = 0; i < layer3Weights.length; i++)
        {
            layer3Weights[i] = -1 + 2*Math.random();
        }

        for(int i = 0; i < layer1.length; i++)
        {
            layer1Bias[i] = -1 + 2*Math.random();
        }
        for(int i = 0; i < layer2.length; i++)
        {
            layer2Bias[i] = -1 + 2*Math.random();
        }
        for(int i = 0; i < layer3.length; i++)
        {
            layer3Bias[i] = -1 + 2*Math.random();
        }
    }

    public void feedForward(double[] inputs)
    {
        for(int i = 0; i < layer1.length; i++)
        {
            double sum = 0;
            for(int j = 0; j < inputs.length; j++)
            {
                sum += inputs[j] * inputWeights[i * inputs.length + j];
            }
            layer1[i] = f(sum) + layer1Bias[i];
        }
        for(int i = 0; i < layer2.length; i++)
        {
            double sum = 0;
            for(int j = 0; j < layer1.length; j++)
            {
                sum += layer1[j] * layer1Weights[i * layer1.length + j];
            }
            layer2[i] = f(sum) + layer2Bias[i];
        }
        for(int i = 0; i < layer3.length; i++)
        {
            double sum = 0;
            for(int j = 0; j < layer2.length; j++)
            {
                sum += layer2[j] * layer2Weights[i * layer2.length + j];
            }
            layer3[i] = f(sum) + layer3Bias[i];
        }
        for(int i = 0; i < outputs.length; i++)
        {
            double sum = 0;
            for(int j = 0; j < layer3.length; j++)
            {
                sum += layer3[j] * layer3Weights[i * layer3.length + j];
            }
            outputs[i] = f(sum);
        }
    }

    public static double f(double sum)
    {
        return (1.0 / (1 + Math.pow(Math.E, -sum)));
    }

    public void printOutputs()
    {
        for(Double d : outputs)
        {
            System.out.println(d);
        }
    }

    public ArrayList<Integer> getHolds()
    {
        ArrayList<Integer> holds = new ArrayList<>(5);
        for(int i = 0; i < 5; i++)
        {
            if(outputs[i] > .5)
            {
                holds.add(i);
            }
        }
        return holds;
    }

    public ArrayList<Pair> getScoreCategory()
    {
        ArrayList<Pair> holds = new ArrayList<>(13);
        for(int i = 6; i < outputs.length; i++)
        {
            if(outputs[i] > .5)
            {
                holds.add(new Pair(i-6, outputs[i]));
            }
        }
        holds.sort(Pair::compareTo);
        return holds;
    }

    public double getReroll()
    {
        return outputs[5];
    }

    public ArrayList<YahtzeeNet> cross(YahtzeeNet parent2, int num)
    {
        ArrayList<YahtzeeNet> nets = new ArrayList<>(10);
        for(int i = 0; i < num-1; i++)
        {
            YahtzeeNet newChild = this;
            for(int j = 0; j < (int)(inputWeights.length*Math.random()); j++)
            {
                newChild.inputWeights[j] = parent2.inputWeights[j];
            }
            for(int j = 0; j < (int)(layer1Weights.length*Math.random()); j++)
            {
                newChild.layer1Weights[j] = parent2.layer1Weights[j];
            }
            for(int j = 0; j < (int)(layer2Weights.length*Math.random()); j++)
            {
                newChild.layer2Weights[j] = parent2.layer2Weights[j];
            }
            for(int j = 0; j < (int)(layer3Weights.length*Math.random()); j++)
            {
                newChild.layer3Weights[j] = parent2.layer3Weights[j];
            }

            for(int j = 0; j < (int)(layer1Bias.length*Math.random()); j++)
            {
                newChild.layer1Bias[j] = parent2.layer1Bias[j];
            }
            for(int j = 0; j < (int)(layer2Bias.length*Math.random()); j++)
            {
                newChild.layer2Bias[j] = parent2.layer2Bias[j];
            }
            for(int j = 0; j < (int)(layer3Bias.length*Math.random()); j++)
            {
                newChild.layer3Bias[j] = parent2.layer3Bias[j];
            }
            //3% chance of mutation in each weight and bias.
            if(Math.random() > MUTATION_CHANCE)
            {
                //random layer1Weight
                newChild.inputWeights[(int)(9*18*Math.random())] = -1 + 2*Math.random();
            }
            if(Math.random() > MUTATION_CHANCE)
            {
                //random layer1Weight
                newChild.layer1Weights[(int)(9*4*Math.random())] = -1 + 2*Math.random();
            }
            if(Math.random() > MUTATION_CHANCE)
            {
                //random layer1Bias
                newChild.layer1Bias[(int)(9*Math.random())] = -1 + 2*Math.random();
            }
            if(Math.random() > MUTATION_CHANCE)
            {
                //random layer2Weight
                newChild.layer2Weights[(int)(9*4*Math.random())] = -1 + 2*Math.random();
            }
            if(Math.random() > MUTATION_CHANCE)
            {
                //random layer2Bias
                newChild.layer2Bias[(int)(4*Math.random())] = -1 + 2*Math.random();
            }
            if(Math.random() > MUTATION_CHANCE)
            {
                //random layer3Weight
                newChild.layer3Weights[(int)(9*19*Math.random())] = -1 + 2*Math.random();
            }
            if(Math.random() > MUTATION_CHANCE)
            {
                //random layer3Bias
                newChild.layer3Bias[(int)(9*Math.random())] = -1 + 2*Math.random();
            }
            nets.add(newChild);
        }
        //add one random net
        nets.add(new YahtzeeNet());
        return nets;
    }
}
