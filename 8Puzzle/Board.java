public class Board {

	private int[][] blocks;
	private Queue<Board> blockQueue;
	public Board(int[][] blocks) {
		// construct a board from an N-by-N array of blocks
		this.blocks = blocks;
		
	} 

	private void createNeighborQueue() {
		blockQueue = new Queue<Board>();
		int N = this.dimension();
		int i = 0;
		int j = 0;

		//StdOut.println("d" + N);
		
		for (int ii = 0; ii < N; ii++)
			for (int jj = 0; jj < N; jj++)
				if (blocks[ii][jj] == 0) {
					i = ii;
					j = jj;
					break;
					}
				
		/*
		StdOut.println("i" + i);
		StdOut.println("j" + j);
				
		while (blocks[i][j] != 0) {
			while (blocks[i][j] != 0) {
				StdOut.println(blocks[i][j]);
				StdOut.println("i" + i);
				StdOut.println("j" + j);
				j++;
			}
			i++;
		}
*/
		if (i - 1 >= 0) {
			
			int[][] tempBlock = new int[N][N]; 
			for (int ii = 0; ii < N; ii++)
				for (int jj = 0; jj < N; jj++)
					tempBlock[ii][jj] = blocks[ii][jj];
			tempBlock[i][j] = tempBlock[i - 1][j];
			tempBlock[i - 1][j] = 0;
			blockQueue.enqueue(new Board(tempBlock));
		}

		if (i + 1 < N) {
			int[][] tempBlock = new int[N][N]; 
			for (int ii = 0; ii < N; ii++)
				for (int jj = 0; jj < N; jj++)
					tempBlock[ii][jj] = blocks[ii][jj];
			tempBlock[i][j] = tempBlock[i + 1][j];
			tempBlock[i + 1][j] = 0;
			blockQueue.enqueue(new Board(tempBlock));
		}

		if (j - 1 >= 0) {
			int[][] tempBlock = new int[N][N]; 
			for (int ii = 0; ii < N; ii++)
				for (int jj = 0; jj < N; jj++)
					tempBlock[ii][jj] = blocks[ii][jj];
			tempBlock[i][j] = tempBlock[i][j - 1];
			tempBlock[i][j - 1] = 0;
			blockQueue.enqueue(new Board(tempBlock));
		}
		
		if (j + 1 < N) {
			int[][] tempBlock = new int[N][N]; 
			for (int ii = 0; ii < N; ii++)
				for (int jj = 0; jj < N; jj++)
					tempBlock[ii][jj] = blocks[ii][jj];
			tempBlock[i][j] = tempBlock[i][j + 1];
			tempBlock[i][j + 1] = 0;
			blockQueue.enqueue(new Board(tempBlock));
		}
	}
	
	// (where blocks[i][j] = block in row i, column j)
	public int dimension() {

		return blocks.length;
	} // board dimension N

	public int hamming() {
		// number of blocks out of place
		int h = 0;
		int N = dimension();

		if (blocks[N-1][N-1] != 0) h++;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (blocks[i][j] != (i * N + j + 1)) 
					if (i < N-1 || j < N-1)
						h++;

		return h;
	}

	public int manhattan() {
		// sum of Manhattan distances between blocks and goal
		int m = 0;
		int N = dimension();

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				int value = blocks[i][j];
					if (value != 0)
						m = Math.abs(getI(value) - i) + Math.abs(getJ(value) - j) + m;
			}
		return m;

	} 

	public boolean isGoal() {
		// is this board the goal board?
		int N = dimension();
		if (blocks[N-1][N-1] != 0) return false;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (blocks[i][j] != (i * N + j + 1)) 
					if (i < N-1 || j < N-1)
						return false;
				
				
		return true;
	}

	public Board twin() {
		// a board obtained by exchanging two adjacent blocks in the same row
		int N = dimension();
		int[][] tempBlock = new int[N][N];
		for (int ii = 0; ii < N; ii++)
			for (int jj = 0; jj < N; jj++)
				tempBlock[ii][jj] = blocks[ii][jj];
		
		if (blocks[0][0] == 0 || blocks[0][1] == 0) {
			int tempInt = blocks[1][0];
			tempBlock[1][0] = tempBlock[1][1];
			tempBlock[1][1] = tempInt;
			
		} else {
			int tempInt = blocks[0][0];
			tempBlock[0][0] = tempBlock[0][1];
			tempBlock[0][1] = tempInt;
		}
		
		return new Board(tempBlock);
	}

	public boolean equals(Object y) {
		// does this board equal y?
		if (y instanceof Board == false)
			return false;
		Board thisy = (Board) y;

		if (thisy.dimension() != this.dimension())
			return false;

		int N = this.dimension();

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (blocks[i][j] != thisy.blocks[i][j])
					return false;

		return true;
	}

	public Iterable<Board> neighbors() {
		// all neighboring boards
		createNeighborQueue();
		return blockQueue;
	}


	public String toString() {
		// string representation of the board (in the output format specified
		// below)
		int N = dimension();

		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", blocks[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	private int getI(int value) {
		return (value - 1) / dimension();
	}

	private int getJ(int value) {
		return (value - 1) % dimension();
	}
}
