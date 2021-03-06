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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mnkgame.domain.AiGomoku;
import mnkgame.domain.GameLogic;

/**
 *
 * @author julinden
 */
public class MnkGameUi extends Application{
    private GameLogic logic;
    private AiGomoku aiG;
    private Stage stage;
    
    private Scene gameSetupScene;
    private Scene mainScene;
    private boolean firstTurn;
    private int bot;
    
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
        Label gridWidthLabel = new Label("Enter grid width (between 5-15)");
        TextField gridWidthInput = new TextField();
        Label gridHeightLabel = new Label("Enter grid height (between 5-15)");
        TextField gridHeightInput = new TextField();
        Label kLabel = new Label("Enter k (smaller than the largest dimension, minimum 5)");
        TextField kInput = new TextField();
        Label depthLabel = new Label("Enter depth(recommended 0-3)");
        TextField depthInput = new TextField();
        CheckBox aiStartCheck = new CheckBox("Ai starts the game(otherwise you start)");
        CheckBox aiCheck = new CheckBox("Ai vs Ai");
        inputPane.getChildren().addAll(gridWidthLabel, gridWidthInput, gridHeightLabel, gridHeightInput, kLabel, kInput,depthLabel, depthInput,aiStartCheck, aiCheck);
        Label errorMessage = new Label();
        
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> { 
            try { 
                int tempGridWidth = Integer.parseInt(gridWidthInput.getText());
                int tempGridHeight = Integer.parseInt(gridHeightInput.getText());
                int tempK = Integer.parseInt(kInput.getText());
                int tempDepth = Integer.parseInt(depthInput.getText());
            
                if ( tempGridWidth > 4 && tempGridWidth < 16 && tempGridHeight > 4 && tempGridHeight < 16){
                    if(tempK > 4 && tempK <= max(tempGridWidth, tempGridHeight)) {
                        logic.newGame(tempGridWidth, tempGridHeight, tempK, tempDepth);
                        this.aiG = logic.getAi();
                        
                        System.out.println("new ai");
                        errorMessage.setText("");
                        if(aiStartCheck.isSelected()) {
                            int[] aiMove = new int[2];
                            aiMove = aiG.bestMoveFinder(logic.getCurrentPlayer());
                            this.bot = 1;
                            logic.stonePlacer(aiMove[0], aiMove[1]);   
                            logic.changePlayer();

                        } else {
                            this.bot = -1;
                        }
                        this.mainScene = aiCheck.isSelected() ? createMainSceneAi() : createMainScene();
                        
                        this.firstTurn = true;
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
        System.out.println("current: " + logic.getCurrentPlayer());
        String playerTurn;

        HBox upPane = new HBox(10);
        Label turnLabel = new Label(playerString(logic.getCurrentPlayer()) + "'s turn");
        turnLabel.setFont(equalSizeFont);
        Button startButton = new Button("New Game");
        startButton.setOnAction((event) -> {
                    start(stage);
                });
        upPane.getChildren().addAll(turnLabel, startButton);
        gamePane.setTop(upPane);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10,10,10,10));
        
        Button[][] buttons = new Button[logic.getBoard().m][logic.getBoard().n];
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
                buttons[j][i] = button;
                
                
                int xx = j;
                int yy = i;
                

                button.setOnAction((event) -> {
                    System.out.println("nappia painettu");
                    if(logic.stonePlacer(xx, yy)) {
                        button.setText(playerString(logic.getCurrentPlayer()));
                        if(logic.checkWin() || logic.checkFull()){
                            turnLabel.setText("GAME OVER");
                            // start(stage);
                        } else {
                            logic.changePlayer();
                            turnLabel.setText(playerString(logic.getCurrentPlayer()) + "'s turn");
                            if (logic.getCurrentPlayer() == this.bot) {
                                int[] aiMove = new int[2];
                                aiMove = aiG.bestMoveFinder(this.bot);

                                Button buttonAi = buttons[aiMove[0]][aiMove[1]];
                                buttonAi.fire();
                            }
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
    
    private Scene createMainSceneAi() {
        BorderPane gamePane = new BorderPane();
        
        Font equalSizeFont = Font.font("Monospaced", 30);
        System.out.println("current: " + logic.getCurrentPlayer());
        String playerTurn;
        
        HBox upPane = new HBox(10);
        Label turnLabel = new Label(playerString(logic.getCurrentPlayer()) + "'s turn");
        turnLabel.setFont(equalSizeFont);
        Button startButton = new Button("New Game");
        startButton.setOnAction((event) -> {
                    start(stage);
                });
        upPane.getChildren().addAll(turnLabel, startButton);
        gamePane.setTop(upPane);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10,10,10,10));
        
        Button[][] buttons = new Button[logic.getBoard().m][logic.getBoard().n];
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
                buttons[j][i] = button;
                
                
                int xx = j;
                int yy = i;
                

                button.setOnAction((event) -> {
                    System.out.println("nappia painettu");
                    if(logic.stonePlacer(xx, yy)) {
                        button.setText(playerString(logic.getCurrentPlayer()));
                        if(logic.checkWin() || logic.checkFull()){
                            turnLabel.setText("GAME OVER");
                            //start(stage);
                        } else {
                            logic.changePlayer();
                            turnLabel.setText(playerString(logic.getCurrentPlayer()) + "'s turn");
                            int[] aiMove = new int[2];
                            aiMove = aiG.bestMoveFinder(logic.getCurrentPlayer());
                            Button buttonAi = buttons[aiMove[0]][aiMove[1]];
                            buttonAi.fire();
                            
                        }
                    } else {
                        ;
                    }
                });
                
                
                
            }
        }
        if(this.firstTurn) {
            int[] aiMove = new int[2];
            aiMove = aiG.bestMoveFinder(logic.getCurrentPlayer());
            this.firstTurn = false;
            Button buttonAi = buttons[aiMove[0]][aiMove[1]];
            buttonAi.fire();
        }
        
        
        
        
        
        gamePane.setCenter(grid);
        mainScene = new Scene(gamePane, 800,800);
        return mainScene;
    }
    
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
    for (Node node : gridPane.getChildren()) {
        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
            return node;
        }
    }
    return null;
    }
    
    private String playerString(int playerNumber) {
        if (playerNumber == 1) {
            return "X";
        } else {
            return "O";
        }
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
