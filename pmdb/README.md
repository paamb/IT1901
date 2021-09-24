# Personal Movie Database (PMDB)
Personal movie database er et prosjekt i faget IT1901. Appen gir brukeren mulighet for lagre filmer og vurdere filmer brukeren har sett. Dette gjør at brukeren har mulighet for å få finne filmer som personen har planlagt å se, og finne tilbake til filmer som brukeren synes var gode.

Appen har dermed to hovedfunksjonaliteter:
- Lagre filmer som brukeren skal se i fremtiden.
- Vurdere filmer som brukeren har sett.

## Bygging og kjøring av prosjektet

Prosjektet er bygd på maven og må utføre kommandoer deretter.

Er du i rotmappen **gr2105** må du gå inn i mappen **pmdb** før du kan gå videre, kjør:

```
cd pmdb
```

### Kjøring av prosjektet


For å sette opp prosjektet, kjør:

```
mvn clean install
```

For å kjøre prosjektet, kjør:

```
cd ui
mvn javafx:run
```

### Testing av prosjektet
For å kjøre alle testene, kjør:

```
mvn test
```

Hvis du bare vil kjøre de grafiske testene, kjør:

```
cd ui
mvn test
```

Hvis du bare vil kjøre backend testene, kjør:
```
cd core
mvn test
```

## Prototype
Under er det lagt ved bilder av prototypen:

![Bildet ble ikke vist](images/desktopImage.png)


Bilde 1:

Dette er bildet en bruker får opp når personen åpner appen. Øverst ser man faner der man kan enten velge å se "Min liste", "Vurderte filmer" eller "Alle filmer". Hvis man trykker på "Vurderte filmer" kommer man til en side der man kan se filmene som man har sett og er vurdert. Trykker man på "Min liste" kommer man til siden som viser filmene man har lagt til, men ikke vurderte enda. Hvis man trykker "Alle filmer" kommer man til siden der man ser alle filmene, både de som er vurderte, og de som er i min liste.

På hver side har man muligheten for å bestemme hvilke filmer man vil se basert på en sjanger. 

Trykker man på "pluss (+)"-knappen nederst til høyre i bildet har man mulighet for å legge til en film i "Min liste" (se bilde 2).

Trykker man på "Endre"-knappen på en film vil man få opp et bilde som gir brukeren muligheten for å endre på filmen (se bilde 2).


![Bildet ble ikke vist](images/movieImage.png)

Bilde 2:

Bildet viser et utklipp av en enkelt film. Her har man mulighet for å endre på filmen. Man skal ha mulighet for å endre på tittelen, beskrivelsen av filmen, lengden på filmen, og om filmen er sett. Trykker man "Bekreft" vil endringene bli lagret.

Hvis man trykker på "pluss (+)"-knappen på startskjermen (se bilde 1), vil man også kommet til skjermbildet bilde 2, men da er skjermbildet tomt. Man har muligheten for å lage et nytt filmobjekt, som vil bli lagt til i "Min liste" når man har fylt ut og trykket "Bekreft".
