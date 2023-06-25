# Broadcast

OpenAPI: (noch nicht verfügbar)

## [/broadcast/queue](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/broadcast/queue)

Zeigt alle Broadcasts in der Warteschlange.

**Einschränkung**: 🔓

**GET** `/unicacityaddon/v1/{tokenString}/broadcast/queue`

| Parameter     | Beschreibung |
|---------------|--------------|
| `tokenString` | Addon-Token  |

## [/broadcast/send](http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/broadcast/send?message=Test%20Nachricht&sendTime=1684868332000)

Fügt einen neuen Broadcast zur Warteschlange hinzu.

**Einschränkung**: 🔒 (Administratoren)

**GET** `/unicacityaddon/v1/{tokenString}/broadcast/send?message={message}&sendTime={sendTime}`

| Parameter     | Beschreibung                                      |
|---------------|---------------------------------------------------|
| `tokenString` | Addon-Token                                       |
| `message`     | Nachricht                                         |
| `sendTime`    | Zeitpunkt wann der Broadcast gesendet werden soll |
