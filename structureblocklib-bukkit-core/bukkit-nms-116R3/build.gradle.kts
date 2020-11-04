dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    compileOnly("org.spigotmc:spigot116R3:1.16.4-R3.0")
    testCompile("org.spigotmc:spigot116R3:1.16.4-R3.0")
}
