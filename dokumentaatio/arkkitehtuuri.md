# Arkkitehtuurikuvaus

## Rakenne

TreeniApp'in rakenne noudattaa kolmitasoista kerrosarkkitehtuuria:

![Rakennekaavio](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/rakennekaavio.png)

JavaFX:llä toteutettu käyttöliittymä sisältyy pakkaukseen _treeniapp.ui_. Sovelluslogiikka on keskitetty pakkaukseen _treeniapp.domain_. Sovelluksen tietojen pysyväistallennus sisältyy pakkaukseen _treeniapp.dao_. Tiedon tallentaminen on tässä versiossa toteutettu h2-tietokannalla, johon liittyvät luokat on sisällytetty pakkaukseen _treeniapp.dao.sql_.

## Käyttöliittymä

Ohjelman käyttöliittymä sisältää viisi eri näkymää:
- kirjautuminen
- uuden käyttäjän luominen
- käyttäjän treenilista
- yksittäinen treeni
- treenin lisääminen

Näkymät on toteutettu itsenäisinä Scene-olioina. Treenin lisääminen on toteutettu muista erilliseen Stage-olioon (eli se avautuu erilliseen ikkunaan), muut näkymät ovat näkyvissä yksi kerrallaan yhteisessä Stage-oliossa.

Käyttöliittymä on rakennettu ohjelmallisesti luokkaan [treeniapp.ui.TreeniUi](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/ui/TreeniUi.java). Käyttöliittymä on eriytetty sovelluslogiikasta ohjaamalla käyttöliittymän kutsut _treeniAppService_-olion metodeille.

## Sovelluslogiikka

### Luokka- ja pakkauskaavio

Ohjelman eri osien suhde on järjestetty seuraavan luokka- ja pakkauskaavion mukaisesti:

![Pakkauskaavio](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkauskaavio.png)

## Päätoiminnallisuudet

### Käyttäjän sisään- ja uloskirjautuminen

Kun käyttäjä kirjautumisnäkymässä syöttää käyttäjätunnuksen ja klikkaa painiketta _loginButton_ tai kun kirjautunut käyttäjä klikkaa päänäkymässä painiketta _logoutButton_, etenee sovelluksen kontrolli seuraavalla tavalla:

![Sekvenssikaavio: Login/Logout](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssikaavio-login_logout.png)

### Uuden käyttäjän luominen

Kun käyttäjä kirjautumisnäkymässä painaa painiketta _loginNewUSerButton_, pääsee hän uuden käyttäjän luomisnäkymään. Kun hän syöttää oikemuotoisen käyttäjätunnuksen ja nimen sekä klikkaa painiketta _newUserButton_ , etenee sovelluksen kontrolli seuraavalla tavalla:

![Sekvenssikaavio: New User](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssikaavio-newuser.png)
