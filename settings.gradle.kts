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

rootProject.name = "Test Airlines"
include(":app")
include(":home")
include(":core_ui")
include(":search")
include(":core_navigation")
include(":tickets")
include(":utils")
