

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

    compileSdk = libs.versions.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.buildTools.get()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.lottie)
    implementation("com.jsibbold:zoomage:1.3.1")
    implementation("com.patrickpissurno:ripple-effect:1.3.1")
    implementation("com.weiwangcn.betterspinner:library:1.1.0")
    implementation(libs.play.services.ads)
    implementation(libs.androidx.constraintlayout)
    implementation(project(":shared"))
    implementation(project(":pdf-viewer"))
    implementation(project(":features:ui"))
    implementation(project(":features:wads"))
    implementation(project(":threader"))
    implementation(libs.androidx.core.ktx)
}