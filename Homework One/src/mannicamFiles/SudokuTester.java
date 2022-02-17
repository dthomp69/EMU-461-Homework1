package mannicamFiles;

//Tester program for sudoku solver
public class SudokuTester
{
    //Main method for testing
    public static void main(String[] args)
    {   
        //specifc sudoku puzzle
        int[][] board = {{0, 0, 0, 0, 0, 0, 9, 0, 0},
                         {0, 0, 5, 9, 0, 3, 0, 4, 0},
                         {9, 7, 0, 5, 0, 0, 6, 0, 0},
                         {0, 0, 0, 0, 0, 2, 0, 6, 8},
                         {1, 8, 0, 0, 0, 0, 0, 2, 9},
                         {5, 2, 0, 8, 0, 0, 0, 0, 0},
                         {0, 0, 2, 0, 0, 1, 0, 3, 6},
                         {0, 1, 0, 7, 0, 9, 4, 0, 0},
                         {0, 0, 4, 0, 0, 0, 0, 0, 0}};

        //solve sudoku puzzle
        Sudoku s = new Sudoku(board);
        s.solve();
    }
}