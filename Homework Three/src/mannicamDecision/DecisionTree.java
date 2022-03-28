package mannicamDecision;

import java.io.*;
import java.util.*;

//Decision tree
public class DecisionTree
{
    /*************************************************************************/

    //Record class
    private class Record 
    {
        private int[] attributes;            //attributes of record
        private int className;               //class of record

        //constructor of Record
        private Record(int[] attributes, int className)
        {
            this.attributes = attributes;    //set attributes
            this.className = className;      //set class
        }
    }
    
    /*************************************************************************/

    //Tree node class
    private class Node
    {
        private String nodeType;    //node type - internal or leaf
        private int condition;      //condition if node is internal
        private int className;      //class name if node is leaf
        private Node left;          //left branch
        private Node right;         //right branch

        //Constructor of Node
        private Node(String nodeType, int value, Node left, Node right)
        {
            this.nodeType = nodeType;         //set node type
            this.left = left;                 //set left branch
            this.right = right;               //set right branch

            if (nodeType.equals("internal"))  
            {                                 //if node is internal
                condition = value;            //set condition to value
                className = -1;               //node has no class name
            }
            else                              
            {                                 //if node is leaf
                className = value;            //set class to value
                condition = -1;               //node has no condition
            }
        }
    }

    /*************************************************************************/

    private int numberRecords;                //number of training records
    private int numberAttributes;             //number of attributes
    private int numberClasses;                //number of classes
    private ArrayList<Record> records;        //list of training records
    private ArrayList<Integer> attributes;    //list if attributes
    private Node root;                        //root of decision tree

    /*************************************************************************/

    //Constructor of decision tree
    public DecisionTree()
    {
        //initial data is empty
        numberRecords = 0;  
        numberAttributes = 0; 
        numberClasses = 0;
        records = null; 
        attributes = null;                      
        root = null;                                 
    }

    /*************************************************************************/

    //Mehod loads data from training file
    public void loadTrainingData(String trainingFile) 
    throws IOException
    {
         Scanner inFile = new Scanner(new File(trainingFile));

         //read number of records, attributes, classes
         numberRecords = inFile.nextInt();    
         numberAttributes = inFile.nextInt();    
         numberClasses = inFile.nextInt();    

         //create empty list of records
         records = new ArrayList<Record>();        

         //for each record
         for (int i = 0; i < numberRecords; i++)    
         {
             //create attribute array
             int[] attributeArray = new int[numberAttributes]; 
                                     
             //read attributes                
             for (int j = 0; j < numberAttributes; j++) 
                  attributeArray[j] = inFile.nextInt();  
 
             //read class name
             int className = inFile.nextInt();

             //create record
             Record record = new Record(attributeArray, className);

             //add record to list
             records.add(record);
         }

         //ceate list of attributes
         attributes = new ArrayList<Integer>();
         for (int i = 0; i < numberAttributes; i++)
             attributes.add(i+1);

         inFile.close();
    }

    /*************************************************************************/

    //Method builds decision tree from training data
    public void buildTree()
    {
         root = build(records, attributes);   //initial call to build method
    }                                         

    /*************************************************************************/

    //Method builds decision tree from given records and attributes, returns
    //root of tree that is built
   private Node build(ArrayList<Record> records, ArrayList<Integer> attributes)
    {
        //root node is empty initially
        Node node = null;

        //if all records have same class
        if (sameClass(records))                   
        {
            //find class name
            int className = records.get(0).className;      

            //node is leaf with that class
            node = new Node("leaf", className, null, null);
        }
        //if there are no attributes
        else if (attributes.isEmpty())
        {
            //find majority class of records
            int className = majorityClass(records);

            //node is leaf with that class
            node = new Node("leaf", className, null, null);
        }
        else
        {
            //find best condition for current records and attributes
            int condition = bestCondition(records, attributes);

            //collect all records which have 0 for condition
            ArrayList<Record> leftRecords = collect(records, condition, 0);

            //collect all records which have 1 for condition
            ArrayList<Record> rightRecords = collect(records, condition, 1);

            //if either left records or right records is empty
            if (leftRecords.isEmpty() || rightRecords.isEmpty())
            {
                //find majority class of records
                int className = majorityClass(records);
                //node is leaf with that class
                node = new Node("leaf", className, null, null);
            }
            else
            {
                //create copies of current attributes
                ArrayList<Integer> leftAttributes = copyAttributes(attributes);
               ArrayList<Integer> rightAttributes = copyAttributes(attributes);

                //remove best condition from current attributes
                leftAttributes.remove(new Integer(condition));
                rightAttributes.remove(new Integer(condition));

                //create internal node with best condition
                node = new Node("internal", condition, null, null);

                //create left subtree recursively
                node.left = build(leftRecords, leftAttributes);

                //create right subtree recursively
                node.right = build(rightRecords, rightAttributes);
            }
        }

        //return root node of tree that is built
        return node;
    }

    /*************************************************************************/

    //Method decides whether all records have the same class
    private boolean sameClass(ArrayList<Record> records)
    {
        //compare class of each record with class of first record
        for (int i = 0; i < records.size(); i++)
            if (records.get(i).className != records.get(0).className)
               return false; 
                              
        return true;
    }

    /*************************************************************************/

    //Method finds the majority class of records
    private int majorityClass(ArrayList<Record> records)
    {
        int[] frequency = new int[numberClasses]; //frequency array

        for (int i = 0; i < numberClasses; i++)   //initialize frequencies
             frequency[i] = 0;

        for (int i = 0; i < records.size(); i++)  //find frequencies of classes
             frequency[records.get(i).className - 1] += 1;

        int maxIndex = 0;                         //find class with maximimum
        for (int i = 0; i < numberClasses; i++)   //frequency
            if (frequency[i] > frequency[maxIndex])
               maxIndex = i;

        return maxIndex + 1;                      //return majority class
    }

