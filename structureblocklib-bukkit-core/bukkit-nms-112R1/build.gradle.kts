dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    compileOnly("org.spigotmc:spigot112R1:1.12.0-R1.0")
    testCompile("org.spigotmc:spigot112R1:1.12.0-R1.0")
}
