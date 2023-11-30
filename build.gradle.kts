plugins {
    java
    jacoco

    id("jvm-test-suite")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.diffplug.spotless") version "6.23.0"
    id("com.github.spotbugs") version "5.2.5"
    id("com.avast.gradle.docker-compose") version "0.17.5"
    id("com.github.ben-manes.versions") version "0.50.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories { mavenCentral() }

val flinkVersion = "1.18.0"

dependencies {
    compileOnly("org.apache.flink:flink-java:$flinkVersion")
    compileOnly("org.apache.flink:flink-streaming-java:$flinkVersion")

    val junitVersion = "5.10.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.apache.flink:flink-test-utils:$flinkVersion")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.mockito:mockito-junit-jupiter:5.7.0")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.9")
}

testing {
    suites {
        configureEach {
            if (this is JvmTestSuite) {
                dependencies {
                    implementation("org.assertj:assertj-core:3.24.2")
                }
            }
        }

        val test by getting(JvmTestSuite::class) {
            dependencies {
                val junitVersion = "5.9.3"
                implementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
                implementation("org.apache.flink:flink-test-utils:$flinkVersion")

                implementation("org.mockito:mockito-junit-jupiter:5.7.0")

                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
                runtimeOnly("org.slf4j:slf4j-simple:2.0.9")
            }
        }
        register<JvmTestSuite>("integrationTest") {
            dependencies {
                implementation("com.mashape.unirest:unirest-java:1.4.9")
                implementation("org.awaitility:awaitility:4.2.0")
                implementation("com.github.docker-java:docker-java:3.3.4")
            }
        }
    }
}

tasks {
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
    setProjectName(null)
}

spotless {
    java { googleJavaFormat() }
    kotlinGradle { ktlint() }
}
