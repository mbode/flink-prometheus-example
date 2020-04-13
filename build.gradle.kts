plugins {
    java
    jacoco

    id("org.unbroken-dome.test-sets") version "3.0.1"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("com.diffplug.gradle.spotless") version "3.28.1"
    id("com.github.spotbugs") version "4.0.5"
    id("com.avast.gradle.docker-compose") version "0.10.10"
    id("com.github.ben-manes.versions") version "0.28.0"
}

repositories { jcenter() }

testSets { create("integrationTest") }

dependencies {
    val flinkVersion = "1.10.0"
    compileOnly("org.apache.flink:flink-java:$flinkVersion")
    compileOnly("org.apache.flink:flink-streaming-java_2.11:$flinkVersion")

    val junitVersion = "5.6.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.apache.flink:flink-test-utils_2.11:$flinkVersion")
    testImplementation("org.assertj:assertj-core:3.15.0")
    testImplementation("org.mockito:mockito-junit-jupiter:3.3.3")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntimeOnly("org.slf4j:slf4j-simple:1.7.30")

    val integrationTestImplementation by configurations
    integrationTestImplementation("com.mashape.unirest:unirest-java:1.4.9")
    integrationTestImplementation("org.awaitility:awaitility:4.0.2")
    integrationTestImplementation("com.github.docker-java:docker-java:3.2.1")
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

dockerCompose { isRequiredBy(tasks["integrationTest"]) }

spotless {
    java { googleJavaFormat() }
    kotlinGradle { ktlint() }
}
