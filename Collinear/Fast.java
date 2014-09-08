import java.util.Arrays;

public class Fast {
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

		for (int p = 0; p < N; p++) {
			Arrays.sort(ptArray);
			Arrays.sort(ptArray, p, N - 1, ptArray[p].SLOPE_ORDER);

			int count = 0;
			for (int q = p; q < N - 2; q++) {

				if (ptArray[p].slopeTo(ptArray[q + 1]) == ptArray[p].slopeTo(ptArray[q + 2])) {
					if (count == 0) {
						count = 3;
					} else {
						count++;
					}
				} else {
					if (count > 3) {
						StdOut.print(ptArray[q - count].toString());
						for (int i = 1; i < count; i++)
							StdOut.print(" -> " + ptArray[q - count + i].toString());
						count = 0;
						StdOut.println();
					}
				}

			}
			if (count > 3) {
				StdOut.print(ptArray[N - count].toString());

				for (int i = 1; i < count; i++) {
					StdOut.print(" -> " + ptArray[N - count + i].toString());
					ptArray[N - count].drawTo(ptArray[N - count + i]);
				}
				count = 0;
				StdOut.println();
			}
		}

	}
}
