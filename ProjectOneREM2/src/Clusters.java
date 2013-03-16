import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.Math;

public class Clusters {

	private ArrayList<Point> arrayList; //Object to hold the list of points

	public Clusters() { //Constructor for the Clusters class
		arrayList = new ArrayList<Point>();
	}
	
	public void addToArray(Double newPointXValue, Double newPointYValue) { //Method to add a point to the array by passing the array its x and y coordinates as doubles
		Point point = new Point (newPointXValue, newPointYValue);
		arrayList.add(point);
	}
	
	public void addToArray(Point p) { //Method to add a point to the array by passing the array a point
		arrayList.add(p);
	}
	
	public Integer getSize() { //Method to get the size of the array
		return arrayList.size();
	}	
	
	public ArrayList<Point> getArray() { //Method to get the array
		return arrayList;
	}
	
	public void readInPointsFromFile(String fileName) { //Method to read in the list of points from the file specified in the line arguments
		try {
			FileInputStream fileInputStream = new FileInputStream(fileName);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
			String dataInput; //String for reading in from the file
			Double doublePointX = 0.0; //Double for holding the x coordinates
			Double doublePointY = 0.0; //Double for holding the y coordinates
	
			while ((dataInput = bufferedReader.readLine()) != null) {
				StringTokenizer stringTokenizer = new StringTokenizer(dataInput, ","); //String tokenizer to break up the string dataInput into indvidual doubles.  It delineates using a comma
				while (stringTokenizer.hasMoreTokens()) {
					doublePointX = Double.parseDouble(stringTokenizer.nextToken());
					doublePointY = Double.parseDouble(stringTokenizer.nextToken());
					Point pointToAdd = new Point(doublePointX, doublePointY);
					arrayList.add(pointToAdd);
				}
			}
			dataInputStream.close();
		}
		catch (Exception e){
			System.err.println("Error: " + e.getMessage());	
		}	
	}
	
	public Double getDistance(Integer indexOfOriginalClusterList, Integer indexOfCentroidList, ArrayList<Point> centroidList) { //Method for finding the distance between two points.  
		final Double TWO = 2.0; // 2 for squaring																				//This method is passed the index of the point from arrayList,
		Double x1 = arrayList.get(indexOfOriginalClusterList).getXCoord();														//the index of the point from a second ArrayList<Point>, and is
		Double y1 = arrayList.get(indexOfOriginalClusterList).getYCoord();														//passed the ArrayList<Point> that the second point is in.
		Double x2 = centroidList.get(indexOfCentroidList).getXCoord();															//Returns the distance between the points as a double value.
		Double y2 = centroidList.get(indexOfCentroidList).getYCoord();
		Double differenceInXCoordinates = x1 - x2;
		Double differenceInYCoordinates = y1 - y2;
		Double distance = Math.sqrt(Math.pow(differenceInXCoordinates, TWO) + Math.pow(differenceInYCoordinates, TWO)); //Formula for the Euclidean distance
		return distance;
	}
	
	public ArrayList<Point> getNewCentroids(ArrayList<Integer> indexArrayList, Integer numberOfClustersToMake) { //Method for computing new centroids once the first ones are created randomly in the makeClusters() method.
		ArrayList<Point> returningNewArrayForCentroids = new ArrayList<Point>();//arrayList<Point> returned		 //Takes as parameters an ArrayList<Integer> that holds the indices of the arrayList that are closest to the centroids,
		Integer counterForMethod = 0;																			 //and the number of clusters to make. Returns an ArrayList<Point> that holds the new centroids to update 
		Integer counterForMethod2 = 0;																		   	 //arrayForCentroids from the makeClusters() method
		Integer sizeOfOriginalClusterList = arrayList.size();
		while (counterForMethod < numberOfClustersToMake) {
			Double totalOfXCoords = 0.0;
			Double totalOfYCoords = 0.0;
			Integer intToDivideBy = 0;
			counterForMethod2 = 0;
			while (counterForMethod2 < sizeOfOriginalClusterList) { //Goes through the list of points and the corresponding list of indices that tell to which centroid the points belong, adding up the x and y values, then dividing
					if (counterForMethod == indexArrayList.get(counterForMethod2)) { //by the number of points belonging to that particular centroid
						totalOfXCoords = totalOfXCoords + arrayList.get(counterForMethod2).getXCoord();
						totalOfYCoords = totalOfYCoords + arrayList.get(counterForMethod2).getYCoord();
						intToDivideBy = intToDivideBy + 1;
					}
					counterForMethod2 = counterForMethod2 + 1;
			}
			Point pointToAdd = new Point ((totalOfXCoords/intToDivideBy), (totalOfYCoords/intToDivideBy)); //Creates the new point from the averages of the x and y coordinates
			returningNewArrayForCentroids.add(pointToAdd);
			counterForMethod = counterForMethod + 1;
		}	
		return returningNewArrayForCentroids; // return returningNewArrayForCentroids that holds the new clusters' centroids
	}
	
