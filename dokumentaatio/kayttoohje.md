# Käyttöohje

## Konfigurointi

Ohjelma olettaa, että sen käynnistyshakemistossa on konfiguraatiotiedosto _config.properties_, jossa määritellään käytettävän tietokannan nimi, käyttäjätunnus ja salasana:

```
databaseDB=jdbc:h2:./treeniapp    (tietokannan nimi)
usernameDB=sa                     (tietokannan käyttäjätunnus)
passwordDB=                       (tietokannan salasana)
```

Normaalitilanteessa käyttäjä voi käyttää yllä mainittuja oletusarvoja.

