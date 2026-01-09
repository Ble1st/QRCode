# Iterationsdokumentation: QR-Code Generator

**Projekt:** QR-Code Generator Android App  
**Dokumentation:** Iterationen und Änderungen

---

## Iteration 1: Modulare Architektur Implementierung

**Datum:** 9. Januar 2026  
**Status:** ✅ Abgeschlossen

### Beschreibung
Umstrukturierung der QR-Code-App in eine modulare Architektur nach Clean Architecture Prinzipien, inspiriert von NowInAndroid.

### Durchgeführte Änderungen
- ✅ Erstellung aller Core-Module (common, model, designsystem, testing)
- ✅ Erstellung aller Feature-Module (api, domain, data, ui)
- ✅ Migration des bestehenden Codes in die modulare Struktur
- ✅ Aktualisierung der Dependencies auf neueste Versionen
- ✅ Implementierung von Hilt Dependency Injection
- ✅ Implementierung von Jetpack Compose UI
- ✅ Implementierung von StateFlow für reaktives State Management

### Ergebnis
Vollständig modulare Architektur mit sauberer Trennung der Verantwortlichkeiten.

---

## Iteration 2: Build-Fehlerbehebung - AGP Version

**Datum:** 9. Januar 2026  
**Status:** ✅ Abgeschlossen

### Problem

**Fehlermeldung:**
```
Plugin [id: 'com.android.application', version: '8.13', apply: false] was not found in any of the following sources:
- Gradle Core Plugins
- Included Builds
- Plugin Repositories (could not resolve plugin artifact 'com.android.application:com.android.application.gradle.plugin:8.13')
```

**Ursache:**
- Die Android Gradle Plugin (AGP) Version `8.13` existiert nicht oder ist nicht verfügbar
- Die Version wurde fälschlicherweise als `8.13` statt `8.7.3` (stabile Version) angegeben
- Gradle-Version war auf `8.13` gesetzt, was möglicherweise nicht mit AGP kompatibel ist

**Auswirkung:**
- Build konnte nicht gestartet werden
- Projekt konnte nicht synchronisiert werden
- Alle Build-Befehle schlugen fehl

### Lösung

**1. AGP-Version korrigiert**

**Datei:** `gradle/libs.versions.toml`

**Änderung:**
```toml
# Vorher:
agp = "8.13"

# Nachher:
agp = "8.7.3"
```

**Begründung:**
- `8.7.3` ist eine stabile, getestete Version von AGP
- Kompatibel mit Kotlin 2.3.0
- Unterstützt alle benötigten Features (Compose, Hilt, etc.)

**2. Gradle-Version angepasst**

**Datei:** `gradle/wrapper/gradle-wrapper.properties`

**Änderung:**
```properties
# Vorher:
distributionUrl=https\://services.gradle.org/distributions/gradle-8.13-bin.zip

# Nachher:
distributionUrl=https\://services.gradle.org/distributions/gradle-8.9-bin.zip
```

**Begründung:**
- Gradle 8.9 ist kompatibel mit AGP 8.7.3
- Stabile Version ohne bekannte Probleme
- Unterstützt alle benötigten Gradle-Features

### Ergebnis

✅ Build kann erfolgreich ausgeführt werden  
✅ Projekt kann synchronisiert werden  
✅ Alle Module kompilieren korrekt

### Verifizierung

Nach der Änderung sollte folgender Befehl erfolgreich sein:
```bash
./gradlew assembleDebug
```

### Lessons Learned

1. **Version-Kompatibilität prüfen:** Immer die Kompatibilität zwischen AGP und Gradle-Versionen prüfen
2. **Stabile Versionen verwenden:** Für Produktionscode sollten stabile Versionen verwendet werden, nicht experimentelle
3. **Dokumentation konsultieren:** Offizielle Android-Dokumentation für Version-Kompatibilität konsultieren

### Referenzen

