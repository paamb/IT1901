[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2105/gr2105.git)

# Personal Movie Database (PMDB)

Personal movie database er appen laget av gruppe 5 i faget IT1901. 

## Beskrivelse av appen

PMDB-appen er en app som gir brukeren muligheten til å anmelde og organisere filmer brukeren har sett på eller ønsker å se. For en mer informativ beskrivelse, se [denne README-filen](/pmdb/README.md).

## Kodingsprosjektet

Kodingsprosjektet ligger i undermappen **pmdb** som man kan komme inn i ved å utføre kommandoen `cd pmdb` fra rotnivå. 

Appen er bygd med Maven sitt standard directory layout, og vi benytter oss av Maven kommandoer for kjøring av projektet. Dette står det mer om i [README-en i pmdb](/pmdb/README.md).

Appen består av fire moduler:

Core, Ui, Rest og Integrationtests

- Core er backenden, denne modulen ligger under pmdb/core.
- Ui er brukergrensesnittet, denne modulen ligger under pmdb/ui
- Rest er konfigurering og oppsett av REST-serveren, denne modulen ligger under pmdb/rest
- Integrationtests setter opp webapplikasjonen til REST-serveren, denne modulen ligger i pmdb/integrationtests
