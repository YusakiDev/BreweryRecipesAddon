import io.papermc.hangarpublishplugin.model.Platforms

plugins {
    kotlin("jvm") version "2.0.21"
    id("io.papermc.hangar-publish-plugin") version "0.1.2"
    id("com.modrinth.minotaur") version "2.8.7"
}

group = "dev.jsinco.recipes"
version = "BX3.4.5"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.jsinco.dev/releases")
}

dependencies {
    compileOnly("com.dre.brewery:BreweryX:3.4.5-SNAPSHOT#4")
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    compileOnly("net.luckperms:api:5.4")
}


kotlin {
    jvmToolchain(21)
}


tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

hangarPublish {
    publications.register("plugin") {
        version.set(project.version.toString())
        channel.set("Release")
        id.set("Recipes-BreweryX-Addon")
        apiKey.set(System.getenv("HANGAR_TOKEN"))
        platforms {
            register(Platforms.PAPER) {
                jar.set(tasks.jar.flatMap { it.archiveFile })
                platformVersions.set(listOf("1.21.x"))
            }
        }
        changelog.set(readChangeLog())
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set(project.name) // This can be the project ID or the slug. Either will work!
    versionNumber.set(project.version.toString())
    versionType.set("release") // This is the default -- can also be `beta` or `alpha`
    uploadFile.set(tasks.jar)
    loaders.addAll("bukkit", "spigot", "paper", "purpur", "folia")
    gameVersions.addAll("1.21.x")
    changelog.set(readChangeLog())
}

fun readChangeLog(): String {
    val text: String = System.getenv("CHANGELOG") ?: file("CHANGELOG.md").run {
        if (exists()) readText() else "No Changelog found."
    }
    return text.replace("\${version}", project.version.toString())
}

