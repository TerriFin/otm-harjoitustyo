/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.ui;

import com.mycompany.ekonomista.dao.DbHighscoresDao;
import com.mycompany.ekonomista.dao.DummyCompanyDao;
import com.mycompany.ekonomista.dao.FileCompanyDao;
import com.mycompany.ekonomista.dao.HighscoresDao;
import com.mycompany.ekonomista.domain.GameService;
import com.mycompany.ekonomista.domain.MusicPlayer;
import com.mycompany.ekonomista.domain.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * DO NOT LOOK HERE, WHOLE UI IS ONE BIG MESS, YOU DO NOT WANT TO WITNESS IT
 * 
 * @author samisaukkonen
 */
public class GameGui extends Application {
    private GameService gameService;
    private HighscoresDao hiScores;
    private MusicPlayer musicPlayer;
    
    private Mainmenu mainmenu;
    private CompanyCreator companyCreator;
    private Game game;
    private Hiscores hiScoresScene;

    @Override
    public void init() {
        gameService = new GameService(new FileCompanyDao("companies.data"));
        hiScores = new DbHighscoresDao("jdbc:sqlite:HiScores.db");
        musicPlayer = new MusicPlayer("EkonomistaTheme.mp4");
        
        mainmenu = new Mainmenu();
        companyCreator = new CompanyCreator();
        game = new Game();
        hiScoresScene = new Hiscores();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        musicPlayer.playMusicOnLoop();
        primaryStage.setTitle("Ekonomista");
        primaryStage.setScene(mainmenu.getMainmenuScene(primaryStage, companyCreator, gameService, game, hiScoresScene, hiScores));
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
