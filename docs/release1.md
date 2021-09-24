# Release-1

I release-1 har vi laget MVP for appen. Vi har valgt å fokusere på kodestruktur, lesing/lagring til JSON-fil og å lage en prototype.
I core har vi opprettet grensesnittene

```
IMovie.java
IReview.java
```

med de tilhørende klassene

```
Movie.java
Review.java
```

i tillegg til

```
Label.java
WatchList.java
Storage.java
```

Foreløpig er det bare logikken i `Movie.java`, `WatchList.java` som blir benyttet.
Vi har benyttet oss av java-biblioteket _jackson_ for lagring og skriving til JSON-fil.
Dette er implementert i klassen `Storage.java`.

For brukergrensesnittet (ui) har vi en hovedklasse `App.java`. Vi har også to FXML-filer: `App.fxml` og `EditMovie.fxml` med tilhørende kontroller-klasser: `AppController.java` og `EditMovieController.java`.

`App.fxml` definerer hvordan appen ser ut ved oppstart. `EditMove.fxml` definerer vinduet for å opprette eller endre på en eksisterende film (`Movie.java`). Når et Movie-objekt er opprettet så blir det lagt til i `WatchList.java` . Deretter lages en JSON-fil ved hjelp av `Storage.java` som tar inn en `Collection<Movie>` fra `WatchList.java`. `Storage.java` henter også filmer fra denne filen ved oppstart, slik at en liste med de lagrede filmene kan vises i hovedvinduet.
