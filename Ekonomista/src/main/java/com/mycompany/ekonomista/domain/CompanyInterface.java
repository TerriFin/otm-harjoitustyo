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
public interface CompanyInterface {

    public void tick(int changesCourse, int howMuch);

    public int sell(int amount);
    
    public int buy(int amount, int money);
}
