plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    api(projects.core.model)
    implementation(projects.core.model)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}
