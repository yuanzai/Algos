public class Solver {
	private int movesCount = 0;
	
	private MinPQ<searchNode> boardPQ;
	private MinPQ<searchNode> twinBoardPQ;
	private Stack<Board> solutionQueue;
	private boolean solvable;
	
	public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)

		boardPQ = new MinPQ<searchNode>();
		twinBoardPQ = new MinPQ<searchNode>();
		movesCount = 0;
		solvable = false;

		searchNode node = null;
		searchNode twinNode = null;

		node = new searchNode(initial,initial.manhattan(),0,null);
		twinNode = new searchNode(initial.twin(),initial.twin().manhattan(),0,null);
		
		//solutionQueue.enqueue(testBoard);
		if (node.getBoard().isGoal()) solvable = true;

		while (!node.getBoard().isGoal() && !twinNode.getBoard().isGoal()) {			
			//node.getBoard().createNeighborQueue();
			for (Board b : node.getBoard().neighbors()) {
				if (node.previous != null ) { 
					if (!b.equals(node.previous.getBoard())) 
						boardPQ.insert(new searchNode(b, b.manhattan(), node.getMoves()+1,node));
				} else {
					boardPQ.insert(new searchNode(b, b.manhattan(), node.getMoves()+1,node));
				}
			}
			node = boardPQ.delMin();

			if (node.getBoard().isGoal()) {
				solvable = true;
			}
			
			//twinNode.getBoard().createNeighborQueue();
			for (Board b : twinNode.getBoard().neighbors()) {
				if (twinNode.previous != null ) { 
					if (!b.equals(twinNode.previous.getBoard())) 
						twinBoardPQ.insert(new searchNode(b, b.manhattan(),twinNode.getMoves()+1,twinNode));
				} else {
					twinBoardPQ.insert(new searchNode(b, b.manhattan(), twinNode.getMoves()+1,twinNode));
				}
			}
			twinNode = twinBoardPQ.delMin();

			if (twinNode.getBoard().isGoal()) {
				solvable = false;
			}

		}
		
		movesCount=node.getMoves();
		solutionQueue = new Stack<Board>();
		solutionQueue.push(node.getBoard());
		while (node.previous != null) {
			solutionQueue.push(node.previous.getBoard());
			node = node.previous;
		}
		
	}

	public boolean isSolvable() {
		// is the initial board solvable?
		return solvable;

	}

	public int moves() {
		// min number of moves to solve initial board; -1 if no solution
		if (!solvable) return -1;
		return movesCount;
	}

	public Iterable<Board> solution() {
		// sequence of boards in a shortest solution; null if no solution
		if (!solvable) return null;
		return solutionQueue;
	}
	
	private class searchNode implements Comparable<searchNode>{
		private Board testBoard;
		public int priority;
		private int moves;
		public searchNode previous; 
		
		public searchNode(Board b, int ma, int mo, searchNode previous) {
			this.priority = ma + mo;
			this.moves = mo;
			testBoard = b;
			this.previous = previous;
		}
		
		public int getMoves() {
			return moves;
		}
		public Board getBoard() {
			return testBoard;
		}

		@Override
		public int compareTo(searchNode that) {

			if (that == null) throw new NullPointerException();
			if (this.priority > that.priority) return 1;
			if (this.priority < that.priority) return -1;
			return 0;
		}
		
	}

	public static void main(String[] args) {
		// solve a slider puzzle (given below)
	    // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++) 
	            blocks[i][j] = in.readInt();
	            
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
}
