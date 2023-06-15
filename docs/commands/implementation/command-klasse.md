# Command Klasse

Commands erben durch die `UnicacityCommand` Klasse. Diese wiederrum erbt durch die Command Klasse von LabyMod.
Commands rufen in ihrem Konstruktor die `UnicacityCommand` Klasse auf und übergeben die Addon Instance und die
[Command Annotation](command-annotation.md).

Beispiel Command:

```java linenums="1"
@UCCommand(prefix = "beispiel", aliases = {"bspiel", "bs"})
public class BeispielCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public BeispielCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        System.out.println("Hallo Welt!");
        return true;
    }
}
```