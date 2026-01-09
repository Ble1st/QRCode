# MVP: QR-Code Generator Android App

**Projekt:** QR-Code Generator  
**Version:** 1.0  
**Datum:** 2026-01-XX  
**Autor:** Projektteam  

---

## 1. MVP-Definition

### 1.1 Was ist ein MVP?
Ein **Minimum Viable Product (MVP)** ist die kleinstmögliche Version eines Produkts, die noch funktionsfähig ist und den Kernnutzen erfüllt. Für dieses Projekt bedeutet das: **Funktioniert, nicht perfekt**.

### 1.2 MVP-Ziel
Eine Android-App, die Text/URL in QR-Codes umwandelt, diese anzeigt und speichern kann - **ohne Schnickschnack**.

---

## 2. MVP-Features (Must-Have)

### 2.1 Kernfunktionalität (P0 - Kritisch)

#### ✅ Feature 1: Texteingabe
- **Was:** Einfaches Eingabefeld für Text oder URL
- **Warum:** Grundvoraussetzung für QR-Code-Generierung
- **Akzeptanz:** Benutzer kann Text eingeben

#### ✅ Feature 2: QR-Code-Generierung
- **Was:** Automatische Umwandlung von Text in QR-Code
- **Warum:** Hauptfunktion der App
- **Akzeptanz:** QR-Code wird nach Eingabe generiert und ist scannbar

#### ✅ Feature 3: QR-Code-Anzeige
- **Was:** Visuelle Darstellung des generierten QR-Codes
- **Warum:** Benutzer muss QR-Code sehen können
- **Akzeptanz:** QR-Code wird klar angezeigt (mind. 256x256 Pixel)

#### ✅ Feature 4: Dateispeicherung (SAF)
- **Was:** QR-Code als PNG-Datei speichern via Storage Access Framework
- **Warum:** Pflichtanforderung aus Lastenheft
- **Akzeptanz:** Benutzer kann Speicherort wählen und Datei speichern

---

## 3. Nicht im MVP (Nice-to-Have)

### 3.1 Ausgeschlossene Features

#### ❌ Feature 5: QR-Code-Scanning
- **Grund:** Nicht Teil der Anforderung (nur Generierung)

#### ❌ Feature 6: QR-Code-Bearbeitung
- **Grund:** Design-Anpassungen (Farben, Logo) nicht erforderlich

#### ❌ Feature 7: Historie/Favoriten
- **Grund:** Keine Persistenz außer Dateispeicherung nötig

#### ❌ Feature 8: Batch-Generierung
- **Grund:** Nur einzelne QR-Codes erforderlich

#### ❌ Feature 9: Cloud-Speicherung
- **Grund:** Nur lokale Speicherung gefordert

#### ❌ Feature 10: Erweiterte Validierung
- **Grund:** Happy Path reicht - keine komplexe Fehlerbehandlung

---

## 4. MVP-Technologie-Stack

### 4.1 Minimale Dependencies
```kotlin
// QR-Code-Generierung
implementation("com.journeyapps:zxing-android-embedded:4.3.0")

// MVVM-Architektur
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
implementation("androidx.activity:activity-ktx:1.8.2")

// UI (bereits vorhanden)
implementation("com.google.android.material:material:1.13.0")
implementation("androidx.appcompat:appcompat:1.7.1")
```

### 4.2 Minimale Architektur
```
MainActivity (UI)
    ↓
QRCodeViewModel (State Management)
    ↓
QRCodeGenerator (Business Logic)
FileStorageManager (Storage Logic)
```

**Keine zusätzlichen Layer:** Repository, Use Cases, etc. sind für MVP nicht nötig.

---

## 5. MVP-UI-Anforderungen

### 5.1 Minimale UI-Komponenten
- ✅ TextInputEditText (Eingabefeld)
- ✅ Button "Generieren" (optional - kann auch automatisch sein)
- ✅ ImageView (QR-Code-Anzeige)
- ✅ Button "Speichern"

### 5.2 Design-Anforderungen
- ✅ Material Design Components verwenden
- ❌ Kein Custom Design nötig
- ❌ Keine Animationen
- ❌ Keine erweiterte Gestaltung

**Prinzip:** Funktioniert > Sieht gut aus

---

## 6. MVP-Fehlerbehandlung

### 6.1 Happy Path Fokus
Für MVP werden nur **grundlegende** Fehlerfälle behandelt:

#### ✅ Behandelt:
- Leere Eingabe → Hinweis anzeigen
- QR-Code-Generierung fehlgeschlagen → Fehlermeldung
- Speicherung fehlgeschlagen → Fehlermeldung
- SAF-Dialog abgebrochen → Keine Aktion (OK)

#### ❌ Nicht behandelt:
- Sehr langer Text (>2000 Zeichen)
- Sonderzeichen/Emojis
- Netzwerkfehler (nicht relevant)
- Speicher voll (System zeigt Fehler)
- Mehrfache schnelle Klicks

**Prinzip:** Wenn der Benutzer korrekt eingibt, funktioniert es.

---

## 7. MVP-Test-Anforderungen

### 7.1 Minimale Tests
- ✅ **Manuelle Tests:** Happy Path durchspielen
- ✅ **Funktionalitätstest:** QR-Code scannen mit anderer App
- ❌ Keine Unit Tests erforderlich (für MVP)
- ❌ Keine UI Tests erforderlich (für MVP)

