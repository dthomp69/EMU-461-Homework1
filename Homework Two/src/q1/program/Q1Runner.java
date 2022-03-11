package q1.program;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Q1Runner {
	/*************************************************************************/

	// number of nearest neighbors
	private static final int NEIGHBORS = 5;

	// Main method
	public static void main(String[] args) throws IOException {

		// Gets file names. NO ERROR PREVENTION
		Scanner scanner = new Scanner(System.in);
		System.out.println("Name of trainingData?");
		String file1Name = scanner.nextLine();
		System.out.println("Name of testingData?");
		String file2Name = scanner.nextLine();
//		System.out.println(file1Name);
//		System.out.println(file2Name);

		// preprocess files
		// TODO: Make user specified input files
		convertTrainingFile(file1Name, "convertedFile1");

		// TODO: make leave one out validation
		/*
		 * Pretty sure that since there's no validation file, validation is done
		 * entirely inside of the nearestNeighbor algorithm. Commenting out related code
		 * inside of this class
		 */
//		convertValidationFile("originalvalidationfile", "validationfile");

		convertTestFile(file2Name, "convertedFile2");

		// construct nearest neighbor classifier
		NearestNeighbor classifier = new NearestNeighbor();

		// load training data
		classifier.loadTrainingData("convertedFile1");

		// set nearest neighbors
		classifier.setParameters(NEIGHBORS);

		// validate classfier
//		classifier.validate("validationfile");

		// TODO: make the below method call actually work, since that's what leave one
		// out will be.
//		classifier.validate();
		classifier.validate("convertedFile1");

		// classify test data
		classifier.classifyData("convertedFile2", "NNOut");

		// postprocess files
		convertClassFile("NNOut", "NNOutConverted");
	}

	/*************************************************************************/

	// Method converts training file to numerical format
	private static void convertTrainingFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q1\\program\\" + inputFile));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q1\\program\\" + outputFile));

		// read number of records, attributes, classes
		int numberRecords = inFile.nextInt();
		int numberAttributes = inFile.nextInt();
		int numberClasses = inFile.nextInt();

		// write number of records, attributes, classes
		outFile.println(numberRecords + " " + numberAttributes + " " + numberClasses);

		// for each record
		for (int i = 0; i < numberRecords; i++) {
//			String grade = inFile.next(); // convert grade
//			double gradeNumber = convertGradeToNumber(grade);
//			outFile.print(gradeNumber + " ");
//
//			double gpa = inFile.nextDouble(); // convert gpa
//			double gpaNumber = convertGPA(gpa);
//			outFile.print(gpaNumber + " ");
//
//			String className = inFile.next(); // convert class name
//			int classNumber = convertClassToNumber(className);
//			outFile.println(classNumber);

			// Normalize Data
//			int creditScore = inFile.nextInt();
//			outFile.print(creditScore + " ");
//
//			int income = inFile.nextInt();
//			outFile.print(income + " ");
//
//			int age = inFile.nextInt();
//			outFile.print(age + " ");
//
//			String gender = inFile.next();
//			int genderConverted = convertGender(gender);
//			outFile.print(genderConverted + " ");
//
//			String maritalStatus = inFile.next();
//			int maritalStatusConverted = convertMaritalStatus(maritalStatus);
//			outFile.print(maritalStatusConverted + " ");

			int creditScore = inFile.nextInt();
			double creditScoreNormalized = normalizeCreditScore(creditScore);
			outFile.print(creditScoreNormalized + " ");

			int income = inFile.nextInt();
			double incomeNormalized = normalizeIncome(income);
			outFile.print(incomeNormalized + " ");

			int age = inFile.nextInt();
			double ageNormalized = normalizeAge(age);
			outFile.print(ageNormalized + " ");

			String gender = inFile.next();
			int genderConverted = convertGender(gender);
			outFile.print(genderConverted + " ");

			String maritalStatus = inFile.next();
			int maritalStatusConverted = convertMaritalStatus(maritalStatus);
			double normalizedMaritalStatus = normalizeMaritalStatus(maritalStatusConverted);
			outFile.print(normalizedMaritalStatus + " ");

			String className = inFile.next();
			int convertedClass = convertClass(className);
			outFile.println(convertedClass);
		}

		inFile.close();
		outFile.close();
	}

	/*************************************************************************/

	// Method converts validation file to numerical format
//	private static void convertValidationFile(String inputFile, String outputFile) throws IOException {
//		// input and output files
//		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q1\\program\\" + inputFile));
//		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
//
//		// read number of records
//		int numberRecords = inFile.nextInt();
//
//		// write number of records
//		outFile.println(numberRecords);
//
//		// for each record
//		for (int i = 0; i < numberRecords; i++) {
//			String grade = inFile.next(); // convert grade
//			double gradeNumber = convertGradeToNumber(grade);
//			outFile.print(gradeNumber + " ");
//
//			double gpa = inFile.nextDouble(); // convert gpa
//			double gpaNumber = convertGPA(gpa);
//			outFile.print(gpaNumber + " ");
//
//			String className = inFile.next(); // convert class name
//			int classNumber = convertClassToNumber(className);
//			outFile.println(classNumber);
//		}
//
//		inFile.close();
//		outFile.close();
//	}

	/*************************************************************************/

	// Method converts test file to numerical format
	private static void convertTestFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q1\\program\\" + inputFile));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q1\\program\\" + outputFile));

		// read number of records
		int numberRecords = inFile.nextInt();

		// write number of records
		outFile.println(numberRecords);

		// TODO: How do I know when to stop if there's no number of records at the top?
		// for each record
