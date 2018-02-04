import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private static final double C_95 = 1.96;
	private final int count;
	private final double[] trials;
	private double statMean;
	private double statDev;

	public PercolationStats(int n, int t) {
		if (n <= 0) {
			throw new IllegalArgumentException("n must be greater than 0");
		}
		if (t <= 0) {
			throw new IllegalArgumentException("t must be greater than 0");
		}
		count = t;
		trials = new double[count];
		for (int tNum = 0; tNum < t; tNum++) {
			Percolation pcl = new Percolation(n);
			double sites = 0.0;
			while (!pcl.percolates()) {
				int row = StdRandom.uniform(1, n + 1);
				int col = StdRandom.uniform(1, n + 1);
				if (!pcl.isOpen(row, col)) {
					pcl.open(row, col);
					sites++;
				}
			}
			trials[tNum] = sites / (n * n);
		}
	}

	public double mean() {
		statMean = StdStats.mean(trials);
		return statMean;
	}

	public double stddev() {
		statDev = StdStats.stddev(trials);
	    return statDev;
	}

	public double confidenceLo() {
		return statMean - (C_95 * statDev) / Math.sqrt(count);
	}

	public double confidenceHi() {
		return statMean + (C_95 * statDev) / Math.sqrt(count);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length != 2) {
			return;
		}
		try {
			int n = Integer.parseInt(args[0]);
			int t = Integer.parseInt(args[1]);
			PercolationStats ps = new PercolationStats(n, t);
			String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
			StdOut.println("mean      = " + ps.statMean);
			StdOut.println("stddev    = " + ps.statDev);
			StdOut.println(" 95% confidence interval = " + confidence);
		} catch (NumberFormatException e) {
			StdOut.println("argument must be integer type");
			return;
		}

	}

}
