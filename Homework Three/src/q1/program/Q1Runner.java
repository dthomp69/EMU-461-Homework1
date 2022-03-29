package q1.program;

import java.io.*;
import java.util.Scanner;

//Program tests neural network in a specific application
public class Q1Runner {
	// Stores the number of inputs, so that the input file can be normalized
	public static int numberOfInputs;
	public static int numberOfOutputs;

	public static double validationError;

	public static int numberOfNodes = 3;
	public static int numberOfIterations = 1000;
	public static double learningRate = 0.5;
	public static int randomSeed = 4001;

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

		// Normalize trainingData

		normalizeTrainingData(trainingFileName);

		// load training data
		network.loadTrainingData("normalizedTrainingFile");

		// set parameters of network
		network.setParameters(numberOfNodes, numberOfIterations, learningRate, randomSeed);

		// train network
		network.train();

		// Normalize input file

		normalizeInputFile(inputFileName);

		// test network
		network.testData("normalizedInputFile", "normalizedOutputfile");

		// Normalize validation file

		normalizeValidationFile(validationFileName);

		// validate network
		network.validate("normalizedValidationFile");
		validationError = network.getValidationError();

		// DeNormalize Output File

		deNormalizeOutput("normalizedOutputfile");
	}

	/****************************************************************/

	public static void normalizeTrainingData(String fileName) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q1\\program\\" + fileName));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q1\\output\\" + "normalizedTrainingFile"));

		int numberRecords = inFile.nextInt();
		int numberInputs = inFile.nextInt();
		numberOfInputs = numberInputs;
		int numberOutputs = inFile.nextInt();
		numberOfOutputs = numberOutputs;

		outFile.println(numberRecords + " " + numberInputs + " " + numberOutputs);

		for (int i = 0; i < numberRecords; i++) {
			for (int j = 0; j < numberInputs; j++) {
				double original = inFile.nextDouble();
				double normalized = Q1Normalize(original);
				outFile.print(normalized + " ");
			}
			for (int j = 0; j < numberOutputs; j++) {
				double original = inFile.nextDouble();
				double normalized = Q1Normalize(original);
				outFile.print(normalized + " ");
			}
			outFile.println();
		}
		inFile.close();
		outFile.close();
	}

	/****************************************************************/

	public static void normalizeInputFile(String fileName) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q1\\program\\" + fileName));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q1\\output\\" + "normalizedInputFile"));

		int numberRecords = inFile.nextInt();

		outFile.println(numberRecords);

		for (int i = 0; i < numberRecords; i++) {
			for (int j = 0; j < numberOfInputs; j++) {
				double original = inFile.nextDouble();
				double normalized = Q1Normalize(original);
				outFile.print(normalized + " ");
			}
			outFile.println();
		}
		inFile.close();
		outFile.close();
	}

	/****************************************************************/

	public static void normalizeValidationFile(String fileName) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q1\\program\\" + fileName));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q1\\output\\" + "normalizedValidationFile"));

		int numberRecords = inFile.nextInt();

		outFile.println(numberRecords);

		for (int i = 0; i < numberRecords; i++) {
			for (int j = 0; j < numberOfInputs; j++) {
				double original = inFile.nextDouble();
				double normalized = Q1Normalize(original);
				outFile.print(normalized + " ");
			}
			for (int j = 0; j < numberOfOutputs; j++) {
				double original = inFile.nextDouble();
				double normalized = Q1Normalize(original);
				outFile.print(normalized + " ");
			}
			outFile.println();
		}
		inFile.close();
		outFile.close();
	}

	/****************************************************************/

	public static void deNormalizeOutput(String fileName) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q1\\output\\" + fileName));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q1\\output\\" + "outputFile"));

		while (inFile.hasNext()) {
			double original = inFile.nextDouble();
			double deNormalized = Q1DeNormalize(original);
			outFile.println(deNormalized);
		}

		outFile.println();

		// Remember to de-normalize the average error value.
		outFile.print("Average error: ");
		// TODO: Ask in class if we need to DeNormalize
//		outFile.println(Q1DeNormalize(validationError));
		outFile.println(validationError);
		outFile.println("Number of nodes: " + numberOfNodes);
		outFile.println("Number of iterations: " + numberOfIterations);
		outFile.println("Learning rate: " + learningRate);
		outFile.println("Random seed: " + randomSeed);

		inFile.close();
		outFile.close();
	}

	/****************************************************************/

	/*
	 * For this problem, I used the range of [0, 200] for both problems, because
	 * that was the range I saw across file1 and file2
	 * 
	 * This simplifies to x/200 and y*200 respectively, since the a value of the
	 * range is zero
	 */

	public static double Q1Normalize(double x) {
		return (x / 200);
	}

	public static double Q1DeNormalize(double y) {
		return y * 200;
	}
}
