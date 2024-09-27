import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt) // Kotlin Kapt for Annotation Processing
    alias(libs.plugins.hilt) // Dagger Hilt for Dependency Injection
    id("kotlin-parcelize")

}

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))

val kakaoAppKey = localProperties["KAKAO_APP_KEY"] as? String
    ?: error("KAKAO_APP_KEY not found in local.properties")
val baseUrl = localProperties["BASE_URL"] as? String
    ?: error("BASE_URL not found in local.properties")

android {
    namespace = "com.ssafy.pickit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ssafy.pickit"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "KAKAO_APP_KEY", "\"$kakaoAppKey\"")
            manifestPlaceholders["KAKAO_APP_KEY"] = kakaoAppKey
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "KAKAO_APP_KEY", "\"$kakaoAppKey\"")
            manifestPlaceholders["KAKAO_APP_KEY"] = kakaoAppKey
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }

    packagingOptions {
        excludes.add("META-INF/DISCLAIMER")
    }
}

dependencies {
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // Kakao SDK
    implementation(libs.kakao.login)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Gson
    implementation(libs.gson)

    // Retrofit2
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // OkHttp3
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Hilt (Dagger Hilt)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler) // Hilt의 Annotation Processor

    // Glide
    implementation(libs.glide)
    kapt(libs.glide.compiler) // Glide의 Annotation Processor

    // Web3J
    implementation(libs.web3j.core)
    implementation(libs.web3j.contracts)
    implementation(libs.bouncycastle)

    // Jetpack: Activity, KTX, Lifecycle, ViewModel
    implementation(libs.activity.ktx)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)
}

