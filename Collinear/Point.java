/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

	// compare points by slope
	public final Comparator<Point> SLOPE_ORDER = new SlopeOrder(); // YOUR DEFINITION HERE

	private final int x; // x coordinate
	private final int y; // y coordinate

	private class SlopeOrder implements Comparator<Point> {

		@Override
		public int compare(Point p1, Point p2) {
			// TODO Auto-generated method stub
			
			if (slopeTo(p1) > slopeTo(p2)) return 1;
			if (slopeTo(p1) < slopeTo(p2)) return -1;
			return 0;
		}
		
	}
	
	// create the point (x, y)
	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
	}

	// plot this point to standard drawing
	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	// draw line between this point and that point to standard drawing
	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	// slope between this point and that point
	public double slopeTo(Point that) {
		/*The slopeTo() method should return the slope between the invoking point (x0, y0) 
		 * and the argument point (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0). 
		 * Treat the slope of a horizontal line segment as positive zero [added 7/29]; 
		 * treat the slope of a vertical line segment as positive infinity; 
		 * treat the slope of a degenerate line segment (between a point and itself) as negative infinity.
		*/
		/* YOUR CODE HERE */
		if (that == null) throw new NullPointerException();

		if (that.x == this.x && that.y == this.y) {
			return Double.NEGATIVE_INFINITY;
		} else if (that.x == this.x) {
			return Double.POSITIVE_INFINITY;
		} else if (that.y == this.y) {
			return 0.0;
		} else {
			return ((double) that.y - (double)this.y) / ((double)that.x - (double)this.x);
		}
	}

	// is this point lexicographically smaller than that one?
	// comparing y-coordinates and breaking ties by x-coordinates
	public int compareTo(Point that) {
		/* YOUR CODE HERE */
		/*The compareTo() method should compare points by their y-coordinates, 
		 * breaking ties by their x-coordinates. Formally, the invoking point (x0, y0) 
		 * is less than the argument point (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1. 
		 */
		if (that == null) throw new NullPointerException();
		if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;
		if (this.y > that.y || (this.y == that.y && this.x >= that.x)) return 1;
		return 0;
	}

	// return string representation of this point
	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	// unit test
	public static void main(String[] args) {
		/* YOUR CODE HERE */
	}
}