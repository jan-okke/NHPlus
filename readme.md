# NHPlus

## Informationen zur Lernsituation
Du bist Mitarbeiter der HiTec GmbH, die seit über 15 Jahren IT-Dienstleister und seit einigen Jahren ISO/IEC 27001 zertifiziert ist. Die HiTec GmbH ist ein mittelgroßes IT-Systemhaus und ist auf dem IT-Markt mit folgenden Dienstleistungen und Produkten vetreten: 

Entwicklung: Erstellung eigener Softwareprodukte

Consulting: Anwenderberatung und Schulungen zu neuen IT- und Kommunikationstechnologien , Applikationen und IT-Sicherheit

IT-Systembereich: Lieferung und Verkauf einzelner IT-Komponenten bis zur Planung und Installation komplexer Netzwerke und Dienste

Support und Wartung: Betreuung von einfachen und vernetzten IT-Systemen (Hard- und Software)

Für jede Dienstleistung gibt es Abteilungen mit spezialisierten Mitarbeitern. Jede Abteilung hat einen Abteilungs- bzw. Projektleiter, der wiederum eng mit den anderen Abteilungsleitern zusammenarbeitet.

 

## Projektumfeld und Projektdefinition

Du arbeitest als Softwareentwickler in der Entwicklungsabteilung. Aktuell bist du dem Team zugeordnet, das das Projekt "NHPlus" betreut. Dessen Auftraggeber - das Betreuungs- und Pflegeheim "Curanum Schwachhausen" - ist ein Pflegeheim im Bremer Stadteil Schwachhausen - bietet seinen in eigenen Zimmern untergebrachten Bewohnern umfangreiche Therapie- und Serviceleistungen an, damit diese so lange wie möglich selbstbestimmt und unabhängig im Pflegeheim wohnen können. Curanum Schwachhausen hat bei der HiTec GmbH eine Individualsoftware zur Verwaltung der Patienten und den an ihnen durchgeführten Behandlungen in Auftrag gegeben. Aktuell werden die Behandlungen direkt nach ihrer Durchführung durch die entsprechende Pflegekraft handschriftlich auf einem Vordruck erfasst und in einem Monatsordner abgelegt. Diese Vorgehensweise führt dazu, dass Auswertungen wie z.B. welche Behandlungen ein Patient erhalten oder welche Pflegkraft eine bestimmte Behandlung durchgeführt hat, einen hohen Arbeitsaufwand nach sich ziehen. Durch NHPlus soll die Verwaltung der Patienten und ihrer Behandlungen elektronisch abgebildet und auf diese Weise vereinfacht werden.

Bei den bisher stattgefundenen Meetings mit dem Kunden konnten folgende Anforderungen an NHPlus identifiziert werden:

- alle Patienten sollen mit ihrem vollen Namen, Geburtstag, Pflegegrad, dem Raum, in dem sie im Heim untergebracht sind, sowie ihrem Vermögensstand erfasst werden.

- Die Pflegekräfte werden mit ihrem vollen Namen und ihrer Telefonnumer erfasst, um sie auf Station schnell erreichen zu können.

- jede Pflegekraft erfasst eine Behandlung elektronisch, indem sie den Patienten, das Datum, den Beginn, das Ende, die Behandlungsart sowie einen längeren Text zur Behandlung erfasst.

- Die Software muss den Anforderungen des Datenschutzes entsprechen. 

- Die Software ist zunächst als Desktopanwendung zu entwickeln, da die Pflegekräfte ihre Behandlungen an einem stationären Rechner in ihrem Aufenthaltsraum erfassen sollen.

 

Da in der Entwicklungsabteilung der HiTech GmbH agile Vorgehensweisen vorgeschrieben sind, wurde für NHPlus Scum als Vorgehensweise gewählt.

 

## Stand des Projektes

In den bisherigen Sprints wurden die Module zur Erfassung der Patienten- und Behandlungsdaten fertiggestellt. Es fehlt das Modul zur Erfassung der Pflegekräfte. Deswegen kann bisher ebenfalls nicht erfasst werden, welche Pflegekraft eine bestimmte Behandlung durchgeführt hat. In der letzten Sprint Review sind von der Curanum Schwachhausen Zweifel angebracht worden, dass die bisher entwickelte Software den Anforderungen des Datenschutzes genügt.

## Technische Hinweise

Wird das Open JDK verwendet, werden JavaFX-Abhängigkeiten nicht importiert. Die Lösung besteht in der Installation der neuesten JDK-Version der Firma Oracle.

## Technische Hinweise zur Datenbank

- Benutzername: SA
- Passwort: SA
- Bitte nicht in die Datenbank schauen, während die Applikation läuft. Das sorgt leider für einen Lock, der erst wieder verschwindet, wenn IntelliJ neugestartet wird!

## Testfälle
### Userstory 1 - Assets löschen

- View geöffnet, Vermögensstand wurde nicht mehr angezeigt
- Nutzer angelegt, bearbeitet und gelöscht, funktionierte weiterhin.

### Userstory 2 - Archivierung von Behandlungsdaten

- Auf Löschen geklickt, Daten waren nicht mehr in der Tabelle
- Daten waren verschlüsselt in der Archivdatenbank

### Userstory 3 - Datenlöschung nach 10 Jahren

- Einen von mehreren Datensätzen in der Datenbank angepasst, sodass das Archivierungsdatum älter als 10 Jahre ist
- Programm gestartet
- Der Datensatz wurde erfolgreich gelöscht, aber nur der > 10 Jahre alte.

### Userstory 4 - Login beim Programmstart

- Beim Starten der Anwendung kommt ein Loginfenster hoch
- Bei falschem Login passiert nichts
- Bei korrektem Login schließt sich das Loginfenster und das Hauptfenster erscheint

### Userstory 5 - Anlegen von Pflegern

- Es gibt eine Ansicht zum Anlegen von Pflegern
- Der Benutzer kann die Daten angeben und auf Hinzufügen klicken
- Die Daten sind in der Datenbank und Tabelle vorhanden

### Userstory 6 - Löschung von Pflegern

- Es gibt die Möglichkeit, Pfleger zu archivieren
- Diese sind dann in der Datenbank nicht mehr vorhanden (nur noch in der Archivtabelle)
- In der Tabelle werden die archivierten Pfleger auch nicht mehr angezeigt

### Userstory 7 - SQL Injections verhindern

- Wenn der Nutzer eines der in Validation.invalidSigns im Eingabefeld eingibt, wird die Aktion nicht durchgeführt

## Benutzername und Passwort für das Login

- Benutzername: 123
- Passwort: 123

## Nicht geschaffte Features

- Beim Ändern einer Behandlung wird nur der Name des Caregivers angezeigt statt der ComboBox, dieser lässt sich auch nicht ändern.
- SQL Injections sind nicht komplett verhindert, Eingaben wie 1 == 1 sind immer noch per Edgecases möglich