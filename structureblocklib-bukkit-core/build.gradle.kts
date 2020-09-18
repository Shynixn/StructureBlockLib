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
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-19R2"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-110R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-111R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-112R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-113R2"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-114R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-115R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-116R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-116R2"))

    compileOnly("org.spigotmc:spigot114R1:1.14.4-R1.0")

    testCompile("org.spigotmc:spigot112R1:1.12.0-R1.0")
}
