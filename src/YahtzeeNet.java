import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * A Neural Network Structured to Play Yahtzee.
 */
public class YahtzeeNet
{
    /**
     * Each YahtzeeNet has a series of double[] arrays that hold weights, biases, and values for the neural network.
     */
    private double[] inputWeights;
    private double[] layer1;
    private double[] layer1Bias;
    private double[] layer1Weights;
    private double[] layer2;
    private double[] layer2Bias;
    private double[] layer2Weights;
    private double[] layer3;
    private double[] layer3Bias;
    private double[] layer3Weights;
    private double[] layer4;
    private double[] layer4Bias;
    private double[] layer4Weights;
    private double[] layer5;
    private double[] layer5Bias;
    private double[] layer5Weights;
    private double[] outputs;

    /** Constant chance of mutation. */
    private static final double MUTATION_CHANCE = 0.0;
    /** Constant size of inputs. */
    private static final int INPUT_SIZE = 19;
    /** Constant layer 1 size */
    private static final int LAYER1_SIZE = 57;
    /** Constant layer 2 size */
    private static final int LAYER2_SIZE = 152;
    /** Constant layer 3 size */
    private static final int LAYER3_SIZE = 36;
    /** Constant layer 4 size */
    private static final int LAYER4_SIZE = 152;
    /** Constant layer 5 size */
    private static final int LAYER5_SIZE = 57;
    /** Constant output size */
    private static final int OUTPUT_SIZE = 19;

    /**
     * Default constructor for a YahtzeeNet
     */
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
        layer3Weights = new double[LAYER3_SIZE*LAYER4_SIZE];
        layer4 = new double[LAYER4_SIZE];
        layer4Bias = new double[LAYER4_SIZE];
        layer4Weights = new double[LAYER4_SIZE*LAYER5_SIZE];
        layer5 = new double[LAYER5_SIZE];
        layer5Bias = new double[LAYER5_SIZE];
        layer5Weights = new double[LAYER5_SIZE*OUTPUT_SIZE];
        outputs = new double[OUTPUT_SIZE];

        //initialize each array to random values.
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
        for(int i = 0; i < layer4Weights.length; i++)
        {
            layer4Weights[i] = -1 + 2*Math.random();
        }
        for(int i = 0; i < layer5Weights.length; i++)
        {
            layer5Weights[i] = -1 + 2*Math.random();
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
        for(int i = 0; i < layer4.length; i++)
        {
            layer4Bias[i] = -1 + 2*Math.random();
        }
        for(int i = 0; i < layer5.length; i++)
        {
            layer5Bias[i] = -1 + 2*Math.random();
        }
    }

    /**
     * Deep Copy constructor.
     * @param o YahtzeeNet to copy.
     */
    public YahtzeeNet(YahtzeeNet o)
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
        layer3Weights = new double[LAYER3_SIZE*LAYER4_SIZE];
        layer4 = new double[LAYER4_SIZE];
        layer4Bias = new double[LAYER4_SIZE];
        layer4Weights = new double[LAYER4_SIZE*LAYER5_SIZE];
        layer5 = new double[LAYER5_SIZE];
        layer5Bias = new double[LAYER5_SIZE];
        layer5Weights = new double[LAYER5_SIZE*OUTPUT_SIZE];
        outputs = new double[OUTPUT_SIZE];

        for(int i = 0; i < inputWeights.length; i++)
        {
            inputWeights[i] = o.inputWeights[i];
        }
        for(int i = 0; i < layer1Weights.length; i++)
        {
            layer1Weights[i] = o.layer1Weights[i];
        }
        for(int i = 0; i < layer2Weights.length; i++)
        {
            layer2Weights[i] = o.layer2Weights[i];
        }
        for(int i = 0; i < layer3Weights.length; i++)
        {
            layer3Weights[i] = o.layer3Weights[i];
        }
        for(int i = 0; i < layer4Weights.length; i++)
        {
            layer4Weights[i] = o.layer4Weights[i];
        }
        for(int i = 0; i < layer5Weights.length; i++)
        {
            layer5Weights[i] = o.layer5Weights[i];
        }

