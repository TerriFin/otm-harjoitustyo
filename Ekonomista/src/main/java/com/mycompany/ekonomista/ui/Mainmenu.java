/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.ui;

import com.mycompany.ekonomista.dao.HighscoresDao;
import com.mycompany.ekonomista.domain.Company;
import com.mycompany.ekonomista.domain.GameService;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author samisaukkonen
 */
public class Mainmenu {

    public Scene getMainmenuScene(Stage primaryStage, CompanyCreator companyCreator, GameService gameService, Game game, Hiscores hiScoresScene, HighscoresDao hiScores) {
        VBox mainMenu = new VBox(10);

        VBox titleNodes = new VBox(30);
        titleNodes.setAlignment(Pos.CENTER);

        Label mainTitle = new Label("Ekonomista");
        mainTitle.setFont(new Font("Arial", 50));

        Label mainTitleHelper = new Label("If starting money set over 5000, no new leaderboards scores can be set.");
        mainTitleHelper.setFont(new Font("Arial", 12));

        titleNodes.getChildren().addAll(mainTitle, mainTitleHelper);

        HBox gameStartingInputs = new HBox(10);
        gameStartingInputs.setAlignment(Pos.CENTER);

        Label userNameLabel = new Label("Insert player name");

        TextField playerNameInput = new TextField();
        playerNameInput.setMaxWidth(100);

        Label startingMoneyLabel = new Label("Insert starting money");

        TextField startingMoneyInput = new TextField();
        startingMoneyInput.setText("5000");
        startingMoneyInput.setMaxWidth(50);

        gameStartingInputs.getChildren().addAll(userNameLabel, playerNameInput, startingMoneyLabel, startingMoneyInput);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        Button startButton = new Button("Start game");
        startButton.setOnAction(e -> {

            // start game
            if (playerNameInput.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid input");
                alert.setHeaderText("Player name missing");
                alert.setContentText("Please insert player name");

                alert.showAndWait();
            } else if (playerNameInput.getText().contains(" ")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid input");
                alert.setContentText("Player name cannot contain spaces");

                alert.showAndWait();
            } else {
                try {
                    gameService.setUser(playerNameInput.getText(), Integer.parseInt(startingMoneyInput.getText()));
                    primaryStage.setScene(game.getGameScene(primaryStage, gameService, hiScores));

                    Timeline mainGameLoop = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            game.updateGame();
                        }
                    }));

                    mainGameLoop.setCycleCount(Timeline.INDEFINITE);
                    mainGameLoop.play();
                } catch (Exception exception) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid input");
                    alert.setHeaderText("Invalid player money amount");
                    alert.setContentText("Please check that there is no spaces and/or letters in money input field");

                    alert.showAndWait();
                }
            }
        });

        Button newCompanyButton = new Button("Create new company");
        newCompanyButton.setOnAction(e -> {

            // switch scene to company creator
            primaryStage.setScene(companyCreator.getCompanyCreatorScene(primaryStage, this, gameService, game, hiScoresScene, hiScores));
        });

        buttons.getChildren().addAll(startButton, newCompanyButton);

        HBox hiScoreContainer = new HBox(10);
        hiScoreContainer.setAlignment(Pos.CENTER);

        Button hiScoreButton = new Button("HiScores");
        hiScoreButton.setAlignment(Pos.CENTER);
        hiScoreButton.setOnAction(e -> {
            primaryStage.setScene(hiScoresScene.getHiscoresScene(primaryStage, this, game, companyCreator, gameService, hiScores));
        });

        hiScoreContainer.getChildren().add(hiScoreButton);

        mainMenu.getChildren().addAll(titleNodes, gameStartingInputs, buttons, hiScoreContainer);

        return new Scene(mainMenu, 600, 250);
    }
}
