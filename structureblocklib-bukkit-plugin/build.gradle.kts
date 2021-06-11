import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.net.URL
import java.nio.file.Files
import java.util.*

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
    archiveName = "${baseName}-${version}-mojangmapping.${extension}"
    relocate("org.intellij", "com.github.shynixn.structureblocklib.lib.org.intellij")
    relocate("org.jetbrains", "com.github.shynixn.structureblocklib.lib.org.jetbrains")
}

tasks.register("pluginJar", Exec::class.java) {
    dependsOn("shadowJar")
    workingDir = buildDir

    if (!workingDir.exists()) {
        workingDir.mkdir();
    }

    val folder = File(workingDir, "mapping")

    if (!folder.exists()) {
        folder.mkdir()
    }

    val file = File(folder, "SpecialSources.jar")

    if (!file.exists()) {
        URL("https://repo.maven.apache.org/maven2/net/md-5/SpecialSource/1.10.0/SpecialSource-1.10.0-shaded.jar").openStream()
            .use {
                Files.copy(it, file.toPath())
            }
    }

    val shadowJar = tasks.findByName("shadowJar")!! as ShadowJar
    val obfArchiveName = "${shadowJar.baseName}-${shadowJar.version}-obfuscated.${shadowJar.extension}"
    val archiveName = "${shadowJar.baseName}-${shadowJar.version}.${shadowJar.extension}"
    val sourceJarFile = File(buildDir, "libs/" + shadowJar.archiveName)
    val obfJarFile = File(buildDir, "libs/$obfArchiveName")
    val targetJarFile = File(buildDir, "libs/$archiveName")

    val obsMapping =
        "java -jar ${file.absolutePath} -i \"$sourceJarFile\" -o \"$obfJarFile\" -m \"\$HOME/.m2/repository/org/spigotmc/minecraft-server/1.17-R0.1-SNAPSHOT/minecraft-server-1.17-R0.1-SNAPSHOT-maps-mojang.txt\" --reverse" +
                "&& java -jar ${file.absolutePath} -i \"$obfJarFile\" -o \"$targetJarFile\" -m \"\$HOME/.m2/repository/org/spigotmc/minecraft-server/1.17-R0.1-SNAPSHOT/minecraft-server-1.17-R0.1-SNAPSHOT-maps-spigot.csrg\""

    if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("windows")) {
        commandLine = listOf("cmd", "/c", obsMapping.replace("\$HOME", "%userprofile%"))
    } else {
        commandLine = listOf("sh", "-c", obsMapping)
    }
}

dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    implementation(project(":structureblocklib-bukkit-core"))
    compileOnly("org.spigotmc:spigot114R1:1.14.4-R1.0")
}
