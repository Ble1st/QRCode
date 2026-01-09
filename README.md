# QR-Code Generator Android App

**Version:** 1.0.6  
**Stand:** 9. Januar 2026  
**Status:** ✅ Produktionsbereit

---

## 📱 Projektübersicht

Die QR-Code Generator App ist eine moderne Android-Anwendung, die Text und URLs in QR-Codes umwandelt, diese anzeigt und speichern kann. Die App folgt Clean Architecture Prinzipien und verwendet moderne Android-Technologien wie Jetpack Compose, Hilt Dependency Injection und Material Design 3.

---

## 🎯 Projektanforderungen (Homeoffice ITA24-1-APP)

### Kernfunktionalität (Must-Have)

#### ✅ 1. Texteingabe
- **Anforderung:** Einfaches Eingabefeld für Text oder URL
- **Implementierung:** Material 3 `OutlinedTextField` mit automatischer QR-Code-Generierung
- **Status:** ✅ Vollständig implementiert

#### ✅ 2. QR-Code-Generierung
- **Anforderung:** Automatische Umwandlung von Text in QR-Code
- **Implementierung:** ZXing Android Embedded Bibliothek (Version 4.3.0)
- **Features:**
  - UTF-8 Encoding
  - Error Correction Level M
  - Konfigurierbare Größe (Standard: 512x512 Pixel)
  - Automatische Generierung bei Texteingabe
- **Status:** ✅ Vollständig implementiert

#### ✅ 3. QR-Code-Anzeige
- **Anforderung:** Visuelle Darstellung des generierten QR-Codes
- **Implementierung:** 
  - Responsive QR-Code-Größe (80% Bildschirmbreite, min 256dp, max 512dp)
  - Material 3 Card mit modernem Design
  - Animationen beim Erscheinen/Verschwinden
  - Scroll-Funktion für kleine Bildschirme
- **Status:** ✅ Vollständig implementiert

#### ✅ 4. Dateispeicherung (Storage Access Framework - SAF)
- **Anforderung:** QR-Code als PNG-Datei speichern via Storage Access Framework
- **Implementierung:** 
  - SAF-Dialog für benutzerdefinierte Speicherorte
  - MediaStore API für direkte Speicherung in Galerie
  - FileProvider für sichere Datei-Freigabe (Share-Funktion)
- **Status:** ✅ Vollständig implementiert

### Erweiterte Features (Nice-to-Have)

#### ✅ 5. Mehrere Speicher-Optionen
- **Speicherplatz wählen:** Benutzer kann Speicherort selbst auswählen (SAF)
- **In Galerie speichern:** Direkte Speicherung in Galerie über MediaStore API
- **Teilen:** QR-Code kann über alle Share-Apps geteilt werden
- **Status:** ✅ Vollständig implementiert

#### ✅ 6. Moderne UI/UX
- Material Design 3 mit expressiven Shapes
- Responsive Design für alle Bildschirmgrößen
- Material Icons für bessere Erkennbarkeit
- Snackbar für Erfolgs- und Fehlermeldungen
- Animationen für moderne UX
- **Status:** ✅ Vollständig implementiert

---

## 🏗️ Architektur

### Modulare Clean Architecture

Die App ist in eine modulare Architektur nach Clean Architecture Prinzipien strukturiert:

```
QRCode/
├── app/                          # Haupt-App-Modul
│   ├── src/main/
│   │   ├── kotlin/com/ble1st/qrcode/
│   │   │   ├── QRCodeApplication.kt    # Hilt Application-Klasse
│   │   │   └── MainActivity.kt         # Entry Point mit Compose
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
│
├── core/                         # Core-Module (gemeinsame Funktionalität)
│   ├── common/                   # Utilities, Extensions
│   ├── model/                    # Datenmodelle
│   ├── designsystem/             # Design System, Themes, Komponenten
│   └── testing/                  # Test-Utilities
│
└── feature/                      # Feature-Module
    └── qrcode/
        ├── api/                  # Öffentliche API
        ├── domain/               # Domain Layer (Business Logic)
        ├── data/                 # Data Layer (Implementierung)
        └── ui/                   # UI Layer (Compose)
```

### Architektur-Patterns

- **Clean Architecture:** Klare Trennung der Schichten (Presentation, Domain, Data)
- **MVVM Pattern:** Model-View-ViewModel für UI-State-Management
- **Dependency Injection:** Hilt für zentrale Dependency-Verwaltung
- **Repository Pattern:** Abstraktion der Datenquellen

---

## 🛠️ Technologie-Stack

### Core Dependencies

