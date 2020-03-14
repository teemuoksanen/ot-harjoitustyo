package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }

    @Test
    public void kortinSaldoOikeinAlussa() {
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void rahanLisaysToimii() {
        kortti.lataaRahaa(100);
        assertEquals("saldo: 1.10", kortti.toString());
    }

    @Test
    public void ostosOnnistuuKunRahaaOnTarpeeksi() {
        kortti.lataaRahaa(100);
        kortti.otaRahaa(50);
        assertEquals("saldo: 0.60", kortti.toString());
    }

    @Test
    public void saldoEiMuutuKunRahaaEiOleTarpeeksi() {
        kortti.lataaRahaa(100);
        kortti.otaRahaa(200);
        assertEquals("saldo: 1.10", kortti.toString());
    }

    @Test
    public void palauttaaTrueKunRahaaOnTarpeeksi() {
        kortti.lataaRahaa(100);
        assertEquals(true, kortti.otaRahaa(50));
    }

    @Test
    public void palauttaaFalseKunRahaaEiOleTarpeeksi() {
        kortti.lataaRahaa(100);
        assertEquals(false, kortti.otaRahaa(200));
    }

    @Test
    public void palauttaaOikeanSaldon() {
        kortti.lataaRahaa(100);
        assertEquals(110, kortti.saldo());
    }
}
