

plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.walhalla.health"

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

        consumerProguardFiles("consumer-rules.pro")
        resConfigs("en", "ru")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "libs")))

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.lottie)
    implementation(libs.zoomage)
    //implementation(libs.ripple.effect)
    implementation(libs.library)
    implementation(libs.play.services.ads)
    implementation(libs.androidx.constraintlayout)
    implementation(project(":shared"))
    implementation(project(":pdf-viewer"))
    implementation(project(":features:ui"))
    implementation(project(":features:wads"))
    implementation(project(":threader"))
    implementation(libs.androidx.core.ktx)
}