/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.ui;

import com.mycompany.ekonomista.dao.DummyCompanyDao;
import com.mycompany.ekonomista.dao.FileCompanyDao;
import com.mycompany.ekonomista.domain.GameService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author samisaukkonen
 */
public class GameGui extends Application {
    private GameService gameService;
    
    private Mainmenu mainmenu;
    private CompanyCreator companyCreator;
    private Game game;

    @Override
    public void init() {
        gameService = new GameService(new FileCompanyDao());
        
        mainmenu = new Mainmenu();
        companyCreator = new CompanyCreator();
        game = new Game();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Ekonomista");
        primaryStage.setScene(mainmenu.getMainmenuScene(primaryStage, companyCreator, gameService, game));
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
