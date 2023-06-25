# Revive

OpenAPI: (noch nicht verfügbar)

## [/revive](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/revive)

Fügt einen neuen Revive hinzu.

**Einschränkung**: 🔐 Mitglieder des Rettungsdienstes

**GET** `/unicacityaddon/v1/{tokenString}/revive`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/revive/{playerOrRank}](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/revive/RettichLP)

Zeigt die Revive-Anzahl aller Spieler des angegebenen Rangs oder die Revive-Anzahl des angegebenen Spielers an.

**Einschränkung**: 🔐 angegebener Spieler oder Mitglieder (Rang 4+) des Rettungsdienstes

**GET** `/unicacityaddon/v1/{tokenString}/revive/{playerOrRank}`

| Parameter      | Beschreibung                          |
|----------------|---------------------------------------|
| `tokenString`  | Addon-Token                           |
| `playerOrRank` | Minecraft Name des Spielers oder Rang |
