# Wanted Reason

OpenAPI: (noch nicht verfügbar)

## [/wantedreason](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/wantedreason)

Zeigt die Wanted-Liste an.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/wantedreason`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/wantedreason/add](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/wantedreason/add?reason=Test-Grund&points=20)

Fügt einen neuen Wanted-Grund zur Wanted-Liste hinzu.

**Einschränkung**: 🔐 Mitglieder (Rang 4+) der Polizei

**GET** `/unicacityaddon/v1/{tokenString}/wantedreason/add?reason={reason}&points={points}`

| Parameter     | Beschreibung             |
|---------------|--------------------------|
| `tokenString` | Addon-Token              |
| `reason`      | Wanted-Grund             |
| `points`      | Anzahl an Wanted-Punkten |

## [/wantedreason/remove](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/wantedreason/remove?reason=Test-Grund)

Entfernt einen Wanted-Grund von der Wanted-Liste.

**Einschränkung**: 🔐 Mitglieder (Rang 4+) der Polizei

**GET** `/unicacityaddon/v1/{tokenString}/wantedreason/remove?reason={reason}`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |
| `reason`      | Wanted-Grund |