### 7.2 Test-Szenarien
1. **Szenario 1:** Text eingeben → QR-Code wird angezeigt
2. **Szenario 2:** QR-Code scannen → Text wird korrekt dekodiert
3. **Szenario 3:** Speichern klicken → Datei wird gespeichert
4. **Szenario 4:** Gespeicherte Datei öffnen → QR-Code ist sichtbar

---

## 8. MVP-Erfolgskriterien

Die App gilt als **MVP-fertig**, wenn:

### 8.1 Funktionale Kriterien
- [x] Benutzer kann Text/URL eingeben
- [x] QR-Code wird automatisch generiert
- [x] QR-Code wird angezeigt
- [x] QR-Code ist mit Standard-Scanner lesbar
- [x] Benutzer kann Speicherort via SAF wählen
- [x] Datei wird als PNG gespeichert
- [x] Gespeicherte Datei kann geöffnet werden

### 8.2 Qualitätskriterien
- [x] App stürzt bei korrekter Eingabe nicht ab
- [x] Code ist strukturiert (MVVM)
- [x] README.md vorhanden mit KI-Reflexion
- [x] Keine offensichtlichen Bugs im Happy Path

### 8.3 Nicht erforderlich für MVP
- [ ] Perfektes Design
- [ ] Umfangreiche Fehlerbehandlung
- [ ] Unit/UI Tests
- [ ] Performance-Optimierung
- [ ] Internationalisierung
- [ ] Dark Mode Support

---

## 9. MVP-Zeitplan

### 9.1 Rapid Prototyping Ansatz
- **Phase 1 (60 Min):** Setup, Dependencies, Basis-Architektur
- **Phase 2 (60 Min):** QR-Code-Generierung implementieren
- **Phase 3 (60 Min):** UI erstellen, ViewModel integrieren
- **Phase 4 (60 Min):** SAF-Speicherung implementieren, Testing, Dokumentation

**Gesamt:** ~4 Stunden (wie in Aufgabenstellung)

### 9.2 Priorisierung
1. **Sprint 1:** QR-Code-Generierung funktioniert
2. **Sprint 2:** UI zeigt QR-Code an
3. **Sprint 3:** Speicherung funktioniert
4. **Sprint 4:** Refactoring, Dokumentation

---

## 10. MVP vs. Vollversion

| Feature | MVP | Vollversion |
|---------|-----|-------------|
| QR-Code-Generierung | ✅ | ✅ |
| QR-Code-Anzeige | ✅ | ✅ |
| SAF-Speicherung | ✅ | ✅ |
| Automatische Speicherung | ❌ | ✅ |
| QR-Code-Scanning | ❌ | ✅ |
| Historie | ❌ | ✅ |
| Design-Anpassungen | ❌ | ✅ |
| Batch-Generierung | ❌ | ✅ |
| Cloud-Sync | ❌ | ✅ |
| Unit Tests | ❌ | ✅ |
| UI Tests | ❌ | ✅ |

---

## 11. MVP-Risiken

### 11.1 Technische Risiken
| Risiko | Wahrscheinlichkeit | Auswirkung | MVP-Maßnahme |
|--------|-------------------|------------|--------------|
| ZXing-Bibliothek Probleme | Niedrig | Hoch | Alternative recherchieren |
| SAF-Intent komplex | Mittel | Mittel | Beispiel-Code verwenden |
| ViewModel-Setup | Niedrig | Niedrig | Standard-Pattern |

### 11.2 Zeit-Risiken
- **Risiko:** Feature-Creep (zu viel implementieren)
- **Maßnahme:** Strikte Fokussierung auf MVP-Features
- **Fallback:** Nicht-kritische Features streichen

---

## 12. MVP-Akzeptanz

### 12.1 Definition of Done (MVP)
Die App ist MVP-fertig, wenn:
1. ✅ Alle Must-Have Features implementiert sind
2. ✅ Happy Path funktioniert ohne Abstürze
3. ✅ Code ist kompilierbar und lauffähig
4. ✅ README.md mit Dokumentation vorhanden
5. ✅ Git-Repository mit Commits vorhanden

### 12.2 Nicht erforderlich für MVP
- ❌ Perfekte Code-Qualität (sauber genug)
- ❌ Vollständige Test-Abdeckung
- ❌ Production-ready Performance
- ❌ Umfangreiche Dokumentation

---

## 13. MVP-Nächste Schritte (Post-MVP)

Nach erfolgreichem MVP können folgende Features hinzugefügt werden:

1. **Phase 2:** Automatische Speicherung als Fallback
2. **Phase 3:** QR-Code-Scanning hinzufügen
3. **Phase 4:** Historie/Favoriten
4. **Phase 5:** Design-Verbesserungen
5. **Phase 6:** Unit/UI Tests
6. **Phase 7:** Performance-Optimierung

---

## 14. Zusammenfassung

### MVP = Minimum Viable Product
- **Minimum:** Nur die absolut notwendigen Features
- **Viable:** Funktioniert zuverlässig im Happy Path
- **Product:** Lauffähige, nutzbare App

### MVP-Fokus
1. ✅ QR-Code-Generierung
2. ✅ QR-Code-Anzeige
3. ✅ SAF-Speicherung
4. ✅ Saubere Architektur (MVVM)
5. ✅ Dokumentation

### MVP-Prinzipien
- **Funktioniert > Perfekt**
- **Happy Path > Edge Cases**
- **Kernfunktionalität > Features**
- **Schnell > Vollständig**

---

**MVP-Status:** ✅ Definition abgeschlossen  
**Nächster Schritt:** Implementierung starten
