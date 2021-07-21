plugins {
    java
    jacoco

    id("org.unbroken-dome.test-sets") version "4.0.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("com.diffplug.spotless") version "5.14.2"
    id("com.github.spotbugs") version "4.7.2"
    id("com.avast.gradle.docker-compose") version "0.14.5"
    id("com.github.ben-manes.versions") version "0.39.0"
}

repositories { mavenCentral() }

testSets { create("integrationTest") }

dependencies {
    val flinkVersion = "1.13.1"
    compileOnly("org.apache.flink:flink-java:$flinkVersion")
    compileOnly("org.apache.flink:flink-streaming-java_2.11:$flinkVersion")

    val junitVersion = "5.7.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.apache.flink:flink-test-utils_2.11:$flinkVersion")
    testImplementation("org.assertj:assertj-core:3.20.2")
    testImplementation("org.mockito:mockito-junit-jupiter:3.11.2")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntimeOnly("org.slf4j:slf4j-simple:1.7.31")

    val integrationTestImplementation by configurations
    integrationTestImplementation("com.mashape.unirest:unirest-java:1.4.9")
    integrationTestImplementation("org.awaitility:awaitility:4.1.0")
    integrationTestImplementation("com.github.docker-java:docker-java:3.2.11")
}

tasks {
    withType(Test::class).configureEach { useJUnitPlatform() }
    "jacocoTestReport"(JacocoReport::class) {
        reports {
            xml.isEnabled = true
            html.isEnabled = true
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
