import java.util.Arrays;

public class Brute {
	public static void main(String[] args) {
		In in = new In(args[0]);
		final int N = in.readInt();
		in.readLine();

		Point[] ptArray = new Point[N];
		for (int i = 0; i < N; i++) {
			String s = in.readLine().trim();
			if (!s.isEmpty()) {
				String[] sArray = s.split("\\s+");
				ptArray[i] = new Point(Integer.valueOf(sArray[0].trim()), Integer.valueOf(sArray[1].trim()));
				ptArray[i].draw();
			} else {
				i--;
			}

		}
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		Arrays.sort(ptArray);
		for (int p = 0; p < N; p++)
			for (int q = p + 1; q < N; q++)
				for (int r = q + 1; r < N; r++)
					for (int s = r + 1; s < N; s++)
						if (ptArray[p].slopeTo(ptArray[q]) == ptArray[p].slopeTo(ptArray[r])
								&& ptArray[p].slopeTo(ptArray[q]) == ptArray[p].slopeTo(ptArray[s])) {
							StdOut.println(ptArray[p].toString() + " -> " + ptArray[q].toString() + " -> " + ptArray[r].toString() + " -> "
									+ ptArray[s].toString());
							ptArray[p].drawTo(ptArray[s]);

						}
	}
}
