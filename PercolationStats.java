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
  private int trials; // number of experiment
  private double[] percolationThreshold;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("");
    }

    this.n = n;
    // this.trials = trials;
    this.percolationThreshold = new double[trials];
    Percolation percolation;

    for (int i = 0; i < trials; i++) {
      percolation = new Percolation(n);
      executeExperiment(percolation);
      double openSites = percolation.numberOfOpenSites();
      double iethPercolationThreshold = openSites / (n*n);
      percolationThreshold[i] = iethPercolationThreshold;
    }
  }

  private void executeExperiment(Percolation percolation) {
    int randomRow = StdRandom.uniform(n);
    int randomCol = StdRandom.uniform(n);

    // Choose a site uniformly at random among all _blocked_ sites.
    // Open the site.

    while (!percolation.percolates()) {
      randomRow = StdRandom.uniform(n);
      randomCol = StdRandom.uniform(n);

      if (!percolation.isOpen(randomRow, randomCol))
        percolation.open(randomRow, randomCol);
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(percolationThreshold);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(percolationThreshold);
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
    int n = 200, trials = 100;

    if (args.length != 0) {
      n = Integer.parseInt(args[0]);
      trials = Integer.parseInt(args[1]);
    }

    System.out.println(n + "  " + trials);

    PercolationStats percolationStats = new PercolationStats(n, trials);
    System.out.printf("mean                    = %f\n", percolationStats.mean());
    System.out.printf("stddev                  = %f\n", percolationStats.stddev());
    System.out.printf("95/ confidence interval = [%f, %f]\n", percolationStats.confidenceLo(),  percolationStats.confidenceHi());
  }
}