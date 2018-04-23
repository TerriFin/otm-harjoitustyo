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

    public void tick() {
        for (Company company : companies) {
            if (company.getCompanyIndex() == 0) {
                company.setOwnedStockToZero();
            }

            company.tick(random.nextInt(101), random.nextInt(company.getMaxChangePerTick()));
        }
    }

    public boolean buyCompanyStocks(int amount, Company company) {
        return user.buyCompanyStocks(amount, company);
    }

    public boolean sellCompanyStocks(int amount, Company company) {
        return user.sellCompanyStocks(amount, company);
    }
}
