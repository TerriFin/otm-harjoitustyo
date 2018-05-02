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
 * This class is responsible for loading from and uploading saved companies from a file.
 * It does this by maintaining ArrayList, and when a company is either added or removed it overrides the file and puts new, modified ArrayList in.
 * It also will make new file for companies if needed.
 *
 * @author samisaukkonen
 */
public class FileCompanyDao implements CompanyDao {

    private String adress;
    private List<Company> companies;

    public FileCompanyDao(String adress) {
        this.adress = adress;
        this.companies = new ArrayList<>();
        
        try {
            File f = new File(adress);
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

    /**
     * 
     * @return ArrayList this class maintains
     */
    @Override
    public List<Company> getAll() {
        return companies;
    }

    /**
     * This method requires company information, makes a new company based on them, and then saves it in if needed (if it was not in already).
     * 
     * @param companyName name of the company
     * @param companyIndex starting index
     * @param chanceToChangeCourse company volatility
     * @param maxTickChange max amount the index can change in tick
     * @param maxChangePerTick max amount of change that can happen in a tick
     * @return true or false depending if company was already in
     */
    @Override
    public boolean create(String companyName, int companyIndex, int chanceToChangeCourse, int maxTickChange, int maxChangePerTick) {
        Company company = new Company(companyName, companyIndex, chanceToChangeCourse, maxTickChange, maxChangePerTick);

        if (companies.contains(company)) {
            return false;
        }

        companies.add(company);

        try {
            FileOutputStream fos = new FileOutputStream(adress, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(companies);
            oos.close();
        } catch (Exception e) {
            System.out.println("There was a problem adding company to file");
        }

        return true;
    }

    /**
     * Removes the specified company from maintained ArrayList and overwrites file.
     * 
     * @param company company in question
     */
    @Override
    public void delete(Company company) {
        companies.remove(company);

        try {
            FileOutputStream fos = new FileOutputStream(adress, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(companies);
            oos.close();
        } catch (Exception e) {
            System.out.println("There was a problem deleting company from file");
        }
    }

}
