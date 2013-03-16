import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

/*
 * JUnit tests for the various methods of the Cluster class, 
 * not counting the parameterized test which is in the 
 * ClustersParameterizedTest.java file.
 */
public class ClustersTest {
	
	@Test
	public void testAddToArrayWithDoubleParameters() {//Test for addToArray(Double newPointXValue, Double newPointYValue)
		Clusters clusterForTest = new Clusters();
		final Double doubleXCoord = 1.5; // creates 2 coordinates for 1 point
		final Double doubleYCoord = 3.1;
		
		clusterForTest.addToArray(doubleXCoord, doubleYCoord); // uses the addToArrayMethod
		
		assertEquals(doubleXCoord, clusterForTest.getArray().get(0).getXCoord()); // then checks to see if the X value of the first (and only) item in clusterForTest has the correct X value
		assertEquals(doubleYCoord, clusterForTest.getArray().get(0).getYCoord()); // then checks to see if the Y value of the first (and only) item in clusterForTest has the correct Y value
	}
	
	@Test
	public void testAddToArrayWithPointParameter() {//Test for addToArray(Point p)
		Clusters clusterForTest = new Clusters();	
		final Point firstPoint = new Point (1.5, 2.0); //creates 2 points to add to the Cluster
		final Point secondPoint = new Point (2.5, 3.0);
		final Integer numberOfPoints = 2;
		
		clusterForTest.addToArray(firstPoint); // adds the two points to the cluster using the addToArray() method
		clusterForTest.addToArray(secondPoint);
		
		assertEquals(firstPoint, clusterForTest.getArray().get(0)); // makes sure the first item in the Cluster object is the same as the point it was supposed to add
		assertEquals(secondPoint, clusterForTest.getArray().get(1)); // makes sure the second item in the Cluster object is the same as the point it was supposed to add
		assertEquals(numberOfPoints, clusterForTest.getSize(), 0.0); // makes sure the size of the Cluster object is 2, because we added 2 points
	}
	
	@Test
	public void testGetNewCentroids() { //Test for getNewCentroids(ArrayList<Integer> indexArrayList, Integer numberOfClustersToMake)
		Integer numberOfClustersToMake = 2; // the number of clusters (and centroids) to make
			
		final Point firstPoint = new Point (1.4, 2.5); // create 6 points
		final Point secondPoint = new Point (1.6, 3.0);
		final Point thirdPoint = new Point (5.0, 6.0);
		final Point fourthPoint = new Point (7.2, 7.1);
		final Point fifthPoint = new Point (3.0, 3.5);
		final Point sixthPoint = new Point (5.8, 4.9);
		
		Clusters clusterForTest = new Clusters(); //add the 6 points to the Cluster		
		clusterForTest.addToArray(firstPoint);
		clusterForTest.addToArray(secondPoint);
		clusterForTest.addToArray(thirdPoint);
		clusterForTest.addToArray(fourthPoint);
		clusterForTest.addToArray(fifthPoint);
		clusterForTest.addToArray(sixthPoint);
		
		ArrayList<Integer> indexArrayList = new ArrayList<Integer>(); // create a ArrayList<Integer> that holds the corresponding indexes of the previous Centroids
		indexArrayList.add(0); // because (1.4, 2.5) was closest to the old, fictional centroid 0
		indexArrayList.add(0); // because (1.6, 3.0) was closest to the old, fictional centroid 0
		indexArrayList.add(1); // because (5.0, 6.0) was closest to the old, fictional centroid 1
		indexArrayList.add(1); // because (7.2, 7.1) was closest to the old, fictional centroid 1
		indexArrayList.add(0); // because (3.0, 3.5) was closest to the old, fictional centroid 0
		indexArrayList.add(1); // because (5.8, 4.9) was closest to the old, fictional centroid 1
		
		ArrayList<Point> expectedNewCentroids = new ArrayList<Point>(); //create the expected new Centroid ArrayList<Point>
		final Point firstNewCentroid = new Point ((6.0/3), (9.0/3)); //new centroid created from points at indices 0, 1, 4 of clusterForTest
		final Point secondNewCentroid = new Point ((18.0/3), (18.0/3)); //new centroid created from points at indices 2, 3, 5 of clusterForTest
		expectedNewCentroids.add(firstNewCentroid);
		expectedNewCentroids.add(secondNewCentroid);
		
		ArrayList<Point> returningNewCentroids = clusterForTest.getNewCentroids(indexArrayList, numberOfClustersToMake); //call getNewCentroids();		
		Integer counterForTest = 0;
		while (counterForTest < numberOfClustersToMake) {
			assertEquals(returningNewCentroids.get(counterForTest).getXCoord(), expectedNewCentroids.get(counterForTest).getXCoord(), 0.0); // test to make sure the generated and expected are equal
			assertEquals(returningNewCentroids.get(counterForTest).getYCoord(), expectedNewCentroids.get(counterForTest).getYCoord(), 0.0);
			counterForTest = counterForTest + 1;
		}	
	}
	
