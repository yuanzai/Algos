public class PercolationStats {
	private double[] result;

	public PercolationStats(int N, int T) {
		// perform T independent computational experiments on an N-by-N grid
		result = new double[T];
		if (N <= 0 || T <= 0)
			throw new IllegalArgumentException();

		for (int i = 0; i < T; i++) {
			Percolation myRun = new Percolation(N);
			int row;
			int col;
			int k = 1;

			while (myRun.percolates() == false) {
				do {
					row = StdRandom.uniform(N) + 1;
					col = StdRandom.uniform(N) + 1;
				} while (myRun.isOpen(row, col));
				myRun.open(row, col);
				k++;
			}
			result[i] = (double) k / (N * N);
		}
	}

	public double mean() {
		// sample mean of percolation threshold
		double sum = 0;
		for (int i = 0; i < result.length; i++)
			sum = sum + result[i];
		return sum / result.length;
	}

	public double stddev() {
		// sample standard deviation of percolation threshold
		double variance = 0;
		double mean = mean();

		if (result.length == 1)
			return Double.NaN;

		for (int i = 0; i < result.length; i++) {
			variance = ((result[i] - mean) * (result[i] - mean)) + variance;
		}

		return Math.sqrt(variance / ((double) result.length - 1));
	}

	public double confidenceLo() {
		// returns lower bound of the 95% confidence interval
		return this.mean()
				- (1.96 * this.stddev() / Math.sqrt((double) result.length));
	}

	public double confidenceHi() {
		// returns upper bound of the 95% confidence interval
		return this.mean()
				+ (1.96 * this.stddev() / Math.sqrt((double) result.length));
	}

	public static void main(String[] args) {
		// test client, described below
		 Stopwatch sw = new Stopwatch();
		int N = Integer.valueOf(args[0]);
		int T = Integer.valueOf(args[1]);

		PercolationStats testRun = new PercolationStats(N, T);
		System.out.println("mean                    = " + testRun.mean());
		System.out.println("stddev                  = " + testRun.stddev());
		System.out.println("95% confidence interval = "
				+ testRun.confidenceLo() + ", " + testRun.confidenceHi());
		 System.out.println("Time elapsed            = " + sw.elapsedTime());

	}

}
