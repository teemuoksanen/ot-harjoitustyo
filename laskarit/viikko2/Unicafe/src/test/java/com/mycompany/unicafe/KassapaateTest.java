package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate kassa;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(100);
    }

    @Test
    public void luotuKassaOlemassa() {
        assertTrue(kassa!=null);      
    }

    @Test
    public void kassanSaldoOikeinAlussa() {
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void edullistenLounaidenMaaraOikeinAlussa() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaidenLounaidenMaaraOikeinAlussa() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void edullinenLounasRahalla_Ok_KassaTasmaa() {
        kassa.syoEdullisesti(1000);
        assertEquals(100240, kassa.kassassaRahaa());
    }

    @Test
    public void edullinenLounasRahalla_Ok_VaihtorahaOikein() {
        assertEquals(1000-240, kassa.syoEdullisesti(1000));
    }

    @Test
    public void edullinenLounasRahalla_Ok_LounasmaaraTasmaa() {
        kassa.syoEdullisesti(1000);
        kassa.syoEdullisesti(1000);
        assertEquals(2, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void edullinenLounasRahalla_MaksuEiRiita_KassaTasmaa() {
        kassa.syoEdullisesti(10);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void edullinenLounasRahalla_MaksuEiRiita_VaihtorahaOikein() {
        assertEquals(10, kassa.syoEdullisesti(10));
    }

    @Test
    public void edullinenLounasRahalla_MaksuEiRiita_LounasmaaraTasmaa() {
        kassa.syoEdullisesti(10);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukasLounasRahalla_Ok_KassaTasmaa() {
        kassa.syoMaukkaasti(1000);
        assertEquals(100400, kassa.kassassaRahaa());
    }

    @Test
    public void maukasLounasRahalla_Ok_VaihtorahaOikein() {
        assertEquals(1000-400, kassa.syoMaukkaasti(1000));
    }

    @Test
    public void maukasLounasRahalla_Ok_LounasmaaraTasmaa() {
        kassa.syoMaukkaasti(1000);
        kassa.syoMaukkaasti(1000);
        assertEquals(2, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maukasLounasRahalla_MaksuEiRiita_KassaTasmaa() {
        kassa.syoMaukkaasti(10);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void maukasLounasRahalla_MaksuEiRiita_VaihtorahaOikein() {
        assertEquals(10, kassa.syoMaukkaasti(10));
    }

    @Test
    public void maukasLounasRahalla_MaksuEiRiita_LounasmaaraTasmaa() {
        kassa.syoMaukkaasti(10);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void edullinenLounasKortilla_Ok_VeloitusOnnistuu() {
        kassa.lataaRahaaKortille(kortti, 300);
        assertTrue(kassa.syoEdullisesti(kortti));
        assertEquals(160, kortti.saldo());
    }

    @Test
    public void maukasLounasKortilla_Ok_VeloitusOnnistuu() {
        kassa.lataaRahaaKortille(kortti, 300);
        assertTrue(kassa.syoMaukkaasti(kortti));
        assertEquals(0, kortti.saldo());
    }

    @Test
    public void edullinenLounasKortilla_Ok_LounasmaaraTasmaa() {
        kassa.lataaRahaaKortille(kortti, 300);
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukasLounasKortilla_Ok_LounasmaaraTasmaa() {
        kassa.lataaRahaaKortille(kortti, 300);
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void edullinenLounasKortilla_MaksuEiRiita() {
        assertFalse(kassa.syoEdullisesti(kortti));
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertEquals(100, kortti.saldo());
    }

    @Test
    public void maukasLounasKortilla_MaksuEiRiita() {
        assertFalse(kassa.syoMaukkaasti(kortti));
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        assertEquals(100, kortti.saldo());
    }

    @Test
    public void edullinenLounasKortilla_Ok_KassaEiKasva() {
        kassa.lataaRahaaKortille(kortti, 300);
        kassa.syoEdullisesti(kortti);
        assertEquals(100300, kassa.kassassaRahaa());
    }

    @Test
    public void maukasLounasKortilla_Ok_KassaEiKasva() {
        kassa.lataaRahaaKortille(kortti, 300);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100300, kassa.kassassaRahaa());
    }

    @Test
    public void kortinLatausToimii() {
        kassa.lataaRahaaKortille(kortti, 600);
        assertEquals(100600, kassa.kassassaRahaa());
        assertEquals(700, kortti.saldo());
    }

    @Test
    public void kortinNegatiivinenLatausEiVaikuta() {
        kassa.lataaRahaaKortille(kortti, -600);
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(100, kortti.saldo());
    }
    
}
