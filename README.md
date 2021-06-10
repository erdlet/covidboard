# COVIDBOARD - Kurzuebersicht wichtiger Corona Zahlen

Diese Webanwendung zeigt fuer ausgewaehlte Landkreise die 7 Tage Inzidenz sowie den Status der Oeffnungen.

## Technologie

- Jakarta EE 8 
- Jakarta MVC / Eclipse Krazo
- Eclipse Krazo Pebble Extension
- WildFly 22 
- MariaDB

## Abfrage-URI / Beispiel AGS

URI: https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/RKI_Landkreisdaten/FeatureServer/0/query?where=1%3D1&outFields=BL,BL_ID,county,last_update,cases7_per_100k,AGS&returnGeometry=false&outSR=4326&f=json

AGS: 09185 (ND), 09186 (PAF)

URI für alles: https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/RKI_Landkreisdaten/FeatureServer/0/query?where=1%3D1&outFields=AGS,BL,cases7_per_100k,county,cases,deaths&returnGeometry=false&outSR=4326&f=json

## Datenquelle
Die Zahlen werden dem [COVID-19](https://npgeo-corona-npgeo-de.hub.arcgis.com/datasets/917fc37a709542548cc3be077a786c17_0/geoservice?geometry=-16.655%2C46.211%2C38.673%2C55.839) Datenhub des Bundesamtes fuer Kartographie und Geodaesi entnommen.

```
Quellenvermerk: Robert Koch-Institut (RKI), dl-de/by-2-0  Haftungsausschluss: „Die Inhalte, die über die Internetseiten des Robert Koch-Instituts zur Verfügung gestellt werden, dienen ausschließlich der allgemeinen Information der Öffentlichkeit, vorrangig der Fachöffentlichkeit“
```
