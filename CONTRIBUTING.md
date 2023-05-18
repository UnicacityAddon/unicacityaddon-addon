# Contributing to UnicacityAddon

:+1::tada: Zunächst einmal vielen Dank, dass Du Dir die Zeit genommen hast, uns zu unterstützen! :tada::+1:

Im Folgenden findest Du eine Reihe von Richtlinien für Beiträge zu UnicacityAddon. Dies sind meist Richtlinien, keine Regeln. Verwende Dein bestes Urteilsvermögen und zöger nicht, Änderungen an diesem Dokument in einem Pull-Request vorzuschlagen.

[Code of Conduct](#code-of-conduct)

[Ich will nicht alles lesen, ich habe nur eine Frage!!!](#ich-will-nicht-alles-lesen-ich-habe-nur-eine-frage)

[Wie kann ich beitragen?](#wie-kann-ich-beitragen)
- [Bug und Feature](#bug-und-feature)
   - [Was macht ein gutes Bug-Issue aus](#was-macht-ein-gutes-bug-issue-aus)
   - [Was macht ein gutes Feature-Issue aus](#was-macht-ein-gutes-feature-issue-aus)
- [Erster Code Beitrag](#erster-code-beitrag)

[Code](#code)
- [Branch](#branch)
- [Pull Request](#pull-requests)
- [Commit](#commit)

## Code of Conduct

Dieses Projekt und alle daran Beteiligten unterliegen dem [Verhaltenskodex](CODE_OF_CONDUCT.md). Durch die Teilnahme wird von Ihnen erwartet, dass Sie diesen Kodex einhalten. Bitte melden Sie inakzeptables Verhalten an [service@rettichlp.de](mailto:service@rettichlp.de).

## Ich will nicht alles lesen, ich habe nur eine Frage

Bitte erstellen Sie kein Issue für eine Frage. Wenn Sie eine Frage haben, ist es meistens schneller uns direkt zu kontaktieren über [Teamspeak](ts3server://unicacity.de?port=9987).
Des Weiteren haben wir eine Webseite mit FAQ und ein Wiki.

## Wie kann ich beitragen?

### Bug und Feature
Bitte verwende beim Melden von Bugs oder Features das bereitgestellte Issue-Template.
- [Bug oder Fehler](https://github.com/rettichlp/UnicacityAddon-1.12.2/blob/main/.github/ISSUE_TEMPLATE/bug-oder-fehler.md)
- [Feature](https://github.com/rettichlp/UnicacityAddon-1.12.2/blob/main/.github/ISSUE_TEMPLATE/feature.md)

Bevor Du ein Bug meldest, schau bitte, ob dieser nicht bereits gemeldet und eventuell sogar schon bearbeitet wurde.
Falls Du einen Bug meldest, sammel bitte so viel Informationen wie möglich und hänge diese an das Bug-Issue an.
Wenn Du ein geschlossenes Issue findest, von dem Du denkst, dass es sich um das gleiche Problem handelt, erstelle bitte ein neues Issue und verlinke das geschlossene.

#### Was macht ein gutes Bug-Issue aus
- **Verwende einen klaren und aussagekräftigen Titel** für das Problem, um das Problem zu identifizieren.
- **Beschreibe die genauen Schritte, die das Problem reproduzieren**, so detailliert wie möglich. Beginne zum Beispiel damit, ob Minecraft länger geladen hat als sonst, welchen Befehl Du verwendet hast. Wenn Du die Schritte auflistest, **sag nicht nur, was Du getan hast, sondern erklär auch, wie Du es getan hast** (Hotkey, manuelle Eingabe, ...).
- **Erkläre, welches Verhalten Du stattdessen erwartet hast und warum.**
- **Füge Screenshots und animierte GIFs bei**, die zeigen, wie der Fehler entstanden ist und diesen deutlich veranschaulichen. Du kannst [dieses Tool](https://www.cockos.com/licecap/) verwenden, um GIFs unter macOS und Windows aufzunehmen und [dieses Tool](https://github.com/colinkeenan/silentcast) oder [dieses Tool](https://github.com/GNOME/byzanz) unter Linux.
- **Wenn das Spiel abstürzt**, füge den Absturzbericht bei. Dieser wird automatisch von Minecraft geöffnet. Füge den Absturzbericht in einem [Markdown-Codeblock](https://help.github.com/articles/markdown-basics/#multiple-lines), einem [Dateianhang](https://help.github.com/articles/file-attachments-on-issues-and-pull-requests/) oder in einem verlinkten [Gist](https://gist.github.com/) hinzu.
- **Wenn das Problem nicht durch eine bestimmte Aktion ausgelöst wurde**, beschreibe, was Du getan hast, bevor das Problem aufgetreten ist

#### Was macht ein gutes Feature-Issue aus
- **Verwende einen klaren und aussagekräftigen Titel** für das Problem, um den Vorschlag zu identifizieren.
- **Gib eine Schritt-für-Schritt-Beschreibung der vorgeschlagenen Verbesserung** mit so vielen Details wie möglich an.
- **Gib spezifische Beispiele an, um die Schritte zu demonstrieren**. Füge Snippets zum Kopieren/Einfügen ein, die Du in diesen Beispielen verwendest, als [Markdown-Codeblöcke](https://help.github.com/articles/markdown-basics/#multiple-lines) bei.
- **Beschreibe das aktuelle Verhalten** und **erkläre, welches Verhalten Du stattdessen erwartet hast** und warum.
- **Füge Screenshots und animierte GIFs bei**, die zeigen, wie das Feature umgesetzt werden soll oder auf den Teil von UnicacityAddon hinweisen, auf den sich der Vorschlag bezieht. Du kannst [dieses Tool](https://www.cockos.com/licecap/) verwenden, um GIFs unter macOS und Windows aufzunehmen und [dieses Tool](https://github.com/colinkeenan/silentcast) oder [dieses Tool](https://github.com/GNOME/byzanz) unter Linux.
- **Erkläre, warum diese Erweiterung für die meisten UnicacityAddon-Benutzer nützlich wäre**.

### Erster Code Beitrag

Für einen leichteren Einstieg in das Projekt gibt es `good first issue` und ` help wanted` Label:
- [good first issue](https://github.com/rettichlp/UnicacityAddon-1.12.2/labels/good%20first%20issue) - Issues die nur ein paar Zeilen Code benötigen.
- [help wanted](https://github.com/rettichlp/UnicacityAddon-1.12.2/labels/help%20wanted) - Issues die etwas anspruchsvoller als `good first issue`

## Code

### Branch
Der Branch Name ergibt sich aus dem Titel des Issues. Dabei steht UCAF für Feature und UCAB für Bug. Leerzeichen werden durch Bindestriche ersetzt. Das Template für Branches ist Folgendes:
- Feature (UCAF): `feature/UCAF-NUMMER-BESCHREIBUNG`
- Bug (UCAB): `bug/UCAB-NUMMER-BESCHREIBUNG`

Wenn das Issue `UCAF - Add reinforcements` heißt und die Nummer 4 (GitHub Nummer) hat, ergibt sich folgender Branch: `feature/UCAF-4-Add-reinforcements`

### Pull Requests
Für Pull Requests gibt es ein paar Richtlinien, die für ein übersichtliches Arbeiten wichtig sind. Der Name der Pullrequest ist immer der name des Branches, der gemerged werden soll.

Wenn der Branch `feature/UCAF-4-Add-reinforcements` heißt, ist der Name der Pull Request ebenfalls `feature/UCAF-4-Add-reinforcements`.

In die Beschreibung einer Pull Request kommt das Issue, das dadurch geschlossen wird. In diesem Fall wäre die Beschreibung:
> Hinweis: Es werden [Closing Keywords](https://docs.github.com/articles/closing-issues-using-keywords) verwendet.

```
closes #4
```

> Achtung: Da immer ein Issue verlinkt werden muss, gibt es demnach für alles ein Issue. Das Arbeiten ohne Issue ist unerwünscht. Issues können auch nach dem eigentlichen Coding angelegt werden. Es besteht jedoch kein Recht auf das Mergen von gestellten Pull Requests.

### Commit
Commit Nachrichten dürfen beliebig gestaltet werden.

Es wird jedoch um folgendes gebeten:
- keine Emojis
- Gegenwart verwenden: "Add feature" statt "Added feature"
- Imperativ verwenden: "Move to..." statt "Moves to..."
