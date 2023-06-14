# Command Annotation

Um die Zuordnung von Command Präfix, Aliases und weiterer Command abhängiger Daten zu vereinfachen gibt es eine
Annotation. Mit dieser können `prefix`, `aliases`, `onlyOnUnicacity` und `usage` zugeordnet werden.

## Prefix
Gibt das Präfix des Commands an. Ein Command Präfix ist der Text, der in Kombination mit einem Schrägstrich (`/`)
die Command Ausführung einleitet. Mit diesem Präfix wird auch der Hilfetext generiert.

Type: `String`

!!! warning

    `prefix` ist verpflichtend. Die restlichen Einstellungen sind optional.

## Aliases
Gibt die Aliases des Commands an. Command Aliase sind alternative Präfixe des Commands. Der Wert ist standardmäßig
ein leeres Array.

Type: `String[]`

## OnlyOnUnicacity
Legt fest ob der Command nur auf [UnicaCity](https://unicacity.de/) funktionieren soll. Der Wert ist standardmäßig auf `true`. Wenn der
Command auch auf anderen Servern oder im Singleplayer funktionieren soll, muss dieser auf `false` gesetzt werden.

Type: `Boolean (true)`

## Usage
Zeigt die Command Parameter als Text an. Der Hilfetext, der durch die `usage` angezeigt wird, besteht nur aus den
Command Parametern. Das Präfix wird weggelassen. Der Wert ist standardmäßig ein leerer `String`.

Beispiel für den Teleport Command (`/tp RettichLP 1 2 3`):
```java
@UCCommand(prefix = "tp", usage = "[Spieler] [x] [y] [z]")
```

Wenn der Hilfe Text über die `sendUsage()` Methode der `UnicacityCommand` Klasse gesendet wird, wird dieser mit dem
Präfix zusammengesetzt und direkt an den Spieler gesendet. Im oberen Beispiel wäre der Hilfetext, der an den Spieler
gesendet wird: `/tp [Spieler] [x] [y] [z]`.

Type: `String`