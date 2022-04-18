package q1.program;

import java.util.Random;

//Program tests travelling salesman solver
public class AntTester
{
    /********************************************************************/

    //Main method
    public static void main(String[] args)
    {
         //create adjacency matrix
         int size = 20;
         int seed = 4536;
         int[][] matrix = new int[size][size];
         createMatrix(matrix, size, seed);

         //display adjacency matrix
         displayMatrix(matrix, size);

         //create travelling salesman solver
         Ant a = new Ant(matrix, size);

         //set parameters
         int iterations = 10;
         double chemicalExponent = 1.0;
         double distanceExponent = 1.0;
         double initialDeposit = 0.01;
         double depositAmount = 100;
         double decayRate = 0.5;
         a.setParameters(iterations, chemicalExponent, distanceExponent, 
         initialDeposit, depositAmount, decayRate, 4329);

         //find optimal solution
         a.solve();
    }

    /*********************************************************************/

    //method creates a random adjacency matrix
    public static void createMatrix(int[][] matrix, int size, int seed)
    {
         Random random = new Random(seed);

         //set diagonal to 0
         for (int i = 0; i < size; i++)
             matrix[i][i] = 0;

         //set random weights
         for (int i = 0; i < size; i++)
             for (int j = 0; j < i; j++)
                 matrix[i][j] = matrix[j][i] = random.nextInt(20) + 1;
    }

    /**********************************************************************/

    //method prints an adjacency matrix
    public static void displayMatrix(int[][] matrix, int size)
    {
         for (int i = 0; i < size; i++)
         {
             for (int j = 0; j < size; j++)
                 System.out.print(matrix[i][j] + " ");
             System.out.println();
         }
    }

    /***********************************************************************/
}