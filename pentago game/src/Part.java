package com.company;

/**
 * this class holds information of each part of play ground
 * such as number of part and its own map
 *
 * @author ARMIN GODARZI
 */
public class Part {
    private int numberOfPart;
    private int[][] map;

    /**
     * constructor
     *
     * @param numberOfPart is number of part
     */
    public Part(int numberOfPart) {
        this.numberOfPart = numberOfPart;
        map = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                map[i][j] = 0;
            }
        }
    }

    /**
     * @return number of part
     */
    public int getNumberOfPart() {
        return numberOfPart;
    }

    /**
     * @return map of part
     */
    public int[][] getMap() {
        return map;
    }

    /**
     * @param i     i of spot
     * @param j     j of spot
     * @param value value of spot
     */
    public void setMap(int i, int j, int value) {
        map[i][j] = value;
    }

    /**
     * a method to rotate matrix as our map is a matrix
     * this method is copied from geeks for geeks
     */
    public void rotate() {
        int row = 0, col = 0;
        int n = 3, m = 3;
        int prev, curr;
        while (row < m && col < n) {

            if (row + 1 == m || col + 1 == n)
                break;
            prev = map[row + 1][col];
            for (int i = col; i < n; i++) {
                curr = map[row][i];
                map[row][i] = prev;
                prev = curr;
            }
            row++;
            for (int i = row; i < m; i++) {
                curr = map[i][n - 1];
                map[i][n - 1] = prev;
                prev = curr;
            }
            n--;
            if (row < m) {
                for (int i = n - 1; i >= col; i--) {
                    curr = map[m - 1][i];
                    map[m - 1][i] = prev;
                    prev = curr;
                }
            }
            m--;
            if (col < n) {
                for (int i = m - 1; i >= row; i--) {
                    curr = map[i][col];
                    map[i][col] = prev;
                    prev = curr;
                }
            }
            col++;
        }
    }

    /**
     * this method counts number of marbles in every part and returns it
     *
     * @return number of marbles
     */
    public int countMarble() {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (map[i][j] > 0)
                    count++;
            }
        }
        return count;
    }
}