    /*************************************************************************/

    //Method collects records that have a given value for a given attribute
    private ArrayList<Record> collect(ArrayList<Record> records, int condition, 
    int value)
    {
        //initialize collection
        ArrayList<Record> result = new ArrayList<Record>();
        
        //go thru records and collect those that have given value
        //for given attribute
        for (int i = 0; i < records.size(); i++)
            if (records.get(i).attributes[condition-1] == value)
               result.add(records.get(i));

        //return collection
        return result;
    }

    /*************************************************************************/

    //Method makes copy of list of attributes
    private ArrayList<Integer> copyAttributes(ArrayList<Integer> attributes)
    {
        //initialize copy list
        ArrayList<Integer> result = new ArrayList<Integer>();

        //insert all attributes into copy list 
        for (int i = 0; i < attributes.size(); i++)
            result.add(attributes.get(i));

        //return copy list
        return result;
    }

    /*************************************************************************/

    //Method finds best condition for given records and attributes
    private int bestCondition(ArrayList<Record> records, ArrayList<Integer> 
    attributes)
    {
        //evaluate first attribute
        double minValue = evaluate(records, attributes.get(0));
        int minIndex = 0;

        //go thru all attributes
        for (int i = 0; i < attributes.size(); i++)
        {
            double value = evaluate(records, attributes.get(i));
                                           //evaluate attribute
            if (value < minValue) 
            {                              //if value is less than
                minValue = value;          //current minimum then
                minIndex = i;              //update minimum
            }    
        }

        return attributes.get(minIndex);   //return best attribute
    }

    /*************************************************************************/

    //Method evaluates an attribute using weighted average entropy
    private double evaluate(ArrayList<Record> records, int attribute)
    {
         //collect records that have attribute value 0
         ArrayList<Record> leftRecords = collect(records, attribute, 0);

         //collect records that have attribute value 1
         ArrayList<Record> rightRecords = collect(records, attribute, 1);

         //find entropy of left records
         double entropyLeft = entropy(leftRecords);

         //find entropy of right records
         double entropyRight = entropy(rightRecords);

         //find weighted average entropy
         double average = entropyLeft*leftRecords.size()/records.size() +
                          entropyRight*rightRecords.size()/records.size();

         //return weighted average entropy
         return average;
    }

    /*************************************************************************/

    //Method finds entropy of records using gini measure
    private double entropy(ArrayList<Record> records)
    {
         double[] frequency = new double[numberClasses];    //frequency array

         for (int i = 0; i < numberClasses; i++)     //initialize frequencies
             frequency[i] = 0;

         for (int i = 0; i < records.size(); i++)    //find class frequencies
             frequency[records.get(i).className - 1] += 1;

         double sum = 0;                             //find sum of frequencies
         for (int i = 0; i < numberClasses; i++)
             sum = sum + frequency[i];

         for (int i = 0; i < numberClasses; i++)     //normalize frequencies
             frequency[i] = frequency[i]/sum;

         sum = 0;
         for (int i = 0; i < numberClasses; i++)     //find sum of squares
             sum = sum + frequency[i]*frequency[i];

         return 1 - sum;                             //gini measure
    }

    /*************************************************************************/

    //Method finds class of given attributes
    private int classify(int[] attributes)
    {
        //start at root node
        Node current = root;

        //go down the tree
        while (current.nodeType.equals("internal"))     
        {                                                //if attribute value
             if (attributes[current.condition - 1] == 0) //of condition is 0
                 current = current.left;                 //go to left
             else
                 current = current.right;                //else go to right
        }

        return current.className;                        //return class name
    }                                                    //when reaching leaf

    /*************************************************************************/

    //Method reads test records from test file and writes classified records
    //to classified file
    public void classifyData(String testFile, String classifiedFile) 
    throws IOException
    {
         Scanner inFile = new Scanner(new File(testFile));
         PrintWriter outFile = new PrintWriter(new FileWriter(classifiedFile));

         //read number of records
         int numberRecords = inFile.nextInt();

         //write number of records
         outFile.println(numberRecords);

         //for each record
         for (int i = 0; i < numberRecords; i++)
         {
             //create attribute array
             int[] attributeArray = new int[numberAttributes];

             //read attributes
             for (int j = 0; j < numberAttributes; j++)
                  attributeArray[j] = inFile.nextInt();

             //find class of attributes
             int className = classify(attributeArray);

             //write class name
             outFile.println(className);
         }

         inFile.close();
         outFile.close();
    }

    /*************************************************************************/

    //Method validates classifier using validation file and displays
    //error rate
    public void validate(String validationFile) throws IOException
    {
         Scanner inFile = new Scanner(new File(validationFile));

         //read number of records
         int numberRecords = inFile.nextInt();

         //initially zero errors
         int numberErrors = 0;

         //for each record
         for (int i = 0; i < numberRecords; i++)
         {
             //create attribute array
             int[] attributeArray = new int[numberAttributes];

             //read attributes
             for (int j = 0; j < numberAttributes; j++)
                  attributeArray[j] = inFile.nextInt();

             //read actual class
             int actualClass = inFile.nextInt();

             //find class predicted by classifier
             int predictedClass = classify(attributeArray);

             //error if predicted and actual classes do not match
             if (predictedClass != actualClass)
                numberErrors += 1;
         }

         //find and print error rate 
         double errorRate = 100.0*numberErrors/numberRecords;
         System.out.println("validation error: " + errorRate);

         inFile.close();
    }

    /*************************************************************************/
}