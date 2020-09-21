# StructureBlockLib  [![Build Status](https://maven-badges.herokuapp.com/maven-central/com.github.shynixn.structureblocklib/structureblocklib-bukkit-api/badge.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/com.github.shynixn.structureblocklib/structureblocklib-bukkit-api) [![GitHub license](http://img.shields.io/badge/license-MIT-blue.svg?style=flat-square)](https://raw.githubusercontent.com/Shynixn/StructureBlockLib/master/LICENSE)

| branch        | status        | version | download |
| ------------- | ------------- | -------- | --------| 
| master        | [![Build Status](https://img.shields.io/travis/Shynixn/StructureBlockLib/master.svg?style=flat-square)](https://travis-ci.org/Shynixn/StructureBlockLib) |![GitHub license](https://img.shields.io/nexus/r/https/oss.sonatype.org/com.github.shynixn.structureblocklib/structureblocklib-bukkit-plugin.svg?style=flat-square)  |[Download latest release](https://github.com/Shynixn/StructureBlockLib/releases)|
| development  | [![Build Status](https://img.shields.io/travis/Shynixn/StructureBlockLib/development.svg?style=flat-square)](https://travis-ci.org/Shynixn/StructureBlockLib)|![GitHub license](https://img.shields.io/nexus/s/https/oss.sonatype.org/com.github.shynixn.structureblocklib/structureblocklib-bukkit-plugin.svg?style=flat-square) |  [Download snapshots](https://oss.sonatype.org/content/repositories/snapshots/com/github/shynixn/structureblocklib/structureblocklib-bukkit-plugin/) |

JavaDocs: https://shynixn.github.io/StructureBlockLib/apidocs/

## Description

StructureBlockLib is a bukkit API and implementation for handling structures on spigot server.

## Features

* Full blown Bukkit Api for the StructureBlock.
* API to save or load structures without an actual structure block. 
* Asynchronous implementation and API.
* Fluent API.
* Version support 1.9.R1 - 1.16.R2

## Installation

1. Include the dependency to StructureBlockLib

**Maven**
```xml
<dependency>
     <groupId>com.github.shynixn.structureblocklib</groupId>
     <artifactId>structureblocklib-bukkit-api</artifactId>
     <version>2.0.0</version>
     <scope>provided</scope>
</dependency>
```
**Gradle**

```xml
dependencies {
    compileOnly("com.github.shynixn.structureblocklib:structureblocklib-bukkit-api:2.0.0")
}
```

**Jar File**

[StructureBlockLibBukkitApi.jar](http://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=com.github.shynixn.structureblocklib&a=structureblocklib-bukkit-api&v=LATEST)

## Code Examples

* If you need more information, check out the sample implementation of a plugin using StructureBlockLib in the `structureblocklib-bukkit-sample` folder.
* All calls are not blocking and complete in the future.

##### Store a structure on your server to a target file 
```java
// Minimal configuration
Plugin plugin;
Path path = plugin.getDataFolder().toPath().resolve("mystructure.nbt");

StructureBlockLibApi.INSTANCE
    .saveStructure(plugin)
    .at(new Location(Bukkit.getWorld("world"), 100, 100, 100))
    .sizeX(32)
    .sizeY(5)
    .sizeZ(32)
    .saveToPath(path)
    .onException(e -> plugin.getLogger().log(Level.SEVERE, "Failed to save structure.", e))
    .onResult(e -> plugin.getLogger().log(Level.INFO, ChatColor.GREEN + "Saved structure 'mystructure'."));
```
```java
// Maximal configuration
Plugin plugin;
Path path = plugin.getDataFolder().toPath().resolve("mystructure.nbt");

StructureBlockLibApi.INSTANCE
    .saveStructure(plugin)
    .at(new Location(Bukkit.getWorld("world"), 100, 100, 100))
    .sizeX(32)
    .sizeY(5)
    .sizeZ(32)
    .includeEntities(false) // See JavaDoc for default values.
    .restriction(StructureRestriction.UNLIMITED)  // See JavaDoc for default values.
    .author("me")
    .saveToPath(path)
    .onProgress(c -> plugin.getLogger().log(Level.INFO, String.format("Percentage %.2f", c)))
    .onException(e -> plugin.getLogger().log(Level.SEVERE, "Failed to save structure.", e))
    .onResult(e -> plugin.getLogger().log(Level.INFO, ChatColor.GREEN + "Saved structure 'mystructure'."));
```

##### Store a structure on your server to the default structure storage, so it can be used by ordinary StructureBlocks
```java
// Minimal configuration
Plugin plugin;

StructureBlockLibApi.INSTANCE
    .saveStructure(plugin)
    .at(new Location(Bukkit.getWorld("world"), 100, 100, 100))
    .sizeX(32)
    .sizeY(5)
    .sizeZ(32)
    .saveToWorld("world", "me", "mystructure")
    .onException(e -> plugin.getLogger().log(Level.SEVERE, "Failed to save structure.", e))
    .onResult(e -> plugin.getLogger().log(Level.INFO, ChatColor.GREEN + "Saved structure 'mystructure'."));
```

##### Load a structure on your server from a source file 
```java
// Minimal configuration
Plugin plugin;
Path path = plugin.getDataFolder().toPath().resolve("mystructure.nbt");

StructureBlockLibApi.INSTANCE
    .loadStructure(plugin)
    .at(new Location(Bukkit.getWorld("world"), 100, 100, 100))
    .loadFromPath(path)
    .onException(e -> plugin.getLogger().log(Level.SEVERE, "Failed to load structure.", e))
    .onResult(e -> plugin.getLogger().log(Level.INFO, ChatColor.GREEN + "Loaded structure 'mystructure'."));
```

```java
// Maximal configuration
Plugin plugin;
Path path = plugin.getDataFolder().toPath().resolve("mystructure.nbt");

StructureBlockLibApi.INSTANCE
    .loadStructure(plugin)
    .at(new Location(Bukkit.getWorld("world"), 100, 100, 100))
    .includeEntities(true) // See JavaDoc for default values.
    .seed(50L) // See JavaDoc for default values.
    .integrity(0.2F) // See JavaDoc for default values.
    .mirror(StructureMirror.FRONT_BACK) // See JavaDoc for default values.
    .rotation(StructureRotation.ROTATION_90) // See JavaDoc for default values.
    .loadFromPath(path)
    .onException(e -> plugin.getLogger().log(Level.SEVERE, "Failed to load structure.", e))
    .onResult(e -> plugin.getLogger().log(Level.INFO, ChatColor.GREEN + "Loaded structure 'mystructure'."));
```
       
##### Load a structure on your server from the default structure storage, so you can use structures from ordinary StructureBlocks

```java
// Minimal configuration
Plugin plugin;

StructureBlockLibApi.INSTANCE
    .loadStructure(plugin)
    .at(new Location(Bukkit.getWorld("world"), 100, 100, 100))
    .loadFromWorld("world", "me", "mystructure")
    .onException(e -> plugin.getLogger().log(Level.SEVERE, "Failed to load structure.", e))
    .onResult(e -> plugin.getLogger().log(Level.INFO, ChatColor.GREEN + "Loaded structure 'mystructure'."));
```

##### Modify and use an existing structure block
```java
Plugin plugin;
Location location = new Location(Bukkit.getWorld("world"), 100, 100, 100);
location.getBlock().setType(Material.STRUCTURE_BLOCK);

StructureBlockSave structureBlock = StructureBlockLibApi.INSTANCE.getStructureBlockAt(location, plugin);
structureBlock.setStructureMode(StructureMode.SAVE);
structureBlock.setSaveName("sample_save");
structureBlock.setSizeX(31);
structureBlock.setSizeY(15);
structureBlock.setSizeZ(12);
structureBlock.update();
```

## Shipping and Running

* In order to use the StructureBlockLib Api on your server, you need to put the implementation of the Api on your server.
This can be achieved by either **installing the standalone plugin**  ``StructureBlockLib.jar`` **or shipping the implementation
with your plugin**. 

#### Installing the StructureBlockLib.jar

* Just go to the [releases page](https://github.com/Shynixn/StructureBlockLib/releases) and download the plugin.
 
#### Shipping the implementation with your plugin

* Include both dependencies and shade them in your plugin jar file. If you do not know how to do that, you should
go with the option above instead. There are several tutorials on spigotmc.org.

**Maven**
```xml
<dependency>
     <groupId>com.github.shynixn.structureblocklib</groupId>
     <artifactId>structureblocklib-bukkit-api</artifactId>
     <version>2.0.0</version>
     <scope>compile</scope>
</dependency>
<dependency>
     <groupId>com.github.shynixn.structureblocklib</groupId>
     <artifactId>structureblocklib-bukkit-core</artifactId>
     <version>2.0.0</version>
     <scope>compile</scope>
</dependency>
```
**Gradle**

```xml
dependencies {
    implementation("com.github.shynixn.structureblocklib:structureblocklib-bukkit-api:2.0.0")
    implementation("com.github.shynixn.structureblocklib:structureblocklib-bukkit-core:2.0.0")
}
```
 
## Contributing

* Fork the StructureBlockLib project on github and clone it to your local environment.
* Install Java 8 (later versions are not supported by the ``downloadDependencies`` task)
* Install Apache Maven
* Make sure ``java`` points to a Java 8 installation (``java -version``)
* Make sure ``$JAVA_HOME`` points to a Java 8 installation
* Make sure ``mvn`` points to a Maven installation (``mvn --version``)
* Execute gradle sync for dependencies
* Install the additional spigot dependencies by executing the following gradle task (this task can take a very long time)

```xml
[./gradlew|gradlew.bat] downloadDependencies
```

* Build the module files by executing the following gradle task.

```xml
[./gradlew|gradlew.bat] shadowJar
```

## Licence

The source code is licensed under the MIT license. 
