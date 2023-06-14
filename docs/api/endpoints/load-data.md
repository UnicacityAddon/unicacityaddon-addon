# Load Data

!!! danger "Deprecated"

    Dieser Endpunkt ist veraltet und wird in einer zukünftigen Version entfernt.

## [/load](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/load)

Dieser Endpunkt wurde nur für die Migration von JSON-Dateien zur Datenbank genutzt. Es mussten die Inhalte der Dateien
in die Datenbank eingetragen werden. Um dies durchzuführen wurde dieser Endpunkt erstellt. Aktuell wird er nicht
genutzt.

**Einschränkung**: 🔒 (Administratoren)

**GET** `/unicacityaddon/v1/{tokenString}/load`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |
