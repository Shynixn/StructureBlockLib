dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    compileOnly("org.spigotmc:spigot116R1:1.16.0-R1.0")
    testCompile("org.spigotmc:spigot116R1:1.16.0-R1.0")
}
