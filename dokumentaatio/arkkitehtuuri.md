# Arkkitehtuurikuvaus

## Rakenne

TreeniApp'in rakenne noudattaa kolmitasoista kerrosarkkitehtuuria:

![Rakennekaavio](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/rakennekaavio.png)

JavaFX:llä toteutettu käyttöliittymä sisältyy pakkaukseen _treeniapp.ui_. Sovelluslogiikka on keskitetty pakkaukseen _treeniapp.domain_. Sovelluksen tietojen pysyväistallennus sisältyy pakkaukseen _treeniapp.dao_. Tiedon tallentaminen on tässä versiossa toteutettu h2-tietokannalla, johon liittyvät luokat on sisällytetty pakkaukseen _treeniapp.dao.sql_.

## Käyttöliittymä

Ohjelman käyttöliittymä sisältää kuusi eri näkymää:
- kirjautuminen
- uuden käyttäjän luominen
- käyttäjän treenilista
- yksittäinen treeni
- treenin lisääminen
- asetukset

Näkymät on toteutettu neljänä eri luokkana:
- [treeniapp.ui.LoginUi](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/ui/LoginUi.java): kirjautuminen ja uuden käyttäjän luominen
- [treeniapp.ui.WorkoutsUi](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/ui/WorkoutsUi.java): käyttäjän treenilista ja yksittäinen treeni
- [treeniapp.ui.AddWorkoutUi](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/ui/AddWorkoutUi.java): treenin lisääminen
- [treeniapp.ui.SettingsUi](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/ui/SettingsUi.java): asetukset

Kukin luokka vastaa yhtä Stage-olioa (eli kukin niistä avautuu erilliseen ikkunaan) ja luokan eri näkymät on toteutettu niissä itsenäisinä Scene-olioina.

Näiden lisäksi käyttöliittymäluokilla on yhteinen luokka [treeniapp.ui.UiService](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/ui/UiService.java), joka tarjoaa yhteisiä, useammassa näkymässä käytettäviä metodeja (esim. virheilmoitukset) varsinaisille käyttöliittymäluokille.

Käyttöliittymä on rakennettu ohjelmallisesti luokkaan [treeniapp.ui.TreeniUi](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/ui/TreeniUi.java). Käyttöliittymä on eriytetty sovelluslogiikasta ohjaamalla käyttöliittymän kutsut _treeniAppService_-olion metodeille.

## Sovelluslogiikka

Sovelluksen loogisen datamallin muodostavat [User](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/domain/User.java)-, [Workout](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/domain/Workout.java)-, ja [Sport](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/domain/Sport.java)-luokat. User-luokka kuvaa ohjelman rekisteröityneitä käyttäjiä, Workout-luokka käyttäjien tekemiä yksittäisiä treenejä ja Sport-luokka eri urheilulajeja, joita treeneihin voi merkitä.

Toiminnallisista kokonaisuuksista vastaa luokka [TreeniAppService](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/domain/TreeniAppService.java). Luokka tarjoaa käyttöliittymän toiminnoille esimerkiksi seruaavat metodit:
- boolean login(String username)
- void logout()
- boolean newUser(String username, String name)
- boolean newWorkout(Workout workout)
- boolean newSport(Sport sport)
- List<Workout> getWorkouts(User user)
- Workout getWorkoutById(int id)
- String getTotalTimeFormatted(User user)

_TreeniAppService_ pääsee käsiksi käyttäjiin, treeneihin ja lajeihin pakkauksen _treeniapp.dao_ rajapintojen UserDao, WorkOutDao ja SportDao kautta. Nämä rajapinnat toteuttavat luokat injektoidaan sovelluslogiikalle sen konstruktorikutsun yhteydessä.

Lisäksi sovelluslogiikan osana on [Formatter](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/domain/Formatter.java)-luokka, jonka tarkoituksena on muotoilla tallennettua raakadataa käytettäväksi erityisesti käyttöliittymässä.

### Luokka- ja pakkauskaavio

