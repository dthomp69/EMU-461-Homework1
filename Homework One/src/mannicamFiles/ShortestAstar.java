package mannicamFiles;

import java.util.LinkedList;

//This program solves shortest path finding problem in graph. 
//It uses A* search.
public class ShortestAstar
{
    //Node class (inner class)
    private class Node
    {
        private int id;            //vertex id
        private double gvalue;     //path cost
        private double hvalue;     //heuristic value
        private double fvalue;     //gvalue plus hvalue
        private Node parent;       //parent of vertex

        //Constructor of Node class
        private Node(int id)
        {
            this.id = id;          //set vertex id
            this.gvalue = 0;       //path cost, heuristic value
            this.hvalue = 0;       //fvalue are all 0
            this.fvalue = 0; 
            this.parent = null;    //no parent
        }
    }

    private int[][] matrix;        //adjacency matrix of graph
    private double[][] locations;  //locations of vertices
    private int size;              //number of vertices
    private Node initial;          //initial node
    private Node goal;             //goal node

    //Constructor of ShortestAstar class
    public ShortestAstar(int vertices, int[][] edges, double[][] locations, int initial, int goal)
    {
        size = vertices;                         //set number of vertices

        matrix = new int[size][size];            //initialize adjacency matrix
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = 0;

        for (int i = 0; i < edges.length; i++)   //place 1's in adjacency
        {                                        //matrix using edges
            int u = edges[i][0];
            int v = edges[i][1];
            matrix[u][v] = matrix[v][u] = 1;
        }

        this.locations = new double[size][2];    //set vertex locations
        for (int i = 0; i < size; i++)
           for (int j = 0; j < 2; j++)
               this.locations[i][j] = locations[i][j];

        this.initial = new Node(initial);       //create initial node
        this.goal = new Node(goal);             //create goal node
    }

    //Method finds shortest path
    public void solve()
    {
         LinkedList<Node> openList = new LinkedList<Node>();  //open list
         LinkedList<Node> closedList = new LinkedList<Node>();//closed list

         openList.addFirst(initial);   //add initial node to open list     

         while (!openList.isEmpty())   //while open list has more nodes
         {
             int best = selectBest(openList);       //select best node

             Node node = openList.remove(best);     //remove node
                                                    
             closedList.addLast(node);              //add node to closed list

             if (goal(node))                        //if node is goal
             {
                 displayPath(node);                 //display path to goal
                 return;                            //stop search
             }
             else                                   //if node is not goal
             {
                 LinkedList<Node> children = generate(node);
                                                       //create children
                 for (int i = 0; i < children.size(); i++)
                 {                                     //for each child
                     Node child = children.get(i);    
                                                       //if child is not in closed list
                     if (!exists(child, closedList))   
                     {
                          if (!exists(child, openList))//if child is not in open list
                              openList.addLast(child); //add to open list
                          else                         
                          {                            //if child is already in open list
                              int index = find(child, openList);
                              if (child.fvalue < openList.get(index).fvalue)
                              {                            //if fvalue of new copy
                                  openList.remove(index);  //is less than old copy
                                  openList.addLast(child); //replace old copy
                              }                            //with new copy
                          }                               
                     }     
                 }                                  
             }                                       
         }

         System.out.println("no solution");          //no solution if there are
    }                                                //no nodes in open list
    
    //Method creates children of node
    private LinkedList<Node> generate(Node node)
    {
        LinkedList<Node> children = new LinkedList<Node>();  //list of children

        for (int i = 0; i < size; i++)         //go thru adjacency matrix
            if (matrix[node.id][i] != 0)       //and determine neighbors
            {
                Node child = new Node(i);      //create node for child

                                               //parent path cost plus distance
                child.gvalue = node.gvalue + distance(node.id, child.id);
                                               //heuristic value of child
                child.hvalue = heuristic(child);
                                               //gvalue plus hvalue
                child.fvalue = child.gvalue + child.hvalue;

                child.parent = node;           //assign parent to child

                children.addLast(child);       //add to children list
            }

        return children;                       //return children list
    }

    //Method computes heuristic value of node
    //Heuristic value is straight line distance between node and goal
    private double heuristic(Node node)
    {
        return distance(node.id, goal.id);
    }

    //Method computes the straight line distance between two vertices
    private double distance(int u, int v)
    {
        double x1 = locations[u][0];     //coordinates of vertex one
        double y1 = locations[u][1];

        double x2 = locations[v][0];     //coordinates of vertex two
        double y2 = locations[v][1];
                                         //distance between two vertices
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    //Method locates the node with minimum fvalue in a list of nodes
    private int selectBest(LinkedList<Node> list)
    {
        double minValue = list.get(0).fvalue;        //initialize minimum
        int minIndex = 0;                            //value and location

        for (int i = 0; i < list.size(); i++)
        {
            double value = list.get(i).fvalue;
            if (value < minValue)                    //updates minimums if node
            {                                        //with smaller fvalue is found
                minValue = value;                   
                minIndex  = i;
            } 
        }

        return minIndex;                             //return minimum location
    }   

    //Method decides whether a node is goal
    private boolean goal(Node node)
    {
        return identical(node, goal);          //compare node with goal
    }

    //Method decides whether a node is in a list
    private boolean exists(Node node, LinkedList<Node> list)
    {
        for (int i = 0; i < list.size(); i++)  //compare node with each
            if (identical(node, list.get(i)))  //element of list
               return true;

        return false;
    }

    //Method finds location of a node in a list
    private int find(Node node, LinkedList<Node> list)
    {
        for (int i = 0; i < list.size(); i++)    //compare node with each
            if (identical(node, list.get(i)))    //element of list
               return i;

        return -1;
    }

    //Method decides whether two nodes are identical
    private boolean identical(Node p, Node q)
    {
        return p.id == q.id;           //compare vertex id of nodes
    }

    //Method displays path from initial to current node
    private void displayPath(Node node)
    {
        LinkedList<Node> list = new LinkedList<Node>();

        Node pointer = node;           //start at current node

        while (pointer != null)        //go back towards initial node
        {
            list.addFirst(pointer);    //add nodes to list

            pointer = pointer.parent;  //keep going back
        }
                                       //print nodes in list
        for (int i = 0; i < list.size(); i++)  
            displayNode(list.get(i));
                                       //print path cost
        System.out.println(": " + list.getLast().gvalue);
    }

    //Method displays node
    private void displayNode(Node node)
    {
        System.out.print(node.id + " "); //print vertex id of node
    }
}