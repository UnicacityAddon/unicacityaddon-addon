# Handy Commands

## ACall

Wenn man auf [UnicaCity](https://unicacity.de/) einen Spieler anrufen möchte, benötigt man dessen Nummer. Die Nummer
kann man über einen Command herausfinden. Anschließend muss man den Anruf Command ausführen, um den Spieler anzurufen.

Dieser Command ermöglicht das Anrufen eines Spielers mit nur einem Command.

Bei der Ausführung des Commands wird zunächst der Nummer Command ausgeführt, um die Nummer des Spielers zu erhalten.
Danach wird der Spieler mit der Nummer angerufen.

**Syntax**: `/acall [Spieler]`

| Parameter                                   | Beschreibung                       | Beispiel    |
|---------------------------------------------|------------------------------------|-------------|
| `Spieler` <span style="color:red;">*</span> | Spieler, der angerufen werden soll | `RettichLP` |

## ASMS

Wenn man auf [UnicaCity](https://unicacity.de/) einem Spieler eine SMS schreiben möchte, benötigt man dessen Nummer. Die
Nummer kann man über einen Command herausfinden. Anschließend muss man den SMS Command ausführen, um dem Spieler eine
Nachricht zu senden.

Dieser Command ermöglicht das Senden einer SMS mit nur einem Command.

Bei der Ausführung des Commands wird zunächst der Nummer Command ausgeführt, um die Nummer des Spielers zu erhalten.
Danach wird die SMS an die Nummer gesendet mit der angegebenen Nachricht.

**Syntax**: `/asms [Spieler] [Nachricht]`

| Parameter                                     | Beschreibung                                       | Beispiel      |
|-----------------------------------------------|----------------------------------------------------|---------------|
| `Spieler` <span style="color:red;">*</span>   | Spieler, der angerufen werden soll                 | `RettichLP`   |
| `Nachricht` <span style="color:red;">*</span> | Nachricht, die an den Spieler gesendet werden soll | `Hallo Welt!` |

## Blockieren

Wenn ein Spieler störende Nachrichten per SMS sendet, kann dieser nicht blockiert werden.

Dieser Command ermöglicht, dass diese Nachricht nicht im Chat gezeigt wird und auch der Nachrichten-Ton nicht abgespielt
wird. Der Spieler bleibt bis zum Spielneustart oder bis zur erneuten Ausführung des Commands blockiert.

Wenn ein Spieler blockiert wurde, wird bei eingehenden Chat-Nachrichten geschaut, ob es sich um eine SMS des Spielers
handelt. Wenn dies der Fall ist, wird diese Nachricht nicht im Chat gezeigt.

!!! example "Experimentelles Feature"

    Diese Funktion ist testweise implementiert und kann noch Bugs aufweisen.

**Syntax**: `/blockieren [Spieler]`

**Aliases**: `/block` `/blocknumber`

| Parameter                                   | Beschreibung                       | Beispiel    |
|---------------------------------------------|------------------------------------|-------------|
| `Spieler` <span style="color:red;">*</span> | Spieler, der blockiert werden soll | `RettichLP` |

## Reply

Um einem Spieler auf eine SMS zu antworten, müsste eine neue SMS geschrieben werden. Entweder über den
[UnicaCity](https://unicacity.de/) Command `/sms [Nummer] [Nachricht]` oder den Addon Command
`/asms [Spieler] [Nachricht]`.

Dieser Command ermöglicht das direkte antworten auf eine SMS ohne die Angabe eines Spielers.

Wennm eine SMS ankommt, steht die Nummer des Senders in der Nachricht. Diese wird extrahiert und gespeichert. Bei der
Ausführung des Commands wird geschaut, ob eine Nummer hinterlegt wurde und wenn dies der Fall ist, wird die Nachricht an
diese Nummer gesendet.

**Syntax**: `/r [Nachricht]`

| Parameter                                     | Beschreibung                        | Beispiel      |
|-----------------------------------------------|-------------------------------------|---------------|
| `Nachricht` <span style="color:red;">*</span> | Nachricht, die gesendet werden soll | `Hallo Welt!` |

## Stumm

Wenn man SMS Nachrichten sehen möchte, aber nicht durch den Nachrichten-Ton gestört werden möchte, kann man diesen nicht
deaktivieren.

Dieser Command ermöglicht, dass die SMS im Chat gezeigt wird, aber der Nachrichten-Ton nicht abgespielt wird. Diese
Einstellung bleibt bis zum Spielneustart oder bis zur erneuten Ausführung des Commands.

Wenn eine SMS ankommt und der Nachrichten-Ton abgespielt wird, cancelt das Addon das Sound-Event, sodass kein
Nachrichten-Ton gespielt wird.

!!! example "Experimentelles Feature"

    Diese Funktion ist testweise implementiert und kann noch Bugs aufweisen.

**Syntax**: `/stumm`

**Aliases**: `/nichtstören` `/donotdisturb`