	@Test
	public void testMakeClusters() {//Test for  makeClusters(Integer numberOfClustersToMake, Integer numberOfIterationsToComplete)
		/*
		 * Because the randomizing function is inside the makeClusters() method,
		 * the test will have 1 of two outcomes, either the group with the 4 
		 * points clustered around the vicinity of (1,1) will be in the first
		 * cluster and the points clustered around the vicinity of (5,5) will 
		 * be in the second cluster, or the other way around.  Because the 
		 * outcome of which cluster is first is random, the test accounts for
		 * either case to be true.    
		 */
		final Point firstPoint = new Point (1.0, 1.0); // create 6 points
		final Point secondPoint = new Point (1.0, 0.0);
		final Point thirdPoint = new Point (0.0, 1.0);
		final Point fourthPoint = new Point (0.0, 0.0);
		final Point fifthPoint = new Point (5.0, 5.0);
		final Point sixthPoint = new Point (5.0, 6.0);
		final Point seventhPoint = new Point (6.0, 5.0);
		final Point eighthPoint = new Point (6.0, 6.0);
		
		Clusters clusterForTest = new Clusters(); //add the 6 points to the Cluster		
		clusterForTest.addToArray(firstPoint);
		clusterForTest.addToArray(secondPoint);
		clusterForTest.addToArray(thirdPoint);
		clusterForTest.addToArray(fourthPoint);
		clusterForTest.addToArray(fifthPoint);
		clusterForTest.addToArray(sixthPoint);
		clusterForTest.addToArray(seventhPoint);
		clusterForTest.addToArray(eighthPoint);
		
		Integer sizeOfClusterArray = clusterForTest.getSize();
		
		ArrayList<Integer> expectedIndexArrayList1 = new ArrayList<Integer>(); // create a ArrayList<Integer> that holds the corresponding indexes of the previous Centroids
		expectedIndexArrayList1.add(0); // because (1.0, 1.0) was closest to the old, fictional centroid 0
		expectedIndexArrayList1.add(0); // because (1.0, 0.0) was closest to the old, fictional centroid 0
		expectedIndexArrayList1.add(0); // because (0.0, 1.0) was closest to the old, fictional centroid 0
		expectedIndexArrayList1.add(0); // because (0.0, 0.0) was closest to the old, fictional centroid 0
		expectedIndexArrayList1.add(1); // because (5.0, 5.0) was closest to the old, fictional centroid 1
		expectedIndexArrayList1.add(1); // because (5.0, 6.0) was closest to the old, fictional centroid 1
		expectedIndexArrayList1.add(1); // because (6.0, 5.0) was closest to the old, fictional centroid 1
		expectedIndexArrayList1.add(1); // because (6.0, 6.0) was closest to the old, fictional centroid 1
		
		ArrayList<Integer> expectedIndexArrayList2 = new ArrayList<Integer>(); // create a ArrayList<Integer> that holds the corresponding indexes of the previous Centroids
		expectedIndexArrayList2.add(1); // because (1.0, 1.0) was closest to the old, fictional centroid 1
		expectedIndexArrayList2.add(1); // because (1.0, 0.0) was closest to the old, fictional centroid 1
		expectedIndexArrayList2.add(1); // because (0.0, 1.0) was closest to the old, fictional centroid 1
		expectedIndexArrayList2.add(1); // because (0.0, 0.0) was closest to the old, fictional centroid 1
		expectedIndexArrayList2.add(0); // because (5.0, 5.0) was closest to the old, fictional centroid 0
		expectedIndexArrayList2.add(0); // because (5.0, 6.0) was closest to the old, fictional centroid 0
		expectedIndexArrayList2.add(0); // because (6.0, 5.0) was closest to the old, fictional centroid 0
		expectedIndexArrayList2.add(0); // because (6.0, 6.0) was closest to the old, fictional centroid 0
		
		Integer numberOfClustersToMake = 2; // the number of clusters (and centroids) to make
		Integer numberOfIterationsToRun = 50; // the number of iterations to run the clustering algorithm
		ArrayList<Integer> indexArrayList = clusterForTest.makeClusters(numberOfClustersToMake, numberOfIterationsToRun);
		
		Integer counterForTest = 0;
		
		Boolean verify;
		
		while (counterForTest < sizeOfClusterArray) {// verify is true if either cluster around 1,1 is first and cluster around 5,5 is second, or if the other way around
			verify = false;
			verify = ((indexArrayList.get(counterForTest) == expectedIndexArrayList1.get(counterForTest)) || (indexArrayList.get(counterForTest) == expectedIndexArrayList2.get(counterForTest)));
			assertTrue(verify);
			counterForTest = counterForTest + 1;
		}
	}
}
