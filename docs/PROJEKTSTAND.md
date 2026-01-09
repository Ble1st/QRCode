# Projektstand: QR-Code Generator

**Stand:** 9. Januar 2026  
**Version:** 1.0.0  
**Status:** ✅ Modulare Architektur implementiert, Build-Fehler behoben

## Übersicht

Die QR-Code Generator App wurde erfolgreich in eine modulare Architektur nach Clean Architecture Prinzipien umstrukturiert. Die App verwendet moderne Android-Technologien und folgt Best Practices für skalierbare Android-Entwicklung.

## Modulare Architektur

### Projektstruktur

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
│   │   ├── util/
│   │   │   ├── Result.kt              # Result-Type für Operationen
│   │   │   ├── Constants.kt           # Gemeinsame Konstanten
│   │   │   └── Extensions.kt          # Kotlin Extensions
│   │   └── build.gradle.kts
│   │
│   ├── model/                    # Datenmodelle
│   │   ├── QRCodeData.kt             # QR-Code Datenmodell
│   │   ├── StorageResult.kt          # Storage-Ergebnis-Typ
│   │   └── build.gradle.kts
│   │
│   ├── designsystem/             # Design System
│   │   ├── theme/
│   │   │   ├── Color.kt              # Farbdefinitionen
│   │   │   ├── Type.kt               # Typografie
│   │   │   └── Theme.kt              # Material 3 Theme
│   │   ├── components/
│   │   │   └── QRCodeCard.kt         # Wiederverwendbare UI-Komponente
│   │   └── build.gradle.kts
│   │
│   └── testing/                  # Test-Utilities
│       ├── TestFixtures.kt          # Test-Daten und Fixtures
│       └── build.gradle.kts
│
└── feature/                      # Feature-Module
    └── qrcode/
        ├── api/                  # Öffentliche API
        │   ├── QRCodeApi.kt          # Feature-API Definition
        │   └── build.gradle.kts
        │
        ├── domain/               # Domain Layer (Business Logic)
        │   ├── repository/
        │   │   └── QRCodeRepository.kt    # Repository Interface
        │   ├── usecase/
        │   │   ├── GenerateQRCodeUseCase.kt    # QR-Code Generierung
        │   │   └── SaveQRCodeUseCase.kt       # QR-Code Speicherung
        │   └── build.gradle.kts
        │
        ├── data/                 # Data Layer (Implementierung)
        │   ├── repository/
        │   │   └── QRCodeRepositoryImpl.kt    # Repository Implementation
        │   ├── datasource/
        │   │   ├── QRCodeGenerator.kt         # QR-Code Generierung (ZXing)
        │   │   └── FileStorageManager.kt      # Dateispeicherung (SAF/MediaStore)
        │   ├── di/
        │   │   └── DataModule.kt              # Hilt Dependency Injection
        │   └── build.gradle.kts
        │
        └── ui/                   # UI Layer (Compose)
            ├── screen/
            │   └── MainScreen.kt              # Haupt-Compose-Screen
            ├── viewmodel/
            │   └── QRCodeViewModel.kt         # ViewModel mit StateFlow
            ├── state/
            │   └── QRCodeUiState.kt         # UI-State-Datenklasse
            └── build.gradle.kts
```

## Modul-Abhängigkeiten

```
app
  └── depends on → feature:qrcode:ui
                      └── depends on → feature:qrcode:domain
                                          └── depends on → feature:qrcode:data
                                                              └── depends on → core:model, core:common
                      └── depends on → core:designsystem
                      └── depends on → feature:qrcode:api

feature:qrcode:domain
  └── depends on → core:model, core:common

feature:qrcode:data
  └── depends on → core:model, core:common, feature:qrcode:domain

feature:qrcode:ui
  └── depends on → feature:qrcode:domain, core:designsystem, feature:qrcode:api