| Technologie | Version | Zweck |
|------------|---------|-------|
| Kotlin | 2.2.20 | Programmiersprache |
| KSP | 2.2.20-2.0.4 | Kotlin Symbol Processing |
| Android Gradle Plugin | 8.13.2 | Build-System |
| Compose BOM | 2025.12.00 | Jetpack Compose |
| Hilt | 2.57.2 | Dependency Injection |
| Coroutines | 1.10.2 | Asynchrone Programmierung |
| ZXing Android Embedded | 4.3.0 | QR-Code Generierung |

### Android Libraries

- **Jetpack Compose:** Moderne deklarative UI
- **Material 3:** Design System mit expressiven Shapes
- **Activity Compose:** 1.12.2
- **Lifecycle Runtime Compose:** 2.7.0
- **Lifecycle ViewModel Compose:** 2.7.0
- **Hilt Navigation Compose:** 1.3.0

### Build-Konfiguration

- **Version Catalog:** Zentrale Dependency-Verwaltung in `gradle/libs.versions.toml`
- **Type-safe Project Accessors:** Moderne Gradle-Features
- **KSP:** Für Hilt Code-Generierung

### ⚠️ Dependency-Versionen

**Wichtiger Hinweis:** Einige Dependency-Versionen sind bewusst älter als die neuesten verfügbaren Versionen, um Kompatibilitätskonflikte zu vermeiden.

**Beispiele:**
- **Kotlin 2.2.20** statt 2.3.0+: Hilt 2.57.2 unterstützt maximal Kotlin 2.2.0 Metadata-Version
- **KSP 2.2.20-2.0.4** statt neuerer Versionen: Muss mit Kotlin-Version kompatibel sein
- **Hilt 2.57.2**: Stabile Version, die mit Kotlin 2.2.20 kompatibel ist

Diese Versionen wurden nach ausführlichem Testing ausgewählt, um sicherzustellen, dass alle Dependencies reibungslos zusammenarbeiten. Ein Upgrade einzelner Dependencies kann zu Inkompatibilitäten führen und erfordert sorgfältige Kompatibilitätstests.

---

## ⚙️ Funktionsweise

### App-Ablauf

1. **App-Start:**
   - `QRCodeApplication` initialisiert Hilt Dependency Injection
   - Timber-Logging wird für Debug-Builds aktiviert
   - `MainActivity` wird gestartet und lädt `MainScreen` Composable

2. **Texteingabe:**
   - Benutzer gibt Text oder URL in das `OutlinedTextField` ein
   - Bei jeder Änderung wird `QRCodeViewModel.generateQRCode()` aufgerufen
   - Der Text wird an `GenerateQRCodeUseCase` weitergegeben

3. **QR-Code-Generierung:**
   - `GenerateQRCodeUseCase` ruft `QRCodeRepository.generateQRCode()` auf
   - `QRCodeRepositoryImpl` delegiert an `QRCodeGenerator`
   - `QRCodeGenerator` verwendet ZXing-Bibliothek zur QR-Code-Erstellung
   - Ergebnis wird als `Bitmap` zurückgegeben

4. **QR-Code-Anzeige:**
   - `QRCodeViewModel` aktualisiert `StateFlow<QRCodeUiState>`
   - `MainScreen` beobachtet den State mit `collectAsState()`
   - QR-Code wird in einer Material 3 Card mit Animationen angezeigt
   - Größe passt sich responsiv an die Bildschirmbreite an

5. **Speicherung/Teilen:**
   - **Speicherplatz wählen:** SAF-Dialog öffnet sich, Benutzer wählt Speicherort
   - **In Galerie speichern:** MediaStore API speichert direkt in `Pictures/QRCode`
   - **Teilen:** FileProvider erstellt temporäre Datei, Share-Intent wird gestartet

### Datenfluss

```
UI Layer (MainScreen)
    ↓ (Events)
ViewModel (QRCodeViewModel)
    ↓ (Use Cases)
Domain Layer (GenerateQRCodeUseCase, SaveQRCodeUseCase)
    ↓ (Repository Interface)
Data Layer (QRCodeRepositoryImpl)
    ↓ (Data Sources)
QRCodeGenerator / FileStorageManager
```

### State Management

- **StateFlow:** Reaktives State-Management für UI-Updates
- **Unidirectional Data Flow:** Events fließen von UI → ViewModel → Domain → Data
- **State Updates:** State fließt von Data → Domain → ViewModel → UI

---

## 📋 Funktionen im Detail

### 1. QR-Code-Generierung

**Funktionsweise:**
- Eingabetext wird an ZXing-Bibliothek übergeben
- QR-Code wird mit UTF-8 Encoding und Error Correction Level M generiert
- Standard-Größe: 512x512 Pixel (anpassbar)
- Ergebnis wird als Bitmap im Memory gespeichert

