rootProject.name="reactive-zip-operator-demo"

dependencyResolutionManagement {
    versionCatalogs {
        create("tools") {
            version("kotlin", "1.7.10")
            version("jvm", "17")

            plugin("kotlin-lang", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-kapt", "org.jetbrains.kotlin.kapt").versionRef("kotlin")
            plugin("kotlin-allopen", "org.jetbrains.kotlin.plugin.allopen").versionRef("kotlin")
            plugin("shadow", "com.github.johnrengelman.shadow").version("7.1.2")

            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").versionRef("kotlin")
            library("kotlin-stdlib", "org.jetbrains.kotlin", "kotlin-stdlib-jdk8").versionRef("kotlin")
        }

        create("libs") {
            version("micronaut", "3.7.1")
            plugin("micronaut", "io.micronaut.application").version("3.6.2")
            library("kotlinlogging", "io.github.microutils:kotlin-logging-jvm:3.0.0")
            library("logback", "ch.qos.logback:logback-classic:1.4.1")
            library("rome", "com.rometools:rome:1.18.0")
            library("reactor-kotlin", "io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.7")
        }
    }
}