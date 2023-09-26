# Grundlagen

## Wozu wird eine API benötigt?

Für manche Funktionen wie zum Beispiel Hausverbote, braucht man einen zentralen Speicher, um diese allen Spielern zur
Verfügung zu stellen. Des Weiteren ist durch einen zentralen Speicher auch eine schnellere Anpassung möglich. Wenn
beispielsweise ein neuer Navipunkt hinzugefügt werden soll, muss man nicht auf ein Update warten. Durch die API kann
direkt über das Spiel, durch einen Command, der Navipunkte hinzugefügt werden.

Ein weiterer wichtiger Grund war die Sicherheit von Daten. Wir haben eine Liste von Navipunkten von
[UnicaCity](https://unicacity.de/) bekommen unter der Voraussetzung, dass diese nicht veröffentlicht wird. Vor der API
haben wir das über ein Environment Secret in GitHub gelöst. Durch die API ist es möglich die Liste über einen
[Endpunkt](../endpoints/navi-point.md) bereitzustellen. Den Zugriff auf diese Endpunkte können wir besser schützen und
die Liste ist nicht mehr durch Dekompilierung der `.jar` Datei zu finden.

## Endpunkte

Die API stellt mehrere Endpunkte zur Verfügung. Alle Endpunkte starten mit der Base-Url: `https://rettichlp.de:8443/`.
Anschließend wird der Pfad der Anwendung und die API Version angegeben: `unicacityaddon/v1/`. Da nicht jeder Zugriff auf
jeden Endpunkt haben soll, muss das Addon-Token angegeben werden (siehe
[Autorisierung](autorisierung.md)). Danach wird der Endpunkt definiert und bei Bedarf Parameter
übergeben.

Base-Url:

```
https://rettichlp.de:8443/unicacityaddon/v1/{addon-token}/...
```

## Postman und OpenAPI

Geplant ist es eine Postman Collection zur Verfügung zu stellen. Das wird nachgeholt, wenn die Zeit da ist.

Eine OpenAPI soll ebenfalls hinzugefügt und auch in den Code eingebaut werden. Dafür fehlt jedoch auch noch die Zeit.
Die Datei wird in jedem Endpunkt am Anfang herunterladbar sein.
