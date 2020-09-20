dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    compileOnly("org.spigotmc:spigot116R2:1.16.2-R2.0")
    testCompile("org.spigotmc:spigot116R2:1.16.2-R2.0")
}
