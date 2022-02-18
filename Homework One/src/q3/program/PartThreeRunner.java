package q3.program;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PartThreeRunner {

	public static void main(String[] args) {
		boolean waitingForSize = true;

		Scanner input = new Scanner(System.in);
		int boardSize = -1;
		String userInput = new String();

		while (waitingForSize == true) {
			System.out.println("Please input an integer for the board size.");
			try {
				int in;
				in = input.nextInt();
				boardSize = in;
				if (boardSize > 2 && boardSize < 7) {
					waitingForSize = false;
				} else {
					System.out.println("Please input a board size between 2 and 7");
					boardSize = -1;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please input an integer.");
				input.nextLine();
			}
		}

//		TicTac ticTac = new TicTac(boardSize);
//		ticTac.play();
		AlphaBeta evaluate = new AlphaBeta(boardSize);
//		evaluate.testPointEvaluation();
//		boolean testingCheckFull;
//		testingCheckFull = evaluate.testCheckFull();
//		if (testingCheckFull) {
//			System.out.println("Board returned true, was found to be full");
//		} else {
//			System.out.println("Board returned false, was found to not be full");
//
//		}
//		boolean testingCheckFull2;
//
//		testingCheckFull2 = evaluate.testCheckFull2();
//		if (testingCheckFull2) {
//			System.out.println("Board returned true, was found to be full");
//		} else {
//			System.out.println("Board returned false, was found to not be full");
//
//		}
		// evaluate.play();

		// TODO: uncomment below when done testing evaluation
		evaluate.play();
	}

}