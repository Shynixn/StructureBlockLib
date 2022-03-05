# StructureBlockLib  [![Build Status](https://maven-badges.herokuapp.com/maven-central/com.github.shynixn.structureblocklib/structureblocklib-bukkit-api/badge.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/com.github.shynixn.structureblocklib/structureblocklib-bukkit-api) [![GitHub license](http://img.shields.io/badge/license-MIT-blue.svg?style=flat-square)](https://raw.githubusercontent.com/Shynixn/StructureBlockLib/master/LICENSE)

| branch        | status        | version | download |
| ------------- | ------------- | -------- | --------| 
| master        | [![Build Status](https://github.com/Shynixn/StructureBlockLib/workflows/CI/badge.svg?branch=master)](https://github.com/Shynixn/StructureBlockLib/actions) |![GitHub license](https://img.shields.io/nexus/r/https/oss.sonatype.org/com.github.shynixn.structureblocklib/structureblocklib-bukkit-plugin.svg?style=flat-square)  |[Download latest release](https://github.com/Shynixn/StructureBlockLib/releases)|
| development  | [![Build Status](https://github.com/Shynixn/StructureBlockLib/workflows/CI/badge.svg?branch=development)](https://github.com/Shynixn/StructureBlockLib/actions)|![GitHub license](https://img.shields.io/nexus/s/https/oss.sonatype.org/com.github.shynixn.structureblocklib/structureblocklib-bukkit-plugin.svg?style=flat-square) |  [Download snapshots](https://oss.sonatype.org/content/repositories/snapshots/com/github/shynixn/structureblocklib/structureblocklib-bukkit-plugin/) |

JavaDocs: https://shynixn.github.io/StructureBlockLib/apidocs/

## Description

StructureBlockLib is a bukkit API and implementation for handling structures on spigot server.

## Features

* Bukkit API for the StructureBlock.
* API to save or load structures without an actual structure block. 
* Asynchronous implementation and API.
* Fluent API.
* Version support 1.9.R1 - 1.18.R2
* Java support 8 - Latest

## Installation

1. Include the dependency to StructureBlockLib

**Maven**
```xml
<dependency>
     <groupId>com.github.shynixn.structureblocklib</groupId>
     <artifactId>structureblocklib-bukkit-api</artifactId>
     <version>2.4.0</version>
     <scope>provided</scope>
</dependency>
```
**Gradle**

```xml
dependencies {
    compileOnly("com.github.shynixn.structureblocklib:structureblocklib-bukkit-api:2.4.0")
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

### For version >= 1.17

* Add the dependencies to the libraries tag

**plugin.yml**
```yaml
libraries:
  - com.github.shynixn.structureblocklib:structureblocklib-bukkit-api:2.4.0
  - com.github.shynixn.structureblocklib:structureblocklib-bukkit-core:2.4.0
```

### For version < 1.17

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
     <version>2.4.0</version>
     <scope>compile</scope>
</dependency>
<dependency>
     <groupId>com.github.shynixn.structureblocklib</groupId>
     <artifactId>structureblocklib-bukkit-core</artifactId>
     <version>2.4.0</version>
     <scope>compile</scope>
</dependency>
```
**Gradle**

```xml
dependencies {
    implementation("com.github.shynixn.structureblocklib:structureblocklib-bukkit-api:2.4.0")
    implementation("com.github.shynixn.structureblocklib:structureblocklib-bukkit-core:2.4.0")
}
```
 
## Contributing

### Setting up development environment

* Install Java 17 or higher
* Fork the StructureBlockLib project on github and clone it to your local environment.
* StructureBlockLib requires spigot server implementations from 1.9.4 to 1.18.2 to be correctly installed in your local Maven cache. 
  As this requires multiple java version to build different versions, a Dockerfile is provided to build these dependencies in a docker container
  and then copy it to your local Maven cache.
  
Note: If using Windows, execute the commands using Git Bash.
````sh
mkdir -p ~/.m2/repository/org/spigotmc/
docker build --target dependencies-jdk8 -t structureblocklib-dependencies-jdk8 .
docker create --name structureblocklib-dependencies-jdk8 structureblocklib-dependencies-jdk8 bash
docker cp structureblocklib-dependencies-jdk8:/root/.m2/repository/org/spigotmc ~/.m2/repository/org/
docker rm -f structureblocklib-dependencies-jdk8
docker build --target dependencies-jdk17 -t structureblocklib-dependencies-jdk17 .
docker create --name structureblocklib-dependencies-jdk17 structureblocklib-dependencies-jdk17 bash
docker cp structureblocklib-dependencies-jdk17:/root/.m2/repository/org/spigotmc ~/.m2/repository/org/
docker rm -f structureblocklib-dependencies-jdk17
````

* Open the project with an IDE, gradle sync for dependencies.

### Testing

#### Option 1

* Setup your own minecraft server
* Change ``// val destinationDir = File("C:/temp/plugins")`` to your plugins folder in the ``structureblocklib-bukkit-sample/build.gradle.kts`` file.
* Run the ``pluginJar`` task to generate a plugin.jar file.
* Run your minecraft server

#### Option 2 :whale: 

* Run the provided docker file. 
* The source code is copied to a new docker container and built to a plugin.
* This plugin is installed on a new minecraft server which is accessible on the host machine on the default port on ``localhost``.

````sh
docker build -t structureblocklib .
docker run --name=structureblocklib -p 25565:25565 -p 5005:5005 structureblocklib
````

## Licence

The source code is licensed under the MIT license. 
