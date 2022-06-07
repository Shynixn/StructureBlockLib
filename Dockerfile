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
RUN java -jar BuildTools.jar --rev 1.13.2
RUN java -jar BuildTools.jar --rev 1.14.4
RUN java -jar BuildTools.jar --rev 1.15
RUN java -jar BuildTools.jar --rev 1.16.4
CMD ["sh","-c","/bin/bash"]

# 2. Resolve minecraft-dependencies for 1.17 - 1.18 - latest with jdk17
FROM amazoncorretto:17 AS dependencies-jdk17
WORKDIR /tmp
RUN yum update -y
RUN yum install maven -y
RUN yum install wget -y
RUN yum install git -y
RUN wget "https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar"
RUN java -jar BuildTools.jar --rev 1.17.1 --remapped
RUN java -jar BuildTools.jar --rev 1.18 --remapped
RUN java -jar BuildTools.jar --rev 1.18.2 --remapped
RUN java -jar BuildTools.jar --rev 1.19 --remapped

# 3. Build plugin for 1.8 - 1.17 with jdk17
FROM amazoncorretto:17 AS plugin-jdk17
WORKDIR /tmp
RUN yum update -y
RUN yum install maven -y
RUN yum install dos2unix -y
COPY --from=dependencies-jdk8 /root/.m2/repository/org/spigotmc /root/.m2/repository/org/spigotmc/
COPY --from=dependencies-jdk17 /root/.m2/repository/org/spigotmc /root/.m2/repository/org/spigotmc/
COPY . /tmp
RUN chmod +x gradlew
RUN dos2unix gradlew
RUN ./gradlew build pluginJar --no-daemon

# 4. Launch a minecraft server with jdk17 and plugin
FROM amazoncorretto:17
# Change to the current plugin version present in build.gradle
ENV PLUGIN_VERSION=2.8.0
# Change to the server version you want to test.
ENV SERVER_VERSION=spigot-1.19.jar
# Port of the Minecraft Server.
EXPOSE 25565
# Port for Remote Debugging
EXPOSE 5005
WORKDIR /app
RUN yum update -y
RUN echo "eula=true" > eula.txt && mkdir plugins
COPY ./structureblocklib-tools/world-1.14 /app/
COPY ./structureblocklib-tools/ops.json /app/
# For < 1.18 COPY --from=dependencies-jdk17 /root/.m2/repository/org/spigotmc/spigot/$SERVER_VERSION /app/spigot.jar
COPY --from=dependencies-jdk17 /tmp/$SERVER_VERSION /app/spigot.jar
COPY --from=plugin-jdk17 /tmp/structureblocklib-bukkit-sample/build/libs/structureblocklib-bukkit-sample-$PLUGIN_VERSION.jar /app/plugins/Structureblocklib.jar
CMD ["sh","-c","java -DIReallyKnowWhatIAmDoingISwear -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar spigot.jar"]
