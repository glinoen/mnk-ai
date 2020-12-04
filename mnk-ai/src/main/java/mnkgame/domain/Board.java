/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame.domain;

import java.util.Stack;

/**
 *
 * @author julinden
 */
public class Board {

    public final int m;
    public final int n;
    public final int k;
    
    private int[][] grid;
    private Stack<int[]> occupiedSquares;

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
        this.occupiedSquares = new Stack<>();
    }
    
    public Board makeCopy() {
        Board copyBoard = new Board(m, n, k);
        copyBoard.setGrid(copyGrid());
        return copyBoard;
    }

    public Boolean placeStone(int id, int x, int y) {
        if (this.grid[y][x] == 0) {
            this.grid[y][x] = id;
            int[] coordinate = new int[]{y,x};
            this.occupiedSquares.push(coordinate);
            return true;
        }
        return false;
    }

    public void removeStone(int x, int y) {
        this.grid[y][x] = 0;
        this.occupiedSquares.pop();
    }

    public Stack<int[]> getOccupiedSquares() {
        return occupiedSquares;
    }

    public void setOccupiedSquares(Stack<int[]> occupiedSquares) {
        this.occupiedSquares = occupiedSquares;
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
    
    public int evalBoard() {
        return evalBoardVert()+evalBoardHor()+evalDiagonals();
    }
    
    public int evalBoardVert() {
        int total = 0;
        for (int i = 0; i < m; i++) {
            int howMany = 0;
            boolean start = false;
            boolean end = false;
            for (int j = 0; j < n; j++) {
                int stoneValueCurrent = this.grid[j][i];
                if (stoneValueCurrent != 0) {
                    howMany++;
                    if(j < n-1) {
                        int stoneValueNext = this.grid[j+1][i];
                        if(stoneValueNext == stoneValueCurrent) {
                            
                        } else if(stoneValueNext == 0) {
                            end = true;
                            total += checkValue(howMany, start, end) * stoneValueCurrent;
                            end = false; start = false;
                            howMany = 0;
                        } else {
                            total += checkValue(howMany, start, end) * stoneValueCurrent; 
                            start = false;
                            howMany = 0;
                        }
                    } else {
                        total += checkValue(howMany, start, end) * stoneValueCurrent; 
                        start = false;
                        howMany = 0;
                    }
                } else {
                    start = true;
                    howMany = 0;
                }
            }
        }
        return total;
    }
    
    public int evalBoardHor() {
        int total = 0;
        for (int i = 0; i < n; i++) {
            int howMany = 0;
            boolean start = false;
            boolean end = false;
            for (int j = 0; j < m; j++) {
                int stoneValueCurrent = this.grid[i][j];
                if (stoneValueCurrent != 0) {
                    howMany++;
                    if(j < n-1) {
                        int stoneValueNext = this.grid[i][j+1];
                        if(stoneValueNext == stoneValueCurrent) {
                            
                        } else if(stoneValueNext == 0) {
                            end = true;
                            total += checkValue(howMany, start, end) * stoneValueCurrent;
                            end = false; 
                            start = false;
                            howMany = 0;
                        } else {
                            total += checkValue(howMany, start, end) * stoneValueCurrent; 
                            start = false;
                            howMany = 0;
                        }
                    } else {
                        total += checkValue(howMany, start, end) * stoneValueCurrent; 
                        start = false;
                        howMany = 0;
                    }
                } else {
                    start = true;
                    howMany = 0;
                }
            }
        }
        return total;
    }
    
    public int evalDiag(int x, int y) {
        int total = 0;
        int howMany = 0;
        int xx = x;
        int yy = y;
        boolean start = false;
        boolean end = false;
        while (xx < m && yy < n) {
            int stoneValueCurrent = this.grid[yy][xx];
            if (stoneValueCurrent != 0) {
                howMany++;
                if(yy < n-1 && xx < m - 1) {
                    int stoneValueNext = this.grid[yy+1][xx+1];
                    if(stoneValueNext == stoneValueCurrent) {
                    } else if(stoneValueNext == 0) {
                        end = true;
                        total += checkValue(howMany, start, end) * stoneValueCurrent;
                        end = false; 
                        start = false;
                        howMany = 0;
                    } else {
                        total += checkValue(howMany, start, end) * stoneValueCurrent; 
                        start = false;
                        howMany = 0;
                    }
                } else {
                    total += checkValue(howMany, start, end) * stoneValueCurrent; 
                    start = false;
                    howMany = 0;
                }
            } else {
                start = true;
                howMany = 0;
            }
            xx++;
            yy++;
        }
        return total;
    }
    
    public int evalAntiDiag(int x, int y) {
        int total = 0;
        int howMany = 0;
        int xx = x;
        int yy = y;
        boolean start = false;
        boolean end = false;
        while (xx < m && yy >= 0) {
            int stoneValueCurrent = this.grid[yy][xx];
            if (stoneValueCurrent != 0) {
                howMany++;
                if(yy > 0 && xx < m - 1) {
                    int stoneValueNext = this.grid[yy-1][xx+1];
                    if(stoneValueCurrent == stoneValueNext) {

                    } else if(stoneValueNext == 0) {
                        end = true;
                        total += checkValue(howMany, start, end) * stoneValueCurrent;
                        end = false; 
                        start = false;
                        howMany = 0;
                    } else {
                        total += checkValue(howMany, start, end) * stoneValueCurrent; 
                        start = false;
                        howMany = 0;
                    }
                } else {
                    total += checkValue(howMany, start, end) * stoneValueCurrent; 
                    start = false;
                    howMany = 0;
                }
            } else {
                start = true;
                howMany = 0;
            }
            xx++;
            yy--;
        }
        return total;
    }
    
    public int evalDiagonals() {
        int total = 0;
        for (int i = 0; i < m; i++) {
            total += evalDiag(i, 0) + evalAntiDiag(i, n - 1);
        }    
        for (int i = 1; i < n; i++) {
            total += evalDiag(0, i) + evalAntiDiag(0, n - 1 - i);
        }
        return total;
    }
    
    public int checkValue(int count, boolean start, boolean end) {
        if(count == k) {
            return 100000;
        }
        if(count == k-1 && start && end) {
            return 15000;
        }
        if(count == k-2 && start && end) {
            return 10000;
        }
        if(count == k-1 && (start || end)) {
            return 5000;
        }
        if(count == k-3 && start && end) {
            return 4000;
        }
        if(count == k-2 && (start || end)) {
            return 2500;
        }
        if(count == k-3 && (start || end)) {
            return 1000;
        }
        return 0;
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
                if(stoneValue == 1) {
                    tempS = tempS.concat(Integer.toString(stoneValue) + " ");
                } else if(stoneValue == 0) {
                    tempS = tempS.concat("|" + " ");
                } else{
                    tempS = tempS.concat("0" + " ");
                }
                
            }
            s = s.concat(tempS + " \n");
        }
        return s;
    } 
}
