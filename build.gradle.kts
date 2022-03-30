plugins {
    java
    jacoco

    id("org.unbroken-dome.test-sets") version "4.0.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.diffplug.spotless") version "6.4.1"
    id("com.github.spotbugs") version "5.0.6"
    id("com.avast.gradle.docker-compose") version "0.15.2"
    id("com.github.ben-manes.versions") version "0.42.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories { mavenCentral() }

testSets { create("integrationTest") }

dependencies {
    val flinkVersion = "1.14.3"
    compileOnly("org.apache.flink:flink-java:$flinkVersion")
    compileOnly("org.apache.flink:flink-streaming-java_2.11:$flinkVersion")

    val junitVersion = "5.8.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.apache.flink:flink-test-utils_2.11:$flinkVersion")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.4.0")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntimeOnly("org.slf4j:slf4j-simple:1.7.36")

    val integrationTestImplementation by configurations
    integrationTestImplementation("com.mashape.unirest:unirest-java:1.4.9")
    integrationTestImplementation("org.awaitility:awaitility:4.2.0")
    integrationTestImplementation("com.github.docker-java:docker-java:3.2.13")
}

tasks {
    withType(Test::class).configureEach { useJUnitPlatform() }
    "jacocoTestReport"(JacocoReport::class) {
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }
    "check" { dependsOn("jacocoTestReport") }
}

dockerCompose {
    isRequiredBy(tasks["integrationTest"])
    projectName = null
}

spotless {
    java { googleJavaFormat() }
    kotlinGradle { ktlint() }
}
