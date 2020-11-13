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
public class AiTest {
    GameLogic logic;
    Ai ai;
    
    @Before
    public void setUp() {
        this.logic = new GameLogic();    
    }
    
    @Test
    public void ticTacToeRightMove() {
        logic.newGame(3,3,3);
        this.ai = new Ai(this.logic);
        logic.stonePlacer(1, 1);
        logic.stonePlacer(1, 0);
        logic.changePlayer();
        logic.stonePlacer(2, 0);
        int[] move = ai.bestMoveFinder(-1);
        assertEquals(1, move[0]);
        assertEquals(2, move[1]);
    }
}
