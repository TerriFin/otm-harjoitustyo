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

/**
 *
 * @author samisaukkonen
 */
public class GameService {
    private CompanyDao companyDao;
    private UserInterface user;
    private List<Company> companys;
    private Random random;
    
    public GameService(CompanyDao companyDao, User user) {
        this.companyDao = companyDao;
        this.user = user;
        this.companys = this.companyDao.getAll();
        this.random = new Random();
    }
    
    public void tick() {
        for (Company company : companys) {
            company.tick(random.nextInt(101), random.nextInt(company.getMaxChangePerTick()));
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
        Company newCompany = companyDao.create(companyToAdd);
        companys.add(newCompany);
    }
    
    public void deleteCompany(Company company) {
        companyDao.delete(company);
        companys.remove(company);
    }
    
    public void printAllCompanys() {
        for (Company company : companys) {
            company.printCompanyInfo();
            System.out.println("");
        }
        
        System.out.println("------------------------");
    }
}
