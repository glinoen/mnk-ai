/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame.domain;

/**
 *
 * @author julinden
 */
public class Board {

    public final int m;
    public final int n;
    public final int k;
    
    private int[][] grid;

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public Board(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.grid = new int[n][m];
    }
    
    public Board makeCopy() {
        Board copyBoard = new Board(m, n, k);
        copyBoard.setGrid(copyGrid());
        return copyBoard;
    }

    public Boolean placeStone(int id, int x, int y) {
        if (this.grid[y][x] == 0) {
            this.grid[y][x] = id;
            return true;
        }
        return false;
    }

    public void removeStone(int x, int y) {
        this.grid[y][x] = 0;
    }

    public boolean checkWin(int id) {
        return checkWinVert(id) || checkWinHor(id) || checkWinDiag(id); 
    }
    
    /**
     * Vertical check
     * @param id
     * @return
     */
    public boolean checkWinVert(int id) {
        for (int i = 0; i < m; i++) {
            int howMany = 0;
            for (int j = 0; j < n; j++) {
                int stoneValue = this.grid[j][i];
                if (stoneValue == id) {
                    howMany++;
                    if (howMany == k) {
                        return true;
                    }
                } else {
                    howMany = 0;
                }
            }
            
        }
        return false;
    }
    
    /**
     * Horizontal check
     * @param id
     * @return
     */
    public boolean checkWinHor(int id) {
        for (int i = 0; i < n; i++) {
            int howMany = 0;
            for (int j = 0; j < m; j++) {
                int stoneValue = this.grid[i][j];
                if (stoneValue == id) {
                    howMany++;
                    if (howMany == k) {
                        return true;
                    }
                } else {
                    howMany = 0;
                }
            }
            
        }
        return false;
    }
    
    /**
     * Check for both diagonal and antidiagonal
     * @param id
     * @return
     */
    public boolean checkWinDiag(int id) {
        for (int i = 0; i < m; i++) {
            if (diagCheck(id, i, 0) || antiDiagCheck(id, i, n - 1)) {
                return true;
            }
        }    
        for (int i = 0; i < n; i++) {
            if (diagCheck(id, 0, i) || antiDiagCheck(id, 0, n - 1 - i)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Diagonal check
     * @param id
     * @param x
     * @param y
     * @return
     */
    public boolean diagCheck(int id, int x, int y) {
        int howMany = 0;
        int xx = x;
        int yy = y;
        while (xx < m && yy < n) {
            int stoneValue = this.grid[yy][xx];
            if (stoneValue == id) {
                howMany++;
                if (howMany == k) {
                    return true;
                }
            } else {
                howMany = 0;
            }
            xx++;
            yy++;
        }
        return false;
    }
    
    /**
     * Antidiagonal check
     * @param id
     * @param x
     * @param y
     * @return
     */
    public boolean antiDiagCheck(int id, int x, int y) {
        int howMany = 0;
        int xx = x;
        int yy = y;
        while (xx < m && yy >= 0) {
            int stoneValue = this.grid[yy][xx];
            if (stoneValue == id) {
                howMany++;
                if (howMany == k) {
                    return true;
                }
            } else {
                howMany = 0;
            }
            xx++;
            yy--;
        }
        return false;
    }
    
    public boolean checkFull() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (this.grid[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int[][] copyGrid() {
        int[][] copy = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                copy[i][j] = this.grid[i][j];
            }
        }
        return copy;
    }
    
    /**
     * Method to print the state of the board
     * @return
     */
    @Override
    public String toString() { 
        String s = "";
        for (int i = 0; i < n; i++) {
            String tempS = "";
            for (int j = 0; j < m; j++) {
                int stoneValue = this.grid[i][j];
                tempS = tempS.concat(Integer.toString(stoneValue) + " ");
            }
            s = s.concat(tempS + " \n");
        }
        return s;
    } 
}
