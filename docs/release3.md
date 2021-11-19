# Release 3

Dokumentasjon for release 3:

- [Implementasjon av labels](#implementasjon-av-labels)
- [Implementasjon av API](#implementasjon-av-api)
- [Getter som returnerer iterator istedenfor kopi av liste](#getter-som-returnerer-iterator-istedenfor-kopi-av-liste)
- [run-with-server script](#run-with-server-script)

### Implementasjon av labels

I release 2 hadde vi planer om å implementere emneknagger til en film, men gikk bort fra det da vi fant ut at vi hadde nok implementasjon fra før av. Vi bestemte oss dermed å implementere emneknagger i denne releasen, da vi allerede hadde en konseptuell modell for hvordan det ville se ut, og at vi allerede hadde implementert en del av logikken i release 2.

#### Problemer ved implementasjon av labels

Da vi skulle implementere `Label` hadde vi problemer med hvordan strukturen skulle se ut. Én `Label` skulle ha mange `Movie`-objekter, og én `Movie` skulle også ha mange `Label`-objekter. Dette skapte en mange-til-mange relasjon mellom `Movie` og `Label`. Vi var usikre på hvordan vi ville implementere denne strukturen. Relasjonen mellom `Movie` og `Label` ga oss også problemer med lagring. Vi var usikre på hvordan vi skulle lagre `Label`-objektene, da vi ville unngå å lagre og opprette samme objekt flere ganger.

Løsning 1:

Vi tenkte først at vi måtte ha en hovedliste i `MovieList`, som holdt styr på alle `Label`-objektene, og at et `Movie`-objekt skulle ha en liste med sine `Label`-objekt. Et nytt `Label`-objekt skulle opprettes om det ikke fantes i hovedlisten i `MovieList`. Da måtte vi lagre `Label`-objektet både i `MovieList`-en og i `Movie`-en med denne `Label`-en. Vi fant ut at dette både var tungvindt og unødvendig.

Løsning 2:

Vi beholdt idéen om at et `Movie`-objekt skulle holde på sine `Label`-objekter. Så lagde vi en metode i `MovieList` kalt `getAllLabels`, som går gjennom alle `Movie`-objektene og finner alle `Label`-objektene. Da slapp vi å lagre `Label`-objektene både i `MovieList` og i `Movie`.

Lagring av Labels:

Vi hadde også som nevnt problemer med å lagre `Label`-objektene, fordi vi ikke ville lagre samme objekt flere ganger. Derfor bestemte vi oss for å lagre `Label`-objektene først, og så lagre en liste men navnene på `Label`-objektene for hver `Movie`-objekt. Når vi da skal deserialisere et `Movie`-objekt matcher vi bare navnet med navnet på objektet som allerede er deseralisert, og legge det til i `Movie`-objekte hvis det matcher.

### Implementasjon av API

Vi har benyttet oss av Jersey for bygging og konfigurering av serveren. Videre har vi brukt Jakarta for å definer og håndtere http requests (PUT, GET). API requestene støtter JSON format.

Vi har tre request metoder for behandling av server. Vi har:

`getMovieList()` - Dette er en get-metode som tar inn serveren i JSON format, og oppretter et `MovieList`-objekt ved hjelp av våre definerte deserialiserere. Den eneste gangen denne metoden blir kalt er under oppstart. Dette gjør vi, fordi appen trenger hele `MovieList`-objektet ved oppstart, fordi vi vil vise alle filmene lagret på serveren ved oppstart. Objektet blir lagret som et felt i `MovieListController`, når en endring skjer på `MovieList`-objektet vil metoden `putMovieList` bli kalt og JSON filen på serveren oppdatert.

`getMovieByTitle` - Dette er en get-metode som henter ut en film ved hjelp av tittel. Denne benyttes ikke i appen, men var grei å ha under testing i Postman.

`putMovieList` - Denne metoden oppdaterer JSON formatet på server med tilhørende `MovieList` som blir sendt inn som parameter.

#### PUT /movielist

#### Request eksempel

```json
{
  "labels": [
    {
      "title": "Action",
      "color": "#58E5EB"
    },
    {
      "title": "Eventyr",
      "color": "#314677"
    }
  ],
  "movies": [
    {
      "title": "Dette er en film",
      "description": "Flott film",
      "duration": 122,
      "watched": true,
      "labels": ["Action", "Eventyr"],
      "reviews": [
        {
          "comment": "Kjempegøy",
          "rating": 3,
          "whenWatched": "2021-11-05"
        }
      ]
    },
    {
      "title": "Film2",
      "description": "Kjempespennende",
      "duration": 122,
      "watched": false,
      "labels": ["Action"],
      "reviews": []
    }
  ]
}
```

### Response eksempel

Dersom vellyket

```
true
```

Ellers

```
false
```

### GET /movielist

### Response eksempel

```json
{
  "labels": [
    {
      "title": "Action",
      "color": "#58E5EB"
    },
    {
      "title": "Eventyr",
      "color": "#314677"
    }
  ],
  "movies": [
    {
      "title": "Dette er en film",
      "description": "Flott film",
      "duration": 122,
      "watched": true,
      "labels": ["Action", "Eventyr"],
      "reviews": [
        {
          "comment": "Kjempegøy",
          "rating": 3,
          "whenWatched": "2021-11-05"
        }
      ]
    },
    {
      "title": "Film2",
      "description": "Kjempespennende",
      "duration": 122,
      "watched": false,
      "labels": ["Action"],
      "reviews": []
    }
  ]
}
```

#### Ugunstig lagring til server

`putMovieList` - metoden lagrer hele `movieList`-objektet mellom hver gang det skjer en endring i objektet. Dette er kostbart, men nødvendig når vi bruker JSON. Dette ville vært unngått om vi hadde benyttet oss av en database, og ikke en JSON-fil.


### Getter som returnerer iterator istedenfor kopi av liste

I release 2 returnerte vi en ny kopi av listene reviews og labels da metodene `getReviews()` og `getLabels` ble kalt på i `Movie`-klassen. Det å lage en ny liste for hver gang man kaller på disse funksjonene er kostbart, og kan være veldig kostbart hvis vi hadde hatt mange `Review`-objekter og `Label`-objekter i disse listene. For å gjøre det mindre kostbart returnerte vi istendenfor en iterator av listene. Å returnere en iterator tar ikke noe mer plass i minner, og gjør det dermed mindre kostbart enn å lage en kopi for hver gang man skal gjøre et kall på funksjonen.

Vi gjorde den samme endringen for funksjonen `getMovies` i `MovieList`-klassen, med samme argumentasjon.

### run-with-server script

Dette scriptet brukes for å starte serveren APIet kjører på og selve appen med samme kommando. Vi har laget dette for å forenkle kjøring av appen med remoteaccess. Ettersom serveren og appen starter samtidig må man koble til ved hjelp av knappen oppe i høyre hjørne for å få tilgang til data fra APIet. Det er viktig å huske på at man at man må stoppe serveren etter at man har brukt den, for kommandoen kan ikke brukes om serveren allerede er på. Dette man kan stoppe serveren ved å utføre kommandoen `CTRL + C` i git bash winduet som kommer opp.


