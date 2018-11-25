import com.github.spotbugs.SpotBugsTask

plugins {
    java
    jacoco

    id("org.unbroken-dome.test-sets") version "2.0.2"
    id("com.github.johnrengelman.shadow") version "4.0.3"
    id("com.diffplug.gradle.spotless") version "3.16.0"
    id("com.github.spotbugs") version "1.6.5"
    id("com.avast.gradle.docker-compose") version "0.8.12"
    id("com.github.ben-manes.versions") version "0.20.0"
}

repositories { jcenter() }

testSets { create("integrationTest") }

dependencies {
    compileOnly("org.apache.flink:flink-java:1.6.2")
    compileOnly("org.apache.flink:flink-streaming-java_2.11:1.6.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation("org.apache.flink:flink-test-utils_2.11:1.6.2")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("org.mockito:mockito-junit-jupiter:2.23.4")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testRuntimeOnly("org.slf4j:slf4j-nop:1.7.25")

    val integrationTestImplementation by configurations
    integrationTestImplementation("com.mashape.unirest:unirest-java:1.4.9")
    integrationTestImplementation("org.awaitility:awaitility:3.1.3")
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
