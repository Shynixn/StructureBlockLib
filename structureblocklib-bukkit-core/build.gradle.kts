import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.net.URL
import java.nio.file.Files
import java.util.*

plugins {
    id("com.github.johnrengelman.shadow") version ("7.0.0")
}

tasks.withType<ShadowJar> {
    dependsOn("jar")
    archiveName = "${baseName}-${version}-mojangmapping.${extension}"
    relocate("org.intellij", "com.github.shynixn.structureblocklib.lib.org.intellij")
    relocate("org.jetbrains", "com.github.shynixn.structureblocklib.lib.org.jetbrains")
}

tasks.register("pluginJarOperation1", Exec::class.java) {
    // Change the output folder of the plugin.
    // val destinationDir = File("C:/temp/plugins")
    val destinationDir = File(buildDir, "libs")

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
    val sourceJarFile = File(buildDir, "libs/" + shadowJar.archiveName)
    val archiveName = "${shadowJar.baseName}-${shadowJar.version}.${shadowJar.extension}"
    val targetJarFile = File(destinationDir, archiveName)

    var obsMapping = createCommand(
        "1.17.1-R0.1-SNAPSHOT",
        "com/github/shynixn/structureblocklib/bukkit/v1_17_R1",
        file,
        shadowJar,
        sourceJarFile,
        targetJarFile
    )
    obsMapping = "$obsMapping && " + createCommand(
        "1.18-R0.1-SNAPSHOT",
        "com/github/shynixn/structureblocklib/bukkit/v1_18_R1",
        file,
        shadowJar,
        targetJarFile,
        targetJarFile
    )
    obsMapping = "$obsMapping && " + createCommand(
        "1.18.2-R0.1-SNAPSHOT",
        "com/github/shynixn/structureblocklib/bukkit/v1_18_R2",
        file,
        shadowJar,
        targetJarFile,
        targetJarFile
    )
    obsMapping = "$obsMapping && " + createCommand(
        "1.19-R0.1-SNAPSHOT",
        "com/github/shynixn/structureblocklib/bukkit/v1_19_R1",
        file,
        shadowJar,
        targetJarFile,
        targetJarFile
    )
    obsMapping = "$obsMapping && " + createCommand(
        "1.19.3-R0.1-SNAPSHOT",
        "com/github/shynixn/structureblocklib/bukkit/v1_19_R2",
        file,
        shadowJar,
        targetJarFile,
        targetJarFile
    )
    obsMapping = "$obsMapping && " + createCommand(
        "1.19.4-R0.1-SNAPSHOT",
        "com/github/shynixn/structureblocklib/bukkit/v1_19_R3",
        file,
        shadowJar,
        targetJarFile,
        targetJarFile
    )

    if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("windows")) {
        commandLine = listOf("cmd", "/c", obsMapping.replace("\$HOME", "%userprofile%"))
    } else {
        commandLine = listOf("sh", "-c", obsMapping)
    }
}

tasks.register("pluginJar", Exec::class.java, ) {
    dependsOn("shadowJar","pluginJarOperation1")
    // Change the output folder of the plugin.
    //val destinationDir = File("C:/temp/plugins")
    val destinationDir = File(buildDir, "libs")
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
    val archiveName = "${shadowJar.baseName}-${shadowJar.version}.${shadowJar.extension}"
    val targetJarFile = File(destinationDir, archiveName)

    var obsMapping = createCommand(
        "1.20-R0.1-SNAPSHOT",
        "com/github/shynixn/structureblocklib/bukkit/v1_20_R1",
        file,
        shadowJar,
        targetJarFile,
        targetJarFile
    )
    obsMapping = "$obsMapping && " + createCommand(
        "1.20.2-R0.1-SNAPSHOT",
        "com/github/shynixn/structureblocklib/bukkit/v1_20_R2",
        file,
        shadowJar,
        targetJarFile,
        targetJarFile
    )

    if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("windows")) {
        commandLine = listOf("cmd", "/c", obsMapping.replace("\$HOME", "%userprofile%"))
    } else {
        commandLine = listOf("sh", "-c", obsMapping)
    }
}

fun createCommand(
    version: String,
    include: String,
    file: File,
    shadowJar: ShadowJar,
    sourceJarFile: File,
    targetJarFile: File
): String {
    val obfArchiveName = "${shadowJar.baseName}-${shadowJar.version}-obfuscated.${shadowJar.extension}"
    val obfJarFile = File(buildDir, "libs/$obfArchiveName")

    return "java -jar ${file.absolutePath} -i \"$sourceJarFile\" -o \"$obfJarFile\"  -only \"$include\" -m \"\$HOME/.m2/repository/org/spigotmc/minecraft-server/${version}/minecraft-server-${version}-maps-mojang.txt\" --reverse" +
            "&& java -jar ${file.absolutePath} -i \"$obfJarFile\" -o \"$targetJarFile\"  -only \"$include\" -m \"\$HOME/.m2/repository/org/spigotmc/minecraft-server/${version}/minecraft-server-${version}-maps-spigot.csrg\""
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
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-118R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-118R2"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-119R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-119R2"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-119R3"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-120R1"))
    implementation(project(":structureblocklib-bukkit-core:bukkit-nms-120R2"))

    compileOnly("org.spigotmc:spigot:1.14.4-R0.1-SNAPSHOT")
    testCompile("org.spigotmc:spigot:1.12-R0.1-SNAPSHOT")
}
