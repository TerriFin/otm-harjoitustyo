/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.dao;

import com.mycompany.ekonomista.domain.User;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samisaukkonen
 */
public class DbHighscoresDao implements HighscoresDao {

    private String adress;

    public DbHighscoresDao(String adress) {
        this.adress = adress;

        File fileDb = new File(adress.split(":")[adress.split(":").length - 1]);

        try {
            if (fileDb.createNewFile()) {
                Connection connection = DriverManager.getConnection(this.adress);
                PreparedStatement statement = connection.prepareStatement("CREATE TABLE HiScores(key INTEGER PRIMARY KEY, playerName STRING, playerScore INTEGER)");
                
                statement.execute();
                
                statement.close();
                connection.close();
            }
        } catch (Exception e) {
            
        }
    }

    @Override
    public boolean addNewHighscore(User user) {
        try {
            Connection connection = DriverManager.getConnection(adress);

            PreparedStatement statement = connection.prepareStatement("INSERT INTO HiScores(playerName, playerScore) VALUES (?, ?)");
            statement.setString(1, user.getName());
            statement.setInt(2, user.getMoney());

            statement.execute();

            statement.close();
            connection.close();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<String> getHighscores() {
        try {
            Connection connection = DriverManager.getConnection(adress);

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM HiScores ORDER BY playerScore DESC");

            ResultSet rs = statement.executeQuery();
            System.out.println("");
            List<String> hiScores = new ArrayList<>();

            while (rs.next()) {
                hiScores.add(rs.getString("playerName") + " " + rs.getInt("playerScore"));
            }

            rs.close();
            statement.close();
            connection.close();

            return hiScores;
        } catch (SQLException e) {
            return new ArrayList<String>();
        }

    }

    @Override
    public boolean removeHiScore(String name, int points) {
        try {
            Connection connection = DriverManager.getConnection(adress);

            PreparedStatement statement = connection.prepareStatement("DELETE FROM HiScores WHERE playerName = '" + name + "' AND '" + points + "'");

            statement.execute();

            statement.close();
            connection.close();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
