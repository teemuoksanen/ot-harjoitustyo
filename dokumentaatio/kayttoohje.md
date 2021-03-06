# Käyttöohje

## Konfigurointi

Ohjelma olettaa, että sen käynnistyshakemistossa on konfiguraatiotiedosto _config.properties_, jossa määritellään käytettävän tietokannan nimi, käyttäjätunnus ja salasana:

```
databaseDB=jdbc:h2:./treeniapp     (tietokannan nimi)
usernameDB=sa                      (tietokannan käyttäjätunnus)
passwordDB=                        (tietokannan salasana)
```

Normaalitilanteessa käyttäjä voi käyttää yllä mainittuja oletusarvoja.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla

```
java -jar TreeniApp.jar
```

## Kirjautuminen

Sovellus käynnistyy kirjautumisnäkymään:

![Kirjautumisnäkymä](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/ohje-kirjautuminen.png)

Voit kirjautua sisään syöttämällä käyttäjänimesi ja painamalla _Kirjaudu_.

## Uuden käyttäjän luominen

Uuden käyttäjän voit luoda painamalla kirjautumisnäkymässä _Luo uusi käyttäjä_. Tällöin avautuu uuden käyttäjän luomisnäkymä:

![Uuden käyttäjän luomisnäkymä](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/ohje-uusikayttaja.png)

Syötä uuden käyttäjän käyttäjätunnus (enintään X merkkiä) sekä nimi (enintään X merkkiä). Uusi käyttäjä luodaan, kun painat _Luo käyttäjä_.

Halutessasi voit palata kirjautumisnäkymään uutta käyttäjää luomatta painamalla _Peruuta_.

## Treeninäkymät

Kun olet kirjautunut sisään oikealla käyttäjätunnuksella, näet kaikki kirjaamasi treenit sekä yhteenvedon treeneistäsi:

![Treenilistanäkymä](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/ohje-treenilista.png)

Voit tarkastella yksittäisen treenin tarkempia tietoja painamalla kyseisen treenirivin oikeassa reunassa olevaa **+**-painiketta:

![Yksittäisen treenin näkymä](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/ohje-treeni.png)

Pääset takaisin treenilistasi painamalla _Takaisin_.

Treeninäkymästä pääset muokkaamaan myös ohjelman asetuksia painamalla _Asetukset_-painiketta. Voit kirjautua ulos ohelmasta painamalla treeninäkymän alareunassa olevaa _Kirjaudu ulos_ -painiketta.

## Uuden treenin lisääminen

Uuden treenin voit lisätä painamalla treeninäkymässä _Lisää treeni_. Tällöin sovellus avaa uuden ikkunan, jossa voit syöttää uuden treenin tiedot:

![Uuden treenin lisäämisnäkymä](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/ohje-lisaatreeni.png)

Treenin lisääminen kannattaa aloittaa valitsemalla urheilulaji. Valitusta lajista riippuen _matka_-kenttä joko näytetään tai ei näytetä - esimerkiksi kuntosalitreenissä matkan lisääminen ei ole tarpeen.

_Päivämäärän_ voit valita kalenterinäkymästä, joka aukeaa painamalla kalenterin kuvaa. Et voi valita tulevaisuudessa olevaa päivää. Treenin aloitusaika valitaan kohdassa _kello_ valitsemalla tunti ja minuutti alasvetovalikoista. Myös treenin _kestossa_ valitaan erikseen tunnit ja minuutit kenttien liukusäätimillä.

_Matkan_ kilometri- ja metrimäärät syötetään erikseen. Voit myös syöttää pelkän metrimäärän (esim. 3500 metriä), jolloin sovellus tallettaessaan automaattisesti ehdottaa korjausta matkaan (esim. 3 km 500 m). Kuten edellä mainittiin, matka-kenttää ei näytetä kaikkien urheilulajien kohdalla.

Lopuksi voit syöttää myös _keskisykkeen_ sekä vapaamuotoiset _muistiinpanot_ treenistäsi.

Tallenna uusi treenisi painamalla lopuksi _Lisää_. Jos haluat aloittaa täyttämisen alusta, voit painaa _Tyhjennä_. Lisäämisikkunan voit sulkea kokonaan painalla _Sulje_.

## Asetukset

Ohjelman asetuksiin pääset treeninäkymän alalaidassa olevasta _Asetukset_-näppäimestä.

![Asetusnäkymä](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/ohje-asetukset.png)

Asetuksissa voit vaihtaa treeninäkymässä näytettävää nimeäsi antamalla uuden nimen ja painamlla _Muuta_. Uusi nimi päivittyy, kun suljet asetusikkunan.

Voit lisätä myös uusia lajeja. Anna lajin nimi sekä valitse, haluatko että treeniä lisättäessä kyseisen lajin kohdalla näytetään matka-kenttä. Paina lopuksi _Lisää_-painiketta.

## Testikäyttäjä

Sovelluksen käyttöä voi testata ilman uuden käyttäjän luomista käyttämällä tunnusta _testaaja_. Käyttäjälle on luotu valmiiksi kolme esimerkkitreeniä.

Testikäyttäjän luomat uudet treenit ja lajit tallentuvat normaalisti tietokantaan, mutta sovelluksen uudelleenkäynnistyksen yhteydessä testikäyttäjän nimi palautetaan aina alkuperäiseen muotoonsa. Testikäyttäjän kolmea esimerkkitreeniä ei pysty käyttöliittymästä myöskään poistamaan, millä pyritään estämään mahdollista tietokannan vaurioitumista, koska nämä esimerkkitreenit uudelleenkirjoitetaan aina sovelluksen uudelleenkäynnistyksen yhteydessä.

![Esimerkkitreenin poisto](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/ohje-testikayttaja.png)
