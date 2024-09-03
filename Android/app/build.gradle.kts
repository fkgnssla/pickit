plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt) // Kotlin Kapt for Annotation Processing
    alias(libs.plugins.hilt) // Dagger Hilt for Dependency Injection
}

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
        release {
            isMinifyEnabled = false
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
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation ("com.google.android.exoplayer:exoplayer:2.19.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
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

