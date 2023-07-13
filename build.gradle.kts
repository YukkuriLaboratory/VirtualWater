plugins {
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.2-SNAPSHOT" apply false
    java
}

val archives_base_name: String by rootProject
val mod_version: String by rootProject
val maven_group: String by rootProject

val minecraft_version: String by rootProject
val yarn_mappings: String by rootProject
val fabric_version: String by rootProject

architectury {
    minecraft = minecraft_version
}

subprojects {
    apply(plugin = "dev.architectury.loom")

//    loom {
//        silentMojangMappingsLicense()
//    }

    dependencies {
        // To change the versions see the gradle.properties file
        add("minecraft", "com.mojang:minecraft:$minecraft_version")
        add("mappings", "net.fabricmc:yarn:$yarn_mappings:v2")
    }
}

val targetJavaVersion = 17
allprojects {
    apply(plugin = "java")
    apply(plugin = "architectury-plugin")

    base.archivesName.set(archives_base_name)
    version = mod_version
    group = maven_group

    tasks {
        withType<JavaCompile>().configureEach {
            // ensure that the encoding is set to UTF-8, no matter what the system default is
            // this fixes some edge cases with special characters not displaying correctly
            // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
            // If Javadoc is generated, this must be specified in that task too.
            options.encoding = "UTF-8"
            if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
                options.release.set(targetJavaVersion)
            }
        }
        jar {
            from("LICENSE") {
                rename { "${it}_${rootProject.property("archives_base_name")}" }
            }
        }
    }
    java {
        val javaVersion = JavaVersion.toVersion(targetJavaVersion)
        if (JavaVersion.current() < javaVersion) {
            toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
        }
        withSourcesJar()
    }
}
