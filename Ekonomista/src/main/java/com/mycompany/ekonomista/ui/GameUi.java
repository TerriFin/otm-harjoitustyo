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
import com.mycompany.ekonomista.domain.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Duration;

/**
 *
 * @author samisaukkonen
 */
public class GameUi extends Application {

    private GameService gameService;
    private CompanyDao companyDao;

    private HashMap<Company, Label> indexes;
    private HashMap<Company, Label> previousIndexes;
    private HashMap<Company, Label> ownedStocks;

    private Label moneylabel;

    private Scene menuScene;
    private Scene newCompanyScene;
    private Scene gameScene;

    @Override
    public void init() throws Exception {
        companyDao = new DummyCompanyDao();
    }

    public void initGame(String name, int startingMoney) {
        gameService = new GameService(companyDao, name, startingMoney);

        indexes = new HashMap<>();
        previousIndexes = new HashMap<>();
        ownedStocks = new HashMap<>();

        moneylabel = new Label("" + gameService.getCurrentMoney() + "€");

        List<Company> companys = gameService.getAllCompanies();

        HBox userInfo = new HBox(10);
        userInfo.autosize();
        userInfo.getChildren().addAll(new Label(name), moneylabel);

        VBox mainGameView = new VBox(10);
        mainGameView.getChildren().add(userInfo);

        for (Company company : companys) {
            mainGameView.getChildren().add(initCompany(company));
        }

        gameScene = new Scene(mainGameView, 600, 400);
    }

    public VBox initCompany(Company company) {
        VBox companyWindow = new VBox(3);

        VBox companyInfo = new VBox(1);

        Label companyNameLabel = new Label(company.getCompanyName());

        Label companyIndexLabel = new Label("Index: " + company.getCompanyIndex());
        indexes.put(company, companyIndexLabel);

        Label previousIndexLabel = new Label("Index change: " + company.getCompanyIndexChange());
        previousIndexes.put(company, previousIndexLabel);

        Label ownedStocksLabel = new Label("Owned stocks: " + company.getOwnedStocks());
        ownedStocks.put(company, ownedStocksLabel);

        companyInfo.getChildren().addAll(companyNameLabel, companyIndexLabel, previousIndexLabel, ownedStocksLabel);

        HBox buyAndSellInterface = new HBox(1);

        TextField buyOrSellAmountInput = new TextField();
        buyOrSellAmountInput.setMinWidth(70);
        buyOrSellAmountInput.setMaxWidth(70);

        Button sellCompanyStocks = new Button("Sell");
        sellCompanyStocks.setMinWidth(70);
        sellCompanyStocks.setOnAction(e -> {
            if (gameService.sellCompanyStocks(Integer.parseInt(buyOrSellAmountInput.getText()), company)) {
                ownedStocksLabel.setText("Owned stocks: " + company.getOwnedStocks());
                moneylabel.setText("" + gameService.getCurrentMoney() + "€");

                buyOrSellAmountInput.setText("");
            }
        });

        Button buyCompanyStocks = new Button("Buy");
        buyCompanyStocks.setMinWidth(70);
        buyCompanyStocks.setOnAction(e -> {
            if (gameService.buyCompanyStocks(Integer.parseInt(buyOrSellAmountInput.getText()), company)) {
                ownedStocksLabel.setText("Owned stocks: " + company.getOwnedStocks());
                moneylabel.setText("" + gameService.getCurrentMoney() + "€");

                buyOrSellAmountInput.setText("");
            }
        });

        buyAndSellInterface.getChildren().addAll(sellCompanyStocks, buyOrSellAmountInput, buyCompanyStocks);

        companyWindow.getChildren().addAll(companyInfo, buyAndSellInterface);

        return companyWindow;
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
            
            initGame(playerNameInput.getText(), Integer.parseInt(startingMoneyInput.getText()));
            primaryStage.setScene(gameScene);

            Timeline mainGameLoop = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    gameService.tick();

                    List<Company> allCompanies = gameService.getAllCompanies();
                    for (Company company : allCompanies) {
                        Label index = indexes.get(company);
                        index.setText("Index: " + company.getCompanyIndex());

                        Label previousIndex = previousIndexes.get(company);
                        previousIndex.setText("Index change: " + company.getCompanyIndexChange());
                        
                        Label ownedStocksLabel = ownedStocks.get(company);
                        ownedStocksLabel.setText("Owned stocks: " + company.getOwnedStocks());
                    }
                }
            }));

            mainGameLoop.setCycleCount(Timeline.INDEFINITE);
            mainGameLoop.play();
        });

        Button newCompanyButton = new Button("Create new company");
        newCompanyButton.setOnAction(e -> {
            
            // switch scene to company creator
            
            primaryStage.setScene(newCompanyScene);
        });

        buttons.getChildren().addAll(startButton, newCompanyButton);

        mainMenu.getChildren().addAll(titleNodes, gameStartingInputs, buttons);

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
        menu.setOnAction(e -> {
            
            // set scene back to main menu
            
            primaryStage.setScene(menuScene);
        });

        Button create = new Button("Create");
        create.setOnAction(e -> {
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
