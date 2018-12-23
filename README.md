# StructureBlockLib  [![Build Status](https://maven-badges.herokuapp.com/maven-central/com.github.shynixn.structureblocklib/structureblocklib-bukkit-api/badge.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/com.github.shynixn.structureblocklib/structureblocklib-bukkit-api) [![GitHub license](http://img.shields.io/badge/license-MIT-blue.svg?style=flat-square)](https://raw.githubusercontent.com/Shynixn/StructureBlockLib/master/LICENSE)

| branch        | status        | coverage | version | download |
| ------------- | ------------- | -------- | --------| ---------| 
| master        | [![Build Status](https://img.shields.io/travis/Shynixn/StructureBlockLib/master.svg?style=flat-square)](https://travis-ci.org/Shynixn/StructureBlockLib) | [![Coverage](https://img.shields.io/codecov/c/github/shynixn/structureblocklib/master.svg?style=flat-square)](https://codecov.io/gh/Shynixn/StructureBlockLib/branch/master)|![GitHub license](https://img.shields.io/nexus/r/https/oss.sonatype.org/com.github.shynixn.structureblocklib/structureblocklib-bukkit-plugin.svg?style=flat-square)  |[Download latest release](https://github.com/Shynixn/StructureBlockLib/releases)|
| development  | [![Build Status](https://img.shields.io/travis/Shynixn/StructureBlockLib/development.svg?style=flat-square)](https://travis-ci.org/Shynixn/StructureBlockLib)|[![Coverage](https://img.shields.io/codecov/c/github/shynixn/structureblocklib/development.svg?style=flat-square)](https://codecov.io/gh/Shynixn/StructureBlockLib/branch/development) |![GitHub license](https://img.shields.io/nexus/s/https/oss.sonatype.org/com.github.shynixn.structureblocklib/structureblocklib-bukkit-plugin.svg?style=flat-square) |  [Download snapshots](https://oss.sonatype.org/content/repositories/snapshots/com/github/shynixn/structureblocklib/structureblocklib-bukkit-plugin/) |

JavaDocs: https://shynixn.github.io/StructureBlockLib/apidocs/

## Description

StructureBlockLib is a bukkit implementation for handling structures on spigot server.

## Features

* NMS Implementation of the StructureBlock.
* StructureApi to save or load structures without an actual structure block. 
* Version support 1.9.R1 - 1.13.R2
* Lightweight

## Installation

There are 2 different ways to use the StructureBlockLib.

* Include and ship StructureBlockLib with your personal project.
This means users don't have to download the StructureBlockLib.jar.

##### DO NOT FORGET TO RELOCATE THE PACKAGE 'com.github.shynixn.structureblocklib' WHEN SHADING THE DEPENDENCY.

```xml
<dependency>
     <groupId>com.github.shynixn.structureblocklib</groupId>
     <artifactId>structureblocklib-bukkit-core</artifactId>
     <version>1.7.0</version>
     <scope>compile</scope>
</dependency>
```

```xml
dependencies {
    compile 'com.github.shynixn.structureblocklib:structureblocklib-bukkit-core:1.7.0'
}
```

* Use the dependency api and install the standalone StructureBlockLib.jar.
Users have to download the StructureBlockLib.jar.

```xml
<dependency>
     <groupId>com.github.shynixn.structureblocklib</groupId>
     <artifactId>structureblocklib-bukkit-api</artifactId>
     <version>1.7.0</version>
     <scope>provided</scope>
</dependency>
```

```xml
dependencies {
    compile 'com.github.shynixn.structureblocklib:structureblocklib-bukkit-api:1.7.0'
}
```

## How to use it

Things to notice:

* StructureBlockLib wraps around the default structure storage of Minecraft. This means all your created
structures via the Api will be stored somewhere in your local world folder.
* Compared to other NBT formats, structures are version independent and structures
created in lower Minecraft versions can be used in the latest one.
* Minecraft 1.13+ uses the previously unused author tag to store structures per author. This means structures are stored at a different location than before.

1.12
```java
../%world%/structures/%name%.nbt
```

1.13
```java
../%world%/generated/%author%/structures/%name%.nbt
```

* StructureBlockLib automatically creates a copy of structures created in 1.12 and below to allow
using structures after upgrading to 1.13.

#### Store a structure on your server via location and offset and without a structure block
```java
// Data
Location corner = new Location(Bukkit.getWorld("world"), 100, 100, 100);
Vector offSet = new Vector(5, 3, -3);

// Get the business logic service.
PersistenceStructureService service = StructureBlockApi.INSTANCE.getStructurePersistenceService();

// Create a save configuration for the meta data author 'shynixn' the identifier 'super_fancy_structure' and the world folder where it should be stored 'world'.
final StructureSaveConfiguration saveConfiguration = service.createSaveConfiguration("shynixn", "super_fancy_structure", "world");

// Saves the structure to the storage at the given corner with the given offSet.
service.save(saveConfiguration, corner, offSet);
```

#### Store a structure on your server between two locations and without a structure block
```java
// Data
Location corner = new Location(Bukkit.getWorld("world"), 100, 100, 120);
Location otherCorner = new Location(Bukkit.getWorld("world"), 120, 120, 120);

// Get the business logic service.
PersistenceStructureService service = StructureBlockApi.INSTANCE.getStructurePersistenceService();

// Create a save configuration for the meta data author 'shynixn' the identifier 'super_fancy_structure' and the world folder where it should be stored 'world'.
final StructureSaveConfiguration saveConfiguration = service.createSaveConfiguration("shynixn", "super_fancy_structure", "world");

// Saves the structure to the storage between the given corners.
service.save(saveConfiguration, corner, otherCorner);
```

#### Load a structure from the storage into the world
```java
// Data
Location target = new Location(Bukkit.getWorld("world"), 100, 100, 120);

// Get the business logic service.
PersistenceStructureService service = StructureBlockApi.INSTANCE.getStructurePersistenceService();

// Create a save configuration for the meta data author 'shynixn' the identifier 'super_fancy_structure' and the world folder where it should be stored 'world'.
final StructureSaveConfiguration saveConfiguration = service.createSaveConfiguration("shynixn", "super_fancy_structure", "world");

// Load the structure to the target location
service.load(saveConfiguration, target);
```

#### Modify and use an existing structure block
```java
// Data
Location blockLocation = new Location(Bukkit.getWorld("world"), 100, 100, 120);

// Get the business logic service.
StructureBlockService service = StructureBlockApi.INSTANCE.getStructureBlockService();

// Create or get the structureblock at the location.
StructureBlock structureBlock = service.getOrCreateStructureBlockAt(blockLocation);

// Modify the structure block.
structureBlock.setStructureMode(StructureMode.SAVE);

// Optionally cast it to your current use case.
StructureBlockSave structureBlockSave = (StructureBlockSave) structureBlock;

// Update the structureblock to see the changes.
structureBlock.update();
```
 

## Contributing

* Fork the StructureBlockLib project on github and clone it to your local environment.
* Use BuildTools.jar from spigotmc.org to build the following dependencies.

```text
java -jar BuildTools.jar --rev 1.9
java -jar BuildTools.jar --rev 1.9.4
java -jar BuildTools.jar --rev 1.10
java -jar BuildTools.jar --rev 1.11
java -jar BuildTools.jar --rev 1.12
java -jar BuildTools.jar --rev 1.13
java -jar BuildTools.jar --rev 1.13.1
java -jar BuildTools.jar --rev 1.13.2
```

* Install the created libraries to your local maven repository.

```text
mvn install:install-file -Dfile=spigot-1.9.jar -DgroupId=org.spigotmc -DartifactId=spigot19R1 -Dversion=1.9.0-R1.0 -Dpackaging=jar
mvn install:install-file -Dfile=spigot-1.9.4.jar -DgroupId=org.spigotmc -DartifactId=spigot19R2 -Dversion=1.9.4-R2.0 -Dpackaging=jar
mvn install:install-file -Dfile=spigot-1.10.2.jar -DgroupId=org.spigotmc -DartifactId=spigot110R1 -Dversion=1.10.2-R1.0 -Dpackaging=jar
mvn install:install-file -Dfile=spigot-1.11.jar -DgroupId=org.spigotmc -DartifactId=spigot111R1 -Dversion=1.11.0-R1.0 -Dpackaging=jar
mvn install:install-file -Dfile=spigot-1.12.jar -DgroupId=org.spigotmc -DartifactId=spigot112R1 -Dversion=1.12.0-R1.0 -Dpackaging=jar
mvn install:install-file -Dfile=spigot-1.13.jar -DgroupId=org.spigotmc -DartifactId=spigot113R1 -Dversion=1.13.0-R1.0 -Dpackaging=jar
mvn install:install-file -Dfile=spigot-1.13.1.jar -DgroupId=org.spigotmc -DartifactId=spigot113R2 -Dversion=1.13.1-R2.0 -Dpackaging=jar
mvn install:install-file -Dfile=spigot-1.13.2.jar -DgroupId=org.spigotmc -DartifactId=spigot113R2 -Dversion=1.13.2-R2.0 -Dpackaging=jar
```

* Reimport the StructureBlockLib maven project and execute 'mvn package' afterwards.
* The generated structureblocklib-bukkit-plugin/target/structureblocklib-bukkit-plugin-version.jar can be used for testing on a server.

## Licence

The source code is licensed under the MIT license. 
