# Statistic

OpenAPI: (noch nicht verfügbar)

## [/statistic/top](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/statistic/top)

Zeigt die Top-Liste mit verschiedenen Kategorien an.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/statistic/top`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/statistic/{player}](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/statistic/RettichLP)

Zeigt die Statistiken des angegebenen Spielers an.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/statistic/{player}`

| Parameter     | Beschreibung                                    |
|---------------|-------------------------------------------------|
| `tokenString` | Addon-Token                                     |
| `player`      | Minecraft Name oder Minecraft UUID des Spielers |

## [/statistic/{player}/add](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/statistic/RettichLP/add?type=DEATH)

Fügt einen Statistik-Eintrag dem angegebenen Spieler hinzu.

**Einschränkung**: 🔐 angegebener Spieler

**GET** `/unicacityaddon/v1/{tokenString}/statistic/{player}/add?type={type}`

| Parameter     | Beschreibung                                    |
|---------------|-------------------------------------------------|
| `tokenString` | Addon-Token                                     |
| `player`      | Minecraft Name oder Minecraft UUID des Spielers |
| `type`        | Statistik-Typ                                   |

!!! quote

    Hey Rettich, warum machst du das öffentlich? Jetzt kann doch jeder sich seine Statistiken modifizieren...

    Ja das stimmt, aber: Man muss erstmal an sein eigenes Addon-Token rankommen. Das wird in den Client-Logs nicht
    ausgegeben und müsste demnach generiert werden. Sollte das jemand getan haben und sich seine Statistiken
    modifizieren, ist das auffälliger als man vielleicht denkt. Den Spieler erwartet dann entweder einen Reset der
    Statistik oder einen Ausschluss von der API.

## [/statistic/{player}/set](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/statistic/RettichLP/set?type=DEATH&amount=10)

Setzt einen Statistik-Eintrag für den angegebenen Spieler.

**Einschränkung**: 🔒 (Administratoren)

**GET** `/unicacityaddon/v1/{tokenString}/statistic/{player}/set?type={type}&amount={amount}`

| Parameter     | Beschreibung                                    |
|---------------|-------------------------------------------------|
| `tokenString` | Addon-Token                                     |
| `player`      | Minecraft Name oder Minecraft UUID des Spielers |
| `type`        | Statistik-Typ                                   |
| `amount`      | Anzahl                                          |