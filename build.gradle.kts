import com.github.spotbugs.SpotBugsTask

plugins {
    java
    jacoco

    id("org.unbroken-dome.test-sets") version "2.1.1"
    id("com.github.johnrengelman.shadow") version "5.0.0"
    id("com.diffplug.gradle.spotless") version "3.19.0"
    id("com.github.spotbugs") version "1.6.11"
    id("com.avast.gradle.docker-compose") version "0.9.1"
    id("com.github.ben-manes.versions") version "0.21.0"
}

repositories { jcenter() }

testSets { create("integrationTest") }

dependencies {
    val flinkVersion = "1.7.2"
    compileOnly("org.apache.flink:flink-java:$flinkVersion")
    compileOnly("org.apache.flink:flink-streaming-java_2.11:$flinkVersion")

    val junitVersion = "5.4.0"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.apache.flink:flink-test-utils_2.11:$flinkVersion")
    testImplementation("org.assertj:assertj-core:3.12.1")
    testImplementation("org.mockito:mockito-junit-jupiter:2.25.0")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntimeOnly("org.slf4j:slf4j-simple:1.7.25")

    val integrationTestImplementation by configurations
    integrationTestImplementation("com.mashape.unirest:unirest-java:1.4.9")
    integrationTestImplementation("org.awaitility:awaitility:3.1.6")
    integrationTestImplementation("com.github.docker-java:docker-java:3.1.1")
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
