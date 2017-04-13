package princeton.percolation;

import edu.princeton.cs.algorithms.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF sitesToVirtualTop; // object to visualize opened sites
    private WeightedQuickUnionUF percolationOccurrence; // object created to check if percolation occurs
    private boolean[] ifOpened; // Array of closed and opened sites. Used for simulation.
    private int n; // Dimension
    private int openedSites; // Number of opened sites

    public Percolation(int n) {


        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;

        ifOpened = new boolean[n * n]; // Init array of false values

        percolationOccurrence = new WeightedQuickUnionUF(n * n + 2); // object which checks if percolation occurs

        sitesToVirtualTop = new WeightedQuickUnionUF(n * n + 1); // n*n sites and virtual top site

    }

    public void open(int row, int col) {

        if (row < 1 || row > n || col < 1 || col > n)
            throw new IndexOutOfBoundsException();

        int position = countIndex(row, col);

        if (!ifOpened[position]) {

            //virtual top site for percolation and visualization
            if (row == 1) {
                percolationOccurrence.union(position, n * n);
                sitesToVirtualTop.union(position, n * n);
            }

            // virtual bottom site for percolation
            if (row == n) {
                percolationOccurrence.union(position, n * n + 1);
            }

            if (row > 1 && ifOpened[position - n]) { // north
                percolationOccurrence.union(position, position - n);
                sitesToVirtualTop.union(position, position - n);
            }

            if (col != 1 && ifOpened[position - 1]) { // west
                percolationOccurrence.union(position, position - 1);
                sitesToVirtualTop.union(position, position - 1);
            }

            if (col != n && ifOpened[position + 1]) { // east
                percolationOccurrence.union(position, position + 1);
                sitesToVirtualTop.union(position, position + 1);
            }

            if (row < n && ifOpened[position + n]) { // south
                percolationOccurrence.union(position, position + n);
                sitesToVirtualTop.union(position, position + n);
            }


            ifOpened[position] = true;
            openedSites++;
        }
    }

    public boolean isOpen(int row, int col) {

        if (row < 1 || row > n || col < 1 || col > n)
            throw new IndexOutOfBoundsException();

        int position = countIndex(row, col);
        return ifOpened[position];
    }

    public boolean isFull(int row, int col) {

        if (row < 1 || row > n || col < 1 || col > n)
            throw new IndexOutOfBoundsException();

        return isOpen(row, col) && sitesToVirtualTop.connected(countIndex(row, col), n * n);

    }

    public int numberOfOpenSites() {
        return openedSites;
    }

    public boolean percolates() {
        if (n == 1 && !this.isOpen(1, 1)) // corner case when array's size is 1
            return false;
        return percolationOccurrence.connected(n * n, n * n + 1); // check if virtual top site and virtual bottom site are connected
    }

    private int countIndex(int row, int col) {
        return n * (row - 1) + (col - 1); // count index in object
    }

}
