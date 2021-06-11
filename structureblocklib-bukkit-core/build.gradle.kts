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

    relocate("org.intellij", "com.github.shynixn.structureblocklib.lib.org.intellij")
    relocate("org.jetbrains", "com.github.shynixn.structureblocklib.lib.org.jetbrains")
}

dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-109R2"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-110R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-111R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-112R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-113R2"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-114R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-115R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-116R3"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-117R1"))

    compileOnly("org.spigotmc:spigot:1.14.4-R0.1-SNAPSHOT")
    testCompile("org.spigotmc:spigot:1.12-R0.1-SNAPSHOT")
}