**Technische Details:**
- Verwendet `com.journeyapps:zxing-android-embedded:4.3.0`
- Asynchrone Generierung über Kotlin Coroutines
- Fehlerbehandlung mit Timber-Logging

### 2. QR-Code-Anzeige

**Funktionsweise:**
- Bitmap wird in `StateFlow` gespeichert
- Compose UI reagiert automatisch auf State-Änderungen
- QR-Code wird in Material 3 Card mit Elevation angezeigt
- Responsive Größe: 80% Bildschirmbreite (min 256dp, max 512dp)

**UI-Features:**
- Fade-In/Slide-In Animationen beim Erscheinen
- Scroll-Funktion für kleine Bildschirme
- Dark/Light Mode Support über Material 3 Theming

### 3. Speicher-Optionen

#### Option 1: Speicherplatz wählen (SAF)
- **Funktionsweise:** Storage Access Framework Dialog öffnet sich
- **Vorteil:** Benutzer hat volle Kontrolle über Speicherort
- **Implementierung:** `ActivityResultContracts.CreateDocument`
- **Keine Permissions nötig:** SAF erfordert keine Runtime-Permissions

#### Option 2: In Galerie speichern
- **Funktionsweise:** MediaStore API erstellt Eintrag in `Pictures/QRCode`
- **Vorteil:** Direkte Speicherung ohne Benutzerinteraktion
- **Implementierung:** `MediaStore.Images.Media` API
- **Sichtbarkeit:** Datei ist sofort in Galerie-App sichtbar

#### Option 3: Teilen
- **Funktionsweise:** FileProvider erstellt temporäre Datei im Cache
- **Vorteil:** Unterstützt alle Share-Apps (WhatsApp, E-Mail, etc.)
- **Implementierung:** `FileProvider` mit `ACTION_SEND` Intent
- **Sicherheit:** Temporäre Dateien werden automatisch bereinigt

### 4. UI/UX-Features

**Responsive Design:**
- QR-Code-Größe passt sich an Bildschirmbreite an
- Scroll-Funktion für kleine Bildschirme
- Zentrierte Ausrichtung für bessere Balance

**Feedback-Mechanismen:**
- Snackbar für Erfolgsmeldungen (grün)
- Snackbar für Fehlermeldungen (rot)
- Loading-States während Generierung

**Material Design 3:**
- Expressive Shapes für moderne Optik
- Material Icons für bessere Erkennbarkeit
- Dark/Light Mode Support
- Konsistente Farbgebung

### 5. Architektur-Features

**Clean Architecture:**
- Klare Trennung: UI → Domain → Data
- Unabhängige Testbarkeit jeder Schicht
- Wiederverwendbare Use Cases

**Dependency Injection:**
- Hilt für automatische Dependency-Verwaltung
- Module-basierte Struktur
- Einfache Mocking für Tests

**Reaktive Programmierung:**
- StateFlow für UI-State
- Coroutines für asynchrone Operationen
- Flow-basierte Datenströme

---

## 🔒 Sicherheit & Best Practices

### Scoped Storage

- ✅ **Keine veralteten Permissions:** App verwendet Scoped Storage korrekt
- ✅ **MediaStore API:** Für direkte Speicherung in Galerie (keine Permissions nötig)
- ✅ **Storage Access Framework:** Für benutzerdefinierte Speicherorte (keine Permissions nötig)
- ✅ **FileProvider:** Für sichere Datei-Freigabe

### Code-Qualität

- ✅ **Clean Architecture:** Klare Trennung der Verantwortlichkeiten
- ✅ **SOLID-Prinzipien:** Single Responsibility, Dependency Inversion
- ✅ **Error Handling:** Umfassende Fehlerbehandlung mit Timber-Logging
- ✅ **Type Safety:** Kotlin Type-System für sicheren Code

---

## 📱 App-Konfiguration

### Build-Konfiguration

- **Package Name:** `com.ble1st.qrcode`
- **Min SDK:** 34 (Android 14)
- **Target SDK:** 36
- **Version Code:** 1
- **Version Name:** 1.0.6

### Voraussetzungen

- Android Studio Hedgehog oder neuer
- JDK 11 oder höher
- Android SDK 34+
- Gradle 8.9+

---

## 🚀 Build & Installation

### Lokaler Build

#### Projekt synchronisieren

```bash
./gradlew build --refresh-dependencies
```

#### Debug-Build erstellen

```bash
./gradlew assembleDebug
```

#### Release-Build erstellen

```bash
./gradlew assembleRelease
```

#### Tests ausführen

```bash
./gradlew test
```

#### APK-Installation

Die generierte APK befindet sich unter:
```
app/build/outputs/apk/debug/app-debug.apk
```

