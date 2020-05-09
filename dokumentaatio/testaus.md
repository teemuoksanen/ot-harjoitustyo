# Testausdokumentti

Sovellusta on pyritty testaamaan kattavasti sekä automatisoiduin yksikkö- ja integraatiotestein JUnitilla että manuaalisilla järjestelmätason testeillä.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Sovelluslogiikan (eli [treeniapp.domain](https://github.com/teemuoksanen/ot-harjoitustyo/tree/master/TreeniApp/src/main/java/treeniapp/domain)-pakkauksen luokkien) testaus on jaettu seuraavasti:

- Testipakkauksen [treeniapp.domain](https://github.com/teemuoksanen/ot-harjoitustyo/tree/master/TreeniApp/src/test/java/treeniapp/domain) juuressa olevat testiluokat sisältävät Sport-, Workout- ja User-olioluokkien yksikkötestit.

- Testipakkauksessa [treeniapp.domain.fake](https://github.com/teemuoksanen/ot-harjoitustyo/tree/master/TreeniApp/src/test/java/treeniapp/domain/fake) olevat testiluokat sisältävät koko sovelluslogiikan integraatiotestauksen lukuunottamatta SQL-tietokantayhteyksiä. Näissä testeissä datan pysyväistallennukseen käytetään DAO-rajapintojen keskusmuistitoteutuksia [FakeSportDao](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/test/java/treeniapp/dao/FakeSportDao.java), [FakeWorkoutDao](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/test/java/treeniapp/dao/FakeWorkoutDao.java) ja [FakeUserDao](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/test/java/treeniapp/dao/FakeUserDao.java). Tämä mahdollistaa sovelluslogiikan testaamisen silloinkin, kun varsinaista pysyväistallennusta ei sovelluksella ole käytössä.

- Testipakkauksessa [treeniapp.domain.sql](https://github.com/teemuoksanen/ot-harjoitustyo/tree/master/TreeniApp/src/test/java/treeniapp/domain/fake) olevat testiluokat vastaavat pääosin vastaavat testit kuin _treeniapp.domain.fake_, mutta se käyttää datan pysyväistallennuksen testaamiseen DAO-rajapintojen oikeita SQL-toteutuksia. Nämä integraatiotestit kattavat siis lähes koko nykyisen sovelluslogiikan.

### DAO-luokat

_TULOSSA_

### Testauskattavuus

![Testauskattavuus](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/testikattavuus.png)

Testit kattavat koko sovelluslogiikan sekä DAO-luokat lukuunottamatta mahdollisten heittojen käsittelyä.

Testauskattavuudessa ei otettu huomioon ainoastaan sovelluksen käynnistämiseen tarkoitettua Main-luokkaa sekä käyttöliittymäkerroksen luokkia.

## Järjestelmätestaus

Sovelluksen järjestelmätestaus suoritettiin manuaalisesti kolmella eri tietokoneella MacOS- ja Linux-ympäristöissä.

### Asennus ja konfigurointi

_TULOSSA_

### Toiminnallisuudet

Toiminnallisuuksia testattiin käymällä manuaalisesti läpi [vaatimusmäärittelyn](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md) mukaisia toiminnallisuuksia ja [käyttöohjeen](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) mukaisia tilanteita.

Toiminnallisuuksien yhteydessä yritettiin antaa tyhjiä, liian lyhyitä, liian pitkiä tai muutoin virheellisiä syötteitä sovelluksen kenttiin.

## Sovellukseen jäänee laatuongelmat

Sovelluksen virheilmoituksia olisi hyvä parantaa ainakin seuraavissa tilanteissa:

- _TULOSSA_
