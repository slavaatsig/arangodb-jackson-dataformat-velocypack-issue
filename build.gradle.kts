import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.example.issue"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.3.41"
    application
    id("org.jetbrains.kotlin.plugin.noarg") version "1.3.41"
}

noArg {
    annotation("com.example.issue.NoArg")
    invokeInitializers = true
}

application {
    mainClassName = "com.example.issue.MainKt"
}

repositories {
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlin-eap")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    // Kotlin coroutines integration with JDK 8 (to support native futures)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.2.0")

    // ArangoDB Java async driver
    implementation("com.arangodb:arangodb-java-driver-async:5.0.7")

    // ArangoDB Velocypack plugin for Jackson
    implementation("com.arangodb:jackson-dataformat-velocypack:0.1.3")

    // Jackson Serialization
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.7")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
