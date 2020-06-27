/******************************************************************************
 *  Name:    Yudi Yamane
 *  NetID:   yudi
 *  Precept: P01
 *
 *  Description:  Modeling Percolation using an N-by-N grid and Union-Find data
 *                structures to determine the threshold.
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private int n; // virtual? grid height and width.
  private int openSites; // number open sites.
  private int[][] grid; // N-by-N grid
  private WeightedQuickUnionUF unionFind; // disjoint set representing the connection in the N-by-N grid.

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("n must be positive");
    }

    // maybe this.n is not needed because we have grid[][]
    this.n = n;
    grid = new int[n][n];
    unionFind = new WeightedQuickUnionUF(n*n + 2);
    openSites = 0;

    for (int i = 0; i <= n; i++) {
      unionFind.union(0, i);
    }
    for (int i = n*n-n+1; i < n*n+1; i++) {
      unionFind.union(n*n + 1, i);
    }

    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        grid[row][col] = 0;
      }
    }
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    validate(row, col);

    openSites++;

    int unionFindIndex = xyTo1D(row, col);
    int nSquared = this.n*this.n;
    // only proceed if unionFindIndex is not connected to virtual nodes (if there was a unionFind for OPEN SITES)
    // if (something)
    //   return

    grid[row][col] = 1;

    // No need to check if they are connected. union method does that.
    // Only connect in unionFind if neighbor is already open.
    // top neighbor
    if (unionFindIndex - this.n >= 0  && row-1 >= 0 && grid[row-1][col] == 1) {
      unionFind.union(unionFindIndex, unionFindIndex - this.n);
    }

    // bottom neighbor
    if (unionFindIndex + this.n < nSquared && row+1 < this.n && grid[row+1][col] == 1) {
      unionFind.union(unionFindIndex, unionFindIndex + this.n);
    }

    // left neighbor
    if (unionFindIndex - 1 >= 0 && col-1 >= 0 && grid[row][col-1] == 1) { // maybe not 0, maybe 1
      unionFind.union(unionFindIndex, unionFindIndex - 1);
    }

    // right neighbor
    if (unionFindIndex + 1 < nSquared && col+1 < n && grid[row][col+1] == 1) { // maybe not 0, maybe 1
      unionFind.union(unionFindIndex, unionFindIndex + 1);
    }
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    validate(row, col);
    // is there another way without using grid?
    // is the site connected to the top or bottom row?
    // yes => true
//    return true;
    return grid[row][col] == 1;
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    validate(row, col);
    int unionFindIndex = xyTo1D(row, col);

    if (unionFind.find(0) == unionFind.find(unionFindIndex) && grid[row][col] == 1) {
      System.out.printf("IS FULL (%d, %d):%d\n", row, col, unionFindIndex);
    }

    // is this site connected to the top row/upper virtual node?
    return (unionFind.find(0) == unionFind.find(unionFindIndex)) && (grid[row][col] == 1);
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return openSites;
  }

  // does the system percolate?
  public boolean percolates() {
    // top row connected to bottom row?
    return unionFind.find(0) == unionFind.find(this.n*this.n+1);
  }

  // converts virtual grid coordinates to union find index
  private int xyTo1D(int y, int x) {
    return y*this.n + x + 1;
  }

  // validates virtual grid coordinates
  private void validate(int row, int col) {
    if (!(0 <= row && row < this.n) || !(0 <= col && col < this.n) )
      throw new IllegalArgumentException("row or column indices are out of range");
  }

  // test client (optional)
  public static void main(String[] args) {
    Percolation p = new Percolation(5);
    System.out.println(p.isOpen(0, 0));
  }
}