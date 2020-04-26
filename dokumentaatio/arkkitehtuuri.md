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

Sovelluksen loogisen datamallin muodostavat [User](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/domain/User.java)-, [Workout](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/domain/Workout.java)-, ja [Sport](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/domain/Sport.java)-luokat. User-luokka kuvaa ohjelman rekisteröityneitä käyttäjiä, Workout-luokka käyttäjien tekemiä yksittäisiä treenejä ja Sport-luokka eri urheilulajeja, joita treeneihin voi merkitä.

Toiminnallisista kokonaisuuksista vastaa luokka [TreeniAppService](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/src/main/java/treeniapp/domain/TreeniAppService.java). Luokka tarjoaa käyttöliittymän toiminnoille esimerkiksi seruaavat metodit:
- boolean login(String username)
- void logout()
- boolean newUser(String username, String name)
- boolean newWorkout(Workout workout)
- List<Workout> getWorkouts(User user)
- Workout getWorkoutById(int id)
- String getTotalTimeFormatted(User user)

_TreeniAppService_ pääsee käsiksi käyttäjiin, treeneihin ja lajeihin pakkauksen _treeniapp.dao_ rajapintojen UserDao, WorkOutDao ja SportDao kautta. Nämä rajapinnat toteuttavat luokat injektoidaan sovelluslogiikalle sen konstruktorikutsun yhteydessä.

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

### Uuden käyttäjän luominen

Kun käyttäjä kirjautumisnäkymässä painaa painiketta _loginNewUSerButton_, pääsee hän uuden käyttäjän luomisnäkymään. Kun hän syöttää oikemuotoisen käyttäjätunnuksen ja nimen sekä klikkaa painiketta _newUserButton_ , etenee sovelluksen kontrolli seuraavalla tavalla:

![Sekvenssikaavio: New User](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssikaavio-newuser.png)

## Ohjelman rakenteeseen jääneet heikkoudet

### Käyttöliittymä

Käyttöliittymän koodi on toistaiseksi yhdessä luokassa ja pääosin sen start-metodissa. Osa käyttöliittymästä on jo erotettu omiksi metodeikseen, mutta työtä olisi syytä jatkaa ja toiminnallisia kokonaisuuksia erottaa mahdollisuuksien mukaan kokonaan omiksi luokikseen.

Ylläpidettävyyden kannalta käyttöliittymärakenteen voisi korvata FXML-määrittelyllä.

### SQL-luokkien ylösheitot

Tietokannan kanssa keskustelevat metodit heittävät nyt exceptionit ylöspäin käsiteltäväksi. Virhetilanteiden käsittelyä olisi syytä parantaa.
