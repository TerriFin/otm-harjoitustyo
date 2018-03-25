/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

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
public class KassapaateTest {

    Kassapaate kassa;
    Maksukortti rikasKortti;
    Maksukortti koyhaKortti;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
        rikasKortti = new Maksukortti(1000);
        koyhaKortti = new Maksukortti(200);
    }

    @Test
    public void kassanRahatAlussaOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void kassanEdullisiaAnnoksiaMyytyOikeaMaaraAlussa() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void kassanMaukkaitaAnnoksiaMyytyOikeaMaaraAlussa() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void josKateismaksuRiittavaKasvaaKassanRahatEdullisesti() {
        kassa.syoEdullisesti(500);
        assertEquals(100240, kassa.kassassaRahaa());
    }

    @Test
    public void josKateismaksuRiittavaKasvaaKassanRahatMaukkaasti() {
        kassa.syoMaukkaasti(500);
        assertEquals(100400, kassa.kassassaRahaa());
    }

    @Test
    public void josKateismaksuYliOnVaihtoRahaOikeinEdullisesti() {
        assertEquals(260, kassa.syoEdullisesti(500));
    }

    @Test
    public void josKateismaksuYliOnVaihtoRahaOikeinMaukkaasti() {
        assertEquals(100, kassa.syoMaukkaasti(500));
    }

    @Test
    public void josKateismaksuRiittavaLounaidenMaaraKasvaaEdullisesti() {
        kassa.syoEdullisesti(500);
        kassa.syoEdullisesti(500);
        kassa.syoEdullisesti(200);
        assertEquals(2, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void josKateismaksuRiittavaLounaidenMaaraKasvaaMaukkaasti() {
        kassa.syoMaukkaasti(500);
        kassa.syoMaukkaasti(500);
        kassa.syoMaukkaasti(200);
        assertEquals(2, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void josKateismaksuEiRiittavaKassanRahatEiMuutuEdullisesti() {
        kassa.syoEdullisesti(200);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void josKateismaksuEiRiittavaKassanRahatEiMuutuMaukkaasti() {
        kassa.syoMaukkaasti(300);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void josKateismaksuEiRiittavaKaikkiRahatPalautetaanEdullisesti() {
        assertEquals(200, kassa.syoEdullisesti(200));
    }

    @Test
    public void josKateismaksuEiRiittavaKaikkiRahatPalautetaanMaukkaasti() {
        assertEquals(300, kassa.syoMaukkaasti(300));
    }

    @Test
    public void josKortillaTarpeeksiRahaaPalautetaanTrueEdullisesti() {
        assertTrue(kassa.syoEdullisesti(rikasKortti));
    }

    @Test
    public void josKortillaTarpeeksiRahaaPalautetaanTrueMaukkaasti() {
        assertTrue(kassa.syoMaukkaasti(rikasKortti));
    }

    @Test
    public void josKortillaTarpeeksiRahaaVeloitetaanOikeaSummaEdullisesti() {
        kassa.syoEdullisesti(rikasKortti);
        assertEquals(760, rikasKortti.saldo());
    }

    @Test
    public void josKortillaTarpeeksiRahaaVeloitetaanOikeaSummaMaukkaasti() {
        kassa.syoMaukkaasti(rikasKortti);
        assertEquals(600, rikasKortti.saldo());
    }

    @Test
    public void josKortillaTarpeeksiRahaaKasvaaMyytyjenLounaidenMaaraEdullisesti() {
        kassa.syoEdullisesti(rikasKortti);
        kassa.syoEdullisesti(rikasKortti);
        kassa.syoEdullisesti(koyhaKortti);
        assertEquals(2, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void josKortillaTarpeeksiRahaaKasvaaMyytyjenLounaidenMaaraMaukkaasti() {
        kassa.syoMaukkaasti(rikasKortti);
        kassa.syoMaukkaasti(rikasKortti);
        kassa.syoMaukkaasti(koyhaKortti);
        assertEquals(2, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void josKortillaEiTarpeeksiRahaaKortinRahatEiMuutuEdullisesti() {
        kassa.syoEdullisesti(koyhaKortti);
        assertEquals(200, koyhaKortti.saldo());
    }

    @Test
    public void josKortillaEiTarpeeksiRahaaKortinRahatEiMuutuMaukkaasti() {
        kassa.syoMaukkaasti(koyhaKortti);
        assertEquals(200, koyhaKortti.saldo());
    }

    @Test
    public void josKortillaEiTarpeeksiRahaaPalauttaaKassaFalseEdullisesti() {
        assertFalse(kassa.syoEdullisesti(koyhaKortti));
    }

    @Test
    public void josKortillaEiTarpeeksiRahaaPalauttaaKassaFalseMaukaasti() {
        assertFalse(kassa.syoMaukkaasti(koyhaKortti));
    }

    @Test
    public void kassanRahatEiMuutuKortillaOstaessaEdullisesti() {
        kassa.syoEdullisesti(rikasKortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void kassanRahatEiMuutuKortillaOstaessaMaukkaasti() {
        kassa.syoMaukkaasti(rikasKortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void kortilleLadattaessaKortinSaldoMuuttuuOikein() {
        kassa.lataaRahaaKortille(koyhaKortti, 1000);
        assertEquals(1200, koyhaKortti.saldo());
    }

    @Test
    public void kortilleLadattaessaKassanRahatKasvavatOikein() {
        kassa.lataaRahaaKortille(koyhaKortti, 1000);
        assertEquals(101000, kassa.kassassaRahaa());
    }

    @Test
    public void ladatessaNegatiivistaSummaaKortilleKortinSaldoEiMuutu() {
        kassa.lataaRahaaKortille(koyhaKortti, -500);
        assertEquals(200, koyhaKortti.saldo());
    }
    
    @Test
    public void ladatessaNegatiivistaSummaaKortilleKassanSaldoEiMuutu() {
        kassa.lataaRahaaKortille(koyhaKortti, -500);
        assertEquals(100000, kassa.kassassaRahaa());
    }
}
