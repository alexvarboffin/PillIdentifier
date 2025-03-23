import com.android.build.gradle.internal.dsl.SigningConfig
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kspCompose)
    id("com.google.firebase.crashlytics")

}

val bannerAdUnitId = "ca-app-pub-5111357348858303/6430918853"
val applicationIdValue = "ca-app-pub-5111357348858303~7134758027"


android {
    namespace = "com.walhalla.pillfinder"

    signingConfigs {
        create("config_w") {
            keyAlias = "pillidentifier"
            keyPassword = "@!sfuQ123zpc"
            storeFile = file("keystore/keystore.jks")
            storePassword = "@!sfuQ123zpc"
        }
    }

    compileSdk = 35
    buildToolsVersion = "35.0.0"

    val versionPropsFile = file("version.properties")

    if (versionPropsFile.canRead()) {
        val code = versionCodeDate()

        defaultConfig {
            resValue("string", "banner_ad_unit_id", "ca-app-pub-5111357348858303/1099385603")
            resValue("string", "application_id", "ca-app-pub-5111357348858303~7134758027")

            multiDexEnabled = false
            resConfigs(
                "en",
                "es",
                "fr",
                "de",
                "it",
                "pt",
                "el",
                "ru",
                "ja",
                "zh-rCN",
                "zh-rTW",
                "ko",
                "ar",
                "uk",
                "vi",
                "uz",
                "az"
            )

            applicationId = "com.walhalla.pillfindel"
            minSdk = rootProject.extra["minSdkVersion0"].toString().toInt()
            targetSdk = rootProject.extra["targetSdkVersion0"].toString().toInt()
            versionCode = code
            versionName = "7.1.$code.release"
        }
    } else {
        throw GradleException("Could not read version.properties!")
    }

    buildTypes {
        getByName("debug") {
            multiDexEnabled = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("config_w")

            firebaseCrashlytics {
                mappingFileUploadEnabled = false
            }
        }

        getByName("release") {
            multiDexEnabled = true
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            testProguardFiles("test-proguard-rules.pro")
            isDebuggable = false
            isJniDebuggable = false
            signingConfig = signingConfigs.getByName("config_w")

            firebaseCrashlytics {
                mappingFileUploadEnabled = true
            }
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }

    flavorDimensions += "W"

    productFlavors {
        create("low21") {
            dimension = "W"
            applicationId = "com.walhalla.pillfindel"
            setProperty("archivesBaseName", "Pillfindel")
            resValue("string", "application_id", applicationIdValue)
            resValue("string", "banner_ad_unit_id", bannerAdUnitId)
            signingConfig = signingConfigs.getByName("config_w")
            //versionNameSuffix = minSdkVersion.toString()
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources.excludes.addAll(
            listOf(
                "com/itextpdf/io/font/cmap_info.txt",
                "com/itextpdf/io/font/cmap/*",
                "com/itextpdf/text/AGPL.txt",
                "com/itextpdf/text/NOTICE.txt",
                "com/itextpdf/text/LICENSE.txt"
            )
        )
    }
}

tasks.register<Copy>("copyAabToBuildFolder") {
    println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm")
    println("mmmmmmmmmmmmmmmmm ${buildDir}/outputs/bundle/release")
    println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm")
    val outputDirectory = file("C:/build")
    if (!outputDirectory.exists()) {
        outputDirectory.mkdirs()
    }

    from("${buildDir}/outputs/bundle/release") {
        include("*.aab")
    }
    into(outputDirectory)
}

apply(from = "../copyReports.gradle")

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.multidex)

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:${rootProject.extra["okHttpVersion"]}")
    implementation("com.squareup.okhttp3:logging-interceptor:${rootProject.extra["okHttpVersion"]}")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.google.firebase:firebase-ads:${rootProject.extra["gmsAds"]}")
    implementation("com.jakewharton.picasso:picasso2-okhttp3-downloader:1.1.0")
    implementation("androidx.preference:preference:1.2.1")
    implementation(project(":features:ui"))
    implementation(project(":features:wads"))
    implementation(project(":threader"))
    implementation(project(":shared"))

    implementation("com.google.firebase:firebase-firestore:${rootProject.extra["firestore"]}")
    implementation("com.google.firebase:firebase-crashlytics:${rootProject.extra["crashlyticsVersion"]}")
    implementation("com.google.firebase:firebase-analytics:${rootProject.extra["analyticsVersion"]}")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.github.kenglxn.QRGen:android:3.0.1") {
        exclude(group = "com.android.support")
    }
    implementation("androidx.lifecycle:lifecycle-process:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-common-java8:${rootProject.extra["lifecycle_version"]}")
    implementation(libs.itextg)
    implementation("com.mikepenz:fontawesome-typeface:4.6.0.3@aar")
    implementation("com.mikepenz:iconics-core:3.1.0@aar")
    implementation("com.weiwangcn.betterspinner:library:1.1.0")
    implementation("com.patrickpissurno:ripple-effect:1.3.1")
    implementation("com.jsibbold:zoomage:1.3.1")
    implementation(project(":pdf-viewer"))
    implementation(project(":health"))
    implementation("androidx.lifecycle:lifecycle-viewmodel:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.21")
    implementation("com.merhold.extensiblepageindicator:extensiblepageindicator:1.0.1") {
        exclude(group = "com.google.android.gms")
    }
}

fun versionCodeDate(): Int {
    return SimpleDateFormat("yyMMdd").format(Date()).toInt()
}