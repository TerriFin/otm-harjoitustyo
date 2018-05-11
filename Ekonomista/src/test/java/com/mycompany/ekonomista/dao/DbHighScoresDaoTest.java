/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.dao;

import com.mycompany.ekonomista.domain.User;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author samisaukkonen
 */
public class DbHighScoresDaoTest {

    DbHighscoresDao hiScoresDao = new DbHighscoresDao("jdbc:sqlite:Test.db");

    // always delete the old .db file, because DbHighscoresDao should be able to make a new empty one
    @Before
    public void setUp() {
        File testDB = new File("Test.db");
        testDB.delete();
        
        hiScoresDao = new DbHighscoresDao("jdbc:sqlite:Test.db");
    }

    @Test
    public void testDbIsEmptyAtStartAndIncrease() {
        assertEquals(0, hiScoresDao.getHighscores().size());
        
        assertTrue(hiScoresDao.addNewHighscore(new User("Jarmo", 3000)));
        
        assertEquals(1, hiScoresDao.getHighscores().size());
    }
    
    @Test
    public void deletingWorks() {
        assertTrue(hiScoresDao.addNewHighscore(new User("Jarmo", 3000)));
        assertTrue(hiScoresDao.addNewHighscore(new User("Pekka", 6000)));
        
        assertEquals(2, hiScoresDao.getHighscores().size());
        
        assertTrue(hiScoresDao.removeHiScore("Jarmo", 3000));
        
        assertEquals(1, hiScoresDao.getHighscores().size());
    }
}