```

## Implementierte Features

### Funktionale Anforderungen

- ✅ **Text-Eingabe:** Benutzer kann Text oder URL eingeben
- ✅ **QR-Code Generierung:** Generierung von QR-Codes aus eingegebenem Text
- ✅ **QR-Code Anzeige:** Visuelle Darstellung des generierten QR-Codes
- ✅ **Dateispeicherung:** Speicherung des QR-Codes als PNG-Bilddatei
  - ✅ **Storage Access Framework (SAF):** Benutzer kann Speicherort selbst auswählen
  - ✅ **Fallback:** Automatische Speicherung in MediaStore/File API

### Technische Features

- ✅ **Modulare Architektur:** Clean Architecture mit separaten Modulen
- ✅ **Dependency Injection:** Hilt für Dependency Management
- ✅ **Reaktive UI:** Jetpack Compose mit StateFlow
- ✅ **Material Design 3:** Moderne UI-Komponenten
- ✅ **Strukturiertes Logging:** Timber für Logging
- ✅ **Asynchrone Programmierung:** Kotlin Coroutines
- ✅ **Version Catalog:** Zentrale Dependency-Verwaltung

## Verwendete Technologien

### Core Dependencies

| Technologie | Version | Zweck |
|------------|---------|-------|
| Kotlin | 2.2.20 | Programmiersprache |
| KSP | 2.2.20-2.0.4 | Kotlin Symbol Processing |
| Android Gradle Plugin | 8.13.2 | Build-System |
| Compose BOM | 2025.12.00 | Jetpack Compose |
| Hilt | 2.57.2 | Dependency Injection |
| Coroutines | 1.10.2 | Asynchrone Programmierung |
| Timber | 5.0.1 | Logging |
| ZXing Android Embedded | 4.3.0 | QR-Code Generierung |

### Android Libraries

- **Jetpack Compose:** Moderne deklarative UI
- **Material 3:** Design System
- **Activity Compose:** 1.12.2
- **Lifecycle Runtime Compose:** 2.7.0
- **Lifecycle ViewModel Compose:** 2.7.0

### Testing Libraries

- **JUnit:** 4.13.2
- **Kotlinx Coroutines Test:** 1.10.2
- **Turbine:** 1.2.0 (Flow Testing)

## Architektur-Patterns

### Clean Architecture

Die App folgt Clean Architecture Prinzipien mit klarer Trennung der Schichten:

1. **Presentation Layer (UI):**
   - Compose Screens
   - ViewModel (State Management)
   - UI State

2. **Domain Layer:**
   - Use Cases (Business Logic)
   - Repository Interfaces

3. **Data Layer:**
   - Repository Implementations
   - Data Sources
   - Hilt Modules

### MVVM Pattern

- **Model:** Domain Models (QRCodeData, StorageResult)
- **View:** Compose Screens (MainScreen)
- **ViewModel:** QRCodeViewModel mit StateFlow

### Dependency Injection

- **Hilt:** Zentrale Dependency Injection
- **Module-basierte Struktur:** Separate Hilt-Module pro Layer

## Code-Struktur Details

### Core:common

**Zweck:** Gemeinsame Utilities und Extensions

**Dateien:**
- `Result.kt`: Sealed class für Operation-Ergebnisse
- `Constants.kt`: Gemeinsame Konstanten (DEFAULT_QR_CODE_SIZE, MAX_TEXT_LENGTH)
- `Extensions.kt`: Utility-Funktionen (getTimestamp())

**Dependencies:**
- Kotlinx Coroutines Core
- Kotlinx Coroutines Test (für Tests)
- Turbine (für Flow Tests)

### Core:model

**Zweck:** Datenmodelle, die von allen Modulen verwendet werden

**Dateien:**
- `QRCodeData.kt`: Data class für QR-Code-Informationen
- `StorageResult.kt`: Sealed class für Storage-Operation-Ergebnisse

**Dependencies:**
- Keine externen Dependencies (pure Kotlin)

### Core:designsystem

**Zweck:** Design System, Themes, wiederverwendbare UI-Komponenten

**Dateien:**
- `Color.kt`: Material 3 Farbdefinitionen
- `Type.kt`: Typografie-Definitionen
- `Theme.kt`: QRCodeTheme Composable mit Dark/Light Mode Support
- `QRCodeCard.kt`: Wiederverwendbare Card-Komponente

**Dependencies:**
- Compose BOM
- Compose UI
- Material 3

### Feature:qrcode:domain

**Zweck:** Business Logic, Use Cases, Repository Interfaces

**Dateien:**
- `QRCodeRepository.kt`: Interface für QR-Code-Operationen
- `GenerateQRCodeUseCase.kt`: Use Case für QR-Code-Generierung
- `SaveQRCodeUseCase.kt`: Use Case für QR-Code-Speicherung

**Dependencies:**
- Core:model
- Core:common
- Kotlinx Coroutines
- Javax Inject

### Feature:qrcode:data

**Zweck:** Data Layer, Repository Implementation, Data Sources

**Dateien:**
- `QRCodeRepositoryImpl.kt`: Repository-Implementierung
- `QRCodeGenerator.kt`: QR-Code-Generierung mit ZXing
- `FileStorageManager.kt`: Dateispeicherung (SAF/MediaStore)
- `DataModule.kt`: Hilt Module für Dependency Injection

**Dependencies:**
- Core:model
- Core:common
- Feature:qrcode:domain
- Hilt
- ZXing Android Embedded
- Timber

### Feature:qrcode:ui

**Zweck:** UI Layer, Compose Screens, ViewModel

**Dateien:**
- `MainScreen.kt`: Haupt-Compose-Screen mit UI-Layout
- `QRCodeViewModel.kt`: ViewModel mit StateFlow für reaktive UI
- `QRCodeUiState.kt`: UI-State-Datenklasse

**Dependencies:**
- Core:designsystem
- Core:model
- Feature:qrcode:domain
- Feature:qrcode:api
- Compose BOM
- Activity Compose
- Lifecycle Runtime Compose
- Lifecycle ViewModel Compose
- Hilt

### App-Modul

**Zweck:** Haupt-App-Modul, das alle Features zusammenführt

**Dateien:**
- `QRCodeApplication.kt`: Application-Klasse mit Hilt und Timber-Initialisierung
- `MainActivity.kt`: Entry Point mit Compose Content und SAF File Picker

**Dependencies:**
- Feature:qrcode:ui
- Core:designsystem
- Hilt
- Activity Compose

## Implementierungsdetails

### QR-Code Generierung

- **Bibliothek:** ZXing Android Embedded 4.3.0
- **Implementierung:** `QRCodeGenerator` in `feature:qrcode:data`
- **Features:**
  - UTF-8 Encoding
  - Error Correction Level M
  - Konfigurierbare Größe (Standard: 512x512)
  - Fehlerbehandlung mit Timber-Logging

### Dateispeicherung

- **Primär:** Storage Access Framework (SAF)
  - Benutzer wählt Speicherort selbst aus
  - Keine Runtime-Permissions erforderlich
- **Fallback:** MediaStore API (Android 10+) oder File API (ältere Versionen)
- **Implementierung:** `FileStorageManager` in `feature:qrcode:data`
- **Format:** PNG mit 100% Qualität

### UI/UX

- **Framework:** Jetpack Compose
- **Design System:** Material 3
- **Features:**
  - Dark/Light Mode Support
  - Dynamische Farben (Android 12+)
  - Responsive Layout
  - Loading States
  - Error Handling mit Fehlermeldungen

### State Management

- **Pattern:** MVVM mit StateFlow
- **State:** `QRCodeUiState` mit:
  - `qrCodeBitmap`: Generierter QR-Code
  - `isGenerating`: Loading-State
  - `errorMessage`: Fehlermeldungen

## Build-Konfiguration

### Version Catalog

Alle Dependencies sind zentral in `gradle/libs.versions.toml` verwaltet.

### Module Build Files

Jedes Modul hat sein eigenes `build.gradle.kts` mit:
- Spezifischen Dependencies
- Namespace-Definition
- Build-Features (Compose, etc.)

### Settings.gradle.kts

Alle Module sind in `settings.gradle.kts` registriert:
- `:app`
- `:core:common`
- `:core:model`
- `:core:designsystem`
- `:core:testing`
- `:feature:qrcode:api`
- `:feature:qrcode:domain`
- `:feature:qrcode:data`
- `:feature:qrcode:ui`

## Nächste Schritte

### Geplante Verbesserungen

1. **Testing:**
   - Unit Tests für Use Cases
   - Unit Tests für Repository Implementation
   - UI Tests für Compose Screens

2. **Features:**
   - QR-Code-Scanning (optional)
   - QR-Code-Historie
   - Verschiedene QR-Code-Formate
   - Export-Optionen (SVG, etc.)

3. **Code-Qualität:**
   - Code-Dokumentation erweitern
   - Code-Reviews
   - Performance-Optimierungen

4. **Dokumentation:**
   - API-Dokumentation
   - Entwickler-Guide
   - Architektur-Diagramme

## Bekannte Probleme & Lösungen

### Iteration 2: Build-Fehlerbehebung (9. Januar 2026)

**Problem:** AGP Version `8.13` wurde nicht gefunden  
**Lösung:** 
- AGP-Version auf `8.7.3` korrigiert (`gradle/libs.versions.toml`)
- Gradle-Version auf `8.9` angepasst (`gradle/wrapper/gradle-wrapper.properties`)
- ✅ Build funktioniert jetzt korrekt

**Details:** Siehe `docs/ITERATIONEN.md` für vollständige Dokumentation.

## Bekannte Einschränkungen

- **MVP-Status:** Fokus auf "Happy Path"
- **Testing:** Noch keine automatisierten Tests implementiert
- **Error Handling:** Basis-Fehlerbehandlung vorhanden, kann erweitert werden
- **UI:** Einfaches Layout, kann mit mehr Features erweitert werden

## Build & Deployment

### Voraussetzungen

- Android Studio Hedgehog oder neuer
- JDK 11 oder höher
- Android SDK 34+
- Gradle 8.13+

### Build-Befehle

```bash
# Projekt synchronisieren
./gradlew build --refresh-dependencies

