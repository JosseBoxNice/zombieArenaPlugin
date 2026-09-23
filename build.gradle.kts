repositories {
  maven {
    name = "papermc"
    url = uri("https://repo.papermc.io/repository/maven-public/")
  }
}

dependencies {
  compileOnly("io.papermc.paper:paper-api:26.3.build.+")
}

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

plugins {
  id("xyz.jpenilla.run-paper") version "3.1.0"
  java
}

group = "io.github.josseboxnice"
version = "1.0.1"

tasks {
  jar {
    archiveFileName.set("ZombieArenaPlugin-${project.version}.jar")
  }

  processResources {
      filesMatching("plugin.yml") {
          expand("version" to project.version)
      }
  }
  runServer {
    // Configure the Minecraft version for our task.
    // This is the only required configuration besides applying the plugin.
    // Your plugin's jar (or shadowJar if present) will be used automatically.
    minecraftVersion("26.3")
  }
}

