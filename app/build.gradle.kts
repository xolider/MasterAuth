plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kapt)
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.runtime)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.icons)
    implementation(libs.runtime.livedata)
    implementation(libs.material)

    implementation(libs.gms.code.scanner)

    implementation(libs.kotlinx.json)
    implementation(libs.coroutines.gms)

    implementation(libs.kotp)
    implementation(libs.security.crypto)
    implementation(libs.room)
    kapt(libs.room.compiler)
    implementation(libs.sqlcipher)
}

kotlin {
    jvmToolchain(21)
}

android {
    namespace = "dev.vicart.masterauth"
    compileSdk = 35
    defaultConfig {
        targetSdk = 35
        minSdk = 29
        applicationId = "dev.vicart.masterauth"
        versionCode = 1
        versionName = "1.0.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}