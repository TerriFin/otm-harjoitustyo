/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.ui;

import com.mycompany.ekonomista.dao.CompanyDao;
import com.mycompany.ekonomista.dao.DummyCompanyDao;
import com.mycompany.ekonomista.domain.Company;
import com.mycompany.ekonomista.domain.GameService;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author samisaukkonen
 */
public class GameUi extends Application {
    private GameService gameService;
    private CompanyDao companyDao;
    
    private Scene menuScene;
    private Scene newCompanyScene;
    private Scene gameScene;
    
    @Override
    public void init() throws Exception {
        companyDao = new DummyCompanyDao();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Mainmenu
        
        VBox mainMenu = new VBox(10);
        
        
        VBox titleNodes = new VBox(30);
        titleNodes.setAlignment(Pos.CENTER);
        
        Label mainTitle = new Label("Ekonomista");
        mainTitle.setFont(new Font("Arial", 50));
        
        Label mainTitleHelper = new Label("If starting money set over 5000, no new leaderboards scores can be set.");
        mainTitleHelper.setFont(new Font("Arial", 12));
        
        titleNodes.getChildren().addAll(mainTitle, mainTitleHelper);
        
        
        HBox startingMoney = new HBox(10);
        startingMoney.setAlignment(Pos.CENTER);
        
        Label startingMoneyLabel = new Label("Starting money");
        
        TextField startingMoneyInput = new TextField();
        startingMoneyInput.setMaxWidth(50);
        
        startingMoney.getChildren().addAll(startingMoneyLabel, startingMoneyInput);
        
        
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        
        Button startButton = new Button("Start game");
        startButton.setOnAction(e->{
            
            // start game
            
            List<Company> companies = companyDao.getAll();
            for (Company company : companies) {
                System.out.println(company.getCompanyName());
            }
        });
        
        Button newCompanyButton = new Button("Create new company");
        newCompanyButton.setOnAction(e->{
            primaryStage.setScene(newCompanyScene);
        });
        
        buttons.getChildren().addAll(startButton, newCompanyButton);
        
        mainMenu.getChildren().addAll(titleNodes, startingMoney, buttons);
        
        menuScene = new Scene(mainMenu, 600, 250);
        
        
        // Company creator menu
        
        VBox companyCreatorMenu = new VBox(30);
        
        
        VBox companyCreatorLabelContainer = new VBox(30);
        companyCreatorLabelContainer.setAlignment(Pos.CENTER);
        
        Label companyCreatorLabel = new Label("Insert information for new company:");
        companyCreatorLabel.setFont(new Font("Arial", 20));
        
        companyCreatorLabelContainer.getChildren().add(companyCreatorLabel);
        
        
        HBox nameNodes = new HBox(10);
        
        Label nameLabel = new Label("Name:");
        nameLabel.setFont(new Font("Arial", 10));
        
        TextField newName = new TextField();
        newName.setMaxWidth(100);
        
        nameNodes.getChildren().addAll(nameLabel, newName);
        
        
        HBox companyIndexNodes = new HBox(10);
        
        Label indexLabel = new Label("Starting index:");
        indexLabel.setFont(new Font("Arial", 10));
        
        TextField startingIndex = new TextField();
        startingIndex.setMaxWidth(50);
        
        companyIndexNodes.getChildren().addAll(indexLabel, startingIndex);
        
        
        HBox courseChangeNodes = new HBox(10);
        
        Label courseChangeLabel = new Label("Chance to change course (must be between 0 and 100):");
        courseChangeLabel.setFont(new Font("Arial", 10));
        
        TextField chanceToChangeCourse = new TextField();
        chanceToChangeCourse.setMaxWidth(50);
        
        courseChangeNodes.getChildren().addAll(courseChangeLabel, chanceToChangeCourse);
        
        
        HBox maxTickChangeNodes = new HBox(10);
        
        Label maxTickChangeLabel = new Label("Max change that can happen in one tick:");
        maxTickChangeLabel.setFont(new Font("Arial", 10));
        
        TextField maxTickChange = new TextField();
        maxTickChange.setMaxWidth(50);
        
        maxTickChangeNodes.getChildren().addAll(maxTickChangeLabel, maxTickChange);
        
        
        HBox maxChangePerTickNodes = new HBox(10);
        
        Label maxChangePerTickLabel = new Label("Max amount of change that can happen in one tick:");
        maxChangePerTickLabel.setFont(new Font("Arial", 10));
        
        TextField maxChangePerTick = new TextField();
        maxChangePerTick.setMaxWidth(50);
        
        maxChangePerTickNodes.getChildren().addAll(maxChangePerTickLabel, maxChangePerTick);
        
        
        HBox companyCreatorButtons = new HBox(30);
        companyCreatorButtons.setAlignment(Pos.CENTER);
        
        Button menu = new Button("Go back");
        menu.setOnAction(e->{
            primaryStage.setScene(menuScene);
        });
        
        Button create = new Button("Create");
        create.setOnAction(e->{
            String companyName = newName.getText();
            int companyIndex = Integer.parseInt(startingIndex.getText());
            int companyChanceToChangeCourse = Integer.parseInt(chanceToChangeCourse.getText());
            int companyMaxTickChange = Integer.parseInt(maxTickChange.getText());
            int companyMaxChangePerTick = Integer.parseInt(maxChangePerTick.getText());
            
            companyDao.create(companyName, companyIndex, companyChanceToChangeCourse, companyMaxTickChange, companyMaxChangePerTick);
            
            newName.setText("");
            startingIndex.setText("");
            chanceToChangeCourse.setText("");
            maxTickChange.setText("");
            maxChangePerTick.setText("");
        });
        
        companyCreatorButtons.getChildren().addAll(menu, create);
        
        
        companyCreatorMenu.getChildren().addAll(companyCreatorLabelContainer, nameNodes, companyIndexNodes, courseChangeNodes, maxTickChangeNodes, maxChangePerTickNodes, companyCreatorButtons);
        
        
        newCompanyScene = new Scene(companyCreatorMenu, 600, 400);
        // Setup primary stage
        
        primaryStage.setTitle("Ekonomista");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
