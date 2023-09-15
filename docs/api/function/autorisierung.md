# Autorisierung

Die Autorisierung ist ein wichtiger Bestandteil, da nicht jeder Spieler alle Daten sehen darf. Zum Beispiel darf ein
Mitglied des Rettungsdienstes lediglich seine eigene Anzahl an Wiederbelebungen sehen. Ab einem bestimmten Rang muss
das Rettungsdienst-Mitglied allerdings alle Wiederbelebungen, also auch die anderer Spieler, sehen könne.

Um eine Autorisierung zu gewährleisten, besitzt jeder Spieler ein eindeutiges Addon-Token. Bei jedem Spielstart wird
dieses Addon-Token neu generiert und bietet somit eine höhere Sicherheit, da es sich somit fast täglich ändert. Das
Addon-Token besteht aus einem Hash des LabyConnect JWT Tokens und der Minecraft UUID des Nutzers. Bei der Erstellung des
neuen Addon-Tokens wird dieses mit dem LabyConnect JWT Token an die API gesendet.

Die API verifiziert das JWT Token mit dem öffentlichen LabyConnect Schlüssel. Wenn das JWT Token gültig ist, werden die
Inhalte des Tokens encoded (Beispiel):

```json
{
  "user_name": "RettichLP",
  "roles": [
    "ROLE_DEVELOPER",
    "ROLE_PLUS"
  ],
  "iss": "LabyConnect",
  "exp": 1689588000,
  "uuid": "25855f4d-3874-4a7f-a6ad-e9e4f3042e19",
  "iat": 1689584400
}
```

Die Minecraft UUID wird anschließend mit der gehashten Minecraft UUID im generiertem Addon-Token verglichen. Stimmen
beide überein, ist das Addon-Token valide und gehört zum mitgesendetem LabyConnect JWT Token. Anhand der Minecraft UUID
können die Berechtigungen des Spielers (z. B. von der [UnicaCity Webseite](https://unicacity.de/)) ausgelesen werden.

Das Addon-Token wird mit der Minecraft UUID und dem Minecraft Namen gespeichert. Somit ist es nicht notwendig die Mojang
API aufzurufen, wenn man eine Minecraft UUID einem Minecraft Namen zuordnen möchte. Das LabyConnect JWT Token wird nicht
gespeichert und nur geloggt, wenn es nicht gültig ist um eine Fehleranalyse zu ermöglichen.

![](https://i.imgur.com/d0kXGI7.jpg)