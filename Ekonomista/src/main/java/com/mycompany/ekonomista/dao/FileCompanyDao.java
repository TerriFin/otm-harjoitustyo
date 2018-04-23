/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.dao;

import com.mycompany.ekonomista.domain.Company;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samisaukkonen
 */
public class FileCompanyDao implements CompanyDao {

    List<Company> companies;

    public FileCompanyDao() {
        this.companies = new ArrayList<>();
        
        try {
            File f = new File("companies.data");
            if (!f.createNewFile()) {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.companies = (List<Company>) ois.readObject();
                ois.close();
            }
        } catch (Exception e) {
            System.out.println("Problem loading companies from file");
        }
    }

    @Override
    public List<Company> getAll() {
        return companies;
    }

    @Override
    public boolean create(String companyName, int companyIndex, int chanceToChangeCourse, int maxTickChange, int maxChangePerTick) {
        Company company = new Company(companyName, companyIndex, chanceToChangeCourse, maxTickChange, maxChangePerTick);

        if (companies.contains(company)) {
            return false;
        }

        companies.add(company);

        try {
            FileOutputStream fos = new FileOutputStream("companies.data", false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(companies);
            oos.close();
        } catch (Exception e) {
            System.out.println("There was a problem adding company to file");
        }

        return true;
    }

    @Override
    public void delete(Company company) {
        companies.remove(company);

        try {
            FileOutputStream fos = new FileOutputStream("companies.data", false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(companies);
            oos.close();
        } catch (Exception e) {
            System.out.println("There was a problem deleting company from file");
        }
    }

}
