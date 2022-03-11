package q4.program;

import java.io.*;
import java.util.Scanner;

//Program tests k-means clustering in a specific application
public class Q4Runner {
	// Main method
	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Name of data?");
		String fileName = scanner.nextLine();

		// Original data may have to be preprocessed
		convertInputFile(fileName, "convertedInputFile");

		// create clustering object
		Kmeans k = new Kmeans();

		// load records
		k.load("convertedInputFile");

		// set parameters
		k.setParameters(3, 100, 9999);

		// perform clustering
		k.cluster();

		// display records and clusters
		k.display("outputfile");
		convertOutputFile("outputfile", "convertedOutputFile");
	}

	private static void convertInputFile(String inputFile, String outputFile) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q4\\program\\" + inputFile));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q4\\output\\" + outputFile));
		inFile.nextLine();
		inFile.nextLine();
		inFile.nextLine();
		inFile.nextLine();

		int numberRecords = inFile.nextInt();
		int numberAttributes = inFile.nextInt();

		outFile.println(numberRecords + " " + numberAttributes + " ");

		// for each record
		for (int i = 0; i < numberRecords; i++) {
			int age = inFile.nextInt();
			double ageNormalized = normalizeAge(age);
			outFile.print(ageNormalized + " ");

			int income = inFile.nextInt();
			double incomeNormalized = normalizeIncome(income);
			outFile.print(incomeNormalized + " ");

			int creditScore = inFile.nextInt();
			double creditScoreNormalized = normalizeCreditScore(creditScore);
			outFile.print(creditScoreNormalized + " ");

			outFile.println();
		}

		inFile.close();
		outFile.close();

	}

	private static void convertOutputFile(String inputFile, String outputFile) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q4\\output\\" + inputFile));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q4\\output\\" + outputFile));

		// read number of records
		int numberRecords = inFile.nextInt();

		// write number of records
		outFile.println(numberRecords);

		// for each record
		for (int i = 0; i < numberRecords; i++) {
			double age = inFile.nextDouble();
			double ageDenormalized = deNormalizeAge(age);
			outFile.print(ageDenormalized + " ");

			double income = inFile.nextDouble();
			double incomeDenormalized = deNormalizeIncome(income);
			outFile.print(incomeDenormalized + " ");

			double creditScore = inFile.nextDouble();
			double creditScoreDenormalized = deNormalizeCreditScore(creditScore);
			outFile.print(creditScoreDenormalized + " ");

			int cluster = inFile.nextInt();
			outFile.print(cluster);

			outFile.println();
		}

		// For some reason it won't just let me call outFile.println(inFile.nextLine()),
		// so I
		// manually did it like this.
		outFile.print(inFile.next() + " ");
		outFile.println(inFile.next());

		inFile.close();
		outFile.close();
	}

	private static double normalizeCreditScore(double value) {
		return 1 - ((value - 500) / (900 - 500));
	}

	private static double normalizeIncome(double value) {
		return 1 - ((value - 20) / (100 - 20));
	}

	private static double normalizeAge(double value) {
		return 1 - ((value - 20) / (100 - 20));
	}

	private static double deNormalizeCreditScore(double value) {
		return ((1 - value) * 400) + 500;
	}

	private static double deNormalizeIncome(double value) {
		return ((1 - value) * 80) + 20;
	}

	private static double deNormalizeAge(double value) {
		return ((1 - value) * 70) + 30;
	}
}
