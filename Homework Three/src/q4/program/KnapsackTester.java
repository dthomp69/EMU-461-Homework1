package q4.program;

/*****************************************************************************/

//Program tests knapsack solver
public class KnapsackTester
{
    //main method
    public static void main(String[] args)
    {
         //knapsack data
         int numberItems = 5;
         int maximumWeight = 40;
         int[][] table = {{15, 600},
                          {12, 200},
                          {10, 300},
                          {14, 500},
                          {18, 700}};

         //create knapsack solver
         Knapsack k = new Knapsack(table, numberItems, maximumWeight);

         //set parameters of genetic algorithm
         k.setParameters(50, 5, 1000, 0.5, 0.1, 4329);

         //find optimal solution
         k.solve();
    }
}

/*****************************************************************************/