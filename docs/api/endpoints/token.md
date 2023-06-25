# Token

OpenAPI: (noch nicht verfügbar)

## [/create](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/create?authToken=Mojang-Session-Token&version=1.0.0)

Überprüft die angegebenen Werte (siehe [Autorisierung](../function/autorisierung.md)) und registriert nach
erfolgreicher Überprüfung das Token, durch das auf nicht öffentliche Endpunkte zugegriffen werden kann.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/create?authToken={authToken}&version={version}`

| Parameter     | Beschreibung                        |
|---------------|-------------------------------------|
| `tokenString` | Addon-Token                         |
| `authToken`   | Mojang Session Token                |
| `version`     | Version des Clients (Addon-Version) |
