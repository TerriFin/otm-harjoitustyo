/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.dao;

import com.mycompany.ekonomista.domain.Company;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samisaukkonen
 */
public class DummyCompanyDao implements CompanyDao {
    List<Company> companies;
    
    public DummyCompanyDao() {
        companies = new ArrayList<>();
        
        companies.add(new Company("Elisa", 40, 35, 20, 4));
        companies.add(new Company("Talvivaara", 200, 80, 120, 40));
        companies.add(new Company("Lassila&Tikanoja", 15, 20, 15, 3));
        companies.add(new Company("Nokian Renkaat", 35, 45, 30, 8));
    }

    @Override
    public List<Company> getAll() {
        return companies;
    }

    @Override
    public Company create(String companyName, int companyIndex, int chanceToChangeCourse, int maxTickChange, int maxChangePerTick) {
        Company company = new Company(companyName, companyIndex, chanceToChangeCourse, maxTickChange, maxChangePerTick);
        companies.add(company);
        return company;
    }

    @Override
    public void delete(Company company) {
        companies.remove(company);
    }
}
