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
		// preprocess files
		// TODO: Make user specified input files
		convertTrainingFile("file1", "convertedFile1");
		// TODO: make leave one out validation
		convertValidationFile("originalvalidationfile", "validationfile");
		convertTestFile("originaltestfile", "testfile");

		// construct nearest neighbor classifier
		NearestNeighbor classifier = new NearestNeighbor();

		// load training data
		classifier.loadTrainingData("convertedFile1");

		// set nearest neighbors
		classifier.setParameters(NEIGHBORS);

		// validate classfier
		classifier.validate("validationfile");

		// classify test data
		classifier.classifyData("testfile", "classifiedfile");

		// postprocess files
		convertClassFile("classifiedfile", "originalclassifiedfile");
	}

	/*************************************************************************/

	// Method converts training file to numerical format
	private static void convertTrainingFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q1\\program\\" + inputFile));
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

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
			int creditscore = inFile.nextInt();

			int income = inFile.nextInt();

			int age = inFile.nextInt();

			String gender = inFile.next();

			String maritalStatus = inFile.next();

			String className = inFile.next();
		}

		inFile.close();
		outFile.close();
	}

	/*************************************************************************/

	// Method converts validation file to numerical format
	private static void convertValidationFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q1\\program\\" + inputFile));
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		// read number of records
		int numberRecords = inFile.nextInt();

		// write number of records
		outFile.println(numberRecords);

		// for each record
		for (int i = 0; i < numberRecords; i++) {
			String grade = inFile.next(); // convert grade
			double gradeNumber = convertGradeToNumber(grade);
			outFile.print(gradeNumber + " ");

			double gpa = inFile.nextDouble(); // convert gpa
			double gpaNumber = convertGPA(gpa);
			outFile.print(gpaNumber + " ");

			String className = inFile.next(); // convert class name
			int classNumber = convertClassToNumber(className);
			outFile.println(classNumber);
		}

		inFile.close();
		outFile.close();
	}

	/*************************************************************************/

	// Method converts test file to numerical format
	private static void convertTestFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q1\\program\\" + inputFile));
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		// read number of records
		int numberRecords = inFile.nextInt();

		// write number of records
		outFile.println(numberRecords);

		// for each record
		for (int i = 0; i < numberRecords; i++) {
			String grade = inFile.next(); // convert grade
			double gradeNumber = convertGradeToNumber(grade);
			outFile.print(gradeNumber + " ");

			double gpa = inFile.nextDouble(); // convert gpa
			double gpaNumber = convertGPA(gpa);
			outFile.println(gpaNumber + " ");
		}

		inFile.close();
		outFile.close();
	}

	/*************************************************************************/

	// Method converts classified file to text format
	private static void convertClassFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(inputFile));
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		// read number of records
		int numberRecords = inFile.nextInt();

		// write number of records
		outFile.println(numberRecords);

		// for each record
		for (int i = 0; i < numberRecords; i++) {
			int number = inFile.nextInt(); // convert class number
			String className = convertNumberToClass(number);
			outFile.println(className);
		}

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
		if (number == 1)
			return "good";
		else
			return "bad";
	}

	/**************************************************/

}
