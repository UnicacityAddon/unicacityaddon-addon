# Autorisierung

Die Autorisierung ist ein wichtiger Bestandteil, da nicht jeder Spieler alle Daten sehen darf. Zum Beispiel darf ein
Mitglied des Rettungsdienstes lediglich seine eigene Anzahl an Wiederbelebungen sehen. Ab einem bestimmten Rang muss
das Rettungsdienst-Mitglied allerdings alle Wiederbelebungen, also auch die anderer Spieler, sehen könne.

Um eine Autorisierung zu gewährleisten, besitzt jeder Spieler ein eindeutiges Addon-Token. Bei jedem Spielstart wird
dieses Addon-Token neu generiert und bietet somit eine höhere Sicherheit, da es sich somit fast täglich ändert. Das
Addon-Token besteht aus einem Hash des [Mojang Session Token](https://wiki.vg/Authentication) und der UUID des Nutzers.
Bei der Erstellung des neuen Addon-Tokens wird dieses mit dem [Mojang Session Token](https://wiki.vg/Authentication) an
die API gesendet.

Die API extrahiert aus dem [Mojang Session Token](https://wiki.vg/Authentication) die UUID des Spielers und vergleicht
diese mit der UUID im generiertem Addon-Token. Wenn beide UUIDs überein stimmen, wird das generierte Addon-Token mit der
UUID gespeichert. Das [Mojang Session Token](https://wiki.vg/Authentication) wird weder gespeichert noch geloggt.

![](https://i.imgur.com/JYFYCfi.jpg)