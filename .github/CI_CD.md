# GitHub Actions CI/CD Pipeline

Diese GitHub Actions Pipeline automatisiert den Build und Release-Prozess der QR-Code Generator App.

## Übersicht

Die Pipeline besteht aus zwei Jobs:

1. **Build Job:** Erstellt die Debug APK mit JDK 21
2. **Release Job:** Erstellt ein GitHub Release mit der APK und SHA256 Checksumme

## Trigger

Die Pipeline wird automatisch ausgelöst, wenn:
- Ein Git Tag mit dem Format `v*` gepusht wird (z.B. `v1.0.0`) - **erstellt automatisch ein Release**
- Manuell über die GitHub Actions UI (`workflow_dispatch`) - **erstellt nur Build, kein Release** (außer wenn Version angegeben wird)

## Workflow-Schritte

### Build Job

1. **Checkout:** Code aus dem Repository auschecken
2. **JDK 21 Setup:** Java Development Kit 21 einrichten
3. **Gradle Build:** `assembleDebug` ausführen
4. **APK umbenennen:** `app-debug.apk` → `qr-generator.apk`
5. **SHA256 Checksumme:** `qr-generator.apk.sha256sum` erstellen
6. **Artifact Upload:** APK und Checksumme als Artifact speichern

### Release Job

1. **Artifact Download:** APK und Checksumme herunterladen
2. **Version extrahieren:** Version aus Git Tag extrahieren
3. **GitHub Release erstellen:** Release mit APK und Checksumme hochladen

## Verwendung

### Erste Release erstellen (Version 1.0.0)

```bash
# 1. Änderungen committen
git add .
git commit -m "Prepare release v1.0.0"

# 2. Tag erstellen und pushen
git tag v1.0.0
git push origin v1.0.0

# Die Pipeline startet automatisch
```

### Weitere Releases

```bash
# Version erhöhen (z.B. v1.1.0)
git tag v1.1.0
git push origin v1.1.0
```

### Manuelles Auslösen

**Option 1: Nur Build (kein Release)**
1. Gehe zu **Actions** Tab im GitHub Repository
2. Wähle **Build and Release** Workflow
3. Klicke auf **Run workflow**
4. Wähle Branch und klicke auf **Run workflow**
5. **Hinweis:** Release-Job wird übersprungen, da kein Tag vorhanden ist

**Option 2: Build + Release (mit Version)**
1. Gehe zu **Actions** Tab im GitHub Repository
2. Wähle **Build and Release** Workflow
3. Klicke auf **Run workflow**
4. Wähle Branch
5. **Wichtig:** Gib im Feld "Version" eine Version ein (z.B. `1.0.0`)
6. Klicke auf **Run workflow**
7. Release wird mit Tag `v{version}` erstellt

## Release-Artefakte

Jedes Release enthält:

- **qr-generator.apk:** Die installierbare Android APK
- **qr-generator.apk.sha256sum:** SHA256 Checksumme zur Verifizierung

## Checksumme verifizieren

```bash
# Checksumme verifizieren
sha256sum -c qr-generator.apk.sha256sum

# Erwartete Ausgabe:
# qr-generator.apk: OK
```

## Installation

```bash
# APK auf Android-Gerät installieren
adb install qr-generator.apk

# Oder manuell:
# 1. APK auf Gerät kopieren
# 2. Installieren über Datei-Manager
# 3. "Unbekannte Quellen" aktivieren falls nötig
```

## Konfiguration

### Version ändern

Die Version wird aus dem Git Tag extrahiert. Um die App-Version zu ändern:

1. **build.gradle.kts** bearbeiten:
   ```kotlin
   defaultConfig {
       versionCode = 1  // Erhöhen für neue Versionen
       versionName = "1.0"  // Version-String
   }
   ```

2. Git Tag erstellen:
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```

### JDK Version ändern

Die Pipeline verwendet JDK 21. Um die Version zu ändern, bearbeite `.github/workflows/build-and-release.yml`:

```yaml
- name: Set up JDK 21
  uses: actions/setup-java@v4
  with:
    java-version: '21'  # Hier ändern
```

## Troubleshooting

### Build schlägt fehl

1. Prüfe die Logs im **Actions** Tab
2. Stelle sicher, dass alle Dependencies verfügbar sind
3. Prüfe, ob `gradlew` ausführbar ist

### Release wird nicht erstellt

**Bei Git Tag:**
1. Stelle sicher, dass der Tag mit `v` beginnt (z.B. `v1.0.0`)
2. Prüfe, ob `GITHUB_TOKEN` verfügbar ist (automatisch bei GitHub Actions)
3. Prüfe Repository-Berechtigungen

**Bei manuellem Auslösen:**
1. **Wichtig:** Der Release-Job wird nur ausgeführt, wenn:
   - Ein Git Tag mit `v*` gepusht wurde, ODER
   - Beim manuellen Auslösen eine Version im Input-Feld angegeben wurde
2. Wenn kein Release erstellt wird, prüfe die Workflow-Logs auf "skipped" Meldungen
3. Um ein Release manuell zu erstellen, gib beim `workflow_dispatch` eine Version ein (z.B. `1.0.0`)

### Checksumme stimmt nicht überein

1. Stelle sicher, dass die APK nicht nach dem Build geändert wurde
2. Prüfe, ob die Checksumme-Datei korrekt erstellt wurde
3. Verwende `sha256sum -c` zur Verifizierung

## Weitere Informationen

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Android Gradle Plugin Documentation](https://developer.android.com/studio/build)
- [Git Tags Documentation](https://git-scm.com/book/en/v2/Git-Basics-Tagging)
