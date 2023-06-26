# Money Commands

## ATMFill

Bankautomaten auf [UnicaCity](https://unicacity.de/) werden zu jedem Serverneustart auf das maximale Füllvermögen von
100.000 $ gesetzt. Wenn Geld abgehoben wird, sinkt das Füllvermögen und wenn Geld eingezahlt wird, steigt diese, aber
nie über 100.000 $. Es kann jedoch weiterhin Geld eingezahlt werden, aber das Füllvermögen erhöht sich nicht.

Dieser Command ermöglicht die Einzahlung von Geld in einen Bankautomaten, jedoch nur bis der Automat voll ist. Dadurch
wird gewährleistet, dass nicht zu viel Geld in einen ATM eingezahlt wird und es somit zu einer "Geldentwertung" kommt.

Wenn der Spieler den Command ausführt, wird als erstes abgefragt wie viel Geld in dem aktuellen Automaten ist.
Anschließend wird maximal so viel Geld eingezahlt wie der Spieler auf der Hand hat oder wie viel Platz sich noch im
Automaten befindet.

**Syntax**: `/atmfill`

## Einzahlen

Wenn man Geld auf der Hand hat und dieses in einen Automaten einzahlen möchte, muss man mit einem Command schauen wie
viel Geld sich auf der Hand befindet. Erst mit einem zusätzlichen Command, bei dem man den Betrag angeben muss, kann man
dieses Geld einzahlen.

Mit diesem Command wird dieser Vorgang in einem Command zusammengefasst und das nervige Eingeben des genauen Betrags
fällt weg.

Jegliche Veränderungen des Geldes auf der Hand oder auf der Bank werden vom Addon getrackt. Somit kann direkt das
gesamte Geld auf der Hand eingezahlt werden.

**Syntax**: `/einzahlen`

## Reichensteuer

Auf [UnicaCity](https://unicacity.de/) zahlt man, ab einem Vermögen von 100.000 $ auf der Bank, einen höheren
Steuersatz. Dieser wird Reichensteuern genannt. Befinden sich zum PayDay weniger oder genau 100.000 $ auf dem Konto,
werden keine Reichensteuern fällig. Ähnlich wie beim [Einzahlen](#einzahlen) Command muss erst geschaut werden, wie viel
Geld der Spieler auf dem Konto hat und anschließend der Differenz-Betrag bis 100.000 $ erneut eingegeben werden.

Mit diesem Command wird dieser Vorgang in einem Command zusammengefasst und das nervige Eingeben des genauen Betrags
fällt weg.

Jegliche Veränderungen des Geldes auf der Hand oder auf der Bank werden vom Addon getrackt. Somit kann direkt das
gesamte Geld bis auf 100.000 $ vom Konto abgebucht werden.

**Syntax**: `/reichensteuer`
