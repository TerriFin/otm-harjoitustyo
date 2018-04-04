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
public class User implements UserInterface {

    private int money;

    public User(int startingMoney) {
        this.money = startingMoney;
    }
    
    @Override
    public int getMoney() {
        return money;
    }

    @Override
    public boolean buyCompanyStocks(int amount, Company company) {
        try {
            money -= company.buy(amount, money);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean sellCompanyStocks(int amount, Company company) {
        try {
            money += company.sell(amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
