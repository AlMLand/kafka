import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

group = "com.AlMLand"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
ext["kafka.connect-api.version"] = "3.4.0"
ext["kafka.connect-utils.version"] = "0.7.177"
ext["slf4j.version"] = "2.0.6"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.apache.kafka:connect-api:${property("kafka.connect-api.version")}")
    implementation("com.github.jcustenborder.kafka.connect:connect-utils:${property("kafka.connect-utils.version")}")
    implementation("org.slf4j:slf4j-api:${property("slf4j.version")}")
    implementation("org.slf4j:slf4j-simple:${property("slf4j.version")}")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
