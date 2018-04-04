/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.domain;

/**
 *
 * @author samisaukkonen
 */
public interface UserInterface {
    
    public int getMoney();
    
    public boolean buyCompanyStocks(int amount, Company company);
    
    public boolean sellCompanyStocks(int amount, Company company);
}
