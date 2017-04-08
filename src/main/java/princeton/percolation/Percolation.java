package princeton.percolation;

import edu.princeton.cs.algorithms.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF quickUnion;
    private boolean[] ifOpened; // Array of closed and opened sites. Used for
    // simulation.
    private int n; // Dimension
    private int openedSites; // Number of opened sites

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;

        ifOpened = new boolean[n * n]; // Init array of false values

        quickUnion = new WeightedQuickUnionUF(n * n + 2); // Init API of
        // weighted quick
        // union algorithm

        // Virtual top site

        for (int i = 0; i < n; i++) {

            quickUnion.union(n * n, i); // Union the first n sites
        }

        //	System.out.println(quickUnion.count());

        // Virtual bottom site

        for (int i = 1; i < n + 1; i++) { // Iterate n times

            quickUnion.union((n * n) - i, n * n + 1); // Union the last n sites

        }

        //	System.out.println(quickUnion.count());
    }

    public void open(int row, int col) {

        if (row < 1 || row > n || col < 1 || col > n)
            throw new IndexOutOfBoundsException();

        int position = n * (row - 1) + (col - 1);

        if (!ifOpened[position]) {

            if (row > 1 && ifOpened[position - n]) {
                quickUnion.union(position, position - n);
            }

            if (col != 1 && ifOpened[position - 1]) {
                quickUnion.union(position, position - 1);
            }

            if (col != n && ifOpened[position + 1]) { // ???
                quickUnion.union(position, position + 1);
            }

            if (row < n && ifOpened[position + n]) {
                quickUnion.union(position, position + n);
            }


            ifOpened[position] = true;
            openedSites++;
        }
    }

    public boolean isOpen(int row, int col) {

        if (row < 1 || row > n || col < 1 || col > n)
            throw new IndexOutOfBoundsException();

        int position = n * (row - 1) + (col - 1);
        return ifOpened[position];
    }

    public boolean isFull(int row, int col) {

        if (row < 1 || row > n || col < 1 || col > n)
            throw new IndexOutOfBoundsException();

        return isOpen(row, col) && quickUnion.connected(n * (row - 1) + (col - 1), n * n);

    }

    public int numberOfOpenSites() {
        return openedSites;
    }

    public boolean percolates() {
        if(n == 1 && !this.isOpen(1, 1))
            return false;
        return quickUnion.connected(n * n, n * n + 1);
    }

}
