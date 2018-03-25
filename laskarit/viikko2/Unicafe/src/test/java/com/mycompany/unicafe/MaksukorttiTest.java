package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOnOlemassa() {
        assertTrue(kortti != null);
    }
    
    @Test
    public void luodunKortinSaldoOikein() {
        assertEquals("saldo: 10.00", kortti.toString());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(100);
        assertEquals("saldo: 11.00", kortti.toString());
    }
    
    @Test
    public void rahanOttaminenToimii() {
        kortti.otaRahaa(9);
        assertEquals("saldo: 9.91", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikeinJosRahaaTarpeeksi() {
        kortti.otaRahaa(500);
        assertEquals("saldo: 5.00", kortti.toString());
    }
    
    @Test
    public void saldoEiMuutuJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(1100);
        assertEquals("saldo: 10.00", kortti.toString());
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosRahaaTarpeeksi() {
        assertTrue(kortti.otaRahaa(550));
    }
    
    @Test
    public void otaRahaaPalauttaaFalseJosRahaaEiTarpeeksi() {
        assertFalse(kortti.otaRahaa(1100));
    }
    
    // Tajusin tässä kohtaa että muutkin testit olisi kannattanut tehdä kortti.saldo() metodilla, mutten jaksa enää muuttaa :p
    @Test
    public void saldoToimiiOikein() {
        assertEquals(1000, kortti.saldo());
    }
}
