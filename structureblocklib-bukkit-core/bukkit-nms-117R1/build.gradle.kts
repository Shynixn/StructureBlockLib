import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    compileOnly("org.spigotmc:spigot117R1:1.17.0-R1.0")
    testCompile("org.spigotmc:spigot117R1:1.17.0-R1.0")
}
