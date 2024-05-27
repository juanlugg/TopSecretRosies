pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Invoice"
include(":app")
include(":features:accountsignin")
include(":features:accountsignup")
include(":infrastructure:firebase")
include(":infrastructure:printer")
include(":features:customerun")
include(":base:ui")
include(":base:utils")
include(":domain:invoiceD")
include(":features:invoice")
include(":features:item")
//include(":features:itemdetail")
//include(":features:itemlist")
include(":features:task")
