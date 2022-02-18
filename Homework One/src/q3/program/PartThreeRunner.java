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

		AlphaBeta evaluate = new AlphaBeta(boardSize);

		evaluate.play();
	}

}
