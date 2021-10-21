# Release 2
## Endring i klassestrukturen

### Strukturen i release 1
I release 1 var tanken at vi skulle ha både ha en klasse for *liste med anmeldelser* (`ReviewList.java`) og en for *liste med filmer* (`MovieList.java`). Hver *film* skulle bare ha én *anmeldelse*. 

Det var et *én-til-én* forhold mellom `Movie` og `Review`. `ReviewList` hadde *én-til-mange* forhold med `Review`, det samme hadde `MovieList` med `Movie`. Se uml-diagram under. Vi benyttet oss av denne strukturen, fordi vi tenkte at det ville gjøre lagringen lettere. Tanken var at vi skulle ha to filer, en for lagring av `ReviewList` og en for lagring av `MovieList`. Dette matchet også hvordan ui-et ville være da vi skulle ha én fane for å vise *listen med anmeldelser* (`ReviewList`) og én for *listen med filmer* (`MovieList`).


### Strukturen i release 2
Vi fant ut at denne klassestrukturen var ugunstig, fordi vi så at det var overflødig å både ha `ReviewList` og `MovieList`. Vi tenkte også at det skulle være mulig for en bruker å lage flere *anmeldelser* til én *film*. Derfor endret vi klasseforholdet til at `Movie` hadde *en-til-mange* forhold med `Review`. Dermed kunne vi droppe `ReviewList`, og istedenfor la `MovieList` inneholde mange `Movies` som igjen inneholder mange `Reviews`. Da kunne vi også lagre alt i en fil. Hvis vi vil nå ha tak i en `Review`, trenger vi bare å finne den tilhørende filmen.
Struktur release 1         |  Struktur release 2
:-------------------------:|:-------------------------:
![Bildet ble ikke vist](../pmdb/images/classDiagramRelease1.png) | ![Bildet ble ikke vist](../pmdb/images/classDiagramRelease2.png)


## Endringer i JSON-lagringen
I release 1 hadde vi bare en klasse for lagring `Storage.java`, som benyttet `jackson` sine metoder for lagring. Vi sendte inn hele `movielist`-objektet vi ville lagre, og lot `jackson` lagre dette automatisk. Vi fant ut at dette var en ugunstig, da vi ikke hadde noe kontroll over hvordan objektet ble lagret. Vi visste ikke hvordan JSON-filen ville se ut, som skapte trøbbel da vi skulle hente hente ut objektet. Dette gjorde det også vanskelig å teste lagring, fordi vi egentlig ikke visste hvordan objektet ville se ut. Vi byttet dermed til en mer manuell lagring der vi selv bestemte hva som skulle lagres, og hvilke JSON-objekter det vil lagres som. Vi fikk da større kontroll over hvordan JSON-filen ville se ut, og testingen ble mer valid.
Persistense release 1 |  Persistense release 2
:-------------------------:|:-------------------------:
![Bildet ble ikke vist](../pmdb/images/classDiagramPersistanceRelease1.png) | ![Bildet ble ikke vist](../pmdb/images/classDiagramPersistanceRelease2.png)


## Dokumentmetafor
Vi har valgt å bruke dokumentmetafor når vi lagrer. Når brukeren skal opprette et *film-objekt*, må han trykke på lagre-knappen for at det skal vises på skjermen og lagret til fil. Det samme gjelder for når brukeren oppretter en *anmeldelse*. Brukeren har mulighet for å både endre på en eksisterende *film* eller *anmeldelse*. Endringene i objektene vil først bli utrettet når man har trykket på lagre-knappen. Som gjør at det er brukt dokumentmetafor. Vi benyttet oss av dokumentmetafor, for å gi brukeren innsikt i hva som blir lagret, og større kontroll. For brukeren er det lett å forstå at *film-objektet* blir lagret når personen trykker på lagre-knappen og pop-up-en lukkes. Brukeren kan også se at *filmen* dukker opp på skjermen etterpå. Når man lukker programmet har appen allerede lagret alle *filmene* og *anmeldelsene* til fil, ettersom at brukeren ha opprettet de underveis. Så brukeren trenger ikke å trykke på en lagre knapp før personene kan lukke appen for at ting skal bli lagret. Dette bryter litt med dokumentmetafor, men vi tenkte at denne lagringen var naturlig å skje automatisk.

## Arkitektur
Vi har benyttet oss en flermodulær arkitektur. Vi har modulene `core` og `ui`. Modulene er skiller backend fra frontend. I `core` ligger alt som har med backend å gjøre. Der ligger logikken i en undermappe `core`, og lagringen i en undermappe `json`. I `ui` modulen ligger *kontrollerklassene* og *fxml-filene*. Vi valgte å skille `core` fra `ui`, fordi det er stor forskjell i arbeidsoppgavene til de to modulene. Hver modul har en `module-info` klasse som definerer hvilke moduler som denne modulen krever, og hvilke pakker inni modulen som er synlig for andre moduler. Dette skaper en god innkapsling, da bare de filene som er nødvendige for andre moduler *eksporteres*. `ui` krever også en del moduler som ikke er nødvendig i `core`, noe som gjør det unødvendig for `core` å importere. Modulariseringen danner også et tydeligere *model-view-controller* skille, som er ønskelig.

Under ligger pakkestrukturen som også ligger i README-filen i pmdb:

![Bildet ble ikke vist](../pmdb/images/packageStructure.png)


### Testing av core/src/json
