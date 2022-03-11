package q2.program;

import java.io.*;
import java.util.*;

//Nearest neighbor classifier
public class NearestNeighbor {
	/*************************************************************************/

	// Record class (inner class)
	private class Record {
		private double[] attributes; // attributes of record
		private int className; // class of record

		// Constructor of Record
		private Record(double[] attributes, int className) {
			this.attributes = attributes; // set attributes
			this.className = className; // set class
		}
	}

	/*************************************************************************/

	private int numberRecords; // number of training records
	private int numberAttributes; // number of attributes
	private int numberClasses; // number of classes
	private int numberNeighbors; // number of nearest neighbors
	private ArrayList<Record> records; // list of training records

	private double ErrorPercent;

	/*************************************************************************/

	// Constructor of NearestNeighbor
	public NearestNeighbor() {
		// initial data is empty
		numberRecords = 0;
		numberAttributes = 0;
		numberClasses = 0;
		numberNeighbors = 0;
		records = null;

		ErrorPercent = 0.0;
	}

	/*************************************************************************/

	// Method loads data from training file
	public void loadTrainingData(String trainingFile) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q2\\program\\" + trainingFile));

		// read number of records, attributes, classes
		numberRecords = inFile.nextInt();
		numberAttributes = inFile.nextInt();
		numberClasses = inFile.nextInt();

		// create empty list of records
		records = new ArrayList<Record>();

		// for each record
		for (int i = 0; i < numberRecords; i++) {
			// create attribute array
			double[] attributeArray = new double[numberAttributes];

			// read attribute values
			for (int j = 0; j < numberAttributes; j++) {
				// Changing scale from [0,1] to [1,2], to see if that fixes an error I'm having
//				int rawAttribute = inFile.nextInt();
//				if (rawAttribute == 0) {
//					attributeArray[j] = 1;
//				}
//				if (rawAttribute == 1) {
//					attributeArray[j] = 2;
//				}

				// Didn't seem to work, keeping normal
				attributeArray[j] = inFile.nextInt();
			}

			// read class name
			int className = inFile.nextInt();

			// create record
			Record record = new Record(attributeArray, className);

//			if (className == 1) {
//				Record record = new Record(attributeArray, 2);
//				records.add(record);
//			} else {
//				Record record = new Record(attributeArray, 1);
//				records.add(record);
//			}

			// add record to list of records
			records.add(record);

		}

//		System.out.println("Data initialized in loadTrainingData");
//		System.out.println("Number of records:" + numberRecords);
//		System.out.println("Number of attributes:" + numberAttributes);
//		System.out.println("Number of classes:" + numberClasses);
//
//		System.out.println("Real number of records:" + records.size());

