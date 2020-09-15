dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    compileOnly("org.spigotmc:spigot19R2:1.9.4-R2.0")
    testCompile("org.spigotmc:spigot19R2:1.9.4-R2.0")
}
