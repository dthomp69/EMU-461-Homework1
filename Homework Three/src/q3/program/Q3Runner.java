package q3.program;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//Program tests neural network in a specific application
public class Q3Runner {
	// Stores the number of inputs, so that the input file can be normalized
	public static int numberOfInputs;
	public static int numberOfOutputs;

	public static double validationError;

	public static int numberOfNodes = 6;
	public static int numberOfIterations = 700000;
	public static double learningRate = 0.07;
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

		// normalizeInputFile(inputFileName);

		// test network
		network.testData(inputFileName, "normalizedOutputfile");

		// Normalize validation file

		normalizeValidationFile(validationFileName);

		// validate network
		network.validate("normalizedValidationFile");
		validationError = network.getValidationError();

		// DeNormalize Output File

		deNormalizeOutput("normalizedOutputfile", inputFileName);
	}

	/****************************************************************/

	public static void normalizeTrainingData(String fileName) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q3\\program\\" + fileName));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q3\\output\\" + "normalizedTrainingFile"));

		int numberRecords = inFile.nextInt();
		int numberInputs = inFile.nextInt();
		numberOfInputs = numberInputs;
		int numberOutputs = inFile.nextInt();
		numberOfOutputs = numberOutputs;

		outFile.println(numberRecords + " " + numberInputs + " " + numberOutputs);

		// Bonus line for spacing
		outFile.println();

		for (int i = 0; i < numberRecords; i++) {
			for (int j = 1; j <= numberInputs; j++) {
				if (j % 16 == 0 && j != 0) {
					outFile.println(inFile.nextInt() + " ");
				} else {
					outFile.print(inFile.nextInt() + " ");
				}
			}
			int rawClass = inFile.nextInt();
			double normalizedClass = normalizeClass(rawClass);
			outFile.println(normalizedClass);

			// Bonus line for looks
			outFile.println();
		}

		inFile.close();
		outFile.close();
	}

	/****************************************************************/

	public static void normalizeInputFile(String fileName) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q3\\program\\" + fileName));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q3\\output\\" + "normalizedInputFile"));

		int numberRecords = inFile.nextInt();

		outFile.println(numberRecords);

		for (int i = 0; i < numberRecords; i++) {

		}
		inFile.close();
		outFile.close();
	}

	/****************************************************************/

	public static void normalizeValidationFile(String fileName) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q3\\program\\" + fileName));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q3\\output\\" + "normalizedValidationFile"));

		int numberRecords = inFile.nextInt();

		outFile.println(numberRecords);

		for (int i = 0; i < numberRecords; i++) {
			for (int j = 1; j <= numberOfInputs; j++) {
				if (j % 16 == 0 && j != 0) {
					outFile.println(inFile.nextInt() + " ");
				} else {
					outFile.print(inFile.nextInt() + " ");
				}
			}
			int rawClass = inFile.nextInt();
			double normalizedClass = normalizeClass(rawClass);
			outFile.println(normalizedClass);

			// Bonus line for looks
			outFile.println();
		}
		inFile.close();
		outFile.close();
	}

	/****************************************************************/

	public static void deNormalizeOutput(String fileName, String inputFileName) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q3\\output\\" + fileName));
		Scanner originalInputFile = new Scanner(
				new File(System.getProperty("user.dir") + "\\src\\q3\\program\\" + inputFileName));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q3\\output\\" + "outputFile"));

//		while (inFile.hasNext()) {
//			outFile.print(originalInputFile.nextLine());
//			outFile.println("   " + deNormalizeClass(inFile.nextDouble()));
//		}
		int numberOfRecords = originalInputFile.nextInt();
		originalInputFile.nextLine();

		outFile.println(numberOfRecords);

		// For each of the records in the input file, copy the next 16 lines from the
		// input file
		for (int i = 0; i < numberOfRecords; i++) {
			for (int j = 0; j < 16; j++) {
				outFile.println(originalInputFile.nextLine());
			}

			outFile.println(deNormalizeClass(inFile.nextDouble()));

			// Clear the gap line
			originalInputFile.nextLine();
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

		originalInputFile.close();
		inFile.close();
		outFile.close();
	}

	/****************************************************************/
	public static double normalizeClass(int input) {
		if (input == 0) {
			return 0.0;
		}
		if (input == 1) {
			return 0.5;
		}
		if (input == 2) {
			return 1.0;
		}
		return -1.0;
	}

	public static int deNormalizeClass(double y) {
		double checkTwo = Math.abs(1.0 - y);
		double checkOne = Math.abs(0.5 - y);
		double checkZero = Math.abs(0.0 - y);

		ArrayList<Double> values = new ArrayList<Double>();

		values.add(checkZero);
		values.add(checkOne);
		values.add(checkTwo);

		int lowestIndex = -1;
		double lowestValue = 100.0;

		for (int i = 0; i < 3; i++) {
			if (values.get(i) < lowestValue) {
				lowestIndex = i;
				lowestValue = values.get(i);
			}
		}

		if (lowestIndex == 0) {
			return 0;
		}
		if (lowestIndex == 1) {
			return 1;
		}
		if (lowestIndex == 2) {
			return 2;
		}

		return -1;
	}

}
