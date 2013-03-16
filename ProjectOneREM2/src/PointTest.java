import static org.junit.Assert.*;

import org.junit.Test;


public class PointTest {

	@Test
	public void testPointConstructor() {
		Point pointP = new Point();
		assertEquals(0.0, pointP.getXCoord(), 0.0);
		assertEquals(0.0, pointP.getYCoord(), 0.0);	
	}
	
	@Test
	public void testPointConstructorWithTwoParameters() {
		final Double x = 1.5;
		final Double y = 3.3;
		Point pointP = new Point (x, y);	
		assertEquals(1.5, pointP.getXCoord(), 0.0);
		assertEquals(3.3, pointP.getYCoord(), 0.0);
	}
	
	@Test
	public void testSetXCoord() {
		final Double x = 1.7;
		Point pointP = new Point ();
		assertEquals(0.0, pointP.getXCoord(), 0.0);
		pointP.setXCoord(x);
		assertEquals(x, pointP.getXCoord());	
	}
	
	@Test
	public void testSetYCoord() {
		final Double y = 0.9;
		Point pointP = new Point ();
		assertEquals(0.0, pointP.getYCoord(), 0.0);
		pointP.setYCoord(y);
		assertEquals(y, pointP.getYCoord());	
	}
	
}