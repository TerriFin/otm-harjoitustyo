/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista;

import com.mycompany.ekonomista.dao.DummyCompanyDao;
import com.mycompany.ekonomista.domain.GameService;
import com.mycompany.ekonomista.domain.User;
import java.util.Timer;

/**
 *
 * @author samisaukkonen
 */
public class TextMain {
    public static void main(String[] args) {
        User user = new User(10000);
        DummyCompanyDao companyDao = new DummyCompanyDao();
        
        GameService gameService = new GameService(companyDao, user);
        
        Timer timer = new Timer();
        timer.schedule(gameService, 0, 3000);
    }
    
}
