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

tasks.register<Exec>("dockerJar") {
    dependsOn("shadowJar")

    commandLine = if (System.getProperty("os.name").toLowerCase().contains("windows")) {
        listOf("cmd", "/c", "docker cp build/libs/. structureblocklib-1.15:/minecraft/plugins")
    } else {
        listOf("sh", "-c", "docker cp build/libs/. structureblocklib-1.15:/minecraft/plugins")
    }
}

dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    implementation(project(":structureblocklib-bukkit-core"))

    compileOnly("org.spigotmc:spigot114R1:1.14.4-R1.0")
}
