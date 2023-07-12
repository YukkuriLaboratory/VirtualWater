pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        maven {
            name = "architectury"
            url = uri("https://maven.architectury.dev/")
        }
        maven {
            name = "forge"
            url = uri("https://maven.minecraftforge.net/")
        }
        gradlePluginPortal()
    }
}

include("common")
include("fabric")
include("forge")
