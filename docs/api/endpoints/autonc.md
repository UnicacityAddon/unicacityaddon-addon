# Addon Group

!!! danger "Deactivated"

    Dieser Endpunkt ist zur Zeit nicht in Verwendung. Durch eine Unicacity Richtlinie ist die Verwendung dieses Features
    aktuell deaktiviert.

OpenAPI: (noch nicht verfügbar)

## [/autonc](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/autonc)

Gibt eine Übersicht aller Auto-NC Schlüsselwörter und Antworten.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/autonc`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/autonc/add](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/autonc/add?words=Wort1,Wort2,Wort3&answer=Antwort)

Gibt eine Übersicht aller Gruppen zurück.

**Einschränkung**: 🔐

**GET** `/unicacityaddon/v1/{tokenString}/autonc/add?words={words}&answer={answer}`

| Parameter     | Beschreibung                   |
|---------------|--------------------------------|
| `tokenString` | Addon-Token                    |
| `words`       | Wörter (Wort1,Wort2,Wort3,...) |
| `answer`      | Antwort                        |

## [/autonc/remove](https://rettichlp.de:8443/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/autonc/remove?id=1)

Fügt den angegebenen Spieler der angegebenen Gruppe hinzu.

**Einschränkung**: 🔒 (Administratoren)

**GET** `/unicacityaddon/v1/{tokenString}/autonc/remove?id={id}`

| Parameter     | Beschreibung                  |
|---------------|-------------------------------|
| `tokenString` | Addon-Token                   |
| `id<br/>`     | ID des zu löschenden Eintrags |
