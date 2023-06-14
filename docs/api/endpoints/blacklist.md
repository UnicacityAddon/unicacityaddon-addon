# Blacklist

OpenAPI: (noch nicht verfügbar)

## [/blacklistreason/{factionString}](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blacklistreason/WESTSIDEBALLAS)

Zeigt die Blacklist-Gründe für die angegebene Fraktion an.

**Einschränkung**: 🔐 Mitglieder der angegebenen Fraktion

**GET** `/unicacityaddon/v1/{tokenString}/blacklistreason/{factionString}`

| Parameter       | Beschreibung |
|-----------------|--------------|
| `tokenString`   | Addon-Token  |
| `factionString` | Fraktion     |

## [/blacklistreason/{factionString}/add](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blacklistreason/WESTSIDEBALLAS/add?reason=Test&price=1000&kills=50)

Fügt einen neuen Blacklist-Grund zur angegebenen Fraktion hinzu.

**Einschränkung**: 🔐 Mitglieder (Rang 4+) der angegebenen Fraktion

**GET** `/unicacityaddon/v1/{tokenString}/blacklistreason/{factionString}/add?reason={reason}&price={price}&kills={kills}`

| Parameter       | Beschreibung                         |
|-----------------|--------------------------------------|
| `tokenString`   | Addon-Token                          |
| `factionString` | Fraktion des neuen Blacklist-Grundes |
| `reason`        | Grund                                |
| `price`         | Preis                                |
| `kills`         | Kills                                |

!!! warning

    Der Grund muss mit "-" als Leerzeichen angegeben werden!

## [/blacklistreason/{factionString}/remove](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blacklistreason/WESTSIDEBALLAS/remove?reason=Test)

Entfernt einen Blacklist-Grund von der angegebenen Fraktion.

**Einschränkung**: 🔐 Mitglieder (Rang 4+) der angegebenen Fraktion

**GET** `/unicacityaddon/v1/{tokenString}/blacklistreason/{factionString}/remove?reason={reason}`

| Parameter       | Beschreibung                         |
|-----------------|--------------------------------------|
| `tokenString`   | Addon-Token                          |
| `factionString` | Fraktion des neuen Blacklist-Grundes |
| `reason`        | Grund                                |

!!! warning

    Der Grund muss mit "-" als Leerzeichen angegeben werden!
