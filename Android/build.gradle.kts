// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false // Android Application Plugin
    alias(libs.plugins.jetbrains.kotlin.android) apply false // Kotlin Android Plugin
    alias(libs.plugins.kotlin.kapt) apply false // Kotlin Kapt Plugin (Annotation Processing)
    alias(libs.plugins.hilt) apply false // Hilt Plugin for Dependency Injection}
}