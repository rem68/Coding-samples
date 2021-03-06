
/*
 * Project 1
 * Name: Robert Emmett Montgomery
 * E-mail: rem68@georgetown.edu
 * Instructor: Singh
 * COSC 150
 * 
 *   In accordance with the class policies and Georgetown's Honor Code,
 * I certify that, with the exceptions of the lecture notes and those 
 * items listed below, I have neither given nor received any assistance
 * on this project.
 * 
 *   I got assistance during office hours from various TAs, including 
 * Adam, Dan, and Duncan.
 * 
 * Description:
 *   This program takes a list of points and groups them into clusters.  
 * The program reads in the list of points from a file that is indicated
 * as a command line argument, then creates a specified number of random
 * centroids from the original points.  The program then goes through a
 * K means algorithm a specified number of iterations to re-cluster for 
 * more accurate clustering.
 *   This program assumes that it is run with command line 
 * arguments. It can be run with 1 command line argument that is the name 
 * of the data file holding the original points.  If it is run with just 
 * this 1 command line argument, then the default values for the number 
 * of clusters and number of iterations are used. This program can also be 
 * run with 3 command line arguments.  If this is the case, the first 
 * command line argument is the data file name, the second argument is 
 * the number of clusters to make and the third is the number of iterations
 * to run though. 
 *   There are comments throughout my code that describe what is happening,
 * but here I will explain my way of making clusters and centroids a little
 * more.  So the Clusters class creates an ArrayList<Point> called 
 * arrayList, which holds the original points.  Once this arrayList filled
 * with points from a data file, it is not changed for the most part.  In
 * order to create the centroids and clusters, I create a separate
 * ArrayList<Point> to hold the centroids (however many are specified by
 * the command line arguments).  I also create an ArrayList<Integer> to 
 * store the indices of the centroids.  The Integer at index 0 in the 
 * Integer array holds, as an Integer, the index for the centroid that 
 * is closest to the point at index 0 of arrayList. The integer at 
 * index 1 in the Integer array holds, as an Integer, the index for the
 * centroid that is closest to the point at index 1 of arrayList.
 * For Example: arrayList of 6 points, making 2 clusters
 * 					
 * 					indexes
 * 					[0]		[1]		[2]		[3]		[4]		[5]
 * arrayList: 		(0,0)	(0,1)	(0,2)	(4,0)	(4,1)	(4,2)
 * 
 * index array:		1		1		1		0		0		0
 * 
 * centroid array: 	(4,1)	(1,1)
 * 
 * Because arrayList[0], arrayList[1], and arrayList[2] are closest 
 * to centroid[1] and arrayList[3], arrayList[4], and arrayList[5] are
 * closest to centroid[0].
 */



public class Main {
	
	public static void main(String[] args) {
		
		final Integer defaultNumberOfClusters = 3;
		final Integer defaultNumberOfIterations = 5;
		if (args.length == 1) {
			try {
				String argument1 = args[0];
			
				Clusters cluster = new Clusters();
				cluster.readInPointsFromFile(argument1);
				cluster.makeClusters(defaultNumberOfClusters, defaultNumberOfIterations);
			}
			catch (Exception e) {
				System.err.println("Error: " + e.getMessage());	
			}
		}
		else if (args.length == 3) {
			try {	
				String argument1 = args[0];
				Integer argument2 = Integer.parseInt(args[1]);
				Integer argument3 = Integer.parseInt(args[2]);
			
				Clusters cluster = new Clusters();
				cluster.readInPointsFromFile(argument1);
				cluster.makeClusters(argument2, argument3);
			}
			catch (Exception e) {
				System.err.println("Error: " + e.getMessage());	
			}
		}
		else {
			System.out.println("This program needs to be run with either 1 or 3 command line arguments.");
			System.out.println("Read the information in the readme to get more information.");
		}	
	}
}
