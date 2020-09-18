dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    compileOnly("org.spigotmc:spigot110R1:1.10.2-R1.0")
    testCompile("org.spigotmc:spigot110R1:1.10.2-R1.0")
}
