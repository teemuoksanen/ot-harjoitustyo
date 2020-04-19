# Arkkitehtuurikuvaus

## Rakenne

TreeniApp'in rakenne noudattaa kolmitasoista kerrosarkkitehtuuria:

![Rakennekaavio](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/rakennekaavio.png)

JavaFX:llä toteutettu käyttöliittymä sisältyy pakkaukseen _treeniapp.ui_. Sovelluslogiikka on keskitetty pakkaukseen _treeniapp.domain_. Sovelluksen tietojen pysyväistallennus sisältyy pakkaukseen _treeniapp.dao_. Tiedon tallentaminen on tässä versiossa toteutettu h2-tietokannalla, johon liittyvät luokat on sisällytetty pakkaukseen _treeniapp.dao.sql_.

## Luokka- ja pakkauskaavio

Ohjelman eri osien suhde on järjestetty seuraavan luokka- ja pakkauskaavion mukaisesti:

![Pakkauskaavio](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkauskaavio.png)

Pakkauskaavio päivitetty 19.4.2020: _TempSportDao_ korvattu _SQLSportDao_:lla.