        for(int i = 0; i < layer1.length; i++)
        {
            layer1Bias[i] = o.layer1Bias[i];
        }
        for(int i = 0; i < layer2.length; i++)
        {
            layer2Bias[i] = o.layer2Bias[i];
        }
        for(int i = 0; i < layer3.length; i++)
        {
            layer3Bias[i] = o.layer3Bias[i];
        }
        for(int i = 0; i < layer4.length; i++)
        {
            layer4Bias[i] = o.layer4Bias[i];
        }
        for(int i = 0; i < layer5.length; i++)
        {
            layer5Bias[i] = o.layer5Bias[i];
        }
    }

    /**
     * Specific Constructor.
     * @param in input array
     * @param bias1 bias 1 array
     * @param weight1 weight 1 array
     * @param bias2 bias 2 array
     * @param weight2 weight 2 array
     * @param bias3 bias 3 array
     * @param weight3 weight 3 array
     * @param bias4 bias 4 array
     * @param weight4 weight 4 array
     * @param bias5 bias 5 array
     * @param weight5 weight 5 array
     */
    public YahtzeeNet(double[] in, double[] bias1, double[] weight1, double[] bias2, double[] weight2, double[] bias3, double[] weight3, double[] bias4, double[] weight4, double[] bias5, double[] weight5)
    {
        inputWeights = in;
        layer1 = new double[LAYER1_SIZE];
        layer1Bias = bias1;
        layer1Weights = weight1;
        layer2 = new double[LAYER2_SIZE];
        layer2Bias = bias2;
        layer2Weights = weight2;
        layer3 = new double[LAYER3_SIZE];
        layer3Bias = bias3;
        layer3Weights = weight3;
        layer4 = new double[LAYER4_SIZE];
        layer4Bias = bias4;
        layer4Weights = weight4;
        layer5 = new double[LAYER5_SIZE];
        layer5Bias = bias5;
        layer5Weights = weight5;
        outputs = new double[OUTPUT_SIZE];
    }

    /**
     * Feed given inputs through the network.
     * For each node, sum all previous nodes*weight + bias.
     * Then put that sum into an activation function to normalize between -1 and 1.
     * @param inputs the inputs to feed forward.
     */
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
        for(int i = 0; i < layer4.length; i++)
        {
            double sum = 0;
            for(int j = 0; j < layer3.length; j++)
            {
                sum += layer3[j] * layer3Weights[i * layer3.length + j];
            }
            layer4[i] = f(sum) + layer4Bias[i];
        }
        for(int i = 0; i < layer5.length; i++)
        {
            double sum = 0;
            for(int j = 0; j < layer4.length; j++)
            {
                sum += layer4[j] * layer4Weights[i * layer4.length + j];
            }
            layer5[i] = f(sum);
        }
        for(int i = 0; i < outputs.length; i++)
        {
            double sum = 0;
            for(int j = 0; j < layer5.length; j++)
            {
                sum += layer5[j] * layer5Weights[i * layer5.length + j];
            }
            outputs[i] = f(sum);
        }
    }

    /**
     * Activation function.
     * @param sum The sum to be normalized between -1 and 1.
     * @return the output of the activation function.
     */
    public static double f(double sum)
    {
        return (1.0 / (1 + Math.pow(Math.E, -sum)));
    }

    /**
     * Print the outputs to the console.
     */
    public void printOutputs()
    {
        for(Double d : outputs)
        {
            System.out.println(d);
        }
    }

    /**
     * return an ArrayList of integers representing how many 1s, 2s, 3s, etc. the AI wants to hold onto.
     * @return ArrayList of Integers from the first 6 spots in the output array.
     */
    public ArrayList<Integer> getHolds()
    {
        ArrayList<Integer> holds = new ArrayList<>(5);
        for(int i = 0; i < 6; i++)
        {
            holds.add((int)(outputs[i]*5));
        }
        return holds;
    }

    /**
     * Return an ArrayList of Pairs that represent the index and how much the AI wants to score a category.
     * @return ArrayList of Pairs for which categories the AI wants to score.
     */
    public ArrayList<Pair> getScoreCategory()
    {
        ArrayList<Pair> holds = new ArrayList<>(13);
        for(int i = 6; i < outputs.length; i++)
        {
            if(outputs[i] > 0)
            {
                holds.add(new Pair(i-6, outputs[i]));
            }
        }
        holds.sort(Pair::compareTo);
        return holds;
    }

    /**
     * Helper function to return an ArrayList with randomly selected weights and biases to mutate (give a random value).
     * @return YahtzeeNet with mutations.
     */
    public YahtzeeNet mutate()
    {
        YahtzeeNet newChild = new YahtzeeNet(this);
        if(Math.random() > MUTATION_CHANCE)
        {
            //random inputWeight
            newChild.inputWeights[(int)(INPUT_SIZE*LAYER1_SIZE*Math.random())] = -1 + 2*Math.random();
        }
        if(Math.random() > MUTATION_CHANCE)
        {
            //random layer1Weight
            newChild.layer1Weights[(int)(LAYER2_SIZE*LAYER1_SIZE*Math.random())] = -1 + 2*Math.random();
        }
        if(Math.random() > MUTATION_CHANCE)
        {
            //random layer1Bias
            newChild.layer1Bias[(int)(LAYER1_SIZE*Math.random())] = -1 + 2*Math.random();
        }
        if(Math.random() > MUTATION_CHANCE)
        {
            //random layer2Weight
            newChild.layer2Weights[(int)(LAYER2_SIZE*LAYER3_SIZE*Math.random())] = -1 + 2*Math.random();
        }
        if(Math.random() > MUTATION_CHANCE)
        {
            //random layer2Bias
            newChild.layer2Bias[(int)(LAYER2_SIZE*Math.random())] = -1 + 2*Math.random();
        }
        if(Math.random() > MUTATION_CHANCE)
        {
            //random layer3Weight
            newChild.layer3Weights[(int)(LAYER3_SIZE*LAYER4_SIZE*Math.random())] = -1 + 2*Math.random();
        }
        if(Math.random() > MUTATION_CHANCE)
        {
            //random layer3Bias
            newChild.layer3Bias[(int)(LAYER3_SIZE*Math.random())] = -1 + 2*Math.random();
        }
        if(Math.random() > MUTATION_CHANCE)
        {
            //random layer4Weight
            newChild.layer4Weights[(int)(LAYER4_SIZE*LAYER5_SIZE*Math.random())] = -1 + 2*Math.random();
        }
        if(Math.random() > MUTATION_CHANCE)
        {
            //random layer4Bias
            newChild.layer4Bias[(int)(LAYER4_SIZE*Math.random())] = -1 + 2*Math.random();
        }
        if(Math.random() > MUTATION_CHANCE)
        {
            //random layer5Weight
            newChild.layer5Weights[(int)(LAYER5_SIZE*OUTPUT_SIZE*Math.random())] = -1 + 2*Math.random();
        }
        if(Math.random() > MUTATION_CHANCE)
        {
            //random layer5Bias
            newChild.layer5Bias[(int)(LAYER5_SIZE*Math.random())] = -1 + 2*Math.random();
        }
        return newChild;
    }

    /**
     * Return list of YahtzeeNets that are random crosses of the given YahtzeeNet and this.
     * @param parent2 the parent to cross with.
     * @param num the number of YahtzeeNets to return.
     * @return an ArrayList of YahtzeeNets of the specified size.
     */
    public ArrayList<YahtzeeNet> cross(YahtzeeNet parent2, int num)
    {
        ArrayList<YahtzeeNet> nets = new ArrayList<>(10);
        for(int i = 0; i < num; i++)
        {
            YahtzeeNet newChild = new YahtzeeNet(this);
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
            for(int j = 0; j < (int)(layer4Weights.length*Math.random()); j++)
            {
                newChild.layer4Weights[j] = parent2.layer4Weights[j];
            }
            for(int j = 0; j < (int)(layer5Weights.length*Math.random()); j++)
            {
                newChild.layer5Weights[j] = parent2.layer5Weights[j];
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
            for(int j = 0; j < (int)(layer4Bias.length*Math.random()); j++)
            {
                newChild.layer4Bias[j] = parent2.layer4Bias[j];
            }
            for(int j = 0; j < (int)(layer5Bias.length*Math.random()); j++)
            {
                newChild.layer5Bias[j] = parent2.layer5Bias[j];
            }
            newChild = newChild.mutate();

            nets.add(newChild);
        }
        return nets;
    }

    /**
     * Print the weights of the YahtzeeNet to the given file.
     * @param file the File name of the File to print the weights to.
     * @throws FileNotFoundException
     */
    public void printWeights(String file) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(file));
        pw.printf("input: %s\n1bias: %s\n1weights: %s\n2bias: %s\n2weights: %s\n3bias: %s\n3weights: %s\n4bias: %s\n4weights: %s\n5bias: %s\n5weights: %s",
                arrayToString(inputWeights),arrayToString(layer1Bias),arrayToString(layer1Weights),
                arrayToString(layer2Bias),arrayToString(layer2Weights),arrayToString(layer3Bias),arrayToString(layer3Weights),
                arrayToString(layer4Bias),arrayToString(layer4Weights),arrayToString(layer5Bias),arrayToString(layer5Weights));
        pw.close();
    }

    /**
     * Get Array as a string. helper method for printWeights().
     * @param array the array to print as a string.
     * @return String representation of an array.
     */
    private static String arrayToString(double[] array)
    {
        String ret = "[";
        for(double d : array)
        {
            ret += d + ",";
        }
        ret = ret.substring(0,ret.length()-1);
        ret += "]";
        return ret;
    }


}
