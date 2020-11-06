/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame.ui;

import static java.lang.Math.max;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mnkgame.domain.GameLogic;

/**
 *
 * @author julinden
 */
public class MnkGameUi extends Application{
    private GameLogic logic;
    private Stage stage;
    
    private Scene gameSetupScene;
    private Scene mainScene;
    
    @Override
    public void init() throws Exception { 
        logic = new GameLogic();
    }
    
    @Override
    public void start(Stage stage) {  
        this.stage = stage;
        this.gameSetupScene = createGameSetupScene();
        stage.setTitle("M,N,K");
        stage.setScene(this.gameSetupScene);
        stage.show();
    }
    
    public Scene createGameSetupScene() {
        VBox startPane = new VBox(10);
        VBox inputPane = new VBox(10);
        startPane.setPadding(new Insets(10));
        Label gridWidthLabel = new Label("Enter grid width (between 3-15)");
        TextField gridWidthInput = new TextField();
        Label gridHeightLabel = new Label("Enter grid height (between 3-15)");
        TextField gridHeightInput = new TextField();
        Label kLabel = new Label("Enter k (smaller than the largest dimension, minimum 3)");
        TextField kInput = new TextField();
        
        inputPane.getChildren().addAll(gridWidthLabel, gridWidthInput, gridHeightLabel, gridHeightInput, kLabel, kInput);
        Label errorMessage = new Label();
        
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> { 
            try { 
                int tempGridWidth = Integer.parseInt(gridWidthInput.getText());
                int tempGridHeight = Integer.parseInt(gridHeightInput.getText());
                int tempK = Integer.parseInt(kInput.getText());
            
                if ( tempGridWidth > 2 && tempGridWidth < 16 && tempGridHeight > 2 && tempGridHeight < 16){
                    if(tempK > 2 && tempK <= max(tempGridWidth, tempGridHeight)) {
                        logic.newGame(tempGridWidth, tempGridHeight, tempK);

                        errorMessage.setText("");
                        this.mainScene = createMainScene();
                        sceneChange(this.mainScene);
                        gridWidthInput.setText("");
                        gridHeightInput.setText("");
                        kInput.setText("");
                    } else {
                        errorMessage.setText("improper k size");
                        errorMessage.setTextFill(Color.RED);
                    }
                    
                } else {
                    errorMessage.setText("improper grid size");
                    errorMessage.setTextFill(Color.RED);
                } 
            } catch (NumberFormatException z) { 
                errorMessage.setText("type a number, not text");
                errorMessage.setTextFill(Color.RED);
            }
                 
        });  
        

        startPane.getChildren().addAll(errorMessage, inputPane, startButton);       
        
        gameSetupScene = new Scene(startPane, 800, 800); 
        return gameSetupScene;
    }
    
    private Scene createMainScene() {
        BorderPane gamePane = new BorderPane();
        
        Font equalSizeFont = Font.font("Monospaced", 30);
        
        String playerTurn;
        if(logic.getCurrentPlayer() == 1) {
            playerTurn = "X";
        } else {
            playerTurn = "O";
        }
        Label turnLabel = new Label(playerTurn + "'s turn");
        turnLabel.setFont(equalSizeFont);
        gamePane.setTop(turnLabel);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10,10,10,10));
        
        for(int i=0; i < logic.getBoard().n; i++) {
            for(int j=0; j < logic.getBoard().m; j++) {
                int stoneValue = logic.getBoard().getGrid()[i][j];
                String buttonValue = " ";
                switch(stoneValue) {
                    case 1:
                        buttonValue = "X";
                        break;
                    case -1:
                        buttonValue = "O";
                        break;
                    case 0:
                        buttonValue = " ";
                        break;
                }
                Button button = new Button(buttonValue);
                button.setFont(equalSizeFont);
                
                grid.add(button, j, i);
                
                int xx = j;
                int yy = i;
                
                button.setOnAction((event) -> {
                    if(logic.stonePlacer(xx, yy)) {
                        button.setText(playerTurn);
                        if(logic.checkWin()){
                            start(stage);
                        } else {
                            logic.changePlayer();
                            this.mainScene = createMainScene();
                            sceneChange(this.mainScene);
                        }
                    } else {
                        ;
                    }
                });
            }
        }
        
        gamePane.setCenter(grid);
        mainScene = new Scene(gamePane, 800,800);
        return mainScene;
    }
    
    @Override
    public void stop() {
        System.out.println("lobu");
        Platform.exit();
        System.exit(0);
    }    
    
    public static void main(String[] args) {
        launch(args);
    }

    public void sceneChange(Scene scene) {
        this.stage.setScene(scene);
    }

    
}
