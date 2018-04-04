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
public class CompanyTest {

    Company company;

    @Before
    public void setUp() {
        /* name, indexAtStart, chanceToChangeCourse, maxTickChange, maxChangePertick
        
           name = name of the company, 
           index = company index,
           chanceToChangeCourse = chance for nextTickPositive to be flipped,
           maxTickChange = how much can the company value change in a tick,
           maxChangePerTick = how much can the nextTickChange change
         */

        company = new Company("Elisa", 500, 40, 50, 20);
    }

    @Test
    public void constructorWorksProperlyAtStart() {
        assertEquals(20, company.getMaxChangePerTick());
        assertEquals("Elisa", company.getCompanyName());
        assertEquals(500, company.getCompanyIndex());
        assertEquals(0, company.getOwnedStocks());
    }

    @Test
    public void buyingStocksThrowsExceptionIfTryingToBuyNegativeAmountOrNotEnoughMoney() {
        company.buy(2, 5000);
        
        try {
            company.buy(2, -1200);
            fail("No exception thrown when buying stocks with negative amount");
        } catch (Exception e) {

        }

        try {
            company.buy(2, 900);
            fail("No exception thrown when buying stocks without enough money");
        } catch (Exception e) {

        }
        
        assertEquals(2, company.getOwnedStocks());
    }
    
    @Test
    public void sellingStocksThrowsExceptionIfTryingToSellNegativeAmountOrNotEnoughStocks() {
        company.buy(2, 5000);
        
        try {
            company.sell(-3);
            fail("No exception thrown when selling negative amount of stocks");
        } catch (Exception e) {
            
        }
        
        try {
            company.sell(3);
            fail("No exception thrown when selling more stocks than currently owned");
        } catch (Exception e) {
            
        }
        
        assertEquals(2, company.getOwnedStocks());
    }
    
    @Test
    public void buyingAndSellingStocksWorksProperlyWhenUsedInConjunction() {
        assertEquals(0, company.getOwnedStocks());
        
        int money = 1505;
        
        money -= company.buy(3, money);
        assertEquals(3, company.getOwnedStocks());
        assertEquals(5, money);
        
        money += company.sell(2);
        assertEquals(1, company.getOwnedStocks());
        assertEquals(money, 1005);
    }
    
    @Test
    public void TicksWorksCorrectlyWhenPositive() {
        assertEquals(500, company.getCompanyIndex());
        
        company.tick(50, 15);
        company.tick(50, 16);
        
        assertEquals(515, company.getCompanyIndex());
        
        company.tick(50, 500);
        company.tick(30, 20);
        
        assertEquals(596, company.getCompanyIndex());
        
        company.tick(0, 0);
        
        assertEquals(626, company.getCompanyIndex());
    }
    
    @Test
    public void TicksWorksCorrectlyWhenNegative() {
        assertEquals(500, company.getCompanyIndex());
        
        company.tick(40, 21);
        company.tick(50, 21);
        
        assertEquals(480, company.getCompanyIndex());
        
        company.tick(50, 15);
        company.tick(30, 19);
        
        assertEquals(390, company.getCompanyIndex());
        
        company.tick(0, 0);
        
        assertEquals(359, company.getCompanyIndex());
    }
    
    @Test public void CompanyIndexCannotGoNegative() {
        company.tick(10, 20);
        
        for (int i = 0; i < 30; i++) {
            company.tick(50, 20);
        }
        
        assertEquals(0, company.getCompanyIndex());
        
        company.tick(50, 20);
        
        assertEquals(0, company.getCompanyIndex());
    }
}
