/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.dao;

import com.mycompany.ekonomista.domain.Company;
import java.util.List;

/**
 *
 * @author samisaukkonen
 */
public interface CompanyDao {
    
    public List<Company> getAll();
    
    public Company create(Company company);
    
    public void delete(Company company);
}
