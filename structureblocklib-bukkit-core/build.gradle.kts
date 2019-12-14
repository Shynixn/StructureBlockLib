import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version("2.0.4")
}

publishing {
    publications {
        (findByName("mavenJava") as MavenPublication).artifact(tasks.findByName("shadowJar")!!)
    }
}

tasks.withType<ShadowJar> {
    dependsOn("jar")
    archiveName = "$baseName-$version.$extension"
}

dependencies {
    implementation(project(":structureblocklib-bukkit-api"))
    implementation(project(":structureblocklib-bukkit-core:structureblocklib-bukkit-nms-114R1"))
    implementation(project(":structureblocklib-bukkit-core:structureblocklib-bukkit-nms-115R1"))

    compileOnly("org.spigotmc:spigot19R1:1.9.0-R1.0")
    compileOnly("org.spigotmc:spigot19R2:1.9.4-R2.0")
    compileOnly("org.spigotmc:spigot110R1:1.10.2-R1.0")
    compileOnly("org.spigotmc:spigot111R1:1.11.0-R1.0")
    compileOnly("org.spigotmc:spigot112R1:1.12.0-R1.0")
    compileOnly("org.spigotmc:spigot113R1:1.13.0-R1.0")
    compileOnly("org.spigotmc:spigot113R2:1.13.2-R2.0")
    compileOnly("org.spigotmc:spigot114R1:1.14.1-R1.0")

    testCompile("org.spigotmc:spigot112R1:1.12.0-R1.0")
}