plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}
architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
    log4jConfigs.from(project(":common").loom.log4jConfigs.from)

    forge {
        convertAccessWideners.set(true)
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
    }
}

val common = configurations.maybeCreate("common")
val shadowCommon = configurations.create("shadowCommon")
configurations {
    compileClasspath.get().extendsFrom(common)
    runtimeClasspath.get().extendsFrom(common)
    getByName("developmentForge").extendsFrom(common)
}

val forge_version: String by rootProject
val architectury_version: String by rootProject

dependencies {
    add("forge", "net.minecraftforge:forge:$forge_version")
    modApi("dev.architectury:architectury-forge:$architectury_version")
    add("common", project(path = ":common", configuration = "namedElements").apply { isTransitive = false })
    add(
        "shadowCommon",
        project(path = ":common", configuration = "transformProductionForge").apply { isTransitive = false },
    )
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("META-INF/mods.toml") {
            expand("version" to project.version)
        }
    }
    shadowJar {
        exclude("fabric.mod.json")

        configurations = listOf(shadowCommon)
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
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
