package q2.program;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Q2Runner {
	/*************************************************************************/

	// number of nearest neighbors
	private static final int NEIGHBORS = 3;

	// Main method
	public static void main(String[] args) throws IOException {
		// Gets file names. NO ERROR PREVENTION
		Scanner scanner = new Scanner(System.in);
		System.out.println("Name of trainingData?");
		String file1Name = scanner.nextLine();
		System.out.println("Name of testingData?");
		String file2Name = scanner.nextLine();

		convertTrainingFile(file1Name, "convertedTrainingData");

		convertTestFile(file2Name, "convertedTestData");

		// construct nearest neighbor classifier
		NearestNeighbor classifier = new NearestNeighbor();

		// load training data
		classifier.loadTrainingData("convertedTrainingData");

		// set nearest neighbors
		classifier.setParameters(NEIGHBORS);

		// validate classfier
		classifier.validate("validationfile");

		// classify test data
		classifier.classifyData("convertedTestData", "NNOut");

		// postprocess files
		convertClassFile("NNOut", "NNOutConverted");
	}

	/*************************************************************************/

	// Method converts training file to numerical format
	private static void convertTrainingFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q2\\program\\" + inputFile));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q2\\program\\" + outputFile));

		// read number of records, attributes, classes
		int numberRecords = inFile.nextInt();
		int numberAttributes = inFile.nextInt();
		int numberClasses = inFile.nextInt();

		// write number of records, attributes, classes
		outFile.println(numberRecords + " " + numberAttributes + " " + numberClasses);

		// Bonus line for spacing
		outFile.println();

		// for each record
		for (int i = 0; i < numberRecords; i++) {
			for (int j = 1; j <= numberAttributes; j++) {
				int attributeName = inFile.nextInt();
				int convertedAttribute = -1;
				if (attributeName == 0) {
					convertedAttribute = 1;
				}
				if (attributeName == 1) {
					convertedAttribute = 2;
				}
				// Print as a new line if it's time to go to the next line
				if (j % 16 == 0 && j != 0) {
					outFile.println(convertedAttribute + " ");
				} else {
					outFile.print(convertedAttribute + " ");
				}
			}
			int className = inFile.nextInt();
			if (className == 0) {
				outFile.println(1 + " ");
			} else {
				outFile.println(2 + " ");
			}
			// Bonus line for spacing between entries
			outFile.println();
		}

		inFile.close();
		outFile.close();
	}

	/*************************************************************************/

	// Method converts test file to numerical format
	private static void convertTestFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q2\\program\\" + inputFile));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q2\\program\\" + outputFile));

		// read number of records
		int numberRecords = inFile.nextInt();

		// write number of records
		outFile.println(numberRecords);

		// Bonus line for spacing
		outFile.println();

		for (int i = 0; i < numberRecords; i++) {

			for (int j = 1; j <= 256; j++) {
				int attributeName = inFile.nextInt();
				// Print as a new line if you're done with that row
				if (j % 16 != 0 || j == 0) {
					if (attributeName == 0) {
						outFile.print(1 + " ");
					} else {
						outFile.print(2 + " ");
					}
				} else {
					if (attributeName == 0) {
						outFile.println(1 + " ");
					} else {
						outFile.println(2 + " ");
					}
				}
			}

			// Print a bonus line so spacing makes sense
			outFile.println();
		}

		inFile.close();
		outFile.close();
	}

	/*************************************************************************/

	// Method converts classified file to text format
	private static void convertClassFile(String inputFile, String outputFile) throws IOException {
		// input and output files
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q2\\output\\" + inputFile));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q2\\output\\" + outputFile));

		// read number of records
		int numberRecords = inFile.nextInt();

		// write number of records
		outFile.println(numberRecords);

		for (int i = 0; i < numberRecords; i++) {

			for (int j = 1; j <= 256; j++) {
				int attributeName = (int) inFile.nextDouble();
				// Print as a new line if you're done with that row
				if (j % 16 != 0 || j == 0) {
					if (attributeName == 1) {
						outFile.print(0 + " ");
					} else {
						outFile.print(1 + " ");
					}
				} else {
					if (attributeName == 1) {
						outFile.println(0 + " ");
					} else {
						outFile.println(1 + " ");
					}
				}
			}
			int className = (int) inFile.nextDouble();
			if (className == 2) {
				outFile.println(1);
			} else {
				outFile.println(0);
			}
		}

		// For some reason it won't just let me call outFile.println(inFile.nextLine()),
		// so I
		// manually did it like this.
		outFile.print(inFile.next() + " ");
		outFile.print(inFile.next() + " ");
		outFile.println(inFile.next());

		outFile.println("Number of neighbors: " + NEIGHBORS);

		inFile.close();
		outFile.close();
	}

}
