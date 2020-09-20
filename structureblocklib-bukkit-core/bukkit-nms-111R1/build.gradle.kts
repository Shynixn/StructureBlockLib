dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    compileOnly("org.spigotmc:spigot111R1:1.11.0-R1.0")
    testCompile("org.spigotmc:spigot111R1:1.11.0-R1.0")
}
