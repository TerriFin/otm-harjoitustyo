/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.dao;

import com.mycompany.ekonomista.domain.User;
import java.util.List;

/**
 *
 * @author samisaukkonen
 */
public interface HighscoresDao {
    public boolean addNewHighscore(User user);
    public List<String> getHighscores();
    public boolean removeHiScore(String name, int points);
}
