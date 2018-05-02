/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.domain;

import java.io.Serializable;

/**
 * This class represents one company, with its own name and stats.
 *
 * @author samisaukkonen
 */
public class Company implements Serializable {

    private String companyName;
    private int companyIndex;
    private int ownedStocks;

    private int tickChange;
    private int lastTickChange;

    private boolean nextTickPositive;
    private int chanceToChangeCourse;
    private int maxTickChange;
    private int maxChangePerTick;

    /**
     * Constructor for company, more detail in parameters.
     * 
     * @param companyName name of the company
     * @param companyIndex starting index of the company
     * @param chanceToChangeCourse percentage chance of company index changing direction, can be thought of as volatility
     * @param maxTickChange max change that can happen in a tick
     * @param maxChangePerTick max amount of change that can happen in a tick
     */
    public Company(String companyName, int companyIndex, int chanceToChangeCourse, int maxTickChange, int maxChangePerTick) {
        this.companyName = companyName;
        this.companyIndex = companyIndex;
        this.ownedStocks = 0;

        this.tickChange = 0;
        this.lastTickChange = 0;

        this.nextTickPositive = true;
        this.chanceToChangeCourse = chanceToChangeCourse;
        this.maxTickChange = maxTickChange;
        this.maxChangePerTick = maxChangePerTick;
    }

    public int getMaxChangePerTick() {
        return maxChangePerTick;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getCompanyIndex() {
        return companyIndex;
    }
    
    public int getCompanyIndexChange() {
        return lastTickChange;
    }

    public int getOwnedStocks() {
        return ownedStocks;
    }

    /**
     * This method processes inputs that have been generated for it, and calculates new index values for every time it is called.
     * 
     * @param changesCourse chance for the next tick effect to be opposite to last one (must be between 0 and 100)
     * @param howMuch how much company index goes up or down
     */
    public void tick(int changesCourse, int howMuch) {
        lastTickChange = tickChange;
        
        if (tickChange < 0) {
            if (companyIndex + tickChange < 0) {
                companyIndex = 0;
            } else {
                companyIndex += tickChange;
            }
        } else {
            companyIndex += tickChange;
        }

        if (chanceToChangeCourse >= changesCourse) {
            nextTickPositive = !nextTickPositive;
        }

        if (howMuch > maxChangePerTick) {
            howMuch = maxChangePerTick;
        }

        if (nextTickPositive) {
            if (tickChange + howMuch > maxTickChange) {
                tickChange = maxTickChange;
            } else {
                tickChange += howMuch;
            }
        } else {
            if (tickChange - howMuch < 0 - maxTickChange) {
                tickChange = 0 - maxTickChange;
            } else {
                tickChange -= howMuch;
            }
        }
    }

    /**
     * This method processes sell orders, and decreases current owned stocks if needed.
     * 
     * @param amount indicates the amount player wishes to sell this stock
     * @return the amount of money a successful sell order brings 
     * 
     */
    public int sell(int amount) {
        if (amount > ownedStocks || amount < 0) {
            throw new IllegalArgumentException();
        } else {
            ownedStocks -= amount;
            return amount * companyIndex;
        }
    }

    /**
     * This method calculates how much money is needed to buy specified amount of stocks and increases owned stocks if needed.
     * 
     * @param amount indicates the amount player wishes to buy this stock
     * @param money player's current money
     * @return the amount of money that must be deducted from player's money supply 
     */
    public int buy(int amount, int money) {
        int amountNeeded = companyIndex * amount;

        if (money < amountNeeded || amount < 0) {
            throw new IllegalArgumentException();
        } else {
            ownedStocks += amount;
            return amountNeeded;
        }
    }
    
    /**
     * sets owned stocks to zero. Kinda self-explanatory.
     */
    public void setOwnedStockToZero() {
        ownedStocks = 0;
    }

    @Override
    public boolean equals(Object o) {
        Company otherCompany = (Company) o;
        return hashCode() == otherCompany.hashCode();
    }

    @Override
    public int hashCode() {
        return companyName.hashCode();
    }
}
