@Suppress("DSL_SCOPE_VIOLATION") // workaround for IntelliJ bug with Gradle Version Catalogs DSL in plugins
plugins {
    alias(tools.plugins.kotlin.lang)
    alias(tools.plugins.kotlin.kapt)
    alias(tools.plugins.kotlin.allopen)
    alias(tools.plugins.shadow)
    alias(libs.plugins.micronaut)
}

version = "0.1"
group = "tech.zone84.examples.zipoperator"

repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation(tools.kotlin.reflect)
    implementation(tools.kotlin.stdlib)
    implementation(libs.kotlinlogging)
    implementation(libs.rome)
    implementation(libs.reactor.kotlin)
    runtimeOnly(libs.logback)
    implementation("io.micronaut:micronaut-validation")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}

application {
    mainClass.set("tech.zone84.examples.zipoperator.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion(tools.versions.jvm.get())
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = tools.versions.jvm.get()
        }
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    version(libs.versions.micronaut.get())
    runtime("netty")
    processing {
        incremental(true)
        annotations("tech.zone84.examples.zipoperator.*")
    }
}



