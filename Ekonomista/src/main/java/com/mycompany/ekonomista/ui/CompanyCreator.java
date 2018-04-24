/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.ui;

import com.mycompany.ekonomista.domain.Company;
import com.mycompany.ekonomista.domain.GameService;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author samisaukkonen
 */
public class CompanyCreator {

    private BorderPane getCompaniesDeleteList(Stage primaryStage, Mainmenu mainmenu, GameService gameService, Game game) {
        List<Company> companies = gameService.getAllCompanies();

        BorderPane toReturn = new BorderPane();
        
        VBox companyNodes = new VBox(10);

        for (Company company : companies) {
            HBox companyNode = new HBox(4);

            Label companyNameLabel = new Label(company.getCompanyName());

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> {
                gameService.deleteCompany(company);
                primaryStage.setScene(this.getCompanyCreatorScene(primaryStage, mainmenu, gameService, game));
            });

            companyNode.getChildren().addAll(companyNameLabel, deleteButton);
            companyNodes.getChildren().add(companyNode);
        }
        
        ScrollPane sp = new ScrollPane(companyNodes);
        sp.setFitToWidth(true);
        
        toReturn.setLeft(companyNodes);
        toReturn.setCenter(sp);
        
        return toReturn;
    }

    public Scene getCompanyCreatorScene(Stage primaryStage, Mainmenu mainmenu, GameService gameService, Game game) {
        BorderPane companyCreatorComponents = new BorderPane();
        companyCreatorComponents.setPadding(new Insets(10, 20, 10, 20));
        
        
        Label mainCompanyCreatorLabel = new Label("Insert info for a new company or delete existing one");
        mainCompanyCreatorLabel.setAlignment(Pos.CENTER);
        mainCompanyCreatorLabel.setFont(new Font("Arial", 30));

        
        VBox companyInfoLabels = new VBox(20);

        Label companyNameLabel = new Label("Company name");
        Label companyStartingIndexLabel = new Label("Starting index");
        Label companyInstabilityLabel = new Label("Instability (must be between 0 and 100)");
        Label companyMaxTickChangeLabel = new Label("Max amount of change that can happen in a tick");
        Label companyTickMaxChangeLabel = new Label("Max change that can happen in a tick");

        companyInfoLabels.getChildren().addAll(companyNameLabel, companyStartingIndexLabel, companyInstabilityLabel, companyMaxTickChangeLabel, companyTickMaxChangeLabel);
        companyInfoLabels.setAlignment(Pos.CENTER_RIGHT);
        
        
        VBox companyInfoInputs = new VBox(10);

        TextField companyNameInput = new TextField();
        companyNameInput.setMaxWidth(100);

        TextField companyStartingIndexInput = new TextField();
        companyStartingIndexInput.setMaxWidth(70);

        TextField companyInstabilityInput = new TextField();
        companyInstabilityInput.setMaxWidth(50);

        TextField companyMaxTickChangeInput = new TextField();
        companyMaxTickChangeInput.setMaxWidth(50);

        TextField companyTickMaxChangeInput = new TextField();
        companyTickMaxChangeInput.setMaxWidth(50);

        companyInfoInputs.getChildren().addAll(companyNameInput, companyStartingIndexInput, companyInstabilityInput, companyMaxTickChangeInput, companyTickMaxChangeInput);
        companyInfoInputs.setAlignment(Pos.CENTER_LEFT);
        
        HBox buttons = new HBox(50);

        Button menuButton = new Button("Back to menu");
        menuButton.setOnAction(e -> {
            primaryStage.setScene(mainmenu.getMainmenuScene(primaryStage, this, gameService, game));
        });

        Button addCompanyButton = new Button("Add company");
        addCompanyButton.setOnAction(e -> {
            try {
                gameService.addCompany(companyNameInput.getText(),
                        Integer.parseInt(companyStartingIndexInput.getText()),
                        Integer.parseInt(companyInstabilityInput.getText()),
                        Integer.parseInt(companyTickMaxChangeInput.getText()),
                        Integer.parseInt(companyMaxTickChangeInput.getText()));
                
                primaryStage.setScene(this.getCompanyCreatorScene(primaryStage, mainmenu, gameService, game));
            } catch (Exception exception) {
                System.out.println("Add error window thingy");
            }
        });
        
        buttons.getChildren().addAll(menuButton, addCompanyButton);
        buttons.setAlignment(Pos.CENTER);
        

        companyCreatorComponents.setTop(mainCompanyCreatorLabel);
        companyCreatorComponents.setLeft(companyInfoLabels);
        companyCreatorComponents.setCenter(companyInfoInputs);
        companyCreatorComponents.setRight(this.getCompaniesDeleteList(primaryStage, mainmenu, gameService, game));
        companyCreatorComponents.setBottom(buttons);

        return new Scene(companyCreatorComponents, 800, 400);
    }
}
