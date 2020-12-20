/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame.domain;

import datastructures.Stack;



/**
 *
 * @author julinden
 */
public class Board {

    public final int m;
    public final int n;
    public final int k;
    public final int winValue;
    private int win;
    private int[][] grid;
    private Stack occupiedSquares;

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
        this.winValue = 10000000;
        this.win = 0;
        this.grid = new int[n][m];
        this.occupiedSquares = new Stack(m, n);
    }

    /**
     * If free, places stone on board and adds it to stack
     * @param id
     * @param x
     * @param y
     * @return
     */
    public Boolean placeStone(int id, int x, int y) {
        if (this.grid[y][x] == 0) {
            this.grid[y][x] = id;
            int[] coordinate = new int[]{y, x};
            this.occupiedSquares.push(coordinate);
            return true;
        }
        return false;
    }

    /**
     * Removes added stone from board and stack
     * @param x
     * @param y
     */
    public void removeStone(int x, int y) {
        this.grid[y][x] = 0;
        this.occupiedSquares.pop();
    }

    public Stack getOccupiedSquares() {
        return occupiedSquares;
    }


    public boolean checkWin(int id) {
        return checkWinVert(id) || checkWinHor(id) || checkWinDiag(id); 
    }
    
    
    
    /**
     * Vertical win check
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
     * Horizontal win check
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
        return this.occupiedSquares.getTop() == this.m*this.n-1;
    }
    
    /**
     * Evaluate board value in all direction, if winning combination detected it is returned
     * @return
     */
    public int evalBoard() {
        this.win = 0;
        int vert = evalBoardVert();
        if (win != 0) {
            return win * winValue;
        }
        int hor = evalBoardHor();
        if (win != 0) {
            return win * winValue;
        }
        int diag = evalDiagonals();
        if (win != 0) {
            return win * winValue;
        }
        return vert + hor + diag;
    }
    
    /**
     * Evaluate vertical combinations
     * @return
     */
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
                    if(howMany == k) {
                        this.win = stoneValueCurrent;
                        return 0;
                    }
                    if(j < n-1) {
                        int stoneValueNext = this.grid[j+1][i];
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
    
    /**
     * Evaluate horizontal combinations
     * @return
     */
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
                    if(howMany == k) {
                        this.win = stoneValueCurrent;
                        return 0;
                    }
                    if(j < m-1) {
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
    
    /**
     * Evaluate diagonal combinations
     * @param x
     * @param y
     * @return
     */
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
                if(howMany == k) {
                    this.win = stoneValueCurrent;
                    return 0;
                }
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
    
    /**
     * Evaluate antidiagonal combinations
     * @param x
     * @param y
     * @return
     */
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
                if(howMany == k) {
                    this.win = stoneValueCurrent;
                    return 0;
                }
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
    
    /**
     * Helper method for diagonal evalution
     * @return
     */
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
    
    /**
     * Heuristic value of open and half-open combinations
     * @param count
     * @param start
     * @param end
     * @return
     */
    public int checkValue(int count, boolean start, boolean end) {
        if(count == k) {
            return 10000000;
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
