// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
//    id("org.jetbrains.kotlin.kapt") // ✅ THIS FIXES THE kapt ERROR
    alias(libs.plugins.kotlin.compose) apply false
}