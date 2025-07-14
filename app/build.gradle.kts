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

    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

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
            minSdk = libs.versions.android.minSdk.get().toInt()
            targetSdk = libs.versions.android.targetSdk.get().toInt()
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
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
    implementation(fileTree(mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "libs")))
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


    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.recyclerview)
    implementation(libs.firebase.core)
    implementation(libs.firebase.ads)
    implementation(libs.picasso2.okhttp3.downloader)

    implementation(libs.androidx.preference.ktx)
    implementation(project(":features:ui"))
    implementation(project(":features:wads"))
    //implementation(project(":threader"))
    implementation(project(":shared"))

    implementation(libs.firebase.firestore)
    implementation(libs.google.firebase.crashlytics)

    implementation(libs.google.firebase.analytics)

    implementation(libs.androidx.swiperefreshlayout)
    implementation("com.github.kenglxn.QRGen:android:3.0.1") {
        exclude(group = "com.android.support")
    }
    implementation(libs.androidx.lifecycle.process)

    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)


    implementation(libs.itextg)

    implementation(libs.iconics.core)
    implementation(libs.iconics.views)
    //implementation(libs.iconics.typeface.fontawesome)
    //implementation(libs.materialdrawer)



    implementation(libs.library)
    //implementation(libs.ripple.effect)
    implementation(libs.zoomage)
    implementation(project(":pdf-viewer"))
    implementation(project(":health"))

    implementation(libs.kotlin.stdlib.jdk8)
//    implementation("com.merhold.extensiblepageindicator:extensiblepageindicator:1.0.1") {
//        exclude(group = "com.google.android.gms")
//    }

    implementation(libs.extensible.page.indicator)

}

fun versionCodeDate(): Int {
    return SimpleDateFormat("yyMMdd").format(Date()).toInt()
}