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
 *
 * @author samisaukkonen
 */
public class GameService extends TimerTask {
    private CompanyDao companyDao;
    private User user;
    
    private List<Company> companies;
    private Random random;
    
    private boolean ironman;
    
    public GameService(CompanyDao companyDao, String userName, int startingMoney) {
        this.companyDao = companyDao;
        this.user = new User(userName, startingMoney);
        
        this.companies = this.companyDao.getAll();
        this.random = new Random();
        
        if (this.user.getMoney() > 5000) {
            this.ironman = false;
        } else {
            this.ironman = true;
        }
    }
    
    public int getCurrentMoney() {
        return user.getMoney();
    }
    
    public boolean isIronman() {
        return ironman;
    }
    
    public List<Company> getAllCompanies() {
        return companies;
    }
    
    public void tick() {
        for (Company company : companies) {
            company.tick(random.nextInt(101), random.nextInt(company.getMaxChangePerTick()));
            
            if (company.getCompanyIndex() == 0) {
                company.setOwnedStockToZero();
            }
        }
    }
    
    public boolean buyCompanyStocks(int amount, Company company) {
        return user.buyCompanyStocks(amount, company);
    }
    
    public boolean sellCompanyStocks(int amount, Company company) {
        return user.sellCompanyStocks(amount, company);
    }
    
    public void printAllCompanys() {
        for (Company company : companies) {
            company.printCompanyInfo();
            System.out.println("");
        }
        
        System.out.println("------------------------");
    }

    @Override
    public void run() {
        tick();
        printAllCompanys();
    }
}
