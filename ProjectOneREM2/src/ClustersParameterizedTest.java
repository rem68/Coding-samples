import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/*
 * A parameterized test for the getDistance() method.
 * This test uses parameters to test multiple points and
 * their resulting distances.
 */
@RunWith(value=Parameterized.class)
public class ClustersParameterizedTest {

    private double xCoordinate1; //the x coordinate of the point in the Cluster
    private double yCoordinate1; //the y coordinate of the point in the Cluster
    private double xCoordinate2; //the x coordinate of the point in the ArrayList<Point> that is a parameter of the getDistance() method
    private double yCoordinate2; //the y coordinate of the point in the ArrayList<Point> that is a parameter of the getDistance() method
    private double expectedDistance; //the expected distance between the point in the Cluster and the point in the ArrayList<Point>

    @Parameters 
    public static Collection<Double[]> getTestParameters() {
       return Arrays.asList(new Double[][] {
          {9.0, 1.0, 1.0, 1.0, 8.0},  //xCoordinate1, yCoordinate1, xCoordinate2, yCoordinate2, expectedDistance   
          {3.0, 8.0, 1.0, 8.0, 2.0},  //xCoordinate1, yCoordinate1, xCoordinate2, yCoordinate2, expectedDistance    
          {4.0, 3.0, 1.0, 3.0, 3.0},  //xCoordinate1, yCoordinate1, xCoordinate2, yCoordinate2, expectedDistance   
       });
    }

    public ClustersParameterizedTest(double xCoordinate1, 
       double yCoordinate1, double xCoordinate2, double yCoordinate2, double expectedDistance) {
    	this.xCoordinate1 = xCoordinate1;
    	this.yCoordinate1 = yCoordinate1;
    	this.xCoordinate2 = xCoordinate2;
    	this.yCoordinate2 = yCoordinate2;
    	this.expectedDistance = expectedDistance;
    }

    @Test
    public void testGetDistance() {
       Point pointOne = new Point(xCoordinate1, yCoordinate1); //the point for the Cluster
       Point pointTwo = new Point(xCoordinate2, yCoordinate2); //the point for the ArrayList<Point>
       Clusters clusterC = new Clusters();
       ArrayList<Point> secondPointArrayList = new ArrayList<Point>();
       
       clusterC.addToArray(pointOne);
       secondPointArrayList.add(pointTwo);
       
       assertEquals(clusterC.getDistance(0, 0, secondPointArrayList), expectedDistance, 0.0);
    } 
}
