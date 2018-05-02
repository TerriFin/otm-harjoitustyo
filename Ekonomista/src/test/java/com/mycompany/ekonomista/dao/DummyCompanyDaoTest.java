/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.dao;

import com.mycompany.ekonomista.domain.Company;
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
public class DummyCompanyDaoTest {
    
    CompanyDao compDao = new DummyCompanyDao();

    @Test
    public void dummyContainsCorrectDataAtStart() {
        assertTrue(compDao.getAll().get(0).getClass() == Company.class);
        assertEquals(4, compDao.getAll().size());
    }
    
    @Test
    public void addingAndRemovingCompaniesWorks() {
        assertEquals(4, compDao.getAll().size());
        
        assertTrue(compDao.create("Perfektio", 20, 42, 12, 2));
        
        assertEquals(5, compDao.getAll().size());
        
        compDao.delete(new Company("Perfektio", 20, 42, 12, 2));
        
        assertEquals(4, compDao.getAll().size());
    }
}
