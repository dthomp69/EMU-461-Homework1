package mannicamNeural;

import java.io.*;

//Program tests neural network in a specific application
public class NeuralNetworkTester
{
    //Main method
    public static void main(String[] args) throws IOException
    {
        //construct neural network
        NeuralNetwork network = new NeuralNetwork();

        //load training data
        network.loadTrainingData("trainingfile");

        //set parameters of network
        network.setParameters(3, 1000, 0.5, 2375);

        //train network
        network.train();

        //test network
        network.testData("inputfile", "outputfile");

        //validate network
        network.validate("validationfile");
    }
}
