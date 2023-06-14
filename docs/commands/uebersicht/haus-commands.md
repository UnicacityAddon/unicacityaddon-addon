# Haus Commands

## HouseBank

Auf [UnicaCity](https://unicacity.de/) gibt es Häuser, die von Spielern gekauft werden können. Jedes Haus hat eine
Hauskasse. Der Befehl um die Hauskasse zu sehen ist nur in diesem Haus ausführbar.

Dieser Command ermöglicht das Anzeigen von den Hauskassen aller Häuser, die der Spieler besitzt, egal wo sich dieser
befindet.

Wenn der Spieler Geld in die Hauskasse einzahlt oder herausnimmt, wird der neue Stand der Hauskasse berechnet und lokal,
auf dem Rechner des Spielers, gespeichert. Wenn der Spieler diesen Command ausführt, werden die Hauskassen Werte
ausgelesen und dem Spieler gesendet.

!!! bug "Kein Bug!"

    Der Wert der Hauskasse(n), der lokal gespeichert wird, kann vom richtigen Wert abweichen. Das liegt daran, dass
    Mieter ihre Miete zahlen. Es gibt keine Möglichkeit herauszufinden wann Miete gezahlt wird, somit kann es zu
    Abweichungen kommen. Der Command dient dazu einen generellen Richtwert zu liefern.

**Syntax**: `/hauskasseninfo (remove)`

**Aliases**: `/hkasseninfo` `/hkinfo`

| Parameter | Beschreibung                                                                            | Beispiel |
|-----------|-----------------------------------------------------------------------------------------|----------|
| `remove`  | Entfernt eine Hauskasse aus der Übersicht (wenn beispielsweise das Haus verkauft wurde) | -        |

## HouseBankDropGetAll

Aus Hauskassen kann Geld genommen und eingezahlt werden. Man muss erst schauen wie viel Geld sich in der Hauskasse
befindet und kann anschließend den gewünschten Betrag ein- oder auszahlen.

Dieser Command ermöglicht es, beide Befehle automatisch auszuführen. Wenn Geld ausgezahlt werden soll, wird nachgeschaut
wie viel Geld in der Hauskasse ist und dieses anschließend herausgenommen.
Beim Einzahlen wird geschaut wie viel Geld auf der Hand des Spielers und in der Hauskasse ist. Wenn das Geld auf der
Hand noch in die Hauskasse passt, wird dieses eingezahlt. Sollte der Spieler mehr Geld auf der Hand haben, wird alles
eingezahlt bis die hauskasse voll ist.

Die Ausführung des Commands dauert etwa eine Sekunde. Das liegt daran, dass zwischen den beiden Befehlen (schauen wie
viel Geld in der Hauskasse ist und das Geld ein-/auszahlen) eine Sekunde Cooldown (Spam-Schutz) sein muss. Das könnte
umgangen werden, wenn der lokal gespeicherte Wert der Hauskasse genutzt wird. Das wiederrum könnte zu Problemen führen,
da der lokal gespeicherte Wert durch gezahlte Miete vom reellen Wert abweichen könnte. Darum haben wir uns für die etwas
längere Ausführungszeit und dafür exakten Betrag entschieden.

!!! info

    Es wird der Standard [UnicaCity](https://unicacity.de/) Befehl für Hauskassen-Interaktionen überschrieben und erhält somit auch eine
    Tab-Completion.

**Syntax**: `/hauskasse [get|drop|info] [Betrag|all]`

**Aliases**: `/hkasse`

| Parameter                                             | Beschreibung                                                                                                                               | Beispiel             |
|-------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|----------------------|
| `get` `drop` `info` <span style="color:red;">*</span> | Hauskassen-Interaktions-Typ<br/>`get` Geld aus der hauskasse nehmen<br/>`drop` Geld in die Hauskasse legen<br/>`info` Betrag der Hauskasse | -                    |
| `Betrag` `all` <span style="color:red;">*</span>      | Geld<br/>`Betrag` Anzahl des Geldes<br/>`all` Gesamtes Geld in der Hauskasse oder auf der Hand des Spielers                                | <br/>`250`<br/>`all` |

## HouseStorage

Auf [UnicaCity](https://unicacity.de/) gibt es Häuser, die von Spielern gekauft werden können. Jedes Haus hat ein Lager.
Der Befehl um das Lager zu sehen ist nur in diesem Haus ausführbar.

Dieser Command ermöglicht das Anzeigen von den Lagern aller Häuser, die der Spieler besitzt, egal wo sich dieser
befindet.

Wenn der Spieler Gegenstände in das Lager legt oder herausnimmt, wird der neue Lagerbestand lokal, auf dem Rechner des
Spielers, gespeichert. Wenn der Spieler diesen Command ausführt, werden die Lagerbestände ausgelesen und dem Spieler
gesendet.

**Syntax**: `/drogenlagerinfo (delete)`

**Aliases**: `/dlagerinfo` `/drugstorage`

| Parameter | Beschreibung                                                                       | Beispiel |
|-----------|------------------------------------------------------------------------------------|----------|
| `delete`  | Entfernt ein Lager aus der Übersicht (wenn beispielsweise das Haus verkauft wurde) | -        |
