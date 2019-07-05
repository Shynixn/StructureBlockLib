import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version("2.0.4")
}

tasks.withType<ShadowJar> {
    archiveName = "$baseName-$version.$extension"

    // Change the output folder of the plugin.
    // destinationDir = File("D:\\Benutzer\\Temp\\plugins")
}

publishing {
    publications {
        (findByName("mavenJava") as MavenPublication).artifact(tasks.findByName("shadowJar")!!)
    }
}

dependencies {
    implementation(project(":structureblocklib-bukkit-api"))
    implementation(project(":structureblocklib-bukkit-core"))

    compileOnly("org.spigotmc:spigot114R1:1.14.1-R1.0")
}