package q1.program;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

//Program tests travelling salesman solver
public class AntTester {
	/********************************************************************/

	// Main method
	public static void main(String[] args) {
		// create adjacency matrix
		int size = 20;
		int seed = 4536;
		int[][] matrix = new int[size][size];
		createMatrix(matrix, size, seed);

		// Read in the adjacency Matrix
		Scanner userInput = new Scanner(System.in);
		System.out.println("Please input the name of the file you would like to read from:");
		String fileNameInput = userInput.nextLine();
		userInput.close();

		// Initialize this to make code not compile with errors.
		int[][] inputMatrix = new int[0][0];
		try {
			inputMatrix = readInMatrix(fileNameInput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// display adjacency matrix
		// TODO: does this need to be length -1/+1?
		displayMatrix(inputMatrix, inputMatrix.length);

		// Displays the randomly generated matrix
		// displayMatrix(matrix, size);

		// create travelling salesman solver
		Ant a = new Ant(inputMatrix, inputMatrix.length);

		// set parameters
		int iterations = 1000000;
		double chemicalExponent = 1.0;
		double distanceExponent = 1.0;
		double initialDeposit = 0.01;
		double depositAmount = 300;
		double decayRate = 0.01;
		a.setParameters(iterations, chemicalExponent, distanceExponent, initialDeposit, depositAmount, decayRate, 7000);

		// find optimal solution
		a.solve();
	}

	/*********************************************************************/

	// method creates a random adjacency matrix
	public static void createMatrix(int[][] matrix, int size, int seed) {
		Random random = new Random(seed);

		// set diagonal to 0
		for (int i = 0; i < size; i++)
			matrix[i][i] = 0;

		// set random weights
		for (int i = 0; i < size; i++)
			for (int j = 0; j < i; j++)
				matrix[i][j] = matrix[j][i] = random.nextInt(20) + 1;
	}

	/**
	 * @throws FileNotFoundException
	 ********************************************************************/

	public static int[][] readInMatrix(String fileName) throws FileNotFoundException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q1\\program\\" + fileName));

		int numberOfCities = inFile.nextInt();
		int numberOfRoads = inFile.nextInt();

		int[][] matrix = new int[numberOfCities][numberOfCities];
		// Fill the matrix with zeroes initially
		// TODO: verify that 0 is what counts as no connectionF
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = 0;
			}
		}

		for (int i = 0; i < numberOfRoads; i++) {
			int x = inFile.nextInt();
			int y = inFile.nextInt();
			int cost = inFile.nextInt();
			matrix[x - 1][y - 1] = cost;
			matrix[y - 1][x - 1] = cost;
		}

		return matrix;
	}

	/**********************************************************************/

	// method prints an adjacency matrix
	public static void displayMatrix(int[][] matrix, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++)
				System.out.print(matrix[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}

	/***********************************************************************/
}