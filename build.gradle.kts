import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    java
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.spotless)
    checkstyle
    jacoco
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    runtimeOnly(libs.h2)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.starter.data.jpa.test)
}

checkstyle {
    toolVersion = libs.versions.checkstyle.get()
    configFile = file("config/checkstyle/checkstyle.xml")
}

jacoco {
    toolVersion = libs.versions.jacoco.get()
}

configure<SpotlessExtension> {
    java {
        target("src/*/java/**/*.java")
        googleJavaFormat("1.36.1")
        removeUnusedImports()
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

tasks.register("format") {
    group = "verification"
    description = "Auto-format code with Spotless."
    dependsOn("spotlessApply")
}

tasks.register("lint") {
    group = "verification"
    description = "Run Spotless check and Checkstyle."
    dependsOn("spotlessCheck", "checkstyleMain", "checkstyleTest")
}

tasks.named("check") {
    dependsOn("lint", tasks.jacocoTestReport)
}
