/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.domain;

/**
 *
 * @author samisaukkonen
 */
public class User {

    private int money;

    public User(int startingMoney) {
        this.money = startingMoney;
    }

    public int getMoney() {
        return money;
    }

    public boolean buyCompanyStocks(int amount, Company company) {
        try {
            money -= company.buy(amount, money);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sellCompanyStocks(int amount, Company company) {
        try {
            money += company.sell(amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void setMoneyTo(int amount) {
        money = amount;
    }
}
