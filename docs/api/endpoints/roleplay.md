# Roleplay

OpenAPI: (noch nicht verfügbar)

## [/roleplay](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/roleplay)

Zeigt alle UUID mit ihrem zugehörigen Roleplay Namen an.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/roleplay`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/roleplay/update](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/roleplay/update)

Aktualisiert Roleplay Daten. Es werden die Daten aktualisiert, die zum angegeben API Token gehören. Somit kann nur jeder
seine eigenen Daten ändern.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/roleplay/update?name={name}`

| Parameter     | Beschreibung                                    |
|---------------|-------------------------------------------------|
| `tokenString` | Addon-Token                                     |
| `name`        | Namen der als Roleplay Name gesetzt werden soll |