Ohjelman eri osien suhde on järjestetty seuraavan luokka- ja pakkauskaavion mukaisesti:

![Pakkauskaavio](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkauskaavio.png)

## Tietojen pysyväistallennus

Tietojen tallennus tapahtuu SQL-tietokantaan pakkauksessa _treeniapp.dao.sql_ olevien [SQLUserDao](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/dao/sql/SQLUserDao.java)-, [SQLWorkoutDao](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/dao/sql/SQLWorkoutDao.java)- ja [SQLSportDao](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/dao/sql/SQLSportDao.java)-luokkien avulla.

Luokat noudattavat Data Access Object -suunnittelumallia. Sovelluslogiikka ei käytä luokkia suoraan, vaan ne on eristetty _UserDao_-, _WorkoutDao_- ja _SportDao_-rajapintojen taakse. Näin ne tallennustapaa voidaan muuttaa joustavasti tarpeen mukaan esim. pilvipalveluun.

SQL-yhteyskutsut on toteutettu keskitetysti [SQLService](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/dao/sql/SQLUserDao.java)-luokassa, jolloin muutosten tekeminen on helpompaa. _SQLService_-luokka saa käytettävän tietokannan tiedot (tietokannan nimi, käyttäjätunnus ja salasana) [config.properties](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/config.properties)-tiedostosta, jolloin niitä voidaan tarvittaessa muokata.

## Päätoiminnallisuudet

### Käyttäjän sisään- ja uloskirjautuminen

Kun käyttäjä kirjautumisnäkymässä syöttää käyttäjätunnuksen ja klikkaa painiketta _loginButton_ tai kun kirjautunut käyttäjä klikkaa päänäkymässä painiketta _logoutButton_, etenee sovelluksen kontrolli seuraavalla tavalla:

![Sekvenssikaavio: Login/Logout](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssikaavio-login_logout.png)

### Käyttäjän treenien haku

Kun käyttäjä kirjautuu sisään, lisää uuden treenin tai päivittää asetuksia, päivittää sovellus treeninäkymän tiedot seuraavan mukaisesti;

![Sekvenssikaavio: redrawWorkouts()](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssikaavio-redrawworkouts.png)

### Uuden käyttäjän luominen

Kun käyttäjä kirjautumisnäkymässä painaa painiketta _loginNewUSerButton_, pääsee hän uuden käyttäjän luomisnäkymään. Kun hän syöttää oikemuotoisen käyttäjätunnuksen ja nimen sekä klikkaa painiketta _newUserButton_ , etenee sovelluksen kontrolli seuraavalla tavalla:

![Sekvenssikaavio: New User](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssikaavio-newuser.png)

### Uuden treenin luominen

Kun käyttäjä luo uuden treenin, etenee sovelluksen kontrolli seuraavalla tavalla:

![Sekvenssikaavio: New Workout](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssikaavio-newworkout.png)

Käyttäjän syötteiden tarkistus tapahtuu käyttöliittymän tasolla. Käyttöliittymävalinnat rajaavat jo automaattisesti mahdollisia arvoja.

## Ohjelman rakenteeseen jääneet heikkoudet

### Käyttöliittymä

Käyttöliittymän koodi on jaettu tällä hetkellä luokkiin "ikkunakohtaisesti". Sovelluksen laajentuessa olisi kuitenkin hyötyä erottaa vielä pienempiä toiminnallisia kokonaisuuksia omiksi luokikseen. Esimerkiksi yksittäiset treenit voisi erottaa omiksi olioluokikseen.

Ylläpidettävyyden kannalta käyttöliittymärakenteen voisi korvata FXML-määrittelyllä.

### SQL-luokkien ylösheitot

Tietokannan kanssa keskustelevat metodit heittävät nyt exceptioneita osittain käyttöliittymätason käsiteltäväksi. Virhetilanteiden käsittelyä olisi syytä parantaa ja keskittää osaksi sovelluslogiikkaa.
