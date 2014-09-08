public class Percolation {
	private boolean[][] percolationTable;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF ufFull;
	private int n;

	public Percolation(int N) {
		// create N-by-N grid, with all sites blocked
		percolationTable = new boolean[N][N];
		uf = new WeightedQuickUnionUF((N * N) + 2);
		ufFull = new WeightedQuickUnionUF((N * N + 1));
		n = N;
	}

	public void open(int i, int j) {
		// open site (row i, column j) if it is not already
		// true being open

		int site = ((i - 1) * n) + j;

		if (i < 1 || i > n || j < 1 || j > n)
			throw new IndexOutOfBoundsException();

		percolationTable[i - 1][j - 1] = true;

		if (j < n && isOpen(i, j + 1)) 
			union(site, site + 1);

		if (j > 1 && isOpen(i, j - 1))
			union(site, site - 1);

		if (i < n && isOpen(i + 1, j))
			union(site, site + n);

		if (i > 1 && isOpen(i - 1, j))
			union(site, site - n);

		if (i == 1)
			union(site, 0);
		
		if (i == n)
			uf.union(site, n * n + 1);
		
		 
	}

	public boolean isOpen(int i, int j) {
		// is site (row i, column j) open?
		if (i < 1 || i > n || j < 1 || j > n)
			throw new IndexOutOfBoundsException();

		return percolationTable[i - 1][j - 1];
	}

	public boolean isFull(int i, int j) {
		// is site (row i, column j) full?
		if (i < 1 || i > n || j < 1 || j > n)
			throw new IndexOutOfBoundsException();
		
		int site = ((i - 1) * n) + j;
		return ufFull.connected(0, site);
	}

	public boolean percolates() {
		// does the system percolate?
		return uf.connected(0, n * n + 1);
	}
	
	private void union(int i ,int j) {
		ufFull.union(i, j);
		uf.union(i, j);
	}
}
