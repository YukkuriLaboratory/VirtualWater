plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
    log4jConfigs.from(project(":common").loom.log4jConfigs.from)
}

val common = configurations.maybeCreate("common")
val shadowCommon = configurations.maybeCreate("shadowCommon")

configurations {
    compileClasspath.get().extendsFrom(common)
    runtimeClasspath.get().extendsFrom(common)
    getByName("developmentFabric").extendsFrom(common)
}

val minecraft_version: String by rootProject
val loader_version: String by rootProject

dependencies {
    modImplementation("net.fabricmc:fabric-loader:$loader_version")
    add("common", project(path = ":common", configuration = "namedElements").apply { isTransitive = false })
    add(
        "shadowCommon",
        project(path = ":common", configuration = "transformProductionFabric").apply { isTransitive = false },
    )
}

tasks {
    processResources {
        inputs.property("version", rootProject.version)
        inputs.property("minecraft_version", minecraft_version)
        inputs.property("loader_version", loader_version)
        filteringCharset = "UTF-8"
        filesMatching("fabric.mod.json") {
            expand(
                "version" to rootProject.version,
                "minecraft_version" to minecraft_version,
                "loader_version" to loader_version,
            )
        }
    }
    shadowJar {
        configurations = listOf(shadowCommon)
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        injectAccessWidener.set(true)
        inputFile.set(shadowJar.get().archiveFile.get())
        dependsOn(shadowJar)
        archiveClassifier.set(null as String?)
    }

    jar {
        archiveClassifier.set("dev")
    }

    sourcesJar {
        val commonSources = project(":common").tasks.sourcesJar.get()
        dependsOn(commonSources)
        from(commonSources.archiveFile.map { zipTree(it) })
    }
}

components.getByName<AdhocComponentWithVariants>("java") {
    withVariantsFromConfiguration(project.configurations.shadowRuntimeElements.get()) {
        skip()
    }
}
