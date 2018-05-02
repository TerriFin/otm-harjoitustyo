/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.dao;

import com.mycompany.ekonomista.domain.Company;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author samisaukkonen
 */
public class FileCompanyDaoTest {

    CompanyDao compDao = new FileCompanyDao("testCompanies.data");

    // Set up the file before each test so they do not interfere with each other
    // Before each test the file only contains one company "test", that has all attributes set to zero
    @Before
    public void setUp() {
        while (compDao.getAll().size() > 0) {
            compDao.delete(compDao.getAll().get(0));
        }

        compDao.create("test", 0, 0, 0, 0);
    }

    @Test
    public void containsRightCompanyAtStart() {
        assertEquals(1, compDao.getAll().size());
        assertTrue(compDao.getAll().get(0).getCompanyName().equals("test"));
    }

    @Test
    public void addingCompaniesWorks() {
        assertTrue(compDao.create("Elisa", 10, 10, 10, 10));
        assertEquals(2, compDao.getAll().size());
        assertTrue(compDao.getAll().contains(new Company("Elisa", 10, 10, 10, 10)));
    }

    @Test
    public void deletingCompaniesWorks() {
        assertEquals(1, compDao.getAll().size());

        compDao.delete(new Company("test", 0, 0, 0, 0));

        assertEquals(0, compDao.getAll().size());
    }

    @Test
    public void addingCompanyAlreadyInFileDoesNotWork() {
        assertFalse(compDao.create("test", 0, 0, 0, 0));
    }

    @Test
    public void fileIsCorrect() throws FileNotFoundException, IOException, ClassNotFoundException {
        compDao.create("Elisa", 0, 0, 0, 0);
        compDao.create("Talvivaara", 0, 0, 0, 0);
        
        List<Company> companies = new ArrayList<>();
        
        FileInputStream fis = new FileInputStream(new File("testCompanies.data"));
        ObjectInputStream ois = new ObjectInputStream(fis);
        companies = (List<Company>) ois.readObject();
        ois.close();
        
        assertEquals(3, companies.size());
        assertTrue(companies.contains(new Company("Talvivaara", 0, 0, 0, 0)));
    }
}
