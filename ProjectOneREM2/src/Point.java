
public class Point {

	private Double[] pointCoordinates = new Double[2];
	
	public Point () {
		pointCoordinates[0] = 0.0;
		pointCoordinates[1] = 0.0;
	}
	
	public Point (Double xSpot, Double ySpot) {
		pointCoordinates[0] = xSpot;
		pointCoordinates[1] = ySpot;
	}

	public Double getXCoord() {
		return pointCoordinates[0];
	}
	
	public Double getYCoord() {
		return pointCoordinates[1];
	}
	
	public void setXCoord(Double xInput) {
		pointCoordinates[0] = xInput;
	}
	
	public void setYCoord(Double yInput) {
		pointCoordinates[1] = yInput;
	}
}
