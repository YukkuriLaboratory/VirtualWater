plugins {
    id("fabric-loom") version "1.2-SNAPSHOT"
    id("maven-publish")
}

val mod_version: String by project
val maven_group: String by project
version = mod_version
group = maven_group

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
}

val minecraft_version: String by project
val yarn_mappings: String by project
val loader_version: String by project
val fabric_version: String by project

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings("net.fabricmc:yarn:$yarn_mappings:v2")
    modImplementation("net.fabricmc:fabric-loader:$loader_version")

    // Fabric API. This is technically optional, but you probably want it anyway.
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabric_version")
}

val targetJavaVersion = 17
tasks {
    processResources {
        inputs.property("version", project.version)
        inputs.property("minecraft_version", minecraft_version)
        inputs.property("loader_version", loader_version)
        filteringCharset = "UTF-8"
        filesMatching("fabric.mod.json") {
            expand(
                "version" to project.version,
                "minecraft_version" to minecraft_version,
                "loader_version" to loader_version,
            )
        }
    }
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
            rename { "${it}_${project.property("archives_base_name")}" }
        }
    }
}

java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
//    archivesBaseName = project.archives_base_name
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the("build") task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}
