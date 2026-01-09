# QR-Code Generator Android App

**Version:** 1.0.5  
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

---

## 📋 Implementierte Features

### Funktionale Features

- ✅ **Text-Eingabe:** Benutzer kann Text oder URL eingeben
- ✅ **QR-Code Generierung:** Automatische Generierung von QR-Codes aus eingegebenem Text
- ✅ **QR-Code Anzeige:** Visuelle Darstellung des generierten QR-Codes
- ✅ **Dateispeicherung:** 
  - Speicherplatz wählen (Storage Access Framework)
  - In Galerie speichern (MediaStore API)
  - Teilen (FileProvider-basierte Share-Funktion)

### Technische Features

- ✅ **Modulare Architektur:** Clean Architecture mit separaten Modulen
- ✅ **Dependency Injection:** Hilt für Dependency Management
- ✅ **Reaktive UI:** Jetpack Compose mit StateFlow
- ✅ **Material Design 3:** Moderne UI-Komponenten
- ✅ **Asynchrone Programmierung:** Kotlin Coroutines
- ✅ **Scoped Storage:** Korrekte Implementierung ohne veraltete Permissions
- ✅ **Version Catalog:** Zentrale Dependency-Verwaltung

### UI/UX Features

- ✅ **Responsive Design:** Anpassung an alle Bildschirmgrößen
- ✅ **Scroll-Funktion:** Für kleine Bildschirme
- ✅ **Material Icons:** Für bessere Erkennbarkeit
- ✅ **Snackbar-Feedback:** Erfolgs- und Fehlermeldungen
- ✅ **Animationen:** Für moderne UX
- ✅ **Dark/Light Mode Support:** Material 3 Theming

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
- **Version Name:** 1.0.5

### Voraussetzungen

- Android Studio Hedgehog oder neuer
- JDK 11 oder höher
- Android SDK 34+
- Gradle 8.9+

---

## 🚀 Build & Installation

### Projekt synchronisieren

```bash
./gradlew build --refresh-dependencies
```

### Debug-Build erstellen

```bash
./gradlew assembleDebug
```

### Release-Build erstellen

```bash
./gradlew assembleRelease
```

### Tests ausführen

```bash
./gradlew test
```

### APK-Installation

Die generierte APK befindet sich unter:
```
app/build/outputs/apk/debug/app-debug.apk
```

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

Die App wurde in 19 Iterationen entwickelt:

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

Siehe `docs/ITERATIONEN.md` für vollständige Details.

---

## 🔄 Nächste Schritte

### Geplante Verbesserungen

1. **Testing:**
   - Unit Tests für Use Cases
   - Unit Tests für Repository Implementations
   - UI Tests für Compose Screens

2. **Features:**
   - QR-Code-Scanning (optional)
   - QR-Code-Historie
   - Verschiedene QR-Code-Formate
   - Export-Optionen (SVG, etc.)

3. **Code-Qualität:**
   - Code-Dokumentation erweitern
   - Performance-Optimierungen

4. **Dokumentation:**
   - API-Dokumentation
   - Entwickler-Guide
   - Architektur-Diagramme

---

## 🐛 Bekannte Probleme

Aktuell keine bekannten Probleme. Alle Build-Fehler wurden behoben.

---

## 📝 Changelog

### Version 1.0.5 (9. Januar 2026)
- ✅ Scoped Storage korrekt implementiert
- ✅ Veraltete Storage-Permissions entfernt
- ✅ Code vereinfacht (keine Version-Checks mehr nötig)

### Version 1.0.4 (9. Januar 2026)
- ✅ Hilt DataModule Binding-Fehler behoben
- ✅ Alle Dependency-Injection-Bindings korrekt konfiguriert

### Version 1.0.3 (9. Januar 2026)
- ✅ Benutzerdefiniertes Launcher-Icon (PNG) eingebunden
- ✅ Authentisches QR-Code-Design im App-Drawer

### Version 1.0.2 (9. Januar 2026)
- ✅ UI-Refactoring mit modernen UX-Verbesserungen
- ✅ Speicher- und Teilen-Funktionen erweitert

### Version 1.0.1 (9. Januar 2026)
- ✅ Modulare Architektur implementiert
- ✅ Alle Build-Fehler behoben

### Version 1.0.0 (9. Januar 2026)
- ✅ Initiale Implementierung
- ✅ MVP-Features vollständig implementiert

---

## 👥 Entwickler

**Projekt:** QR-Code Generator Android App  
**Entwickelt mit:** Android Studio, Kotlin, Jetpack Compose  
**Architektur:** Clean Architecture, MVVM Pattern  
**Dependency Injection:** Hilt

---

## 📄 Lizenz

Dieses Projekt wurde im Rahmen einer Homeoffice-Aufgabe entwickelt.

---

## 🙏 Danksagungen

- **ZXing:** Für die QR-Code-Generierungs-Bibliothek
- **Google:** Für Jetpack Compose und Material Design 3
- **Android Developer Community:** Für Best Practices und Dokumentation

---

**Letzte Aktualisierung:** 9. Januar 2026  
**Status:** ✅ Produktionsbereit
