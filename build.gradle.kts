//ext {
//    minSdkVersion = 21
//    kotlin_version = "1.8.10"
////api 21+
////    def RETROFIT_VERSION = "2.9.0"
////    okHttpVersion= "3.14.9"//for 2.9.0 retrofit
//
////    //API 19 support {crash if minSdkVersion = 21!!!}
////    RETROFIT_VERSION = "2.7.1"
////    okHttpVersion = "3.12.12"//for 2.7.1 retrofit
//
//    //minimum Java 8+ or Android API 21+.
//    RETROFIT_VERSION = "2.11.0"
//    okHttpVersion = "4.12.0"
//}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.composeCompiler) apply false
}

extra.apply {
    set("minSdkVersion", 21)
    set("kotlin_version", "1.8.10")
    set("okHttpVersion", "4.12.0")


    set("ONESIGNAL_APP_ID", "")
    set("APPSFLYER_DEV_KEY", "")
    set("javaVersion", 17)
}


//task clean(type: Delete) {
//    delete rootProject.buildDir
//}
