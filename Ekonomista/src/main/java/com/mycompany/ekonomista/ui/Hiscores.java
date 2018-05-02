/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.ui;

import com.mycompany.ekonomista.dao.HighscoresDao;
import com.mycompany.ekonomista.domain.GameService;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author samisaukkonen
 */
public class Hiscores {

    private VBox formLeaderBoards(Stage primaryStage, Mainmenu mainmenu, Game game, CompanyCreator companyCreator, GameService gameService, HighscoresDao hiScores) {
        List<String> scores = hiScores.getHighscores();

        VBox allScores = new VBox(10);

        for (String score : scores) {
            HBox scoreContainer = new HBox();

            Label scoreLabel = new Label(score);

            String[] parts = score.split(" ");

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Are you sure?");
                alert.setHeaderText("This action cannot be reversed!");
                alert.setContentText("Are you sure you want to delete highscore made by " + parts[0]);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    hiScores.removeHiScore(parts[0], Integer.parseInt(parts[1]));
                    primaryStage.setScene(this.getHiscoresScene(primaryStage, mainmenu, game, companyCreator, gameService, hiScores));
                }
            });

            scoreContainer.getChildren().addAll(scoreLabel, deleteButton);
            allScores.getChildren().add(scoreContainer);
        }

        return allScores;
    }

    public Scene getHiscoresScene(Stage primaryStage, Mainmenu mainmenu, Game game, CompanyCreator companyCreator, GameService gameService, HighscoresDao highScores) {
        BorderPane hiScoreView = new BorderPane();

        VBox allScores = this.formLeaderBoards(primaryStage, mainmenu, game, companyCreator, gameService, highScores);

        VBox buttonContainer = new VBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        
        Button returnToMenuButton = new Button("Return");
        returnToMenuButton.setAlignment(Pos.CENTER);
        returnToMenuButton.setOnAction(e -> {
            primaryStage.setScene(mainmenu.getMainmenuScene(primaryStage, companyCreator, gameService, game, this, highScores));
        });
        
        buttonContainer.getChildren().add(returnToMenuButton);

        ScrollPane sp = new ScrollPane(allScores);
        sp.setFitToWidth(true);
        
        VBox mainLabelContainer = new VBox(10);
        mainLabelContainer.setAlignment(Pos.CENTER);
        Label mainLabel = new Label("Leaderboards");
        mainLabel.setFont(new Font("Arial", 30));
        mainLabel.setAlignment(Pos.CENTER);
        mainLabelContainer.getChildren().add(mainLabel);
        
        hiScoreView.setCenter(buttonContainer);
        hiScoreView.setLeft(sp);
        hiScoreView.setTop(mainLabelContainer);

        return new Scene(hiScoreView, 640, 400);
    }
}
