# This docker file uses multi-stage builds.
# 1. Resolve minecraft-dependencies for 1.8 - 1.16 with jdk8
FROM openjdk:8 AS dependencies-jdk8
WORKDIR /tmp
RUN apt-get update
RUN apt-get install maven -y
RUN wget "https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar"
RUN java -jar BuildTools.jar --rev 1.9.4
RUN java -jar BuildTools.jar --rev 1.10
RUN java -jar BuildTools.jar --rev 1.11
RUN java -jar BuildTools.jar --rev 1.12
RUN java -jar BuildTools.jar --rev 1.16.4
RUN java -jar BuildTools.jar --rev 1.13.2
RUN java -jar BuildTools.jar --rev 1.14.4
RUN java -jar BuildTools.jar --rev 1.15
RUN java -jar BuildTools.jar --rev 1.16.4

# 2. Resolve minecraft-dependencies for 1.17 - Latest
FROM adoptopenjdk/openjdk16 AS dependencies-jdk16
WORKDIR /tmp
RUN apt-get update
RUN apt-get install maven -y
RUN apt-get install wget -y
RUN apt-get install git -y
RUN wget "https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar"
RUN java -jar BuildTools.jar --rev 1.17 --remapped

# 3. Build plugin for 1.8 - 1.16 with jdk8
FROM openjdk:8 AS plugin-jdk8
WORKDIR /tmp
RUN apt-get update
RUN apt-get install maven -y
RUN apt-get install dos2unix -y
COPY --from=dependencies-jdk8 /home/.m2/ /home/.m2/
COPY . /tmp
RUN dos2unix gradlew && ./gradlew printVersion > version.txt
RUN cp ./structureblocklib-tools/settings.gradle.kts.jdk8.txt ./settings.gradle.kts && ./structureblocklib-tools/build.gradle.kts.jdk8.txt ./structureblocklib-bukkit-core/build.gradle.kts
RUN ./gradlew build shadowJar pluginJar --no-daemon

# 4. Run plugin for 1.8 - 1.16 with jdk8
FROM openjdk:8
# Location where the server is running.
WORKDIR /app
RUN echo "eula=true" > eula.txt
COPY --from=plugin-jdk8 /tmp/version.txt .
RUN export PLUGIN_VERSION=$(cat version.txt)
COPY --from=dependencies-jdk8 /tmp/spigot-1.16.4.jar .
COPY --from=dependencies-jdk8 /tmp/structureblocklib-bukkit-sample/build/libs/structureblocklib-bukkit-sample-$PLUGIN_VERSION.jar /app/plugins/
COPY ./structureblocklib-tools/world-1.14/ /app/
COPY ./structureblocklib-tools/ops.json /app/
# Port of the Minecraft Server.
EXPOSE 25565
# Port for Remote Debugging
EXPOSE 5005
CMD ["sh","-c","java -DIReallyKnowWhatIAmDoingISwear -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar spigot-1.16.4.jar"]
