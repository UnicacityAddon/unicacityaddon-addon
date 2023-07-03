# Management

OpenAPI: (noch nicht verfügbar)

## [/mgmt](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/mgmt)

Zeigt Daten wie Client Anzahl, Versionsverteilung oder aktuelle Version an.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/mgmt`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/mgmt/users](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/mgmt/users)

Zeigt alle registrierten Clients mit der Aktivität, Version, UUID und Versionsaktualität an.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/mgmt/users`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/mgmt/version](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/mgmt/version?type=add&version=1.0.0)

Markiert eine Version als aktuell oder veraltet.

**Einschränkung**: 🔒 (Administratoren)

**GET** `/unicacityaddon/v1/{tokenString}/mgmt/version?type={type}&version={version}`

| Parameter     | Beschreibung                                                     |
|---------------|------------------------------------------------------------------|
| `tokenString` | Addon-Token                                                      |
| `type`        | Version als aktuell (`add`) oder veraltet (`remove`) markieren   |
| `version`     | Version, die markiert werden soll (z. B.: `1.0.0` oder `1.45.2`) |
