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
- [x] mahdollisuus luoda uusi käyttäjä

### Perusnäyttö

- [x] perusnäytössä näkyy kirjatut treenit (päivä, laji, kesto), tarkemmat treenikohtaiset tiedot voi avata erikseen
- [x] perusnäytössä näkyy myös yhteenveto treeneistä
- [x] uuden treenin tietojen lisääminen
- [x] uloskirjautuminen

### Uuden treenin lisääminen

- [x] valitusta lajista riippuen kysytään olennaiset tiedot
- [x] virheellisten syötteiden antaminen pyritään estämään (esim. päivän valinta kalenterin avulla)

### Treenin lisätiedot

- [x] treenin kaikki tiedot näkyvissä (jos syötetty)
- [x] treenin poistaminen mahdollista

## Jatkokehitysideoita

Perusversion jälkeen ohjelmaa voidaan täydentää ajan salliessa uusilla ominaisuuksilla:

- [ ] asetusten muuttaminen (esim. oma nimi)
- [ ] kirjautuminen salasanalla
- [ ] treenisuunnitelmien (tulevat treenit) lisääminen
- [ ] eri käyttäjäroolit
- [ ] uuden lajin lisääminen
