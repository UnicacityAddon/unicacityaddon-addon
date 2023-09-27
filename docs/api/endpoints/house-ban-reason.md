# House Ban Reason

OpenAPI: (noch nicht verfügbar)

## [/housebanreason](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/housebanreason)

Zeigt die Hausverbot-Gründe an.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/housebanreason`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/housebanreason/add](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/housebanreason/add?reason=Test-Grund&days=5)

Fügt einen neuen Hausverbot-Grund hinzu.

**Einschränkung**: 🔐 Mitglieder (Rang 4+) des Rettungsdienstes

**GET** `/unicacityaddon/v1/{tokenString}/housebanreason/add?reason={reason}&days={days}`

| Parameter     | Beschreibung     |
|---------------|------------------|
| `tokenString` | Addon-Token      |
| `reason`      | Hausverbot-Grund |
| `days`        | Anzahl der Tage  |

!!! warning

    Der Grund muss mit "-" als Leerzeichen angegeben werden!

## [/housebanreason/remove](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/housebanreason/remove?reason=Test-Grund)

Entfernt einen Hausverbot-Grund.

**Einschränkung**: 🔐 Mitglieder (Rang 4+) des Rettungsdienstes

**GET** `/unicacityaddon/v1/{tokenString}/housebanreason/remove?reason={reason}`

| Parameter     | Beschreibung     |
|---------------|------------------|
| `tokenString` | Addon-Token      |
| `reason`      | Hausverbot-Grund |

!!! warning

    Der Grund muss mit "-" als Leerzeichen angegeben werden!
