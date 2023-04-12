plugins {
    id("java-library")
    id("net.labymod.gradle")
    id("net.labymod.gradle.addon")
}

group = "com.rettichlp.unicacityaddon"
version = "2.0.0-alpha.3"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

labyMod {
    defaultPackageName = "com.rettichlp.unicacityaddon" //change this to your main package name (used by all modules)
    addonInfo {
        namespace = "unicacityaddon"
        displayName = "UnicacityAddon"
        author = "RettichLP & Dimiikou"
        description = "This addon is a modification for UnicaCity providing specialized and nice-to-have features and utilities for everyday gameplay."
        minecraftVersion = "*"
        version = System.getenv().getOrDefault("VERSION", "2.0.0-alpha.3")
    }

    minecraft {
        registerVersions(
                "1.8.9",
                "1.12.2",
                "1.16.5",
                "1.17.1",
                "1.18.2",
                "1.19.2",
                "1.19.3",
                "1.19.4"
        ) { version, provider ->
            configureRun(provider, version)
        }

        subprojects.forEach {
            if (it.name != "game-runner") {
                filter(it.name)
            }
        }
    }

    addonDev {
        snapshotRelease()
    }
}

subprojects {
    plugins.apply("java-library")
    plugins.apply("net.labymod.gradle")
    plugins.apply("net.labymod.gradle.addon")

    repositories {
        maven("https://libraries.minecraft.net/")
        maven("https://repo.spongepowered.org/repository/maven-public/")
    }
}

fun configureRun(provider: net.labymod.gradle.core.minecraft.provider.VersionProvider, gameVersion: String) {
    provider.runConfiguration {
        mainClass = "net.minecraft.launchwrapper.Launch"
        jvmArgs("-Dnet.labymod.running-version=${gameVersion}")
        jvmArgs("-Dmixin.debug=true")
        jvmArgs("-Dnet.labymod.debugging.all=true")
        jvmArgs("-Dmixin.env.disableRefMap=true")

        args("--tweakClass", "net.labymod.core.loader.vanilla.launchwrapper.LabyModLaunchWrapperTweaker")
        args("--labymod-dev-environment", "true")
        args("--addon-dev-environment", "true")
    }

    provider.javaVersion = when (gameVersion) {
        "1.8.9", "1.12.2", "1.16.5" -> {
            JavaVersion.VERSION_1_8
        }

        "1.17.1" -> {
            JavaVersion.VERSION_16
        }

        else -> {
            JavaVersion.VERSION_17
        }
    }

    provider.mixin {
        val mixinMinVersion = when (gameVersion) {
            "1.8.9", "1.12.2", "1.16.5" -> {
                "0.6.6"
            }

            else -> {
                "0.8.2"
            }
        }

        minVersion = mixinMinVersion
    }
}