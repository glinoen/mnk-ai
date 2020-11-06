/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame;
import mnkgame.domain.GameLogic;
import mnkgame.ui.MnkGameUi;

/**
 *
 * @author julinden
 */
public class Main {
    public static void main(String[] args) {
        MnkGameUi.main(args);
    }
}


//public class Main {
//    public static void main(String[] args) {
//        GameLogic logiikka = new GameLogic();
//        logiikka.newGame(5, 6, 4);
//        System.out.println(logiikka.getBoard().toString());
//        for(int i=0; i<4;i++){
//            logiikka.stonePlacer(i, 4-i);
//        }
//        System.out.println(logiikka.getBoard().toString());
//        
//        System.out.println(logiikka.checkWin());
//        
//    }
//}