//		for (int i = 0; i < numberRecords; i++) {
		while (inFile.hasNext()) {
//			String grade = inFile.next(); // convert grade
//			double gradeNumber = convertGradeToNumber(grade);
//			outFile.print(gradeNumber + " ");
//
//			double gpa = inFile.nextDouble(); // convert gpa
//			double gpaNumber = convertGPA(gpa);
//			outFile.println(gpaNumber + " ");

			// Normalize data
			int creditScore = inFile.nextInt();
			double creditScoreNormalized = normalizeCreditScore(creditScore);
			outFile.print(creditScoreNormalized + " ");

			int income = inFile.nextInt();
			double incomeNormalized = normalizeIncome(income);
			outFile.print(incomeNormalized + " ");

			int age = inFile.nextInt();
			double ageNormalized = normalizeAge(age);
			outFile.print(ageNormalized + " ");

			String gender = inFile.next();
			int genderConverted = convertGender(gender);
			outFile.print(genderConverted + " ");

			String maritalStatus = inFile.next();
			int maritalStatusConverted = convertMaritalStatus(maritalStatus);
			double normalizedMaritalStatus = normalizeMaritalStatus(maritalStatusConverted);
			outFile.print(normalizedMaritalStatus + " ");

			outFile.println();

			// No class to normalize
//			String className = inFile.next();
//			int convertedClass = convertClass(className);
//			outFile.println(convertedClass);
		}

		inFile.close();
		outFile.close();
	}

	/*************************************************************************/

	// Method converts classified file to text format
	private static void convertClassFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q1\\output\\" + inputFile));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q1\\output\\" + outputFile));

		// read number of records
		int numberRecords = inFile.nextInt();

		// write number of records
		outFile.println(numberRecords);

		// for each record
		for (int i = 0; i < numberRecords; i++) {
			/*********************************/
			double creditScore = inFile.nextDouble();
			double creditScoreDenormalized = deNormalizeCreditScore(creditScore);
			outFile.print(creditScoreDenormalized + " ");

			double income = inFile.nextDouble();
			double incomeDenormalized = deNormalizeIncome(income);
			outFile.print(incomeDenormalized + " ");

			double age = inFile.nextDouble();
			double ageDenormalized = deNormalizeAge(age);
			outFile.print(ageDenormalized + " ");

			double gender = inFile.nextDouble();
			String genderConverted = deNormalizeGender(gender);
			outFile.print(genderConverted + " ");

			double maritalStatus = inFile.nextDouble();
			double maritalStatusConverted = deNormalizeMaritalStatus(maritalStatus);
			String deConvertMaritalStatus = deConvertMaritalStatus(maritalStatusConverted);
			outFile.print(deConvertMaritalStatus + " ");
			/******************************/

			int number = inFile.nextInt(); // convert class number
			String className = convertNumberToClass(number);
			outFile.println(className);
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

	// Method converts grade to number
	private static double convertGradeToNumber(String grade) {
		if (grade.equals("A"))
			return 1.0;
		else if (grade.equals("B"))
			return 0.5;
		else
			return 0.0;
	}

	/****************************************************************************/

	// Method normalizes gpa
	private static double convertGPA(double gpa) {
		return gpa / 4;
	}

	/****************************************************************************/

	// Method converts class name to number
	private static int convertClassToNumber(String className) {
		if (className.equals("good"))
			return 1;
		else
			return 2;
	}

	/****************************************************************************/

	// Method converts number to class name
	private static String convertNumberToClass(int number) {
//		if (number == 1)
//			return "good";
//		else
//			return "bad";
		if (number == 1) {
			return "undetermined";
		}
		if (number == 2) {
			return "low";
		}
		if (number == 3) {
			return "medium";
		}
		if (number == 4) {
			return "high";
		}
		return "Error";
	}

	/**************************************************/

	private static int convertGender(String gender) {
		if (gender.equals("male")) {
			return 0;
		} else {
			return 1;
		}
	}

	/****************************************************/

	private static int convertMaritalStatus(String maritalStatus) {
		if (maritalStatus.equals("single")) {
			return 0;
		}
		if (maritalStatus.equals("married")) {
			return 1;
		}
		if (maritalStatus.equals("divorced")) {
			return 2;
		}
		// Should never have to return -1, error check basically
		return -1;
	}

	/*********************************************************/

	private static int convertClass(String className) {
		if (className.equals("undetermined")) {
			return 1;
		}
		if (className.equals("low")) {
			return 2;
		}
		if (className.equals("medium")) {
			return 3;
		}
		if (className.equals("high")) {
			return 4;
		}
		return -1;
	}

	/*************************************************************/
	private static double normalizeCreditScore(double value) {
		return 1 - ((value - 500) / (900 - 500));
	}

	private static double normalizeIncome(double value) {
		return 1 - ((value - 30) / (90 - 30));
	}

	private static double normalizeAge(double value) {
		return 1 - ((value - 30) / (80 - 30));
	}

	private static double normalizeMaritalStatus(double value) {
		return 1 - ((value - 0) / (2 - 0));
	}

	/*****************************************************************/
	private static double deNormalizeCreditScore(double value) {
		return ((1 - value) * 400) + 500;
	}

	private static double deNormalizeIncome(double value) {
		return ((1 - value) * 60) + 30;
	}

	private static double deNormalizeAge(double value) {
		return ((1 - value) * 50) + 30;
	}

	private static double deNormalizeMaritalStatus(double value) {
		return ((1 - value) * 2);
	}

	private static String deNormalizeGender(double value) {
		if (value == 0.0) {
			return "Male";
		}
		return "Female";
	}

	private static String deConvertMaritalStatus(double value) {
		if (value == 0.0) {
			return "Single";
		}
		if (value == 1.0) {
			return "Married";
		}
		if (value == 2.0) {
			return "Divorced";
		}
		return "Error";
	}
}
