# Banner

OpenAPI: (noch nicht verfügbar)

## [/banner](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/banner)

Zeigt alle registrierten Banner an und eine Übersicht über die Banner-Aufteilung auf Fraktionen für die letzten 24
Stunden.

!!! tip

    Durch diesen Enpunkt wird die Übersicht der Banner und deren Verlauf generiert. Siehe:
    [Banner](https://unicacityaddon.rettichlp.de/banner)

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/banner`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/banner/add](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/banner/add?faction=FRAKTION&x=0&y=0&z=0&navipoint=Krankenhaus)

Fügt ein neues Banner hinzu.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/banner/add?faction={faction}&x={x}&y={y}&z={z}&navipoint={navipoint}`

| Parameter     | Beschreibung                           |
|---------------|----------------------------------------|
| `tokenString` | Addon-Token                            |
| `faction`     | Fraktion die das Banner übersprüht hat |
| `x`           | X Koordinate des Banners               |
| `y`           | Y Koordinate des Banners               |
| `z`           | Z Koordinate des Banners               |
| `navipoint`   | Navipunkt in der Nähe des Banners      |
