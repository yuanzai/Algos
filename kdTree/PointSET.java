import java.util.TreeSet;

public class PointSET {
	private int size;
	private TreeSet<Point2D> pointSet;
	
	public PointSET() {
		// construct an empty set of points
		size = 0;
		pointSet = new TreeSet<Point2D>(); 
	}

	public boolean isEmpty() {
		// is the set empty?
		return size == 0;
	}

	public int size() {
		// number of points in the set
		return size;
	}

	public void insert(Point2D p) {
		// add the point p to the set (if it is not already in the set)
		if (p == null) throw new NullPointerException();
		if (contains(p)) return;
		pointSet.add(p);
		size++;
	}

	public boolean contains(Point2D p) {
		// does the set contain the point p?
		return pointSet.contains(p);
	}

	public void draw() {
		// draw all of the points to standard draw
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		
		for (Point2D p : pointSet)
			StdDraw.point(p.x(), p.y());
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points in the set that are inside the rectangle
		Queue<Point2D> result = new Queue<Point2D>();
		for (Point2D p : pointSet) {
			if (rect.contains(p)) result.enqueue(p);
		}
		return result;
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to p; null if set is empty
		if (size == 0)
			return null;
		Point2D nearestPoint = null;
		for (Point2D q : pointSet) {
			if (nearestPoint == null) {
				nearestPoint = q;
			} else if (p.distanceSquaredTo(q) < p.distanceSquaredTo(nearestPoint)) {
				nearestPoint = q;
			}
		}
		return nearestPoint;	
	}
}
