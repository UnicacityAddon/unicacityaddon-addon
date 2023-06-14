# Tab-Completion Builder
Um eine Tab-Completion zur Verfügung zu stellen, kann das weiterhin manuell getan werden oder mit einem
`TabCompletionBuilder`.

Dieser kann in der `UnicacityCommand#copmlete(String[])` Methode als Rückgabewert eingefügt werden:
```java
@Override
public List<String> complete(String[] arguments) {
    return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
            .addAtIndex(1, myList1)
            .addAtIndex(2, myList2)
            .addToAllFromIndex(2, myList3)
            .build();
}
```

Als Parameter für den Builder müssen die Addon Instance und der `arguments` Parameter übergeben werden.
Für den `TabCompletionBuilder` gibt es zwei Haupt-Methoden:

* `addAtIndex` - Fügt für einen bestimmten Index mögliche Tab Completions hinzu.
* `addToAllFromIndex` - Fügt ab einem bestimmten Index mögliche Tab Completions hinzu.

Im oberen Code-Ausschnitt wird nach der Eingabe des Commands (z. B. /tp) für den ersten Parameter alle Werte aus
`myList1` als Vervollständigung vorgeschlagen. Wenn dieser Parameter gesetzt wurde, werden anschließend für den zweiten
Parameter alle Werte aus `myList2` und `myList3` vorgeschlagen. Als dritten Parameter werden alle Werte aus `myList3`
vorgeschlagen, da der Inhalt dieser Liste ab einschließlich Index 2 an alle Parameter gegeben wird.