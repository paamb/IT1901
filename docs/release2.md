# Release 2

## Endring i klassestrukturen

### Strukturen i release 1
I release 1 var tanken at vi skulle ha både ha en klasse for *liste med anmeldelser* (`ReviewList.java`) og en for *liste med filmer* (`MovieList.java`). Hver *film* skulle bare ha én *anmeldelse*. 

Det var et *én-til-én* forhold mellom `Movie` og `Review`. `ReviewList` hadde *én-til-mange* forhold med `Review`, det samme hadde `MovieList` med `Movie`. Se uml-diagram under. Vi benyttet oss av denne strukturen, fordi vi tenkte at det ville gjøre lagringen lettere. Tanken var at vi skulle ha to filer, en for lagring av `ReviewList` og en for lagring av `MovieList`. Dette matchet også hvordan ui-et ville være da vi skulle ha én fane for å vise *listen med anmeldelser* (`ReviewList`) og én for *listen med filmer* (`MovieList`).

---

### Strukturen i release 2
Vi fant ut at denne klassestrukturen var ugunstig, fordi vi så at det var overflødig å både ha `ReviewList` og `MovieList`. Vi tenkte også at det skulle være mulig for en bruker å lage flere *anmeldelser* til én *film*. Derfor endret vi klasseforholdet til at `Movie` hadde *en-til-mange* forhold med `Review`. Dermed kunne vi droppe `ReviewList`, og istedenfor la `MovieList` inneholde mange `Movies` som igjen inneholder mange `Reviews`. Da kunne vi også lagre alt i en fil. Hvis vi vil nå ha tak i en `Review`, trenger vi bare å finne den tilhørende filmen.
Struktur release 1         |  Struktur release 2
:-------------------------:|:-------------------------:
![Bildet ble ikke vist](../pmdb/images/classDiagramRelease1.png) | ![Bildet ble ikke vist](../pmdb/images/classDiagramRelease2.png)


---

## Endringer i JSON-lagringen
I release 1 hadde vi bare en klasse for lagring `Storage.java`, som benyttet `jackson` sine metoder for lagring. Vi sendte inn hele `movielist`-objektet vi ville lagre, og lot `jackson` lagre dette automatisk. Vi fant ut at dette var en ugunstig, da vi ikke hadde noe kontroll over hvordan objektet ble lagret. Vi visste ikke hvordan JSON-filen ville se ut, som skapte trøbbel da vi skulle hente hente ut objektet. Dette gjorde det også vanskelig å teste lagring, fordi vi egentlig ikke visste hvordan objektet ville se ut. Vi byttet dermed til en mer manuell lagring der vi selv bestemte hva som skulle lagres, og hvilke JSON-objekter det vil lagres som. Vi fikk da større kontroll over hvordan JSON-filen ville se ut, og testingen ble mer valid.
Persistense release 1 |  Persistense release 2
:-------------------------:|:-------------------------:
![Bildet ble ikke vist](../pmdb/images/classDiagramPersistanceRelease1.png) | ![Bildet ble ikke vist](../pmdb/images/classDiagramPersistanceRelease2.png)

---

## Dokumentmetafor
Vi har valgt å bruke dokumentmetafor når vi lagrer. Når brukeren skal opprette et *film-objekt*, må han trykke på lagre-knappen for at det skal vises på skjermen og lagret til fil. Det samme gjelder for når brukeren oppretter en *anmeldelse*. Brukeren har mulighet for å både endre på en eksisterende *film* eller *anmeldelse*. Endringene i objektene vil først bli utrettet når man har trykket på lagre-knappen. Som gjør at det er brukt dokumentmetafor. Vi benyttet oss av dokumentmetafor, for å gi brukeren innsikt i hva som blir lagret, og større kontroll. For brukeren er det lett å forstå at *film-objektet* blir lagret når personen trykker på lagre-knappen og pop-up-en lukkes. Brukeren kan også se at *filmen* dukker opp på skjermen etterpå. Når man lukker programmet har appen allerede lagret alle *filmene* og *anmeldelsene* til fil, ettersom at brukeren ha opprettet de underveis. Så brukeren trenger ikke å trykke på en lagre knapp før personene kan lukke appen for at ting skal bli lagret. Dette bryter litt med dokumentmetafor, men vi tenkte at denne lagringen var naturlig å skje automatisk.

## Arkitektur

