# Yasin

OpenAPI: (noch nicht verfügbar)

## [/yasin](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/yasin)

Zeigt die Yasin-Liste an.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/yasin`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/yasin/add](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/yasin/add?name=RettichLP)

Fügt einen neuen Spieler zur Yasin-Liste hinzu.

**Einschränkung**: 🔐 Yasin

**GET** `/unicacityaddon/v1/{tokenString}/yasin/add?name={name}`

| Parameter     | Beschreibung                |
|---------------|-----------------------------|
| `tokenString` | Addon-Token                 |
| `name`        | Minecraft Name des Spielers |

## [/yasin/done](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/yasin/done?name=RettichLP)

Markiert einen Spieler auf der Yasin-Liste als erledigt.

**Einschränkung**: 🔐 Yasin

**GET** `/unicacityaddon/v1/{tokenString}/yasin/done?name={name}`

| Parameter     | Beschreibung                |
|---------------|-----------------------------|
| `tokenString` | Addon-Token                 |
| `name`        | Minecraft Name des Spielers |

## [/yasin/remove](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/yasin/remove?name=RettichLP)

Entfernt einen Spieler von der Yasin-Liste.

**Einschränkung**: 🔐 Yasin

**GET** `/unicacityaddon/v1/{tokenString}/yasin/remove?name={name}`

| Parameter     | Beschreibung                |
|---------------|-----------------------------|
| `tokenString` | Addon-Token                 |
| `name`        | Minecraft Name des Spielers |
