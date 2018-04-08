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
public class Company {

    private String companyName;
    private int companyIndex;
    private int ownedStocks;

    private int tickChange;
    private int lastTickChange;

    private boolean nextTickPositive;
    private int chanceToChangeCourse;
    private int maxTickChange;
    private int maxChangePerTick;

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

    public int sell(int amount) {
        if (amount > ownedStocks || amount < 0) {
            throw new IllegalArgumentException();
        } else {
            ownedStocks -= amount;
            return amount * companyIndex;
        }
    }

    public int buy(int amount, int money) {
        int amountNeeded = companyIndex * amount;

        if (money < amountNeeded || amount < 0) {
            throw new IllegalArgumentException();
        } else {
            ownedStocks += amount;
            return amountNeeded;
        }
    }
    
    public void setOwnedStockToZero() {
        ownedStocks = 0;
    }

    public void printCompanyInfo() {
        System.out.println(companyName + "\n"
                + "index: " + companyIndex + "\n"
                + "index change: " + lastTickChange + "\n"
                + "owned stocks: " + ownedStocks);
    }
}
