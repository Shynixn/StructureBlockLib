repositories {
    maven(url = "https://libraries.minecraft.net")
}

dependencies {
    // Dependencies of spigot mojang want to restrict usage to only Java 17. However, we do not care
    // what they want because the general compatibility of this plugin is Java 8. The plugin
    // guarantees that everything works during runtime. This error is a false positive.
    components {
        all {
            allVariants {
                attributes {
                    attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 8)
                }
            }
        }
    }

    implementation(project(":structureblocklib-api"))
    implementation(project(":structureblocklib-core"))
    implementation(project(":structureblocklib-bukkit-api"))
    compileOnly("org.spigotmc:spigot:1.19-R0.1-SNAPSHOT:remapped-mojang")
    testCompile("org.spigotmc:spigot:1.19-R0.1-SNAPSHOT:remapped-mojang")
}
