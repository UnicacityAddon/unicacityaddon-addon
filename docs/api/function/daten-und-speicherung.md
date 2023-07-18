# Daten und Speicherung

Es werden für die Einträge in die Datenbank JPA-Entities verwendet. Diese Entities verwenden jeweils folgende Daten:

## AddonGroup

Speichert Spieler und (sofern zugeordnet) deren Gruppen.

- `minecraftUuid` Minecraft UUID des Spielers
- `groupList` Liste aus Gruppen, zu denen der Spieler gehört

## AutoNC

Speichert Schlüsselwörter oder Symbole und eine Antwort.

- `words` Liste aus Wörtern oder Symbolen
- `answer` Antwort Text

## Banner

Speichert den Banner-Status.

- `x` X Koordinate des Banners
- `y` Y Koordinate des Banners
- `z` Z Koordinate des Banners
- `naviPoint` Navipunkt der in der Nähe liegt
- `time` Zeitpunkt der letzten Übersprühung des Banners

## BlacklistReason

Speichert die Blacklist-Gründe.

- `reason` Grund
- `kills` Anzahl der Kills
- `price` Geldbetrag
- `issuerMinecraftUuid` Minecraft UUID des Spielers, der den Blacklist-Grund hinzugefügt hat
- `faction` Fraktion, der der Blacklist-Grund gehört

## Broadcast

Speichert Broadcasts bis die `sendTime` vorbei ist.

- `senderMinecraftUuid` Minecraft UUID des Spielers, der den Broadcast gesendet hat
- `senderMinecraftName` Minecraft Name des Spielers, der den Broadcast gesendet hat
- `createTime` Zeitpunkt der Erstellung
- `sendTime` Zeitpunkt an dem der Broadcast gesendet werden soll

!!! note

    Der Name wird gespeichert, da die eigentliche Broadcast Nachricht den Namen des Senders enthält. Es soll jedoch
    nicht jeder Client die Mojang API aufrufen, um den Namen anhand der Minecraft UUID abzurufen. Darum wird der Name
    gespeichert und über den API Endpunkt, zum abrufen von aktiven Broadcasts, mitgesendet. Der Name wird serverseitig
    gecached, da er noch für andere Funktionen benötigt wird.

## HouseBan

Speichert Spieler mit einem Hausverbot im Krankenhaus/in der Feuerwache.

- `minecraftUuid` Minecraft UUID des Spielers
- `startTime` Zeitpunkt der Hausverbot-Vergabe
- `houseBanReasonList` Liste von [Hausverbot-Gründen](#housebanreason)

## HouseBanReason

Speichert Hausverbot-Gründe.

- `reason` Hausverbot-Grund
- `days` Hausverbot-Länge in Tagen
- `issuerMinecraftUuid` Minecraft UUID des Spielers, der den Hausverbot-Grund hinzugefügt bzw. vergeben hat

## NaviPoint

Speichert Navipunkte.

- `name` Name
- `x` X Koordinate des Navi-Punktes
- `y` Y Koordinate des Navi-Punktes
- `z` Z Koordinate des Navi-Punktes
- `article` Artikel (der/die/das)

## Revive

Speichert die Anzahl der Revives von Mitgliedern des Rettungsdienstes. Da die Auswertung der Revive Anzahl wöchentlich
passiert, werden Revives maximal 2 Wochen gespeichert. Anschließend werden diese automatisch gelöscht.

- `user` [User](#user)
- `time` Zeitpunkt des Revives

!!! note

    Hier wird statt der Minecraft UUID der User genommen. Das liegt daran, dass dieses Datenbank Entity neu ist und die
    älteren noch nicht von Minecraft UUID auf User geändert wurden.

## Roleplay

Speichert die Roleplay Daten der [User](#user).

- `name` Roleplay Name des Users

## Statistic

Speichert die Statistik der [User](#user).

- `deaths` Tode
- `kd` KD
- `kills` Kills
- `playTime` Spielzeit
- `revives` Anzahl aller Revives
- `services` Anzahl aller Services

!!! note

    Die Anzahl aller Revives wird nicht gelöscht. Im Gegensatz zu [Revive](#revive) bleibt diese Zahl erhalten.

## Token

Speichert das Token eines [Users](#user).

- `user` [User](#user)
- `token` Token
- `creationTime` Zeitpunkt der Erstellung
- `version` Version mit der das Token erstellt wurde (Addon-Version des Spielers)

## User

Speichert den User.

- `minecraftUuid` Minecraft UUID des Spielers
- `password` Passwort (aktuell nicht in Verwendung - Platzhalter für ein zukünftiges Web-Interface für Statistiken)
- `faction` Fraktion
- `rankInFaction` Rang in der Fraktion

## WantedReason

Speichert die Wanted-Gründe.

- `reason` Wanted-Grund
- `wantedPoints` Anzahl Wanted-Punkte

## Yasin

Speichert die Yasin-Liste.

- `minecraftUuid` Minecraft UUID des Spielers
- `done` Erledigt/noch offen

!!! warning

    Hier muss `minecraftUuid` erhalten bleiben und kann nicht durch [User](#user) ersetzt werden. Auf diese Liste können
    Spieler gesetzt werden, die nicht als [User](#user) registriert sind.