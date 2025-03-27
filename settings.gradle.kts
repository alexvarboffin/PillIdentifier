pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://maven.scijava.org/content/repositories/public/")
        jcenter()
    }
}
dependencyResolutionManagement {
    //repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://maven.scijava.org/content/repositories/public/")

        jcenter()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

include(":features:ui")
project(":features:ui").projectDir = File("../WalhallaUI\\features\\ui")

//include(":features:wads")
//project(":features:wads").projectDir = File("../WalhallaUI\\features\\wads\\")

include(":pdf-viewer")
project(":pdf-viewer").projectDir = File("pdf-viewer\\")

include(":health")
project(":health").projectDir = File("health\\")

//include(":threader")
//project(":threader").projectDir = File("D:\\walhalla\\sdk\\android\\multithreader\\threader\\")

include(":shared")
project(":shared").projectDir = File("../WalhallaUI\\shared")