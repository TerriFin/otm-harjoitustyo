/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.ui;

import com.mycompany.ekonomista.domain.Company;
import com.mycompany.ekonomista.domain.GameService;
import java.util.HashMap;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author samisaukkonen
 */
public class Game {
    private GameService gameService;

    private HashMap<Company, Label> indexes;
    private HashMap<Company, Label> previousIndexes;
    private HashMap<Company, Label> ownedStocks;

    private Label currentMoneyLabel;

    private BorderPane getAndInitCompanies(GameService gameService) {
        indexes = new HashMap<>();
        previousIndexes = new HashMap<>();
        ownedStocks = new HashMap<>();

        List<Company> companies = gameService.getAllCompanies();

        BorderPane companiesPane = new BorderPane();

        VBox companyInfos = new VBox(30);

        for (Company company : companies) {
            VBox companyInfo = new VBox(6);

            Label companyNameLabel = new Label(company.getCompanyName());
            Label companyIndexLabel = new Label("Index: " + company.getCompanyIndex());
            Label companyPreviousIndexLabel = new Label("Change: " + company.getCompanyIndexChange());
            Label companyOwnedStocks = new Label("Owned stocks: " + company.getOwnedStocks());

            indexes.put(company, companyIndexLabel);
            previousIndexes.put(company, companyPreviousIndexLabel);
            ownedStocks.put(company, companyOwnedStocks);

            HBox buyAndSellComponents = new HBox(8);

            TextField buyOrSellAmountInput = new TextField();

            Button sellButton = new Button("Sell");
            sellButton.setMinWidth(60);
            sellButton.setOnAction(e -> {
                try {
                    if (buyOrSellAmountInput.getText().isEmpty()) {
                        System.out.println("No EMPTY >:c");
                    } else {
                        gameService.sellCompanyStocks(Integer.parseInt(buyOrSellAmountInput.getText()), company);
                        currentMoneyLabel.setText("Current money: " + gameService.getCurrentMoney() + "$");
                        companyOwnedStocks.setText("Owned stocks: " + company.getOwnedStocks());
                        buyOrSellAmountInput.setText("");
                    }
                } catch (Exception exception) {
                    System.out.println("add error window thingy");
                }
            });

            Button buyButton = new Button("Buy");
            buyButton.setMinWidth(60);
            buyButton.setOnAction(e -> {
                try {
                    if (buyOrSellAmountInput.getText().isEmpty()) {
                        System.out.println("INSERT NUMBER");
                        buyOrSellAmountInput.setText("");
                    } else {
                        gameService.buyCompanyStocks(Integer.parseInt(buyOrSellAmountInput.getText()), company);
                        currentMoneyLabel.setText("Current money: " + gameService.getCurrentMoney() + "$");
                        companyOwnedStocks.setText("Owned stocks: " + company.getOwnedStocks());
                        buyOrSellAmountInput.setText("");
                    }
                } catch (Exception exception) {
                    System.out.println("add erroe window thingy");
                    buyOrSellAmountInput.setText("");
                }
            });

            buyAndSellComponents.getChildren().addAll(sellButton, buyOrSellAmountInput, buyButton);

            companyInfo.getChildren().addAll(companyNameLabel, companyIndexLabel, companyPreviousIndexLabel, companyOwnedStocks, buyAndSellComponents);

            companyInfos.getChildren().add(companyInfo);
        }

        ScrollPane sp = new ScrollPane(companyInfos);
        sp.setFitToWidth(true);

        companiesPane.setLeft(companyInfos);
        companiesPane.setRight(sp);

        return companiesPane;
    }

    public Scene getGameScene(Stage primaryStage, GameService gameService) {
        this.gameService = gameService;
        
        BorderPane gameView = new BorderPane();

        BorderPane companies = this.getAndInitCompanies(gameService);

        VBox userInfo = new VBox(5);
        userInfo.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label(gameService.getUsername());
        usernameLabel.setAlignment(Pos.CENTER);
        currentMoneyLabel = new Label("Current money: " + gameService.getCurrentMoney() + "$");
        currentMoneyLabel.setAlignment(Pos.CENTER);

        userInfo.getChildren().addAll(usernameLabel, currentMoneyLabel);

        gameView.setLeft(companies);
        gameView.setCenter(userInfo);

        return new Scene(gameView, 640, 400);
    }
    
    public void updateGame() {
        gameService.tick();
        
        List<Company> companies = gameService.getAllCompanies();
        
        for (Company company : companies) {
            indexes.get(company).setText("Index: " + company.getCompanyIndex());
            previousIndexes.get(company).setText("Change: " + company.getCompanyIndexChange());
            ownedStocks.get(company).setText("Owned stocks: " + company.getOwnedStocks());
        }
    }
}
