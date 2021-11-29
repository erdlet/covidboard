# COVIDBOARD - 7-Tages-Inzidenzen schnell im Blick

Mit [CovidBoard](https://www.covidboard.de) lassen sich die 7-Tages-Inzidenzen für Deutschland laden und auf einer personalisierbaren Startseite anzeigen.

## Technologien

- Jakarta EE 8
- Jakarta MVC / Eclipse Krazo
- Eclipse Krazo Pebble Extension
- WildFly 23
- Flyway
- MariaDB

## Installation

Die folgenden Punkte beschreiben die Vorkehrungen zum Betrieb von CovidBoard.

### Grundlegendes zum Application Server
Für die Ausführung von CovidBoard wird ein Application Server mit RESTEasy als JAX-RS Implementierung benötigt. Sollte ein
auf Jersey basierender Server verwendet werden, so muss die Maven Dependency `org.eclipse.krazo:krazo-resteasy:...` gegen `org.eclipse.krazo:krazo-jersey:...` ausgetauscht
werden.

Default: 

```xml
<dependency>
  <groupId>org.eclipse.krazo</groupId>
  <artifactId>krazo-resteasy</artifactId>
  <version>${krazo.version}</version>
</dependency>
```

### Datasource java:/jdbc/covidboard

Für den Betrieb der Anwendung muss eine DataSource im Application Server definiert werden. Diese muss den JNDI-Namen `java:/jdbc/covidboard` tragen, damit die Anwendung darauf
zugreifen kann. Generell sollte es egal sein, welche Datenbank verwendet wird. Nutzt man eine andere als MariaDB, so muss in der `persistence.xml` der Hibernate Dialect entsprechend angepasst werden.

Default:

```xml
<properties>
  <property name="hibernate.dialect" value="org.hibernate.dialect.MariaDB103Dialect"/>
</properties>
```

### Cookie-Konfiguration

CovidBoard nutzt technisch notwendige Cookies für gewisse Features. Diese können / müssen je nach Umgebung in der `microprofile-config.properties` konfiguriert werden.

Default:

```
cookies.secure=${COOKIES_SECURE:false}
cookies.cb_favorites.path=${COOKIE_CB_FAVORITES_PATH:/covidboard/mvc}
cookies.cb_favorites.domain=${COOKIE_CB_FAVORITES_DOMAIN:localhost}
```

### Deployment

CovidBoard wird als `war` File gebaut. Dieses kann nach erfolgter Konfiguration des Servers mit Hilfe von `mvn clean package` gebaut und deployt werden. Je nach Pfad auf dem
Server kann eine Anpassung der Cookie-Konfiguration notwendig sein (siehe oben).

Die Datenbank wird durch _Flyway_ beim Start der Anwendung initialisiert / migriert. Dazu ist es notwendig, dass die DataSource korrekt konfiguriert wurde.

## Lizenz
MIT (see [LICENSE](./LICENSE))
