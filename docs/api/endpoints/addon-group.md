# Addon Group

OpenAPI: (noch nicht verfügbar)

## [/player](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/player)

Gibt eine Übersicht aller Gruppen und deren Mitglieder zurück.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/player`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/player/groups](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/player/groups)

Gibt eine Übersicht aller Gruppen zurück.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/player/groups`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/player/add](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/player/add?name=RettichLP&group=CEO)

Fügt den angegebenen Spieler der angegebenen Gruppe hinzu.

**Einschränkung**: 🔒 (Administratoren)

**GET** `/unicacityaddon/v1/{tokenString}/player/add?name={name}&group={group}`

| Parameter     | Beschreibung                |
|---------------|-----------------------------|
| `tokenString` | Addon-Token                 |
| `name`        | Minecraft Name des Spielers |
| `group`       | Name der Gruppe             |

## [/player/remove](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/player/remove?name=RettichLP&group=CEO)

Entfernt den angegebenen Spieler aus der angegebenen Gruppe.

**Einschränkung**: 🔒 (Administratoren)

**GET** `/unicacityaddon/v1/{tokenString}/player/remove?name={name}&group={group}`

| Parameter     | Beschreibung                |
|---------------|-----------------------------|
| `tokenString` | Addon-Token                 |
| `name`        | Minecraft Name des Spielers |
| `group`       | Name der Gruppe             |