	public ArrayList<Integer> makeClusters(Integer numberOfClustersToMake, Integer numberOfIterationsToComplete) { //Method that takes the inputed points and creates clusters from them. Parameters are the number of clusters to make as an int, and
		Integer counterForMethod = 0;																 //number of iterations to complete as an int.  Its output is the points of each cluster. Returns the list of finalIndices for testing purposes only
		Integer sizeOfOriginalClusterList = arrayList.size();
		ArrayList<Point> arrayForCentroids = new ArrayList<Point>(); // ArrayList<Point> for holding the centroids of each cluster
		
		Random randomObjectToPickFirstCentroids = new Random();//Making Random integers
		Integer checkingRandomnessInteger = -1;
		ArrayList<Integer> randomIntegersForPickingCentroids = new ArrayList<Integer>(); // Randomly picking numberOfClustersToMake numbers and putting them in an ArrayList of Integers.
		while (counterForMethod < numberOfClustersToMake) { 							 // The while loop in the else condition makes sure that the newest addition to the 
			if (counterForMethod == 0) {												 // ArrayList does not already exist in the ArrayList.
				randomIntegersForPickingCentroids.add(randomObjectToPickFirstCentroids.nextInt(sizeOfOriginalClusterList));
			}
			else {
				checkingRandomnessInteger = randomObjectToPickFirstCentroids.nextInt(sizeOfOriginalClusterList);	
				while (randomIntegersForPickingCentroids.contains(checkingRandomnessInteger)) {
					checkingRandomnessInteger = randomObjectToPickFirstCentroids.nextInt(sizeOfOriginalClusterList);
				}
				randomIntegersForPickingCentroids.add(checkingRandomnessInteger);
			}
			counterForMethod = counterForMethod + 1;
		}

		counterForMethod = 0; // create centroids																			   //Taking the ArrayList of random integers and using the random integers
		while (counterForMethod < numberOfClustersToMake) {												   //as the indices of arrayList to make these starting centroid points and
			arrayForCentroids.add(arrayList.get(randomIntegersForPickingCentroids.get(counterForMethod))); //add them to the ArrayList<Point> of centroids.
			counterForMethod = counterForMethod + 1;
		}
		
		ArrayList<Integer> finalIndicesOfClosestCentroids = new ArrayList<Integer>(); //ArrayList<Integer> used to store the list of indices of the arrayList that correspond to the
																					  //index of its closest centroid in the ArrayList<Point> of centroids, after the final iteration,
		Integer counterForIterations = 0;											  //in order to print out the final clusters and their points.
		while (counterForIterations < numberOfIterationsToComplete) { // largest while loop, which iterates as many times as specified in the command line arguments
		
			Integer counterForMethod2 = 0;
			ArrayList<Integer> indicesOfClosestCentroids = new ArrayList<Integer>(); //ArrayList<Integer> used to store the list of indices of the arrayList that correspond to the
			Double distanceVariableHolder = 0.0; 									//index of its closest centroid in the ArrayList<Point> of centroids.
		
			counterForMethod = 0;
			while (counterForMethod < sizeOfOriginalClusterList) {
				Double measureOfWhereToPutPoint = 1000000000.0; //double used to store the measure of distance between the point from arrayList and the closest centroid.  Starting 
				Integer indexOfWhereToPutPoint = -1;			//with a very large distance and replacing with smaller and smaller until the smallest distance is found, and therefore
				counterForMethod2 = 0;							//the closest centroid to that point is found. That index of the closest centroid is stored in indexOfWhereToPutPoint 
				while (counterForMethod2 < numberOfClustersToMake) {
					distanceVariableHolder = getDistance(counterForMethod, counterForMethod2, arrayForCentroids);
					
					if (distanceVariableHolder < measureOfWhereToPutPoint) { //This if statement in a while loop captures the closest centroid to the point being tested
						measureOfWhereToPutPoint = distanceVariableHolder;  //so that the centroid's index in arrayForCentroids can be stored for computing the next round of centroids.
						indexOfWhereToPutPoint = counterForMethod2;
					}
					counterForMethod2 = counterForMethod2 + 1;
				}
				
				indicesOfClosestCentroids.add(indexOfWhereToPutPoint); //Add the index of the centroid closest to the point in question to the array of indices of the closest centroid
				counterForMethod = counterForMethod + 1;
			}
	
			System.out.println("Centroids of iteration: " + (counterForIterations + 1));
			counterForMethod = 0;
			while (counterForMethod < arrayForCentroids.size()) {
				System.out.println("(" + arrayForCentroids.get(counterForMethod).getXCoord() + ", " + arrayForCentroids.get(counterForMethod).getYCoord() + ")");
				counterForMethod = counterForMethod + 1;
			}
			counterForMethod = 0;
			counterForMethod2 = 0;
			Integer counterForMethod3 = 0;
			while (counterForMethod < numberOfClustersToMake) {
				counterForMethod2 = 0;
				counterForMethod3 = 0;
				System.out.println("Cluster " + (counterForMethod + 1) + ": ");
				while (counterForMethod2 < sizeOfOriginalClusterList) { // for ease of the user, in the output, the actual indicies of the points, and clusters from their ArrayLists are made larger by 1 so that the first cluster is 
						if (counterForMethod == indicesOfClosestCentroids.get(counterForMethod2)) {// not cluster 0, but cluster 1, and the first point is not point 0, but point 1
							System.out.println("Point " + (counterForMethod3 + 1) + " of cluster " + (counterForMethod + 1) + " is : (" + arrayList.get(counterForMethod2).getXCoord() + ", " + arrayList.get(counterForMethod2).getYCoord() + ")");
							counterForMethod3 = counterForMethod3 + 1;
						}
						counterForMethod2 = counterForMethod2 + 1;
				}
				counterForMethod = counterForMethod + 1;
				System.out.println("");
			
			}
			
			
			arrayForCentroids = getNewCentroids(indicesOfClosestCentroids, numberOfClustersToMake); //replacing the arrayForCentroids with a new set of Centroids calculated by getNewCentroids.
			
			counterForIterations = counterForIterations + 1;
			finalIndicesOfClosestCentroids = indicesOfClosestCentroids; //sets the indicesOfClosestCentroids to a "more global" finalIndicesOfClosestCentroids in order to print out the final data
		}	
		return finalIndicesOfClosestCentroids;
	}
}
