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
    
    private List<Company> companys;
    private Random random;
    
    private boolean ironman;
    
    public GameService(CompanyDao companyDao, User user) {
        this.companyDao = companyDao;
        this.user = user;
        
        this.companys = this.companyDao.getAll();
        this.random = new Random();
        
        if (this.user.getMoney() > 5000) {
            this.ironman = false;
        } else {
            this.ironman = true;
        }
    }
    
    public void tick() {
        for (Company company : companys) {
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
    
    public void addCompany(String name, int startingIndex, int chanceToChangeCourse, int maxTickChange, int maxChangePerTick) {
        Company companyToAdd = new Company(name, startingIndex, chanceToChangeCourse, maxTickChange, maxChangePerTick);
        companyDao.create(companyToAdd);
    }
    
    public void deleteCompany(Company company) {
        companyDao.delete(company);
    }
    
    public boolean isIronman() {
        return ironman;
    }
    
    public void printAllCompanys() {
        for (Company company : companys) {
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
