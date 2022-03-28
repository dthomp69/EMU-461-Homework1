package q3.program;

import java.io.*;
import java.util.*;

//Program tests Bayes classifier in specific application
public class Q3Runner {
	/*************************************************************************/

	// Main method
	public static void main(String[] args) throws IOException {
		// Gets file names. NO ERROR PREVENTION
		Scanner scanner = new Scanner(System.in);
		System.out.println("Name of trainingData?");
		String file1Name = scanner.nextLine();
		System.out.println("Name of testingData?");
		String file2Name = scanner.nextLine();

		// preprocess files
		convertTrainingFile(file1Name, "convertedTrainingFile");
		convertTestFile(file2Name, "convertedTestFile");

		// construct bayes classifier
		Bayes classifier = new Bayes();

		// load training data
		classifier.loadTrainingData("convertedTrainingfile");

		// compute probabilities
		classifier.computeProbability();

		classifier.validate("validationfile");

		// classify data
		classifier.classifyData("convertedTestFile", "classifiedfile");

		// postprocess files
		convertClassFile("classifiedfile", "convertedClassifiedFile");
	}

	/****************************************************************************/

	// Method converts training file to numerical format
	private static void convertTrainingFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q3\\program\\" + inputFile));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q3\\program\\" + outputFile));

		// read number of records, attributes, classes
		int numberRecords = inFile.nextInt();
		int numberAttributes = inFile.nextInt();
		int numberClasses = inFile.nextInt();

		// Clear the next 8 lines to avoid reading in info about the data
		inFile.nextLine();
		inFile.nextLine();
		inFile.nextLine();
		inFile.nextLine();
		inFile.nextLine();
		inFile.nextLine();
		inFile.nextLine();
		inFile.nextLine();

		// read attribute values
		int[] attributeValues = new int[numberAttributes];
		for (int i = 0; i < numberAttributes; i++) {
			attributeValues[i] = inFile.nextInt();
		}

		// write number of records, attributes, classes
		outFile.println(numberRecords + " " + numberAttributes + " " + numberClasses);

		// Add another line for spacing
		outFile.println();

		// write attribute values
		for (int i = 0; i < numberAttributes; i++) {
			outFile.print(attributeValues[i] + " ");
		}
		outFile.println();

		// Print another line for spacing :)
		outFile.println();

		// for each record
		for (int i = 0; i < numberRecords; i++) {

			int languages = inFile.nextInt();
			int convertedLanguages = convertLanguages(languages);
			outFile.print(convertedLanguages + " ");

			String knowledge = inFile.next();
			int convertedKnowledge = convertKnowledge(knowledge);
			outFile.print(convertedKnowledge + " ");

			int expYears = inFile.nextInt();
			int convertedExpYears = convertExpYears(expYears);
			outFile.print(convertedExpYears + " ");

			String major = inFile.next();
			int convertedMajor = convertMajor(major);
			outFile.print(convertedMajor + " ");

			String academicPerformance = inFile.next();
			int convertedAcademicPerformance = convertAcademicPerformance(academicPerformance);
			outFile.print(convertedAcademicPerformance + " ");

			String className = inFile.next();
			int convertedClassName = convertClassName(className);
			outFile.print(convertedClassName + " ");

			outFile.println();
		}

		inFile.close();
		outFile.close();
	}

	/****************************************************************************/

	// Method converts test file to numerical format
	private static void convertTestFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q3\\program\\" + inputFile));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q3\\program\\" + outputFile));

		// read number of records
		int numberRecords = inFile.nextInt();

		// write number of records
		outFile.println(numberRecords);

		// Add another line for spacing
		outFile.println();

		// for each record
		for (int i = 0; i < numberRecords; i++) {

			int languages = inFile.nextInt();
			int convertedLanguages = convertLanguages(languages);
			outFile.print(convertedLanguages + " ");

			String knowledge = inFile.next();
			int convertedKnowledge = convertKnowledge(knowledge);
			outFile.print(convertedKnowledge + " ");

			int expYears = inFile.nextInt();
			int convertedExpYears = convertExpYears(expYears);
			outFile.print(convertedExpYears + " ");

			String major = inFile.next();
			int convertedMajor = convertMajor(major);
			outFile.print(convertedMajor + " ");

			String academicPerformance = inFile.next();
			int convertedAcademicPerformance = convertAcademicPerformance(academicPerformance);
			outFile.print(convertedAcademicPerformance + " ");

			outFile.println();
		}

		inFile.close();
		outFile.close();
	}

	/****************************************************************************/

	// Method converts class file to text format
	private static void convertClassFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q3\\output\\" + inputFile));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q3\\output\\" + outputFile));

		// read number of records
		int numberRecords = inFile.nextInt();

		// write number of records
		outFile.println(numberRecords);

		// for each record
		for (int i = 0; i < numberRecords; i++) {
			int languages = inFile.nextInt();
			int convertedLanguages = deConvertLanguages(languages);
			outFile.print(convertedLanguages + " ");

			int knowledge = inFile.nextInt();
			String convertedKnowledge = deConvertKnowledge(knowledge);
			outFile.print(convertedKnowledge + " ");

			int expYears = inFile.nextInt();
			int convertedExpYears = deConvertExpYears(expYears);
			outFile.print(convertedExpYears + " ");

			int major = inFile.nextInt();
			String convertedMajor = deConvertMajor(major);
			outFile.print(convertedMajor + " ");

			int academicPerformance = inFile.nextInt();
			String convertedAcademicPerformance = deConvertAcademicPerformance(academicPerformance);
			outFile.print(convertedAcademicPerformance + " ");

			int number = inFile.nextInt(); // convert class number
			String className = convertNumberToClass(number);
			outFile.println(className + " ");
		}

		// For some reason it won't just let me call outFile.println(inFile.nextLine()),
		// so I
		// manually did it like this.
		outFile.print(inFile.next() + " ");
		outFile.print(inFile.next() + " ");
		outFile.println(inFile.next());

		inFile.close();
		outFile.close();
	}

	/****************************************************************************/

	private static int convertLanguages(int value) {
		if (value == 0) {
			return 1;
		}
		if (value == 1) {
			return 2;
		}
		if (value == 2) {
			return 3;
		}
		return -1;
	}

	/*****************************************************************************/

	private static int convertKnowledge(String value) {
		if (value.equals("java") || value.equals("yes")) {
			return 1;
		}
		if (value.equals("no")) {
			return 2;
		}
		return -1;
	}

	/*****************************************************************************/

	private static int convertExpYears(int value) {
		if (value == 0) {
			return 1;
		}
		if (value == 1) {
			return 2;
		}
		if (value == 2) {
			return 3;
		}
		return -1;
	}

	/*****************************************************************************/

	private static int convertMajor(String value) {
		if (value.equals("cs")) {
			return 1;
		}
		if (value.equals("other")) {
			return 2;
		}
		return -1;
	}

	/*****************************************************************************/

	private static int convertAcademicPerformance(String value) {
		if (value.equals("A")) {
			return 1;
		}
		if (value.equals("B")) {
			return 2;
		}
		if (value.equals("C")) {
			return 3;
		}
		if (value.equals("D")) {
			return 4;
		}
		return -1;
	}

	/*****************************************************************************/

	private static int convertClassName(String value) {
		if (value.equals("interview")) {
			return 1;
		}
		if (value.equals("no")) {
			return 2;
		}
		return -1;
	}

	/*****************************************************************************/

	private static String convertNumberToClass(int value) {
		if (value == 1) {
			return "interview";
		}
		if (value == 2) {
			return "no";
		}
		return "Error";
	}

	/*****************************************************************************/

	private static int deConvertLanguages(int value) {
		if (value == 1) {
			return 0;
		}
		if (value == 2) {
			return 1;
		}
		if (value == 3) {
			return 2;
		}
		return -1;
	}

	/*****************************************************************************/

	private static String deConvertKnowledge(int value) {
		if (value == 1) {
			return "java";
		}
		if (value == 2) {
			return "no";
		}
		return "Error";
	}

	/*****************************************************************************/

	private static int deConvertExpYears(int value) {
		if (value == 1) {
			return 0;
		}
		if (value == 2) {
			return 1;
		}
		if (value == 3) {
			return 2;
		}
		return -1;
	}

	/*****************************************************************************/

	private static String deConvertMajor(int value) {
		if (value == 1) {
			return "cs";
		}
		if (value == 2) {
			return "other";
		}
		return "Error";
	}

	/*****************************************************************************/

	private static String deConvertAcademicPerformance(int value) {
		if (value == 1) {
			return "A";
		}
		if (value == 2) {
			return "B";
		}
		if (value == 3) {
			return "C";
		}
		if (value == 4) {
			return "D";
		}
		return "Error";
	}

}
