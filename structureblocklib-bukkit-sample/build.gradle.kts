import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version ("2.0.4")
}

publishing {
    publications {
        (findByName("mavenJava") as MavenPublication).artifact(tasks.findByName("shadowJar")!!)
    }
}

tasks.withType<ShadowJar> {
    dependsOn("jar")
    archiveName = "$baseName-$version.$extension"

    // Change the output folder of the plugin.
    // destinationDir = File("D:\\Benutzer\\Temp\\plugins")

    relocate("org.intellij", "com.github.shynixn.structureblocklib.lib.org.intellij")
    relocate("org.jetbrains", "com.github.shynixn.structureblocklib.lib.org.jetbrains")
}

dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    implementation(project(":structureblocklib-bukkit-core"))
    compileOnly("org.spigotmc:spigot114R1:1.14.4-R1.0")
}
