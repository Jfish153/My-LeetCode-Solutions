package leetcode_901To1200;
/**
 * Problem 994.Rotting Oranges
 * Link: https://leetcode.com/problems/rotting-oranges/
 * Author: Luke Ling
 * Date: 03/21/2020
 */

import java.util.ArrayDeque;
import java.util.Queue;

public class _994_RottingOranges {
    public static class Pair { // a customized data structure
        private int row;
        private int col;

        public Pair(int r, int c) {
            row = r;
            col = c;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public void setRow(int e) {
            row = e;
        }

        public void setCol(int e) {
            col = e;
        }

        public void setPair(int a, int b) {
            row = a;
            col = b;
        }
    }

    /**
     * time : O(m * n)
     * space : O(m * n)
     *
     * @param grid
     * @return fresh == 0 ? days : -1
     */
    public static int orangesRotting(int[][] grid) {
        // using BFS
        int m = grid.length;
        int n = grid[0].length;
        Queue<Pair> q = new ArrayDeque<>();
        int fresh = 0;
        int days = 0;
        for (int i = 0; i < m; i++) // initial processing
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1)
                    fresh++;
                else if (grid[i][j] == 2)
                    q.offer(new Pair(i, j));
            }

        while (!q.isEmpty() && fresh != 0) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Pair rotOrange = q.poll();
                assert rotOrange != null;
                int r = rotOrange.getRow();
                int c = rotOrange.getCol();
                if (r - 1 >= 0 && grid[r - 1][c] == 1) { // check up element
                    q.offer(new Pair(r - 1, c));
                    grid[r - 1][c] = 2;
                    fresh--;
                }
                if (r + 1 < m && grid[r + 1][c] == 1) { // check down element
                    q.offer(new Pair(r + 1, c));
                    grid[r + 1][c] = 2;
                    fresh--;
                }
                if (c - 1 >= 0 && grid[r][c - 1] == 1) { // check left element
                    q.offer(new Pair(r, c - 1));
                    grid[r][c - 1] = 2;
                    fresh--;
                }
                if (c + 1 < n && grid[r][c + 1] == 1) { // check right element
                    q.offer(new Pair(r, c + 1));
                    grid[r][c + 1] = 2;
                    fresh--;
                }
            }
            days++;
        }

        return fresh == 0 ? days : -1;
    }

    public static int orangesRotting2(int[][] grid) {
        /** BFS
         * Optimization one:
         Just using one number to represent i and j
         i,j -> one number
         i,j  -> i * column + j
         one integer -> i,j
         i = number / column
         j = number % column

         Optimization two:
         Two dimension array!
         */
        int m = grid.length;
        int n = grid[0].length;
        Queue<Integer> q = new ArrayDeque<>();
        int fresh = 0;
        int days = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    fresh++;
                } else if (grid[i][j] == 2) {
                    q.offer(i * n + j);
                }
            }

        while (!q.isEmpty() && fresh != 0) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int rotOrange = q.poll();
                int r = rotOrange / n;
                int c = rotOrange % n;
                int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // up down right left
                for (int[] dir : dirs) { // for loop to handle four directions
                    int r1 = r + dir[0];
                    int c1 = c + dir[1];
                    if (r1 >= 0 && r1 < m && c1 >= 0 && c1 < n && grid[r1][c1] == 1) {
                        q.offer(r1 * n + c1);
                        grid[r1][c1] = 2;
                        fresh--;
                    }
                }
            }
            days++;
        }

        return fresh == 0 ? days : -1;
    }

    public static void main(String[] args) {
        int[][] g = new int[][]{{2, 1, 1}, {1, 1, 0}, {0, 1, 1}};
        int res = orangesRotting2(g);
        System.out.println(res);
    }
}

