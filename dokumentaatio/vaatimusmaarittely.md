# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen tarkoituksena on pitää yllä käyttäjän treenilokia sekä kertoa tilastoja tehdyistä treeneistä.

## Käyttäjät

Alkuvaiheessa sovelluksella on ainoastaan yksi käyttäjärooli (normaali käyttäjä). Myöhemmin sovellukseen saatetaan lisätä muita käyttäjärooleja (esim. valmentajalle oikeus selata useamman käyttäjän tietoja ja tehdä yhteenvetoja).

## Käyttöliittymäluonnos

Sovellus koostuu kirjautumisikkunasta, perusnäytöstä ja treenin lisäämisnäytöstä.

Sovellus aukeaa kirjautumisikkunaan, josta onnistuneen kirjautumisen jälkeen aukeaa perusnäyttö. Perusnäytöllä on listattu käyttäjän treenit sekä muutamia perustilastoja, kuten treenien yhteismäärä. Uusi treeni lisätään erilliseltä näytöltä.

## Suunnitellut toiminnallisuudet

### Kirjautumisikkuna

- [x] käyttäjä kirjautuu omalla käyttäjänimellään
- [x] jos käyttäjää ei ole olemassa, antaa ohjelma virheilmoituksen

### Perusnäyttö

- [ ] perusnäytössä näkyy kirjatut treenit (päivä, laji, kesto)
- [ ] uuden treenin tietojen lisääminen
- [x] uloskirjautuminen

## Jatkokehitysideoita

Perusversion jälkeen ohjelmaa voidaan täydentää ajan salliessa uusilla ominaisuuksilla:

- [ ] rekisteröityminen
- [ ] kirjautuminen salasanalla
- [ ] tilastot treeneistä
- [ ] mahdolliset lisäkentät treeneihin (matka, keskisyke)
- [ ] treenisuunnitelmien (tulevat treenit) lisääminen
- [ ] eri käyttäjäroolit
