# Arkkitehtuurikuvaus

## Rakenne

TreeniApp'in rakenne noudattaa kolmitasoista kerrosarkkitehtuuria:

![Rakennekaavio](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/rakennekaavio.png)

JavaFX:llä toteutettu käyttöliittymä sisältyy pakkaukseen _treeniapp.ui_. Sovelluslogiikka on keskitetty pakkaukseen _treeniapp.domain_. Sovelluksen tietojen pysyväistallennus sisältyy pakkaukseen _treeniapp.dao_. Tiedon tallentaminen on tässä versiossa toteutettu h2-tietokannalla, johon liittyvät luokat on sisällytetty pakkaukseen _treeniapp.dao.sql_.

## Luokka- ja pakkauskaavio

Ohjelman eri osien suhde on järjestetty seuraavan luokka- ja pakkauskaavion mukaisesti:

![Pakkauskaavio](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkauskaavio.png)

Pakkauskaavio päivitetty 19.4.2020: _TempSportDao_ korvattu _SQLSportDao_:lla.

## Päätoiminnallisuudet

### Käyttäjän sisään- ja uloskirjautuminen

Kun käyttäjä kirjautumisnäkymässä syöttää käyttäjätunnuksen ja klikkaa painiketta _loginButton_ tai kun kirjautunut käyttäjä klikkaa päänäkymässä painiketta _logoutButton_, etenee sovelluksen kontrolli seuraavalla tavalla:

![Sekvenssikaavio: Login/Logout](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssikaavio-login_logout.png)

### Uuden käyttäjän luominen

Kun käyttäjä kirjautumisnäkymässä painaa painiketta _loginNewUSerButton_, pääsee hän uuden käyttäjän luomisnäkymään. Kun hän syöttää oikemuotoisen käyttäjätunnuksen ja nimen sekä klikkaa painiketta _newUserButton_ , etenee sovelluksen kontrolli seuraavalla tavalla:

![Sekvenssikaavio: New User](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssikaavio-newuser.png)
