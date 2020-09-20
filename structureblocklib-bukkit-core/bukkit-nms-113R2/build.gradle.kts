dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    compileOnly("org.spigotmc:spigot113R2:1.13.2-R2.0")
    testCompile("org.spigotmc:spigot113R2:1.13.2-R2.0")
}
