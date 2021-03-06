 # TreeniApp

Sovelluksen avulla käyttäjien on mahdollista pitää kirjaa tekemistään treeneistä sekä tarkistaa niiden tilastoja. Sovellusta voi käyttää useampi käyttäjä.

Sovellus on Helsingin yliopiston tietojenkäsittelytieteen laitoksen **Ohjelmistotekniikan** kurssin harjoitustyö.

## Dokumentaatio

[Käyttöohje](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/testaus.md)

[Työaikakirjanpito](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

## Releaset

[Loppupalautus](https://github.com/teemuoksanen/ot-harjoitustyo/releases/tag/loppupalautus)

[Viikko 6](https://github.com/teemuoksanen/ot-harjoitustyo/releases/tag/viikko6)

[Viikko 5](https://github.com/teemuoksanen/ot-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot

### Ajaminen

Ohjelma ajetaan komennolla

```
mvn compile exec:java -Dexec.mainClass=treeniapp.Main
```
__HUOM!__ Treenien lisääminen ei ole vielä mahdollista, mutta treenien listaaminen onnistuu jo käyttäjätunnuksella *testaaja*.

### Suoritettavan JAR-pakkauksen generointi

Komennolla

```
mvn package
```

tehdään hakemistoon _target_ suoritettava jar-tiedosto nimellä _TreeniApp-1.0-SNAPSHOT.jar_.

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Checkstyle

Tiedoston [checkstyle.xml](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/TreeniApp/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_

### JavaDoc

JavaDoc luodaan komennolla

```
mvn javadoc:javadoc
```
JavaDoc-dokumentaatiota voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_
