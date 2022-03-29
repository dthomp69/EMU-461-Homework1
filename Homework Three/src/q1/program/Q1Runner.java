package q1.program;

import java.io.*;
import java.util.Scanner;

//Program tests neural network in a specific application
public class Q1Runner {
	// Main method
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Name of training file?");
		String trainingFileName = scanner.nextLine();
		System.out.println("Name of input file?");
		String inputFileName = scanner.nextLine();
		System.out.println("Name of validation file?");
		String validationFileName = scanner.nextLine();

		scanner.close();

		// construct neural network
		NeuralNetwork network = new NeuralNetwork();

		// load training data
		network.loadTrainingData(trainingFileName);

		// set parameters of network
		network.setParameters(3, 1000, 0.5, 2375);

		// train network
		network.train();

		// test network
		network.testData(inputFileName, "outputfile");

		// validate network
		network.validate(validationFileName);
	}
}
