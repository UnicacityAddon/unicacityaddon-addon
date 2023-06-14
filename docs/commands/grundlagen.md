# Grundlagen

Commands unterscheiden sich hauptsächlich darin, wo sie ausgeführt werden könne. Es gibt Globale- und
Unicacity-Commands. Beide Arten werden gleich implementiert, aber unterscheiden sich in der
[Command-Annotation](implementation/command-annotation.md#onlyonunicacity).

Globale-Commands können überall ausgeführt werden. Sowohl auf [UnicaCity](https://unicacity.de/), anderen Servern oder
im Singleplayer.

Unicacity-Commands funktionieren jedoch ausschließlich auf dem Minecraft Server [UnicaCity](https://unicacity.de/)
(`*.unicacity.de`). Werden diese Commands auf anderen Servern oder im Singleplayer ausgeführt, werden die Commands
direkt an den Server oder Minecraft weitergeleitet und nicht erst durch das Addon bearbeitet.

!!! note

    Für manche Commands setzt [UnicaCity](https://unicacity.de/) ein bestimmtes Permission Level oder eine Fraktion
    vorraus. Das Addon kontrolliert vor der Ausführung eines Commands jedoch nicht ob der Spieler serverseitige
    Bedingungen erfüllt. Wenn ein Command korrekt eingegeben wurde, wird dieser ausgeführt.
