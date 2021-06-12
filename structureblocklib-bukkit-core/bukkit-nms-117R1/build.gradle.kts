dependencies {
    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    compileOnly("org.spigotmc:spigot:1.17-R0.1-SNAPSHOT:remapped-mojang")
    testCompile("org.spigotmc:spigot:1.17-R0.1-SNAPSHOT:remapped-mojang")
}
