package princeton.percolation;

import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private double[] values;

    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        values = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation test = new Percolation(n);
            while (true) {

                int row;
                int col;
                do {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);

                    if (!test.isOpen(row, col)) {
                        break;
                    }
                } while (true);

                test.open(row, col);

                if (test.percolates())
                    break;

            }

            values[i] = test.numberOfOpenSites() * 1.0 / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(values);
    }

    public double stddev() {
        return StdStats.stddev(values);
    }

    public double confidenceLo() {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(this.values.length);
    }

    public double confidenceHi() {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(this.values.length);
    }

    public static void main(String[] args) {
        PercolationStats simulation = new PercolationStats(100, 200);

        StdOut.println("mean                    = " + simulation.mean());
        StdOut.println("stddev                  = " + simulation.stddev());
        StdOut.println(
                "95% confidence interval = [" + simulation.confidenceLo() + ", " + simulation.confidenceHi() + "]");
    }

}
