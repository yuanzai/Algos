public class Subset {
	public static void main(String[] args) {
		int k = Integer.valueOf(args[0]);
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		String[] s = StdIn.readLine().split(" ");
		for (String str : s) {
			rq.enqueue(str);
		}
		while (k > 0) {
			System.out.println(rq.dequeue());
			k--;
		}
	}
}