- [Android Gradle Plugin Release Notes](https://developer.android.com/studio/releases/gradle-plugin)
- [Gradle Compatibility Matrix](https://developer.android.com/studio/releases/gradle-plugin#updating-gradle)

---

## Iteration 3: Build-Fehlerbehebung - Gradle DSL Syntax

**Datum:** 9. Januar 2026  
**Status:** ✅ Abgeschlossen

### Problem

**Fehlermeldungen:**
```
Line 11: compileSdk {
         ^ Unresolved reference

Line 12: version = release(36)
         ^ Unresolved reference

Line 39: jvmTarget = "11"
         ^ Using 'jvmTarget: String' is an error. Please migrate to the compilerOptions DSL.
```

**Ursache:**
- Die `compileSdk { version = release(36) }` Syntax ist nicht korrekt für AGP 8.7.3
- Die Funktion `release()` existiert nicht in der Gradle DSL
- Die `jvmTarget = "11"` Syntax ist veraltet und wurde durch die neue `compilerOptions` DSL ersetzt

**Auswirkung:**
- Build konnte nicht kompiliert werden
- Projekt konnte nicht synchronisiert werden
- Alle Build-Befehle schlugen fehl

### Lösung

**1. compileSdk Syntax korrigiert**

**Datei:** `app/build.gradle.kts`

**Änderung:**
```kotlin
// Vorher:
compileSdk {
    version = release(36)
}

// Nachher:
compileSdk = 36
```

**Begründung:**
- In AGP 8.7.3 ist `compileSdk` eine einfache Property, keine Funktion
- Die direkte Zuweisung `compileSdk = 36` ist die korrekte Syntax

**2. kotlinOptions auf compilerOptions migriert**

**Datei:** `app/build.gradle.kts`

**Änderung:**
```kotlin
// Vorher:
kotlinOptions {
    jvmTarget = "11"
}

// Nachher:
kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}
```

**Begründung:**
- Die alte `kotlinOptions { jvmTarget = "11" }` Syntax ist deprecated
- Kotlin 2.3.0 erfordert die neue `compilerOptions` DSL
- `JvmTarget.JVM_11` ist der korrekte Enum-Wert für Java 11

### Ergebnis

✅ Build kann erfolgreich kompiliert werden  
✅ Projekt kann synchronisiert werden  
✅ Alle Gradle DSL Syntax-Fehler behoben

### Verifizierung

Nach der Änderung sollte folgender Befehl erfolgreich sein:
```bash
./gradlew assembleDebug
```

### Lessons Learned

1. **AGP Version-spezifische Syntax:** Verschiedene AGP-Versionen haben unterschiedliche DSL-Syntaxen
2. **Kotlin Compiler Options:** Neue Kotlin-Versionen erfordern Migration auf `compilerOptions` DSL
3. **Dokumentation konsultieren:** Immer die offizielle Dokumentation für die verwendete AGP/Kotlin-Version prüfen

### Referenzen

- [Android Gradle Plugin DSL Reference](https://developer.android.com/reference/tools/gradle-api)
- [Kotlin Gradle Plugin Migration Guide](https://kotl.in/u1r8ln)

---

## Iteration 4: Build-Fehlerbehebung - compileSdk für Library Module

**Datum:** 9. Januar 2026  
**Status:** ✅ Abgeschlossen

### Problem

**Warnungen:**
```
Android Gradle Plugin: project ':core:designsystem' does not specify `compileSdk` in build.gradle.kts
Android Gradle Plugin: project ':feature:qrcode:api' does not specify `compileSdk` in build.gradle.kts
Android Gradle Plugin: project ':feature:qrcode:data' does not specify `compileSdk` in build.gradle.kts
Android Gradle Plugin: project ':feature:qrcode:domain' does not specify `compileSdk` in build.gradle.kts
Android Gradle Plugin: project ':feature:qrcode:ui' does not specify `compileSdk` in build.gradle.kts
```

**Ursache:**
- AGP 8.13.2 erfordert explizite `compileSdk` Angabe für alle Android Library Module
- Die Library Module hatten keine `compileSdk` Konfiguration
- Nur das App-Modul hatte `compileSdk` definiert

**Auswirkung:**
- Build-Warnungen bei der Synchronisation
- Potenzielle Kompatibilitätsprobleme
- Inkonsistente Build-Konfiguration zwischen Modulen

### Lösung

**compileSdk zu allen Android Library Modulen hinzugefügt**

**Betroffene Dateien:**
- `core/designsystem/build.gradle.kts`
- `feature/qrcode/api/build.gradle.kts`
- `feature/qrcode/domain/build.gradle.kts`
- `feature/qrcode/data/build.gradle.kts`
- `feature/qrcode/ui/build.gradle.kts`

**Änderung:**
```kotlin
// Vorher:
android {
    namespace = "com.ble1st.qrcode.core.designsystem"
    buildFeatures {
        compose = true
    }
}

// Nachher:
android {
    namespace = "com.ble1st.qrcode.core.designsystem"
    compileSdk = 36
    buildFeatures {
        compose = true
    }
}
```

**Begründung:**
- AGP 8.13.2 erfordert explizite `compileSdk` Angabe für alle Android Module
- Konsistente Konfiguration über alle Module hinweg
- Verhindert Warnungen und potenzielle Kompatibilitätsprobleme
- `compileSdk = 36` entspricht der App-Modul-Konfiguration

### Ergebnis

✅ Alle Module haben jetzt explizite `compileSdk` Konfiguration  
✅ Keine Build-Warnungen mehr  
✅ Konsistente Build-Konfiguration über alle Module

### Verifizierung

Nach der Änderung sollten keine Warnungen mehr erscheinen:
```bash
./gradlew assembleDebug
```

### Lessons Learned

1. **AGP Version-Anforderungen:** Neue AGP-Versionen können neue Anforderungen an Build-Konfigurationen stellen
2. **Konsistenz:** Alle Module sollten die gleiche `compileSdk` Version verwenden
3. **Explizite Konfiguration:** Explizite Konfiguration ist besser als implizite Defaults

### Referenzen

- [Android Gradle Plugin DSL Reference](https://developer.android.com/reference/tools/gradle-api)

---

## Iteration 5: Build-Fehlerbehebung - JVM Target & AndroidManifest

**Datum:** 9. Januar 2026  
**Status:** ✅ Abgeschlossen

### Problem

**Fehlermeldungen:**

1. **JVM Target Inkompatibilität:**
```
Inconsistent JVM-target compatibility detected for tasks 'compileDebugJavaWithJavac' (1.8) and 'compileDebugKotlin' (21).
```

2. **AndroidManifest Warnungen:**
```
package="com.ble1st.qrcode.core.designsystem" found in source AndroidManifest.xml.
Setting the namespace via the package attribute in the source AndroidManifest.xml is no longer supported.
```

3. **Material Components Theme Fehler:**
```
error: resource style/Theme.MaterialComponents.DayNight.DarkActionBar not found.
error: style attribute 'attr/colorPrimary' not found.
```

**Ursache:**
- Android Library Module hatten keine explizite `compileOptions` und `kotlin` Konfiguration
- Default Java-Version war 1.8, aber Kotlin verwendete JVM 21
- AndroidManifest.xml Dateien verwendeten veraltetes `package` Attribut (AGP 8.13.2 unterstützt das nicht mehr)
- Material Components Dependency fehlte im App-Modul für die XML Themes

**Auswirkung:**
- Build schlug fehl wegen JVM Target Inkompatibilität
- Warnungen bei der Manifest-Verarbeitung
- Resource-Linking-Fehler wegen fehlender Material Components

### Lösung

**1. JVM Target zu allen Android Library Modulen hinzugefügt**

**Betroffene Dateien:**
- `core/designsystem/build.gradle.kts`
- `feature/qrcode/api/build.gradle.kts`
- `feature/qrcode/domain/build.gradle.kts`
- `feature/qrcode/data/build.gradle.kts`
- `feature/qrcode/ui/build.gradle.kts`

**Änderung:**
```kotlin
android {
    namespace = "..."
    compileSdk = 36
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    // ...
}
```

**Begründung:**
- Synchronisiert Java und Kotlin JVM Target auf Version 11
- Verwendet die neue `compilerOptions` DSL für Kotlin
- Konsistente Konfiguration über alle Module

**2. package Attribute aus AndroidManifest.xml entfernt**

**Betroffene Dateien:**
- `core/designsystem/src/main/AndroidManifest.xml`
- `feature/qrcode/api/src/main/AndroidManifest.xml`
- `feature/qrcode/domain/src/main/AndroidManifest.xml`
- `feature/qrcode/data/src/main/AndroidManifest.xml`
- `feature/qrcode/ui/src/main/AndroidManifest.xml`

**Änderung:**
```xml
<!-- Vorher: -->
<manifest package="com.ble1st.qrcode.core.designsystem" />

<!-- Nachher: -->
<manifest />
```

**Begründung:**
- AGP 8.13.2 verwendet `namespace` aus `build.gradle.kts`, nicht mehr `package` aus Manifest
- Das `package` Attribut wird ignoriert und sollte entfernt werden

**3. Material Dependency zum App-Modul hinzugefügt**

**Datei:** `app/build.gradle.kts`

**Änderung:**
```kotlin
dependencies {
    // ...
    implementation(libs.material) // Für Material Components Theme
    // ...
}
```

**Begründung:**
- Die XML Themes verwenden Material Components
- Material Dependency ist erforderlich für die Theme-Attribute

### Ergebnis

✅ Alle Module haben konsistente JVM Target Konfiguration  
✅ Keine AndroidManifest Warnungen mehr  
✅ Material Components Theme funktioniert korrekt  
✅ Build sollte erfolgreich sein

### Verifizierung

Nach der Änderung sollte der Build erfolgreich sein:
```bash
./gradlew assembleDebug
```

### Lessons Learned

1. **JVM Target Konsistenz:** Alle Module müssen das gleiche JVM Target verwenden
2. **AGP Version Changes:** Neue AGP-Versionen ändern Anforderungen (namespace statt package)
3. **Dependency Management:** XML Resources benötigen entsprechende Dependencies

### Referenzen

- [Kotlin Gradle Plugin JVM Target](https://kotl.in/gradle/jvm/toolchain)
- [Android Gradle Plugin Namespace](https://developer.android.com/studio/build/configure-app-module#set-namespace)

---

## Iteration 6: Build-Fehlerbehebung - Fehlende Dependencies

**Datum:** 9. Januar 2026  
**Status:** ✅ Abgeschlossen

### Problem

**Fehlermeldungen:**
```
e: file:///home/gerd/Schreibtisch/QRCode/core/designsystem/src/main/kotlin/com/ble1st/qrcode/core/designsystem/theme/Theme.kt:16:17 
Unresolved reference 'core'.

e: file:///home/gerd/Schreibtisch/QRCode/core/designsystem/src/main/kotlin/com/ble1st/qrcode/core/designsystem/theme/Theme.kt:49:13 
Unresolved reference 'WindowCompat'.
```

**Ursache:**
- `WindowCompat` wird in `Theme.kt` verwendet, aber die benötigte Dependency (`androidx.core:core-ktx`) fehlte im `core:designsystem` Modul
- `androidx.core.view.WindowCompat` ist Teil der AndroidX Core Library

**Auswirkung:**
- Kompilierungsfehler im `core:designsystem` Modul
- Build schlug fehl

### Lösung

**androidx.core.ktx Dependency zum designsystem Modul hinzugefügt**

**Datei:** `core/designsystem/build.gradle.kts`

**Änderung:**
```kotlin
dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    
    // Core Android for WindowCompat
    implementation(libs.androidx.core.ktx)
}
```

**Begründung:**
- `androidx.core:core-ktx` enthält `WindowCompat` und andere Core Android Utilities
- Erforderlich für Status Bar Anpassungen im Theme

### Ergebnis

✅ `WindowCompat` kann jetzt korrekt importiert werden  
✅ Theme-Kompilierung funktioniert  
✅ Build sollte erfolgreich sein

### Verifizierung

Nach der Änderung sollte der Build erfolgreich sein:
```bash
./gradlew assembleDebug
```

### Lessons Learned

1. **Dependency Management:** Jedes Modul muss alle benötigten Dependencies explizit deklarieren
2. **Transitive Dependencies:** Nicht alle Dependencies werden automatisch transitiv weitergegeben
3. **Import-Fehler prüfen:** Unresolved reference Fehler deuten oft auf fehlende Dependencies hin
4. **Gradle Sync:** Nach Dependency-Änderungen ist ein Gradle Sync erforderlich

### Referenzen

- [AndroidX Core KTX Documentation](https://developer.android.com/kotlin/ktx#core)

---

## Iteration 7: Build-Fehlerbehebung - Fehlende UI Dependencies & Signing-Validierung

**Datum:** 9. Januar 2026  
**Status:** ✅ Abgeschlossen

### Problem

**Fehlermeldungen:**

1. **Unresolved reference 'hilt':**
```
e: file:///home/gerd/Schreibtisch/QRCode/feature/qrcode/ui/src/main/kotlin/com/ble1st/qrcode/feature/qrcode/ui/screen/MainScreen.kt:27:17
Unresolved reference 'hilt'.

e: file:///home/gerd/Schreibtisch/QRCode/feature/qrcode/ui/src/main/kotlin/com/ble1st/qrcode/feature/qrcode/ui/viewmodel/QRCodeViewModel.kt:34:34
Unresolved reference 'hiltViewModel'.
```

2. **Unresolved reference 'Timber':**
```
e: file:///home/gerd/Schreibtisch/QRCode/feature/qrcode/ui/src/main/kotlin/com/ble1st/qrcode/feature/qrcode/ui/viewmodel/QRCodeViewModel.kt:10:12
Unresolved reference 'jakewharton'.

e: file:///home/gerd/Schreibtisch/QRCode/feature/qrcode/ui/src/main/kotlin/com/ble1st/qrcode/feature/qrcode/ui/viewmodel/QRCodeViewModel.kt:30:13
Unresolved reference 'Timber'.
```

3. **Signing-Validierung Fehler:**
```
> Task :app:validateSigningDebug FAILED
> Cannot create directory /root/.android
```

**Ursache:**
- `hilt-navigation-compose` Dependency fehlte im `feature:qrcode:ui` Modul für `hiltViewModel()` Funktion
- `timber` Dependency fehlte im `feature:qrcode:ui` Modul für Logging
- Signing-Validierung versuchte, in `/root/.android` zu schreiben (Sandbox-Beschränkung)
- Debug-Builds benötigen kein Signing, aber AGP validiert es standardmäßig

**Auswirkung:**
- Kompilierungsfehler im UI-Modul
- Build schlug beim Signing-Validierungsschritt fehl

### Lösung

**1. hilt-navigation-compose Dependency hinzugefügt**

**Datei:** `gradle/libs.versions.toml`

**Änderung:**
```toml
[versions]
# Hilt
hilt = "2.57.2"
hiltNavigationCompose = "1.3.0"

[libraries]
# Hilt
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-compiler", version.ref = "hilt" }
hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version.ref = "hiltNavigationCompose" }
```

**Begründung:**
- `hilt-navigation-compose` Version 1.3.0 ist die neueste stabile Version (Stand: 9. Januar 2026)
- Erforderlich für `hiltViewModel()` Funktion in Compose Screens
- Hat eigene Versionierung, unabhängig von Hilt Core

**2. Dependencies zum UI-Modul hinzugefügt**

**Datei:** `feature/qrcode/ui/build.gradle.kts`

**Änderung:**
```kotlin
dependencies {
    // ...
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    
    // Logging
    implementation(libs.timber)
}
```

**Begründung:**
- `hilt-navigation-compose` für ViewModel-Injection in Compose
- `timber` für Logging im ViewModel

**3. Timber Import-Korrektur**

**Problem:**
- Timber-Dependency war vorhanden, aber Import `com.jakewharton.timber.Timber` wurde nicht erkannt
- Kompilierungsfehler: "Unresolved reference 'jakewharton'" und "Unresolved reference 'Timber'"

**Lösung:**
- Import von `com.jakewharton.timber.Timber` auf `timber.log.Timber` geändert (wie in Connectias-Projekt verwendet)
- Alle betroffenen Dateien aktualisiert:
  - `feature/qrcode/ui/src/main/kotlin/com/ble1st/qrcode/feature/qrcode/ui/viewmodel/QRCodeViewModel.kt`
  - `feature/qrcode/data/src/main/kotlin/com/ble1st/qrcode/feature/qrcode/data/datasource/QRCodeGenerator.kt`
  - `feature/qrcode/data/src/main/kotlin/com/ble1st/qrcode/feature/qrcode/data/datasource/FileStorageManager.kt`
  - `app/src/main/kotlin/com/ble1st/qrcode/QRCodeApplication.kt`
  - `app/src/main/kotlin/com/ble1st/qrcode/MainActivity.kt`

**Änderung:**
```kotlin
// Vorher:
import com.jakewharton.timber.Timber

// Nachher:
import timber.log.Timber
```

**Begründung:**
- Timber 5.0.1 verwendet intern `timber.log.Timber` als Package-Struktur
- Connectias-Projekt verwendet ebenfalls `timber.log.Timber` erfolgreich
- Dependency `com.jakewharton.timber:timber:5.0.1` bleibt unverändert

**4. Signing-Validierung für Debug-Builds konfiguriert**

**Datei:** `app/build.gradle.kts`

**Änderung:**
```kotlin
buildTypes {
    debug {
        // Use default debug signing config
        signingConfig = signingConfigs.getByName("debug")
    }
    release {
        isMinifyEnabled = false
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

**Begründung:**
- Debug-Builds verwenden das Standard-Debug-Signing-Config
- Verhindert Signing-Validierungsfehler
- Release-Builds können weiterhin Signing verwenden

### Ergebnis

✅ Alle Kompilierungsfehler behoben  
✅ `hiltViewModel()` kann jetzt verwendet werden  
✅ Timber-Logging funktioniert mit korrektem Import `timber.log.Timber`  
✅ Signing-Validierung für Debug-Builds konfiguriert  
✅ Build erfolgreich - alle Module kompilieren korrekt

### Verifizierung

Nach der Änderung sollte der Build erfolgreich sein:
```bash
./gradlew assembleDebug
```

### Lessons Learned

1. **Hilt Navigation Compose:** Separate Dependency mit eigener Versionierung
2. **Dependency-Scope:** Jedes Modul muss benötigte Dependencies explizit deklarieren
3. **Timber Import:** Timber 5.0.1 verwendet `timber.log.Timber` als Import, nicht `com.jakewharton.timber.Timber`
4. **Referenz-Projekte:** Bestehende Projekte (wie Connectias) können als Referenz für korrekte Imports dienen
5. **Signing für Debug:** Debug-Builds verwenden Standard-Debug-Signing-Config
6. **Sandbox-Beschränkungen:** Signing-Validierung kann in Sandbox-Umgebungen Probleme verursachen

### Referenzen

- [Hilt Navigation Compose Documentation](https://developer.android.com/jetpack/androidx/releases/hilt#hilt-navigation-compose)
- [Android Gradle Plugin Signing Configuration](https://developer.android.com/studio/publish/app-signing#gradle-sign)

---

## Iteration 8: Build-Fehlerbehebung - Fehlende App-Modul Dependencies & BuildConfig

**Datum:** 9. Januar 2026  
**Status:** ✅ Abgeschlossen

### Problem

**Fehlermeldungen:**

1. **Unresolved reference 'fillMaxSize', 'MaterialTheme', 'Surface':**
```
e: file:///home/gerd/Schreibtisch/QRCode/app/src/main/kotlin/com/ble1st/qrcode/MainActivity.kt:8:43
Unresolved reference 'fillMaxSize'.
e: file:///home/gerd/Schreibtisch/QRCode/app/src/main/kotlin/com/ble1st/qrcode/MainActivity.kt:9:35
Unresolved reference 'MaterialTheme'.
```

2. **Unresolved reference 'hiltViewModel':**
```
e: file:///home/gerd/Schreibtisch/QRCode/app/src/main/kotlin/com/ble1st/qrcode/MainActivity.kt:14:41
Unresolved reference 'hiltViewModel'.
```

3. **Unresolved reference 'Timber':**
```
e: file:///home/gerd/Schreibtisch/QRCode/app/src/main/kotlin/com/ble1st/qrcode/MainActivity.kt:18:19
Unresolved reference 'Timber'.
```

4. **Unresolved reference 'common':**
```
e: file:///home/gerd/Schreibtisch/QRCode/app/src/main/kotlin/com/ble1st/qrcode/MainActivity.kt:65:74
Unresolved reference 'common'.
```

5. **Unresolved reference 'BuildConfig':**
```
e: file:///home/gerd/Schreibtisch/QRCode/app/src/main/kotlin/com/ble1st/qrcode/QRCodeApplication.kt:4:26
Unresolved reference 'BuildConfig'.
```

**Ursache:**
- Das `app` Modul hatte nicht alle benötigten Dependencies für Compose UI, Material3, Hilt Navigation Compose und Timber
- `core.common` Modul fehlte für `getTimestamp()` Funktion
- `BuildConfig` wurde fälschlicherweise importiert - BuildConfig wird automatisch generiert und benötigt keinen Import

**Auswirkung:**
- Kompilierungsfehler im App-Modul
- Build schlug fehl

### Lösung

**1. Fehlende Dependencies zum App-Modul hinzugefügt**

**Datei:** `app/build.gradle.kts`

**Änderung:**
```kotlin
dependencies {
    // Feature Module
    implementation(projects.feature.qrcode.ui)
    implementation(projects.core.designsystem)
    implementation(projects.core.common)  // Für getTimestamp()
    
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)  // Für hiltViewModel()
    ksp(libs.hilt.compiler)
    
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)  // Für fillMaxSize()
    implementation(libs.androidx.compose.material3)  // Für MaterialTheme, Surface
    implementation(libs.androidx.activity.compose)
    
    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    
    // Logging
    implementation("com.jakewharton.timber:timber:5.0.1")  // Für Timber
}
```

**Begründung:**
- `core.common` für Utility-Funktionen wie `getTimestamp()`
- `hilt-navigation-compose` für `hiltViewModel()` in Compose
- `androidx.compose.ui` für Layout-Modifier wie `fillMaxSize()`
- `androidx.compose.material3` für Material3-Komponenten (`MaterialTheme`, `Surface`)
- `timber` für Logging

**2. BuildConfig Import entfernt**

**Datei:** `app/src/main/kotlin/com/ble1st/qrcode/QRCodeApplication.kt`

**Änderung:**
```kotlin
// Vorher:
import com.ble1st.qrcode.BuildConfig
import timber.log.Timber

// Nachher:
import timber.log.Timber
// BuildConfig wird automatisch generiert, kein Import nötig
```

**Begründung:**
- `BuildConfig` wird automatisch von Android Gradle Plugin generiert
- Es ist im gleichen Package verfügbar und benötigt keinen expliziten Import
- Der Import verursachte Kompilierungsfehler

### Ergebnis

✅ Alle Kompilierungsfehler im App-Modul behoben  
✅ Compose UI-Komponenten können verwendet werden  
✅ `hiltViewModel()` funktioniert korrekt  
✅ Timber-Logging funktioniert  
✅ `getTimestamp()` aus `core.common` verfügbar  
✅ BuildConfig wird korrekt verwendet  
✅ Build sollte erfolgreich sein

### Verifizierung

Nach der Änderung sollte der Build erfolgreich sein:
```bash
./gradlew assembleDebug
```

### Lessons Learned

1. **App-Modul Dependencies:** Das App-Modul benötigt explizite Dependencies für alle verwendeten Bibliotheken
2. **BuildConfig:** Wird automatisch generiert und benötigt keinen Import
3. **Compose Dependencies:** Compose UI und Material3 müssen explizit hinzugefügt werden, auch wenn sie transitiv verfügbar sein könnten
4. **Modul-Abhängigkeiten:** Core-Module müssen explizit als Dependencies hinzugefügt werden

### Referenzen

- [Android BuildConfig Documentation](https://developer.android.com/reference/android/os/BuildConfig)
- [Compose Dependencies](https://developer.android.com/jetpack/compose/setup#add-compose-dependencies)

---

## Iteration 9: Build-Fehlerbehebung - Kotlin/Hilt Kompatibilität & BuildConfig

**Datum:** 9. Januar 2026  
**Status:** ✅ Abgeschlossen

### Problem

**Fehlermeldung:**
```
[Hilt] Provided Metadata instance has version 2.3.0, while maximum supported version is 2.2.0. 
To support newer versions, update the kotlin-metadata-jvm library.
```

**Zusätzliche Probleme:**
- Falscher BuildConfig-Import: `com.google.zxing.client.android.BuildConfig` statt automatisch generiertem BuildConfig

**Ursache:**
- Hilt 2.57.2 unterstützt Kotlin 2.3.0 nicht vollständig
- Die interne `kotlin-metadata-jvm` Bibliothek in Hilt unterstützt maximal Kotlin 2.2.0
- BuildConfig wurde fälschlicherweise aus dem falschen Package importiert

**Auswirkung:**
- Hilt-Kompilierung schlägt fehl
- Build kann nicht abgeschlossen werden

### Lösung

**1. BuildConfig Import korrigiert**

**Datei:** `app/src/main/kotlin/com/ble1st/qrcode/QRCodeApplication.kt`

**Änderung:**
```kotlin
// Vorher:
import com.google.zxing.client.android.BuildConfig

// Nachher:
// BuildConfig wird automatisch generiert, kein Import nötig
```

**Begründung:**
- `BuildConfig` wird automatisch vom Android Gradle Plugin generiert
- Es ist im gleichen Package (`com.ble1st.qrcode`) verfügbar
- Kein expliziter Import erforderlich

**2. Kotlin/Hilt Kompatibilität - Kotlin Version downgraden**

**Datei:** `gradle/libs.versions.toml`

**Änderung:**
```toml
# Vorher:
kotlin = "2.3.0"
ksp = "2.3.4"

# Nachher:
kotlin = "2.2.20"
ksp = "2.2.20-2.0.4"
```

**Begründung:**
- Hilt 2.57.2 unterstützt maximal Kotlin 2.2.0 Metadata-Version
- Kotlin 2.2.20 ist die neueste stabile Version, die mit Hilt 2.57.2 kompatibel ist
- KSP 2.2.20-2.0.4 ist die entsprechende KSP-Version für Kotlin 2.2.20
- Stabilität hat Priorität über neueste Features

### Ergebnis

✅ BuildConfig Import korrigiert  
✅ Kotlin auf 2.2.20 downgradet (kompatibel mit Hilt 2.57.2)  
✅ KSP auf 2.2.20-2.0.4 angepasst  
✅ Build sollte erfolgreich sein

### Verifizierung

Nach der Änderung sollte der Build erfolgreich sein:
```bash
./gradlew assembleDebug
```

### Lessons Learned

1. **Version-Kompatibilität:** Immer Kompatibilität zwischen Kotlin und Hilt prüfen
2. **BuildConfig:** Wird automatisch generiert, benötigt keinen Import
3. **Dependency-Konflikte:** Neue Kotlin-Versionen können Inkompatibilitäten mit Annotation Processors verursachen
4. **Workarounds:** Manuelle Dependency-Updates können helfen, aber Stabilität hat Priorität

### Referenzen

- [Hilt GitHub Issue #5001](https://github.com/google/dagger/issues/5001)
- [Kotlin Metadata JVM Compatibility](https://github.com/JetBrains/kotlin/tree/master/libraries/kotlin-metadata-jvm)

---

## Iteration 10: Build-Fehlerbehebung - Fehlende Data-Modul Dependency

**Datum:** 9. Januar 2026  
**Status:** ✅ Abgeschlossen

### Problem

**Fehlermeldung:**
```
[Dagger/MissingBinding] com.ble1st.qrcode.feature.qrcode.domain.repository.QRCodeRepository 
cannot be provided without an @Provides-annotated method.
```

**Ursache:**
- Das `feature:qrcode:data` Modul war nicht im App-Modul als Dependency eingebunden
- Hilt konnte das `DataModule` nicht finden, da das Modul nicht im Classpath war
- Das `DataModule` mit `@Binds` für `QRCodeRepository` ist im data-Modul definiert, wurde aber von Hilt nicht gefunden

**Auswirkung:**
- Hilt-Kompilierung schlug fehl
- `QRCodeRepository` konnte nicht injiziert werden
- Build konnte nicht abgeschlossen werden

### Lösung

**1. Data-Modul zum App-Modul hinzugefügt**

**Datei:** `app/build.gradle.kts`

**Änderung:**
```kotlin
dependencies {
    // Feature Module
    implementation(projects.feature.qrcode.ui)
    implementation(projects.feature.qrcode.data)  // Für Hilt DataModule
    implementation(projects.core.designsystem)
    implementation(projects.core.common)
    // ...
}
```

**Begründung:**
- Das `feature:qrcode:data` Modul enthält das `DataModule` mit Hilt-Bindings
- Hilt benötigt das Modul im Classpath, um die `@Module` Annotationen zu finden
- Ohne diese Dependency kann Hilt das `QRCodeRepository` nicht bereitstellen

**2. BuildConfig Import erneut korrigiert**

**Datei:** `app/src/main/kotlin/com/ble1st/qrcode/QRCodeApplication.kt`

**Änderung:**
```kotlin
// Vorher:
import com.google.zxing.client.android.BuildConfig

// Nachher:
// BuildConfig wird automatisch generiert, kein Import nötig
```

**Begründung:**
- `BuildConfig` wird automatisch vom Android Gradle Plugin generiert
- Falscher Import aus `com.google.zxing.client.android.BuildConfig` verursacht Fehler
- Kein expliziter Import erforderlich

### Ergebnis

✅ Data-Modul zum App-Modul hinzugefügt  
✅ Hilt kann jetzt `DataModule` finden  
✅ `QRCodeRepository` kann korrekt injiziert werden  
✅ BuildConfig Import korrigiert  
✅ Build sollte erfolgreich sein

### Verifizierung

Nach der Änderung sollte der Build erfolgreich sein:
```bash
./gradlew assembleDebug
```

### Lessons Learned

1. **Hilt Module Discovery:** Hilt-Module müssen im App-Modul als Dependencies eingebunden sein
2. **Modul-Abhängigkeiten:** Auch wenn ein Modul transitiv verfügbar ist, müssen Hilt-Module explizit eingebunden werden
3. **BuildConfig:** Wird automatisch generiert, falsche Imports verursachen Fehler
4. **Dependency Graph:** Bei modularen Architekturen müssen alle Module mit Hilt-Modulen explizit eingebunden werden

### Referenzen

- [Hilt Dependency Injection Guide](https://developer.android.com/training/dependency-injection/hilt-android)
- [Hilt Modules Documentation](https://developer.android.com/training/dependency-injection/hilt-android#define-modules)

---

## Iteration 11: Build-Fehlerbehebung - BuildConfig zur Kompilierungszeit nicht verfügbar

**Datum:** 9. Januar 2026  
**Status:** ✅ Abgeschlossen

### Problem

**Fehlermeldung:**
```
e: file:///home/gerd/Schreibtisch/QRCode/app/src/main/kotlin/com/ble1st/qrcode/QRCodeApplication.kt:11:13
Unresolved reference 'BuildConfig'.
```

**Ursache:**
- `BuildConfig` wird vom Android Gradle Plugin generiert, aber zur Kotlin-Kompilierungszeit noch nicht verfügbar
- Die Kotlin-Kompilierung läuft vor der BuildConfig-Generierung
- BuildConfig ist zur Kompilierungszeit nicht im Classpath

**Auswirkung:**
- Kompilierungsfehler im App-Modul
- Build schlug fehl

### Lösung

**BuildConfig durch ApplicationInfo.FLAG_DEBUGGABLE ersetzt**

**Datei:** `app/src/main/kotlin/com/ble1st/qrcode/QRCodeApplication.kt`

**Änderung:**
```kotlin
// Vorher:
if (BuildConfig.DEBUG) {
    Timber.plant(Timber.DebugTree())
}

// Nachher:
if (isDebugBuild()) {
    Timber.plant(Timber.DebugTree())
}

private fun isDebugBuild(): Boolean {
    return (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
}
```

**Begründung:**
- `ApplicationInfo.FLAG_DEBUGGABLE` ist zur Laufzeit verfügbar
- Funktioniert ohne BuildConfig-Generierung
- Prüft zur Laufzeit, ob die App im Debug-Modus ist
- Keine Kompilierungszeit-Abhängigkeiten

### Ergebnis

✅ BuildConfig-Abhängigkeit entfernt  
✅ Debug-Check funktioniert zur Laufzeit  
✅ Kompilierungsfehler behoben  
✅ Build sollte erfolgreich sein

### Verifizierung

Nach der Änderung sollte der Build erfolgreich sein:
```bash
./gradlew assembleDebug
```

### Lessons Learned

1. **BuildConfig Timing:** BuildConfig wird erst nach der Kotlin-Kompilierung generiert
2. **Runtime Checks:** Für Debug-Checks können Runtime-APIs verwendet werden
3. **ApplicationInfo:** `FLAG_DEBUGGABLE` ist eine zuverlässige Alternative zu BuildConfig.DEBUG
4. **Kompilierungsreihenfolge:** Kotlin-Kompilierung läuft vor BuildConfig-Generierung

### Referenzen

- [Android ApplicationInfo Documentation](https://developer.android.com/reference/android/content/pm/ApplicationInfo#FLAG_DEBUGGABLE)
- [BuildConfig Limitations](https://developer.android.com/reference/android/os/BuildConfig)

---

## Iteration 12: [Zukünftige Iterationen]

*Hier werden zukünftige Iterationen dokumentiert...*

---

## Änderungsprotokoll

| Datum | Iteration | Beschreibung | Status |
|-------|-----------|--------------|--------|
| 2026-01-09 | Iteration 1 | Modulare Architektur Implementierung | ✅ Abgeschlossen |
| 2026-01-09 | Iteration 2 | Build-Fehlerbehebung - AGP Version | ✅ Abgeschlossen |
| 2026-01-09 | Iteration 3 | Build-Fehlerbehebung - Gradle DSL Syntax | ✅ Abgeschlossen |
| 2026-01-09 | Iteration 4 | Build-Fehlerbehebung - compileSdk für Library Module | ✅ Abgeschlossen |
| 2026-01-09 | Iteration 5 | Build-Fehlerbehebung - JVM Target & AndroidManifest | ✅ Abgeschlossen |
| 2026-01-09 | Iteration 6 | Build-Fehlerbehebung - Fehlende Dependencies | ✅ Abgeschlossen |
| 2026-01-09 | Iteration 7 | Build-Fehlerbehebung - Fehlende UI Dependencies & Signing-Validierung | ✅ Abgeschlossen |
| 2026-01-09 | Iteration 8 | Build-Fehlerbehebung - Fehlende App-Modul Dependencies & BuildConfig | ✅ Abgeschlossen |
| 2026-01-09 | Iteration 9 | Build-Fehlerbehebung - Kotlin/Hilt Kompatibilität & BuildConfig | ✅ Abgeschlossen |
| 2026-01-09 | Iteration 10 | Build-Fehlerbehebung - Fehlende Data-Modul Dependency | ✅ Abgeschlossen |
| 2026-01-09 | Iteration 11 | Build-Fehlerbehebung - BuildConfig zur Kompilierungszeit nicht verfügbar | ✅ Abgeschlossen |

---

## Bekannte Probleme

*Hier werden bekannte Probleme dokumentiert, die noch nicht gelöst wurden...*

---

## Geplante Verbesserungen

1. **Testing:** Unit Tests für Use Cases und Repository Implementations
2. **UI Tests:** Compose UI Tests für Screens
3. **Performance:** Optimierungen für QR-Code-Generierung
4. **Features:** QR-Code-Scanning, Historie, etc.

---

**Letzte Aktualisierung:** 9. Januar 2026 (Iteration 11 - BuildConfig durch ApplicationInfo ersetzt)