		inFile.close();
	}

	/*************************************************************************/

	// Method sets number of nearest neighbors
	public void setParameters(int numberNeighbors) {
		this.numberNeighbors = numberNeighbors;
	}

	/*************************************************************************/

	// Method reads records from test file, determines their classes,
	// and writes classes to classified file
	public void classifyData(String testFile, String classifiedFile) throws IOException {
		Scanner inFile = new Scanner(new File(System.getProperty("user.dir") + "\\src\\q2\\program\\" + testFile));
		PrintWriter outFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "\\src\\q2\\output\\" + classifiedFile));

		// read number of records
		int numberRecords = inFile.nextInt();

		// write number of records
		outFile.println(numberRecords);

		// for each record
		for (int i = 0; i < numberRecords; i++) {
			// create attribute array
			double[] attributeArray = new double[numberAttributes];

			// read attribute values
			for (int j = 0; j < numberAttributes; j++)
				attributeArray[j] = inFile.nextDouble();

			// find class of attributes
			int className = classify(attributeArray);

			// Write other attributes
			for (int j = 0; j < attributeArray.length; j++) {
//				if (j == 0) {
//					outFile.print((int) attributeArray[j] + " ");
//				} else {
//					if (j % 16 == 0) {
//						outFile.println((int) attributeArray[j] + " ");
//					} else {
//						outFile.print((int) attributeArray[j] + " ");
//					}
//				}
				outFile.print(attributeArray[j] + " ");
			}

			// write class name
			outFile.println(className);
		}

		outFile.println("Validation error: " + this.ErrorPercent + "%");

		inFile.close();
		outFile.close();
	}

	/*************************************************************************/

	// Method determines the class of a set of attributes
	private int classify(double[] attributes) {
		double[] distance = new double[numberRecords];
		int[] id = new int[numberRecords];

		// find distances between attributes and all records
		for (int i = 0; i < numberRecords; i++) {
			distance[i] = distance(attributes, records.get(i).attributes);
			id[i] = i;
		}

		// find nearest neighbors
		nearestNeighbor(distance, id);

		// find majority class of nearest neighbors
		int className = majority(id);

		// return class
		return className;
	}

	/*************************************************************************/

	// Method finds the nearest neighbors
	private void nearestNeighbor(double[] distance, int[] id) {
		// sort distances and choose nearest neighbors
		for (int i = 0; i < numberNeighbors; i++)
			for (int j = i; j < numberRecords; j++)
				if (distance[i] > distance[j]) {
					double tempDistance = distance[i];
					distance[i] = distance[j];
					distance[j] = tempDistance;

					int tempId = id[i];
					id[i] = id[j];
					id[j] = tempId;
				}
	}

	/*************************************************************************/

	// Method finds the majority class of nearest neighbors
	private int majority(int[] id) {
		double[] frequency = new double[numberClasses];

		// class frequencies are zero initially
		for (int i = 0; i < numberClasses; i++)
			frequency[i] = 0;

		// each neighbor contributes 1 to its class
		for (int i = 0; i < numberNeighbors; i++)
			frequency[records.get(id[i]).className - 1] += 1;

		// find majority class
		int maxIndex = 0;
		for (int i = 0; i < numberClasses; i++)
			if (frequency[i] > frequency[maxIndex])
				maxIndex = i;

		return maxIndex + 1;
	}

	/*************************************************************************/

	// Method finds Euclidean distance between two points
	private double distance(double[] u, double[] v) {
		double distance = 0;

		for (int i = 0; i < u.length; i++)
			distance = distance + (u[i] - v[i]) * (u[i] - v[i]);

		distance = Math.sqrt(distance);

		return distance;
	}

	/*************************************************************************/

	// Method validates classifier using validation file and displays error rate
//	public void validate(String validationFile) throws IOException {
//		Scanner inFile = new Scanner(
//				new File(System.getProperty("user.dir") + "\\src\\q1\\program\\" + validationFile));
//
//		// read number of records
//		int numberRecords = inFile.nextInt();
//
//		// initially zero errors
//		int numberErrors = 0;
//
//		// for each record
//		for (int i = 0; i < numberRecords; i++) {
//			double[] attributeArray = new double[numberAttributes];
//
//			// read attributes
//			for (int j = 0; j < numberAttributes; j++)
//				attributeArray[j] = inFile.nextDouble();
//
//			// read actual class
//			int actualClass = inFile.nextInt();
//
//			// find class predicted by classifier
//			int predictedClass = classify(attributeArray);
//
//			// errror if predicted and actual classes do not match
//			if (predictedClass != actualClass)
//				numberErrors += 1;
//		}
//
//		// find and print error rate
//		double errorRate = 100.0 * numberErrors / numberRecords;
//		System.out.println("validation error: " + errorRate + "%");
//
//		inFile.close();
//	}

	// Chang(ed/ing) to implement leave one out
	public void validate(String validationFile) throws IOException {
		// initially zero errors
		int numberErrors = 0;

		for (int i = 0; i < this.records.size(); i++) {
			int originalNumberRecords = this.numberRecords;
			ArrayList<Record> original = (ArrayList<Record>) this.records.clone();

			ArrayList<Record> leaveOneOut = (ArrayList<Record>) original.clone();
			Record takenOut = leaveOneOut.remove(i);

			// System.out.println("Number of records pre minus:" + this.numberRecords);
			this.numberRecords--;
			// System.out.println("Number of records after minus:" + this.numberRecords);

			this.records = leaveOneOut;
			int predictedClass = classify(takenOut.attributes);

			if (predictedClass != takenOut.className) {
				numberErrors++;
			}

			// Reset the arrayList of records and the number of records back to their
			// original values
			this.records = original;
			this.numberRecords = originalNumberRecords;
		}

		// find and print error rate
		double errorRate = 100.0 * numberErrors / numberRecords;
		this.ErrorPercent = errorRate;
		System.out.println("validation error: " + errorRate + "%");
	}

	/************************************************************************/
}