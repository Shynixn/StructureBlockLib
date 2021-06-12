import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version ("7.0.0")
}

tasks.withType<ShadowJar> {
    dependsOn("jar")
    archiveName = "$baseName-$version.$extension"
    relocate("org.intellij", "com.github.shynixn.structureblocklib.lib.org.intellij")
    relocate("org.jetbrains", "com.github.shynixn.structureblocklib.lib.org.jetbrains")
}

dependencies {
    implementation(project(":structureblocklib-api"))
    compileOnly("org.spigotmc:spigot:1.14.4-R0.1-SNAPSHOT")
}
