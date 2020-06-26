/******************************************************************************
 *  Name:    Yudi Yamane
 *  NetID:   yudi
 *  Precept: P01
 *
 *  Description:  Class to compute statistics of a series of percolation
 *                simulations.
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private int n; // N-by-N grid
  private int trials; // number of experiment trials

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("");
    }

    this.n = n;
    this.trials = trials;
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdRandom.gaussian();
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    int[] a = {1, 2, 3, 4};
    return StdStats.stddev(a);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return 0.0;
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return 0.0;

  }

  // test client (see below)
  public static void main(String[] args) {
    System.out.println("hey");
  }
}