Vi har benyttet oss en flermodulær arkitektur. Vi har modulene `core` og `ui`. Modulene er skiller backend fra frontend. I `core` ligger alt som har med backend å gjøre. Der ligger logikken i en undermappe `core`, og lagringen i en undermappe `json`. I `ui` modulen ligger _kontrollerklassene_ og _fxml-filene_. Vi valgte å skille `core` fra `ui`, fordi de utfører ulike oppgaver i appen. Hver modul har en `module-info` klasse som definerer hvilke moduler som denne modulen krever, og hvilke pakker inni modulen som er synlig for andre moduler. Dette skaper en god innkapsling. `ui` krever også en del moduler som ikke er nødvendig i `core`, noe som gjør det unødvendig for `core` å importere. Modulariseringen danner også et tydeligere _model-view-controller_ skille, som er ønskelig.

Under ligger pakkestrukturen som også ligger i README-filen i pmdb:

![Bildet ble ikke vist](../pmdb/images/packageStructure.png)

---

## Arbeidsvaner

Vi har satt oss flere nyttige og smarte arbeidsvaner som alle i gruppen fulgte, slik at arbeidet vårt blir mer produktiv, og ikke minst slik at alle forstår av alle jobber med.

### Gitlab

Det er mange nyttige funksjoner som man kan bruke ved hjelp av git og gitlab som vi tok i bruk, deriblant milestones og issues.

#### Milestones, issues og merge requests

Vi har satt inn milepæler med deadlines på gitlab for hver innlevering slik at vi har noe å jobbe oss mot. Dette har i tillegg hjulpet teamet med å holde seg på sporet og jobbe mot et felles mål.

##### Issues:

Temaet satt opp issues/problemer for hver oppgave som skulle gjøres. Slike issues har vi sørget for at skal være så konkrete som mulig. Er de ikke konkretet, lager vi f.eks checkboxes inne i issuet for å konkretisere gjøremålene. Når du har gjort deg ferdig med issuet, kan du sette opp en merge request på det. Se under for mer detaljert fremgangsmåte.

Altså en måte man kunne gå fram for å løse en spesifikk oppgave på er som følger:

- Lage en issue (nummer 4) på problemet med f.eks navnet: Skrive test til Movie
- Lage en lokal branch på som heter f.eks: 4-skrive-MovieTest
- Skrive det som trengs av kode, og commite så ofte og så godt du kan
- Når du mener at koden er ferdig og du har f.eks kjørt mvn clean install og ingen problemer oppstår, kan du pushe koden din
- Etter at du har pushet, setter du opp en merge request på gitlab som også skal close/lukker issue nummer 4. Der kan du gjerne utdype litt mer hva du har gjort og hvordan.
- Du som har laget merge requesten kan ikke merge den uten videre, den skal først:
  1. Bli approved av noen andre som skal se gjennom koden
  2. Så kan merge reqesten bli merget inn i main, og endringene er dermed oppdatert

Ved denne fremgangsmåten unngår vi å gjøre endringer som de andre i gruppen ikke har godkjent, noe som gir en mer korrekt kode siden flere i teamet har sett igjennom den.

---

### Scrum og møter

Vi benyttet oss av scrum rammeverk for å effektivisere arbeidet vårt. I sammenheng med bruk av scrum, hadde vi sprint review der vi gikk gjennom sprinten (i vårt tilfelle en milestone/innlevering) og etter det hadde vi en sprint retrospective. Etter disse to hadde vi en sprint planning der vi planla neste sprint og satt opp noen issues for å sette i gang sprinten.

I tillegg hadde vi minst to ukentlig møter, der vi diskuterer og programmerer sammen (parprogrammering).

---

### Parprogrammering

Vi har i tillegg benyttet oss av parprogrammering i noen tilfeller. For eksempel jobbet vi på lagring av data i par. Å jobbe i par der gjorde det mye enklere å debugge koden når en programmerer og den andre ser over, og vi opplevde at man tenker bedre når man snakker høyt.

Fordelene vi generelt opplevde med parprogrammeringen er blant annet:

- Det er alltid bedre å ha to personer løse et problem enn en. Vi kom oss til en kortere og enklere løsning når.
- Vi opplevde også at det førte til færre feil i koden. Dette er nok fordi mens en programmerer, ser den andre over den. F.eks kunne den som programmerer å legge til en "private" på en deklarasjon, men feilen ble oppdaget av den som ser på.
- Det førte til at vi også skjønte mer av helheten av appen/programmet fordi du man får innblikk i alle sidene ved koden. Man lærer også en god del av hverandre, det kan f.eks være ting du ikke kan som partneren din kan lære deg.
