import com.github.spotbugs.SpotBugsTask

plugins {
    java
    jacoco

    id("org.unbroken-dome.test-sets") version "2.0.3"
    id("com.github.johnrengelman.shadow") version "4.0.3"
    id("com.diffplug.gradle.spotless") version "3.16.0"
    id("com.github.spotbugs") version "1.6.8"
    id("com.avast.gradle.docker-compose") version "0.8.12"
    id("com.github.ben-manes.versions") version "0.20.0"
}

repositories { jcenter() }

testSets { create("integrationTest") }

dependencies {
    val flinkVersion = "1.7.1"
    compileOnly("org.apache.flink:flink-java:$flinkVersion")
    compileOnly("org.apache.flink:flink-streaming-java_2.11:$flinkVersion")

    val junitVersion = "5.3.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.apache.flink:flink-test-utils_2.11:$flinkVersion")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("org.mockito:mockito-junit-jupiter:2.23.4")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntimeOnly("org.slf4j:slf4j-simple:1.7.25")

    val integrationTestImplementation by configurations
    integrationTestImplementation("com.mashape.unirest:unirest-java:1.4.9")
    integrationTestImplementation("org.awaitility:awaitility:3.1.5")
    integrationTestImplementation("com.github.docker-java:docker-java:3.0.14")
}

tasks {
    withType(Test::class).configureEach { useJUnitPlatform() }
    "composeUp" { dependsOn("shadowJar") }
    withType(SpotBugsTask::class).configureEach {
        reports {
            xml.isEnabled = false
            html.isEnabled = true
        }
    }
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
