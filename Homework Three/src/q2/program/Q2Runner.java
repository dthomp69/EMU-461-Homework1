package q2.program;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//Program tests neural network in a specific application
public class Q2Runner {
	// Stores the number of inputs, so that the input file can be normalized
	public static int numberOfInputs;
	public static int numberOfOutputs;

	public static double validationError;

	public static int numberOfNodes = 5;
	public static int numberOfIterations = 10000;
	public static double learningRate = 0.25;
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

		deNormalizeOutput("normalizedOutputfile", inputFileName);
	}

	/****************************************************************/

	public static void normalizeTrainingData(String fileName) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q2\\program\\" + fileName));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q2\\output\\" + "normalizedTrainingFile"));

		int numberRecords = inFile.nextInt();
		int numberInputs = inFile.nextInt();
		numberOfInputs = numberInputs;
		int numberOutputs = inFile.nextInt();
		numberOfOutputs = numberOutputs;

		outFile.println(numberRecords + " " + numberInputs + " " + numberOutputs);

		for (int i = 0; i < numberRecords; i++) {
			int originalCreditScore = inFile.nextInt();
			double normalizedCreditScore = normalizeCreditScore(originalCreditScore);
			outFile.print(normalizedCreditScore + " ");

			int originalIncome = inFile.nextInt();
			double normalizedIncome = normalizeIncome(originalIncome);
			outFile.print(normalizedIncome + " ");

			int originalAge = inFile.nextInt();
			double normalizedAge = normalizeAge(originalAge);
			outFile.print(normalizedAge + " ");

			String originalSex = inFile.next();
			double normalizedSex = normalizeSex(originalSex);
			outFile.print(normalizedSex + " ");

			String originalMStatus = inFile.next();
			double normalizedMStatus = normalizeMStatus(originalMStatus);
			outFile.print(normalizedMStatus + " ");

			String originalClass = inFile.next();
			double normalizedClass = normalizeClass(originalClass);
			outFile.print(normalizedClass + " ");

			outFile.println();
		}
		inFile.close();
		outFile.close();
	}

	/****************************************************************/

	public static void normalizeInputFile(String fileName) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q2\\program\\" + fileName));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q2\\output\\" + "normalizedInputFile"));

		int numberRecords = inFile.nextInt();

		outFile.println(numberRecords);

		for (int i = 0; i < numberRecords; i++) {
			int originalCreditScore = inFile.nextInt();
			double normalizedCreditScore = normalizeCreditScore(originalCreditScore);
			outFile.print(normalizedCreditScore + " ");

			int originalIncome = inFile.nextInt();
			double normalizedIncome = normalizeIncome(originalIncome);
			outFile.print(normalizedIncome + " ");

			int originalAge = inFile.nextInt();
			double normalizedAge = normalizeAge(originalAge);
			outFile.print(normalizedAge + " ");

			String originalSex = inFile.next();
			double normalizedSex = normalizeSex(originalSex);
			outFile.print(normalizedSex + " ");

			String originalMStatus = inFile.next();
			double normalizedMStatus = normalizeMStatus(originalMStatus);
			outFile.print(normalizedMStatus + " ");

			outFile.println();
		}
		inFile.close();
		outFile.close();
	}

	/****************************************************************/

	public static void normalizeValidationFile(String fileName) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q2\\program\\" + fileName));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q2\\output\\" + "normalizedValidationFile"));

		int numberRecords = inFile.nextInt();

		outFile.println(numberRecords);

		for (int i = 0; i < numberRecords; i++) {
			int originalCreditScore = inFile.nextInt();
			double normalizedCreditScore = normalizeCreditScore(originalCreditScore);
			outFile.print(normalizedCreditScore + " ");

			int originalIncome = inFile.nextInt();
			double normalizedIncome = normalizeIncome(originalIncome);
			outFile.print(normalizedIncome + " ");

			int originalAge = inFile.nextInt();
			double normalizedAge = normalizeAge(originalAge);
			outFile.print(normalizedAge + " ");

			String originalSex = inFile.next();
			double normalizedSex = normalizeSex(originalSex);
			outFile.print(normalizedSex + " ");

			String originalMStatus = inFile.next();
			double normalizedMStatus = normalizeMStatus(originalMStatus);
			outFile.print(normalizedMStatus + " ");

			String originalClass = inFile.next();
			double normalizedClass = normalizeClass(originalClass);
			outFile.print(normalizedClass + " ");

			outFile.println();
		}
		inFile.close();
		outFile.close();
	}

	/****************************************************************/

	public static void deNormalizeOutput(String fileName, String inputFileName) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q2\\output\\" + fileName));
		Scanner originalInputFile = new Scanner(
				new File(System.getProperty("user.dir") + "\\src\\q2\\program\\" + inputFileName));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q2\\output\\" + "outputFile"));

		// Clear the top of the input file
		originalInputFile.nextLine();
		originalInputFile.nextLine();

		while (inFile.hasNext()) {
			outFile.print(originalInputFile.nextLine());
			outFile.println("   " + deNormalizeClass(inFile.nextDouble()));
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

	public static double normalizeCreditScore(double x) {
		return (x - 500) / (400);
	}

	public static double deNormalizeCreditScore(double y) {
		return (y * (400)) + 500;
	}

	/****************************************************************/
	public static double normalizeIncome(double x) {
		return (x - 30) / (60);
	}

	public static double deNormalizeIncome(double y) {
		return (y * (60)) + 30;
	}

	/****************************************************************/
	public static double normalizeAge(double x) {
		return (x - 30) / (50);
	}

	public static double deNormalizeAge(double y) {
		return (y * (50)) + 30;
	}

	/****************************************************************/
	public static double normalizeSex(String input) {
		if (input.equals("male")) {
			return 0.0;
		}
		if (input.equals("female")) {
			return 1.0;
		}
		return -1.0;
	}

	public static String deNormalizeSex(double y) {
		if (y == 0.0) {
			return "male";
		}
		if (y == 1.0) {
			return "female";
		}
		return "error";
	}

	/****************************************************************/
	public static double normalizeMStatus(String input) {
		if (input.equals("single")) {
			return 0.0;
		}
		if (input.equals("divorced")) {
			return 0.5;
		}
		if (input.equals("married")) {
			return 1.0;
		}
		return -1.0;
	}

	public static String deNormalizeMStatus(double y) {
		if (y == 0.0) {
			return "single";
		}
		if (y == 0.5) {
			return "divorced";
		}
		if (y == 1.0) {
			return "married";
		}
		return "error";
	}

	/****************************************************************/
	public static double normalizeClass(String input) {
		if (input.equals("low")) {
			return 0.0;
		}
		if (input.equals("medium")) {
			return 0.25;
		}
		if (input.equals("high")) {
			return 0.5;
		}
		if (input.equals("undetermined")) {
			return 0.75;
		}
		return -1.0;
	}

	public static String deNormalizeClass(double y) {
		double checkUndetermined = Math.abs(.75 - y);
		double checkHigh = Math.abs(.50 - y);
		double checkMedium = Math.abs(.25 - y);
		double checkLow = Math.abs(0.0 - y);

		ArrayList<Double> values = new ArrayList<Double>();

		values.add(checkLow);
		values.add(checkMedium);
		values.add(checkHigh);
		values.add(checkUndetermined);

		int lowestIndex = -1;
		double lowestValue = 100.0;

		for (int i = 0; i < 4; i++) {
			if (values.get(i) < lowestValue) {
				lowestIndex = i;
				lowestValue = values.get(i);
			}
		}

		if (lowestIndex == 0) {
			return "low";
		}
		if (lowestIndex == 1) {
			return "medium";
		}
		if (lowestIndex == 2) {
			return "high";
		}
		if (lowestIndex == 3) {
			return "undetermined";
		}
		return "error";
	}

}
