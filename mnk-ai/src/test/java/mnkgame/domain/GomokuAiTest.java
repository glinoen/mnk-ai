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
    
//    @Test
//    public void ticTacToeRightMove() {
//        logic.newGame(15,15,5);
//        this.ai = new AiGomoku(this.logic, 3);
//        logic.stonePlacer(6, 7);
//        logic.stonePlacer(7, 7);
//        logic.stonePlacer(8, 7);
//        logic.changePlayer();
//        logic.stonePlacer(2, 5);
//        logic.stonePlacer(3, 5);
//        System.out.println(logic.getBoard().evalBoard());
//        System.out.println(logic.getBoard());
//        int[] move = ai.bestMoveFinder(-1);
//        System.out.println("");
//        System.out.println(move[0]);
//        System.out.println(move[1]);
//        assertEquals(1, move[0]);
//        assertEquals(2, move[1]);
//    }
}
