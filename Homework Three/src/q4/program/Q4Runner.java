package q4.program;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*****************************************************************************/

//Program tests knapsack solver
public class Q4Runner {

	private static int numberItems;
	private static int maximumWeight;

	private static int[][] table;

	// main method
	public static void main(String[] args) {
		// My code for q4:

		Scanner scanner = new Scanner(System.in);
		System.out.println("Please input the name of the file:");
		String fileName = scanner.nextLine();

		// Close the scanner!
		scanner.close();

		try {
			buildParametersFromInputFile(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find the file " + fileName);
		}

		// create knapsack solver
		Knapsack k = new Knapsack(table, numberItems, maximumWeight);

		// set parameters of genetic algorithm
		// String length is just the number of items for this problem

		/*
		 * inputs are: int populationSize, int stringLength, int numberIterations,
		 * double crossoverRate, double mutationRate, int seed
		 */
		k.setParameters(600, numberItems, 1000, 0.2, 0.7, 3000);

		// find optimal solution
		k.solve();

	}

	private static void buildParametersFromInputFile(String fileName) throws FileNotFoundException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q4\\program\\" + fileName));

		numberItems = inFile.nextInt();
		maximumWeight = inFile.nextInt();

		// We know it really just needs 2 things in the table, the cost and the value
		table = new int[numberItems][2];

		for (int i = 0; i < numberItems; i++) {
			// Clear the "record number"
			inFile.nextInt();

			// Grab the cost
			table[i][0] = inFile.nextInt();
			// Grab the value
			table[i][1] = inFile.nextInt();
		}

		// Close the scanner!
		inFile.close();
	}
}

/*****************************************************************************/