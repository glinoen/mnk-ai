/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame.domain;

import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author julinden
 */
public class GomokuAiTest {
    GameLogic logic;
    AiGomoku ai;
    
    @Before
    public void setUp() {
        this.logic = new GameLogic();    
    }
    
    
    
    @Test
    public void blockOpenThree() {
        logic.newGame(10,10,5,3);
        this.ai = logic.getAi();
        logic.stonePlacer(5, 4);
        logic.stonePlacer(6, 4);
        logic.stonePlacer(7, 4);
        logic.changePlayer();
        System.out.println(logic.getBoard());
        int[] move = ai.bestMoveFinder(-1);
        assertEquals(4, move[0]);
        assertEquals(4, move[1]);
    }
    
    @Test
    public void winInsteadBlock() {
        logic.newGame(10,10,5,3);
        this.ai = logic.getAi();
        logic.stonePlacer(5, 4);
        logic.stonePlacer(6, 4);
        logic.stonePlacer(7, 4);
        logic.stonePlacer(8, 4);
        logic.changePlayer();
        logic.stonePlacer(5, 2);
        logic.stonePlacer(6, 2);
        logic.stonePlacer(7, 2);
        logic.stonePlacer(8, 2);
        System.out.println(logic.getBoard());
        int[] move = ai.bestMoveFinder(-1);
        assertEquals(4, move[0]);
        assertEquals(2, move[1]);
    }
    
    @Test
    public void agressiveInsteadBlock() {
        logic.newGame(10,10,5,3);
        this.ai = logic.getAi();
        logic.stonePlacer(5, 4);
        logic.stonePlacer(6, 4);
        logic.stonePlacer(7, 4);
        logic.stonePlacer(5, 6);
        logic.stonePlacer(6, 6);
        logic.stonePlacer(7, 6);
        logic.changePlayer();
        logic.stonePlacer(5, 2);
        logic.stonePlacer(6, 2);
        logic.stonePlacer(7, 2);
        System.out.println(logic.getBoard());
        int[] move = ai.bestMoveFinder(-1);
        assertEquals(4, move[0]);
        assertEquals(2, move[1]);
    }
    
    @Test
    public void blockOpenThreeDifPlayer() {
        logic.newGame(10,10,5,3);
        this.ai = logic.getAi();
        logic.changePlayer();
        logic.stonePlacer(5, 4);
        logic.stonePlacer(6, 4);
        logic.stonePlacer(7, 4);
        logic.changePlayer();
        System.out.println(logic.getBoard());
        int[] move = ai.bestMoveFinder(1);
        assertEquals(4, move[0]);
        assertEquals(4, move[1]);
    }
    
    @Test
    public void winInsteadBlockDifPlayer() {
        logic.newGame(10,10,5,3);
        this.ai = logic.getAi();
        logic.stonePlacer(5, 4);
        logic.stonePlacer(6, 4);
        logic.stonePlacer(7, 4);
        logic.stonePlacer(8, 4);
        logic.changePlayer();
        logic.stonePlacer(5, 2);
        logic.stonePlacer(6, 2);
        logic.stonePlacer(7, 2);
        logic.stonePlacer(8, 2);
        System.out.println(logic.getBoard());
        int[] move = ai.bestMoveFinder(1);
        assertEquals(4, move[0]);
        assertEquals(4, move[1]);
    }
    
    @Test
    public void firstMoveInMiddle() {
        logic.newGame(10,10,5,3);
        this.ai = logic.getAi();
        int[] move = ai.bestMoveFinder(-1);
        assertEquals(5, move[0]);
        assertEquals(5, move[1]);
    }
    
}