### CI/CD Pipeline (GitHub Actions)

Die App verwendet eine automatische CI/CD Pipeline für Build und Release:

#### Automatischer Build & Release

Die Pipeline wird automatisch ausgelöst, wenn ein Git Tag mit dem Format `v*` gepusht wird:

```bash
# Erste Release erstellen (Version 1.0.0)
git tag v1.0.0
git push origin v1.0.0
```

#### Pipeline-Features

- ✅ **JDK 21:** Verwendet Java Development Kit 21
- ✅ **Automatischer Build:** Führt `assembleDebug` aus
- ✅ **APK-Umbenennung:** Erstellt `qr-generator.apk`
- ✅ **SHA256 Checksumme:** Erstellt `qr-generator.apk.sha256sum`
- ✅ **GitHub Release:** Automatisches Release mit APK und Checksumme

#### Release-Artefakte

Jedes Release enthält:
- `qr-generator.apk` - Die installierbare Android APK
- `qr-generator.apk.sha256sum` - SHA256 Checksumme zur Verifizierung

#### Checksumme verifizieren

```bash
sha256sum -c qr-generator.apk.sha256sum
```

Weitere Informationen zur CI/CD Pipeline finden Sie in [.github/CI_CD.md](.github/CI_CD.md).

---

## 📖 Verwendung

### QR-Code generieren

1. App öffnen
2. Text oder URL in das Eingabefeld eingeben
3. QR-Code wird automatisch generiert und angezeigt

### QR-Code speichern

**Option 1: Speicherplatz wählen**
1. Auf "Speicherplatz wählen" klicken
2. Speicherort im SAF-Dialog auswählen
3. Datei wird gespeichert

**Option 2: In Galerie speichern**
1. Auf "Galerie" klicken
2. QR-Code wird direkt in der Galerie gespeichert
3. Erfolgsmeldung wird angezeigt

**Option 3: Teilen**
1. Auf "Teilen" klicken
2. Share-Dialog öffnet sich
3. App auswählen (WhatsApp, E-Mail, etc.)
4. QR-Code wird geteilt

---

## 🧪 Testing

### Manuelle Tests

Die App wurde manuell getestet für:
- ✅ QR-Code-Generierung mit verschiedenen Texten
- ✅ QR-Code-Scannbarkeit mit Standard-Scanner-Apps
- ✅ Alle drei Speicher-Optionen
- ✅ Responsive Design auf verschiedenen Bildschirmgrößen
- ✅ Dark/Light Mode

### Geplante Tests

- [ ] Unit Tests für Use Cases
- [ ] Unit Tests für Repository Implementations
- [ ] UI Tests für Compose Screens
- [ ] Integration Tests

---

## 📚 Dokumentation

### Verfügbare Dokumentation

- **README.md** (diese Datei): Projektübersicht und Anforderungen
- **docs/MVP.md:** MVP-Definition und Erfolgskriterien
- **docs/ITERATIONEN.md:** Detaillierte Dokumentation aller Iterationen
- **docs/PROJEKTSTAND.md:** Aktueller Projektstand und Build-Status
- **docs/Moderne_Features_Liste.md:** Übersicht moderner Android-Features

### Iterationen

Die App wurde in 20 Iterationen entwickelt:

1. Modulare Architektur Implementierung
2-11. Build-Fehlerbehebungen
12. QR-Code Speicher- und Teilen-Funktionen erweitert
13. UI-Refactoring - Moderne UX-Verbesserungen
14. UI-Verbesserungen - Titel und zentrierte Eingabe
15. QR-Code-Logo hinzugefügt
16. QR-Code-Launcher-Icon erstellt
17. Benutzerdefiniertes Launcher-Icon (PNG) eingebunden
18. Hilt DataModule Binding-Fehler behoben
19. Scoped Storage - Veraltete Storage-Permissions entfernt
20. CI/CD Pipeline und Dokumentation

Siehe `docs/ITERATIONEN.md` für vollständige Details.

---

## 🐛 Bekannte Probleme

Aktuell keine bekannten Probleme. Alle Build-Fehler wurden behoben.

---

## 👥 Entwickler

**Projekt:** QR-Code Generator Android App  
**Entwickelt mit:** Android Studio, Kotlin, Jetpack Compose  
**Architektur:** Clean Architecture, MVVM Pattern  
**Dependency Injection:** Hilt  
**Entwicklung:** Komplett mit KI erstellt - Cursor

---

## 📄 Lizenz

MIT License

Copyright (c) 2026 QR-Code Generator

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

---

**Letzte Aktualisierung:** 9. Januar 2026  
**Status:** ✅ Produktionsbereit
