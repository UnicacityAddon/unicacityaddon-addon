# House Ban

OpenAPI: (noch nicht verfügbar)

## [/houseban](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/houseban?advanced=false)

Zeigt die aktuellen Hausverbote an.

**Einschränkung**: 🔓 / 🔐 (für `advanced = true` Mitglieder des Rettungsdienstes)

**GET** `/unicacityaddon/v1/{tokenString}/houseban?advanced={advanced}`

| Parameter     | Beschreibung                                                            |
|---------------|-------------------------------------------------------------------------|
| `tokenString` | Addon-Token                                                             |
| `advanced`    | `false` default<br/>`true` zeigt an, wer das Hausverbot eingetragen hat |

## [/houseban/add](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/houseban/add?name=RettichLP&reason=Gewaltandrohung)

Fügt dem angegebenen Spieler den angegebenen Hausverbot-Grund hinzu.

**Einschränkung**: 🔐 Mitglieder (Rang 4+) des Rettungsdienstes

**GET** `/unicacityaddon/v1/{tokenString}/houseban/add?name={name}&reason={reason}`

| Parameter     | Beschreibung                |
|---------------|-----------------------------|
| `tokenString` | Addon-Token                 |
| `name`        | Minecraft Name des Spielers |
| `reason`      | Hausverbot-Grund            |

!!! warning

    Der Grund muss mit "-" als Leerzeichen angegeben werden!

## [/houseban/remove](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/houseban/remove?name=RettichLP&reason=Gewaltandrohung)

Entfernt dem angegebenen Spieler den angegebenen Hausverbot-Grund.

**Einschränkung**: 🔐 Mitglieder (Rang 4+) des Rettungsdienstes

**GET** `/unicacityaddon/v1/{tokenString}/houseban/remove?name={name}&reason={reason}`

| Parameter     | Beschreibung                |
|---------------|-----------------------------|
| `tokenString` | Addon-Token                 |
| `name`        | Minecraft Name des Spielers |
| `reason`      | Hausverbot-Grund            |

!!! warning

    Der Grund muss mit "-" als Leerzeichen angegeben werden!
