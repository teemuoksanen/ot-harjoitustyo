# TreeniApp

Sovelluksen avulla käyttäjien on mahdollista pitää kirjaa tekemistään treeneistä sekä tarkistaa niiden tilastoja. Sovellusta voi käyttää useampi käyttäjä.

Sovellus on Helsingin yliopiston tietojenkäsittelytieteen laitoksen **Ohjelmistotekniikan** kurssin harjoitustyö.

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/teemuoksanen/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

## Komentorivitoiminnot

Ohjelma ajetaan komennolla

```
mvn compile exec:java -Dexec.mainClass=treeniapp.Main
```

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
