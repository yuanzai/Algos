public class KdTree {
	private int size;
	private Node start;
	private Queue<Point2D> rangeResult;
	private Point2D np;

	public KdTree() {
		// construct an empty set of points
		size = 0;
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
		if (p == null)
			throw new NullPointerException();
		
		if (size == 0) {
			start = new Node(p);
		} else {
			if (contains(p))
				return;
			
			sink(new Node(p), start, true);
		}
		size++;
	}

	public boolean contains(Point2D p) {
		// does the set contain the point p?
		return search(p, start, true);
	}

	public void draw() {
		// draw all of the points to standard draw
		reDraw(start,new RectHV(0,0,1,1),true);
	}

	private void reDraw(Node from, RectHV fRect, boolean vert) {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		StdDraw.point(from.p.x(), from.p.y());
		RectHV lbR, rtR;
		if (vert) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius();
			StdDraw.line(from.p.x(), fRect.ymin(), from.p.x(), fRect.ymax());
			
			lbR = new RectHV(fRect.xmin(), fRect.ymin(), from.p.x(), fRect.ymax());
			rtR = new RectHV(from.p.x(), fRect.ymin(), fRect.xmax(), fRect.ymax());
		}else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius();
			StdDraw.line(fRect.xmin(), from.p.y(), fRect.xmax(), from.p.y());
			lbR = new RectHV(fRect.xmin(), fRect.ymin(), fRect.xmax(), from.p.y());
			rtR = new RectHV(fRect.xmin(), from.p.y(), fRect.xmax(), fRect.ymax());
		}
		
		//StdOut.println(from.p.x() + " " + fRect.ymin()+ " " + fRect.xmax()+ " " + fRect.ymax());
		
		if (from.lb != null) 
			reDraw(from.lb, lbR, !vert);	
		
		if (from.rt != null)
			reDraw(from.rt, rtR, !vert);
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		// all points in the set that are inside the rectangle
		rangeResult = new Queue<Point2D>();
		rangeSearch(rect,start,new RectHV(0,0,1,1),true);
		return rangeResult;
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to p; null if set is empty
		if (size == 0) return null;
		np = start.p;
		nearestSearch(p, start, new RectHV(0,0,1,1), true);
		return np;
	}

	private void nearestSearch(Point2D p, Node from, RectHV fRect, boolean vert) {
		RectHV t, nt, temp;
		Node inNode;
		Node notInNode;
		
		if (vert) {
			t = new RectHV(fRect.xmin(), fRect.ymin(), from.p.x(), fRect.ymax());
			nt = new RectHV(from.p.x(), fRect.ymin(), fRect.xmax(), fRect.ymax()); 
		} else {
			t = new RectHV(fRect.xmin(), fRect.ymin(), fRect.xmax(), from.p.y());
			nt = new RectHV(fRect.xmin(), from.p.y(), fRect.xmax(), fRect.ymax());
		}
			
		if (t.contains(p)) {
			inNode = from.lb;
			notInNode = from.rt;
		} else {
			inNode = from.rt;
			notInNode = from.lb;
			temp = t;
			t = nt;
			nt = temp;
		}
		
		if (inNode != null){
			if (inNode.p.equals(p)) np = p;
			if (p.distanceSquaredTo(inNode.p) < p.distanceSquaredTo(np))  np = inNode.p;
			nearestSearch(p, inNode, t, !vert);
			if (nt.distanceSquaredTo(p) < p.distanceSquaredTo(np)) {
				if (p.distanceSquaredTo(notInNode.p) < p.distanceSquaredTo(np))  np = notInNode.p;
				if (notInNode != null)
					nearestSearch(p, notInNode, nt, !vert);
			}
		}
	}
	
	private void rangeSearch(RectHV r, Node from, RectHV fRect, boolean vert) {
		if (r.contains(from.p))
			rangeResult.enqueue(from.p);
		// if (from.lb == null && from.rt == null)

		if (vert) {

			// from lb left
			if (from.lb != null) {
				RectHV t = new RectHV(fRect.xmin(), fRect.ymin(), from.p.x(), fRect.ymax());
				if (t.intersects(r))
					rangeSearch(r, from.lb, t, !vert);
			}
			if (from.rt != null) {
				RectHV t = new RectHV(from.p.x(), fRect.ymin(), fRect.xmax(), fRect.ymax());
				if (t.intersects(r))
					rangeSearch(r, from.rt, t, !vert);
			}
		} else {
			if (from.lb != null) {
				RectHV t = new RectHV(fRect.xmin(), fRect.ymin(), fRect.xmax(), from.p.y());
				if (t.intersects(r))
					rangeSearch(r, from.lb, t, !vert);
			}
			if (from.rt != null) {
				RectHV t = new RectHV(fRect.xmin(), from.p.y(), fRect.xmax(), fRect.ymax());
				if (t.intersects(r))
					rangeSearch(r, from.rt, t, !vert);
			}

		}
	}

	private boolean search(Point2D p, Node from, boolean vert) {
		if (p.equals(from.p))
			return true;
		if (from.lb == null && from.rt == null)
			return false;

		if (vert) {
			if (p.x() < from.p.x()) {
				if (from.lb == null) {
					return false;
				} else {
					return search(p, from.lb, !vert);
				}
			} else {
				if (from.rt == null) {
					return false;
				} else {
					return search(p, from.rt, !vert);
				}
			}
		} else {
			if (p.y() < from.p.y()) {
				if (from.lb == null) {
					return false;
				} else {
					return search(p, from.lb, !vert);
				}
			} else {
				if (from.rt == null) {
					return false;
				} else {
					return search(p, from.rt, !vert);
				}
			}
		}

	}

	private void sink(Node n, Node from, boolean vert) {
		if (vert) {
			if (n.p.x() < from.p.x()) {
				if (from.lb == null) {
					from.lb = n;
				} else {
					sink(n, from.lb, !vert);
				}
			} else {
				if (from.rt == null) {
					from.rt = n;
				} else {
					sink(n, from.rt, !vert);
				}
			}
		} else {
			if (n.p.y() < from.p.y()) {
				if (from.lb == null) {
					from.lb = n;
				} else {
					sink(n, from.lb, !vert);
				}
			} else {
				if (from.rt == null) {
					from.rt = n;
				} else {
					sink(n, from.rt, !vert);
				}
			}
		}
	}

	private static class Node {
		private Point2D p; // the point
		//private RectHV rect; // the axis-aligned rectangle corresponding to this node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree

		public Node(Point2D p) {
			this.p = p;
		}

	}

}
