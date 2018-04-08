/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.domain;

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
public class UserTest {

    Company company;
    User user;

    @Before
    public void setUp() {
        company = new Company("Elisa", 500, 40, 50, 20);
        user = new User(5000);
    }

    @Test
    public void constructorWorksProperlyAtStart() {
        assertEquals(5000, user.getMoney());
    }

    @Test
    public void buyingStocksWorksProperly() {
        boolean success = user.buyCompanyStocks(3, company);

        assertEquals(3500, user.getMoney());
        assertEquals(3, company.getOwnedStocks());
        assertEquals(true, success);

        success = user.buyCompanyStocks(5, company);

        assertEquals(1000, user.getMoney());
        assertEquals(8, company.getOwnedStocks());
        assertEquals(true, success);

        success = user.buyCompanyStocks(3, company);

        assertEquals(1000, user.getMoney());
        assertEquals(8, company.getOwnedStocks());
        assertEquals(false, success);

        success = user.buyCompanyStocks(2, company);

        assertEquals(0, user.getMoney());
        assertEquals(10, company.getOwnedStocks());
        assertEquals(true, success);
    }
    
    @Test
    public void sellingStocksWorksProperly() {
        boolean success = user.buyCompanyStocks(10, company);
        
        assertEquals(0, user.getMoney());
        assertEquals(10, company.getOwnedStocks());
        assertEquals(true, success);
        
        success = user.sellCompanyStocks(4, company);
        
        assertEquals(2000, user.getMoney());
        assertEquals(6, company.getOwnedStocks());
        assertEquals(true, success);
        
        success = user.sellCompanyStocks(5, company);
        
        assertEquals(4500, user.getMoney());
        assertEquals(1, company.getOwnedStocks());
        assertEquals(true, success);
        
        success = user.sellCompanyStocks(2, company);
        
        assertEquals(4500, user.getMoney());
        assertEquals(1, company.getOwnedStocks());
        assertEquals(false, success);
        
        success = user.sellCompanyStocks(1, company);
        
        assertEquals(5000, user.getMoney());
        assertEquals(0, company.getOwnedStocks());
        assertEquals(true, success);
    }
    
    @Test
    public void settingNewMoneyAmountWorks() {
        assertEquals(5000, user.getMoney());
        
        user.setMoneyTo(1000000);
        
        assertEquals(1000000, user.getMoney());
    }
}