# Debug-Build erstellen
./gradlew assembleDebug

# Release-Build erstellen
./gradlew assembleRelease

# Tests ausführen
./gradlew test
```

### App-Konfiguration

- **Package Name:** `com.ble1st.qrcode`
- **Min SDK:** 34
- **Target SDK:** 36
- **Version Code:** 1
- **Version Name:** 1.0.0

## Zusammenfassung

Die QR-Code Generator App wurde erfolgreich in eine modulare, skalierbare Architektur umgewandelt. Die Implementierung folgt modernen Android-Best-Practices und Clean Architecture Prinzipien. Die App ist bereit für weitere Entwicklung und Erweiterungen.

**Status:** ✅ Modulare Architektur vollständig implementiert  
**Build-Status:** ✅ Alle Build-Fehler behoben, Build erfolgreich

## Aktuelle Build-Probleme & Lösungen

### Iteration 11: BuildConfig zur Kompilierungszeit nicht verfügbar (9. Januar 2026)
- **Problem:** BuildConfig wird erst nach Kotlin-Kompilierung generiert
- **Lösung:** BuildConfig.DEBUG durch ApplicationInfo.FLAG_DEBUGGABLE ersetzt
- **Status:** ✅ Debug-Check funktioniert zur Laufzeit

### Iteration 10: Fehlende Data-Modul Dependency (9. Januar 2026)
- **Problem:** Hilt konnte DataModule nicht finden
- **Lösung:** feature:qrcode:data Modul zum App-Modul hinzugefügt
- **Status:** ✅ Hilt-Module werden korrekt gefunden

### Iteration 9: Kotlin/Hilt Kompatibilität (9. Januar 2026)
- **Problem:** Hilt 2.57.2 unterstützt Kotlin 2.3.0 nicht (Metadata-Version 2.3.0 > max. 2.2.0)
- **Lösung:** Kotlin auf 2.2.20 downgradet, KSP auf 2.2.20-2.0.4 angepasst
- **Status:** ✅ Kotlin-Version angepasst, kompatibel mit Hilt

**Details:** Siehe `docs/ITERATIONEN.md` für vollständige Dokumentation aller Iterationen.

## Build-Konfiguration

### .gitignore
Die `.gitignore` Datei enthält folgende Einträge für Build-Verzeichnisse:
- `/build` und `**/build/` - Alle Build-Verzeichnisse (Root und Module)
- `.gradle` - Gradle Cache-Verzeichnis
- `**/generated/` - Generierte Dateien
- `**/kspCaches/` - KSP Cache-Verzeichnisse
- `**/hilt_aggregated_deps/` - Hilt generierte Dependencies
- `*.apk`, `*.ap_`, `*.aab` - Build-Outputs
- `*.dex`, `*.class` - Kompilierte Dateien
- Lokale Konfigurationsdateien (`local.properties`, etc.)

**Status:** ✅ Alle Build-Verzeichnisse und generierte Dateien werden ignoriert

### Build-Befehle

```bash
# Debug-Build erstellen
./gradlew assembleDebug

# Clean Build
./gradlew clean assembleDebug

# Alle Tests ausführen
./gradlew test
```

**Nächster Schritt:** Build erfolgreich - App kann getestet werden
