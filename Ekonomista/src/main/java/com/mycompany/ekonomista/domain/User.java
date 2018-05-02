/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.domain;

/**
 * This class represents player, and is responsible for managing the amount of money player has.
 * 
 * @author samisaukkonen
 */
public class User {

    private String name;
    private int money;

    public User(String name, int startingMoney) {
        this.name = name;
        this.money = startingMoney;
    }
    
    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    /**
     * This method buys the stocks from some company, takes required amount of money away from player if needed.
     * 
     * @param amount the amount of stocks player tries to buy
     * @param company company from which the player tries to buy the stocks from
     * @return either true or false depending if the transaction succeeded 
     */
    public boolean buyCompanyStocks(int amount, Company company) {
        try {
            money -= company.buy(amount, money);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method sells the stocks from some company, gives money based on how much stocks were sold at what value. 
     * Gives money to player if needed.
     * 
     * @param amount amount of stocks being sold
     * @param company company from where player is selling stocks from.
     * @return either true or false depending if transaction was successful
     */
    public boolean sellCompanyStocks(int amount, Company company) {
        try {
            money += company.sell(amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Sets money to a specified value.
     * 
     * @param amount amount money is needed to be set to
     */
    public void setMoneyTo(int amount) {
        money = amount;
    }
}
