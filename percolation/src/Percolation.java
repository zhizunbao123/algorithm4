import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private static final int TOP = 0;
	private int num;
	private final int size;
	private boolean[] grid;
	private final int bottom;
	private final WeightedQuickUnionUF wqu;
	private final WeightedQuickUnionUF fullness;

	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n must be greater than 0");
		}
		size = n;
		bottom = n * n + 1;
		grid = new boolean[n * n + 2];
		grid[0] = true;
		grid[n * n + 1] = true;
		wqu = new WeightedQuickUnionUF(n * n + 2);
		fullness = new WeightedQuickUnionUF(n * n + 1);
		num = 0;

	}

	private void validateSites(int row, int col) {
		if (row < 1 || col < 1 || row > size || col > size) {
			throw new IllegalArgumentException("row/col must be [1, size]");
		}
	}

	public void open(int row, int col) {
		validateSites(row, col);
		if(!isOpen(row, col)) {
			grid[toIndex(row, col)] = true;
			num++;
		} else {
			return;
		}
		
		if (row == 1) {
			wqu.union(TOP, toIndex(row, col));
			fullness.union(TOP, toIndex(row, col));
		}
		if (row == size) {
			wqu.union(bottom, toIndex(row, col));
		}
		if (col > 1 && isOpen(row, col - 1)) {
			wqu.union(toIndex(row, col - 1), toIndex(row, col));
			fullness.union(toIndex(row, col - 1), toIndex(row, col));
		}
		if (col < size && isOpen(row, col + 1)) {
			wqu.union(toIndex(row, col + 1), toIndex(row, col));
			fullness.union(toIndex(row, col + 1), toIndex(row, col));
		}
		if (row > 1 && isOpen(row - 1, col)) {
			wqu.union(toIndex(row - 1, col), toIndex(row, col));
			fullness.union(toIndex(row - 1, col), toIndex(row, col));
		}
		if (row < size && isOpen(row + 1, col)) {
			wqu.union(toIndex(row + 1, col), toIndex(row, col));
			fullness.union(toIndex(row + 1, col), toIndex(row, col));
		}

	}

	public boolean isOpen(int row, int col) {
		validateSites(row, col);
		return grid[toIndex(row, col)];
	}

	public boolean isFull(int row, int col) {
		validateSites(row, col);
		return fullness.connected(TOP, toIndex(row, col));
	}

	public int numberOfOpenSites() {
		return num;
	}

	public boolean percolates() {
		return wqu.connected(TOP, bottom);
	}

	private int toIndex(int row, int col) {
		return (row - 1) * size + col;
	}

}