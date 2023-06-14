# Broadcast

OpenAPI: (noch nicht verfügbar)

## [/event](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/event)

Zeigt die Daten für aktuelle Events. 

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/event`

| Parameter       | Beschreibung |
|-----------------|--------------|
| `tokenString`   | Addon-Token  |

## [/event/bomb](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/event/bomb?startTime=1684868332000)

Fügt ein Bomben Event hinzu.

!!! info

    Das Bomben Event ist nicht im Addon implementiert. Der Endpunkt existiert bereits falls die Logik auf der
    Client-Seite implementiert wird.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/event/bomb?startTime={startTime}`

| Parameter     | Beschreibung               |
|---------------|----------------------------|
| `tokenString` | Addon-Token                |
| `startTime`   | Zeitpunkt des Event-Starts |

## [/event/gangwar](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/event/gangwar?attacker=5&defender=10)

Setzt die aktuelle Punktzahl für Angreifer und Verteidiger während eines Gangwars.

!!! tip

    Diese Funktion wird vom [UnicaCity](https://unicacity.de/) Event Team genutzt um während eines Twitch-Streams den aktuellen Punktestand
    anzuzeigen. Siehe: [Gangwar Übersicht](https://unicacityaddon.rettichlp.de/gangwar)

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/event/gangwar?attacker={attacker}&defender={defender}`

| Parameter     | Beschreibung                |
|---------------|-----------------------------|
| `tokenString` | Addon-Token                 |
| `attacker`    | Punktestand der Angreifer   |
| `defender`    | Punktestand der Verteidiger |
