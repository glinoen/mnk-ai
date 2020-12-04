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
public class BoardTest {
    GameLogic logic;
    AiGomoku aiG;
    
    @Before
    public void setUp() {
        this.logic = new GameLogic(); 
        this.aiG = new AiGomoku(logic, 6);
    }
    
    @Test
    public void evalBoardVertTest() {
        logic.newGame(15,15,5);
        logic.stonePlacer(0, 0);
        logic.stonePlacer(0, 1);
        logic.stonePlacer(0, 2);
        logic.stonePlacer(0, 4);
        logic.stonePlacer(0, 5);
        logic.stonePlacer(0, 6);
        logic.stonePlacer(0, 12);
        logic.stonePlacer(0, 13);
        logic.stonePlacer(0, 14);
        logic.changePlayer();
        logic.stonePlacer(0, 8);
        logic.stonePlacer(0, 9);
        logic.stonePlacer(0, 10);
        logic.changePlayer();
        System.out.println(logic.getBoard());
        assertEquals(5000, logic.getBoard().evalBoardVert());
    }
    
    @Test
    public void evalBoardHorTest() {
        logic.newGame(15,15,5);
        logic.stonePlacer(0,0);
        logic.stonePlacer(1,0);
        logic.stonePlacer(2,0);
        logic.stonePlacer(4,0);
        logic.stonePlacer(5,0);
        logic.stonePlacer(6,0);
        logic.stonePlacer(12,0);
        logic.stonePlacer(13,0);
        logic.stonePlacer(14,0);
        logic.changePlayer();
        logic.stonePlacer(8,0);
        logic.stonePlacer(9,0);
        logic.stonePlacer(10,0);
        logic.changePlayer();
        System.out.println(logic.getBoard());
        assertEquals(5000, logic.getBoard().evalBoardHor());
    }
    
    @Test
    public void evalDiagonal() {
        logic.newGame(15,15,5);
        logic.stonePlacer(0,0);
        logic.stonePlacer(1,1);
        logic.stonePlacer(2,2);
        logic.stonePlacer(4,4);
        logic.stonePlacer(5,5);
        logic.stonePlacer(6,6);
        logic.stonePlacer(12,12);
        logic.stonePlacer(13,13);
        logic.stonePlacer(14,14);
        System.out.println("DIAG");
        System.out.println(logic.getBoard());
        System.out.println("");
        assertEquals(15000, logic.getBoard().evalDiagonals());
    }
    
//    @Test
//    public void evalDiagonal2() {
//        logic.newGame(15,15,5);
//        logic.stonePlacer(1,1);
//        logic.stonePlacer(2,2);
//        logic.stonePlacer(3,3);
//        logic.changePlayer();
//        logic.stonePlacer(1,2);
//        logic.stonePlacer(2,3);
//        int[] move = aiG.bestMoveFinder(-1);
//        System.out.println(move[0] + " " + move[1]);
//        System.out.println("DIAG2");
//        System.out.println(logic.getBoard());
//        System.out.println("");
//        assertEquals(0, logic.getBoard().evalBoard());
//    }
    
    @Test
    public void evalAntiDiagonal() {
        logic.newGame(15,15,5);
        logic.stonePlacer(0,14);
        logic.stonePlacer(1,13);
        logic.stonePlacer(2,12);
        logic.stonePlacer(4,10);
        logic.stonePlacer(5,9);
        logic.stonePlacer(6,8);
        logic.stonePlacer(12,2);
        logic.stonePlacer(13,1);
        logic.stonePlacer(14,0);
        System.out.println("ANTI:");
        System.out.println(logic.getBoard());
        System.out.println("");
        assertEquals(15000, logic.getBoard().evalDiagonals());
    }
}
