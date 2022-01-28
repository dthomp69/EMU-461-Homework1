package mannicamFiles;

//Tester program for shortest path finder with A* search
public class ShortestAstarTester
{
    //Main method for testing
    public static void main(String[] args)
    {   
        //edges of specific graph, 10 vertices 0, 1, 2 . . . 9
        //11 edges (3 6), (2 0) . . .
        //10 vertex locations (85 10), (30 40) . . .

        int[][] edges = {{3, 6}, 
                         {2, 0},
                         {4, 9},
                         {2, 9},
                         {4, 5},
                         {2, 3},
                         {1, 7},
                         {7, 8},
                         {0, 1},
                         {2, 6},
                         {2, 1}};

        double[][] locations = {{85, 10},
                                {30, 40},
                                {25, 60},
                                {50, 30},
                                {35, 65},
                                {15, 95},
                                {75, 35},
                                {40, 75},
                                {55, 20},
                                {80, 90}};

        //find shortest path from vertex 7 to 3
        ShortestAstar s = new ShortestAstar(10, edges, locations, 7, 3);
        s.solve();
    }
}