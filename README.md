# COVISTAT - Kurzuebersicht wichtiger Corona Zahlen

Diese Webanwendung zeigt fuer ausgewaehlte Landkreise die 7 Tage Inzidenz sowie den Status der Oeffnungen.

## Datenquelle
Die Zahlen werden dem [COVID-19](https://npgeo-corona-npgeo-de.hub.arcgis.com/datasets/917fc37a709542548cc3be077a786c17_0/geoservice?geometry=-16.655%2C46.211%2C38.673%2C55.839) Datenhub des Bundesamtes fuer Kartographie und Geodaesi entnommen.

```
Quellenvermerk: Robert Koch-Institut (RKI), dl-de/by-2-0  Haftungsausschluss: „Die Inhalte, die über die Internetseiten des Robert Koch-Instituts zur Verfügung gestellt werden, dienen ausschließlich der allgemeinen Information der Öffentlichkeit, vorrangig der Fachöffentlichkeit“
```

## Technologie

- Jakarta EE 8 
- Jakarta MVC / Eclipse Krazo
- Eclipse Krazo Mustache Extension
- WildFly 22 
- MariaDB

## Konfiguration Wildfly DataSource
Fuer die Nutzung der Datenbank muss die Wildfly DataSource konfiguriert werden. Bei einem frisch heruntergeladenen Server kann dies mit folgenden JBOSS-CLI Befehlen durchgefuehrt werden. Es wird angenommen, dass der Datenbanktreiber lokal vorliegt.

```
# Snapshot der Konfig erstellen
[standalone@localhost:9990] :take-snapshot

# ExampleDS entfernen
[standalone@localhost:9990] data-source --name=ExampleDS disable
[standalone@localhost:9990] data-source --name=ExampleDS remove
[standalone@localhost:9990] /subsystem=ee/service=default-bindings:undefine-attribute(name=datasource)

# Datenbanktreiber installieren
[standalone@localhost:9990] deploy <path-to-driver>

# DataSource einrichten (Username / Passwort aus docker-compose.yml)
[standalone@localhost:9990] data-source add \
--name=covistat-ds \
--jndi-name=java:/jdbc/covistat \
--driver-name=mariadb-java-client-2.7.2.jar \
--connection-url=jdbc:mariadb://localhost:3306/covistat \
--user-name=covistat \
--password=covistatPw \
--check-valid-connection-sql="SELECT 1" \
--background-validation=true \
--background-validation-millis=60000 \
--flush-strategy=IdleConnections \
--min-pool-size=2 \
--max-pool-size=20 \
--pool-prefill=false

# Konfiguration neu laden
 [standalone@localhost:9990] reload
```

## Abfrage-URI / Beispiel AGS

URI: https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/RKI_Landkreisdaten/FeatureServer/0/query?where=1%3D1&outFields=BL,BL_ID,county,last_update,cases7_per_100k,AGS&returnGeometry=false&outSR=4326&f=json

AGS: 09185 (ND), 09186 (PAF)
