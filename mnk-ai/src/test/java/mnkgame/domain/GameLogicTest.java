/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame.domain;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author julinden
 */
public class GameLogicTest {
    GameLogic logic;
    
    @Before
    public void setUp() {
        this.logic = new GameLogic();    
    }
    
    @Test
    public void horizontalWin() {
        logic.newGame(8,7,5);
        for(int i=0; i<5;i++){
            logic.stonePlacer(i+1, 1);
        }
        assertEquals(true, logic.checkWin());    
    }
    
    @Test
    public void verticalWin() {
        logic.newGame(8,7,5);
        for(int i=0; i<5;i++){
            logic.stonePlacer(1, 6-i);
        }
        assertEquals(true, logic.checkWin());    
    }
    
    @Test
    public void diagonalWin() {
        logic.newGame(8,7,5);
        for(int i=0; i<5;i++){
            logic.stonePlacer(i, 1+i);
        }
        assertEquals(true, logic.checkWin());    
    }
    
    @Test
    public void antiDiagonalWin() {
        logic.newGame(8,7,5);
        for(int i=0; i<5;i++){
            logic.stonePlacer(i, 6-i);
        }
        assertEquals(true, logic.checkWin());  
    }
    
    @Test
    public void boardIsFull() {
        logic.newGame(15, 12, 3);
        for(int i=0; i<12; i++) {
            for(int j=0; j<15; j++) {
                logic.stonePlacer(j, i);
            }
        }
        assertEquals(true, logic.checkFull());
    }
}
