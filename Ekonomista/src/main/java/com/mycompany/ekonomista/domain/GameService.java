/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.domain;

import com.mycompany.ekonomista.dao.CompanyDao;
import com.mycompany.ekonomista.dao.DummyCompanyDao;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

/**
 * This class offers all methods that are required in GUI elements to build a game.
 * 
 * @author samisaukkonen
 */
public class GameService {

    private CompanyDao companyDao;
    private List<Company> companies;
    private Random random;
    
    private User user;
    private boolean ironman;

    public GameService(CompanyDao companyDao) {
        this.companyDao = companyDao;
        this.companies = this.companyDao.getAll();
        this.random = new Random();
    }

    /**
     * Sets new user based on username and starting money.
     * Also checks starting money and if it is not over 5000, sets ironMan to be true.
     * 
     * @param userName player selected username
     * @param startingMoney the money player starts with
     */
    public void setUser(String userName, int startingMoney) {
        user = new User(userName, startingMoney);

        if (this.user.getMoney() > 5000) {
            this.ironman = false;
        } else {
            this.ironman = true;
        }
    }

    public int getCurrentMoney() {
        return user.getMoney();
    }
    
    public String getUsername() {
        return user.getName();
    }

    public boolean isIronman() {
        return ironman;
    }

    public List<Company> getAllCompanies() {
        return companies;
    }

    public void addCompany(String companyName, int companyIndex, int chanceToChangeCourse, int maxTickChange, int maxChangePerTick) {
        companyDao.create(companyName, companyIndex, chanceToChangeCourse, maxTickChange, maxChangePerTick);
    }

    public void deleteCompany(Company company) {
        companyDao.delete(company);
        
        companies = companyDao.getAll();
    }

    /**
     * This method is one game tick. It is called once every time game proceeds forwards.
     * This method checks for every company in the game if their index is in zero, and if so sets player owned stocks also to zero.
     * After this, the method calls tick() and passes random numbers into them for every company, proceeding the game.
     */
    public void tick() {
        for (Company company : companies) {
            if (company.getCompanyIndex() == 0) {
                company.setOwnedStockToZero();
            }

            company.tick(random.nextInt(101), random.nextInt(company.getMaxChangePerTick()));
        }
    }

    /**
     * This method is user.buyCompanyStocks, but is in GameService since all game related things need to be accessible through this class.
     * 
     * @param amount amount of stocks player is buying
     * @param company company from where player is buying the stocks from
     * @return either true or false depending if the transaction succeeded
     */
    public boolean buyCompanyStocks(int amount, Company company) {
        return user.buyCompanyStocks(amount, company);
    }

    /**
     * This method is user.sellCompanyStocks, but is in GameService since all game related things need to be accessible through this class.
     * 
     * @param amount amount of stocks player is selling
     * @param company company from where player is selling the stocks from
     * @return either true or false depending if the transaction succeeded
     */
    public boolean sellCompanyStocks(int amount, Company company) {
        return user.sellCompanyStocks(amount, company);
    }
}
