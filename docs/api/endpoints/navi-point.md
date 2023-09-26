# Navi Point

OpenAPI: (noch nicht verfügbar)

## [/navipoint](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/navipoint)

Zeigt die Navi-Punkte an.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/navipoint`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/navipoint/add](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/navipoint/add?name=Krankenhaus&x=1&y=2&z=3&article=das)

Fügt einen neuen Navi-Punkt hinzu.

**Einschränkung**: 🔒 (Administratoren)

**GET** `/unicacityaddon/v1/{tokenString}/navipoint/add?name={name}&x={x}&y={y}&z={z}&article={article}`

| Parameter     | Beschreibung                           |
|---------------|----------------------------------------|
| `tokenString` | Addon-Token                            |
| `name`        | Name des Navi-Punktes                  |
| `x`           | X Koordinate des Navi-Punktes          |
| `y`           | Y Koordinate des Navi-Punktes          |
| `z`           | Z Koordinate des Navi-Punktes          |
| `article`     | Artikel des Navi-Punktes (der/die/das) |

!!! warning

    Der Name muss mit "-" als Leerzeichen angegeben werden!

!!! note

    Der Artikel wird benötigt, da es einen Anwendungsfall gibt, für den der Navi-Punkt mit dem Artikel angezeigt werden
    muss. Für die Sperrgebiet-Nachricht muss der Artikel automatisiert zur Verfügung gestellt werden.

## [/navipoint/remove](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/navipoint/remove?name=Krankenhaus)

Entfernt einen Navi-Punkt.

**Einschränkung**: 🔒 (Administratoren)

**GET** `/unicacityaddon/v1/{tokenString}/navipoint/remove?name={name}`

| Parameter     | Beschreibung          |
|---------------|-----------------------|
| `tokenString` | Addon-Token           |
| `name`        | Name des Navi-Punktes |

!!! warning

    Der Name muss mit "-" als Leerzeichen angegeben werden!
