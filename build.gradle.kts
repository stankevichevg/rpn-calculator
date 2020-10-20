import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import java.math.BigDecimal.valueOf
import java.util.Collections.singletonList

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    idea
    java
    `java-library`
    checkstyle
    jacoco
    eclipse
    `maven-publish`
}

defaultTasks("clean", "build")

project.group = "com.xxx"
project.version = file("version.txt").readText(Charsets.UTF_8).trim()

val Project.gprUsername: String? get() = this.properties["gprUsername"] as String?
val Project.gprPassword: String? get() = this.properties["gprPassword"] as String?

tasks.jar {
    enabled = false
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        mavenLocal()
    }
}

object Versions {
    const val checkstyle = "8.36.2"
    const val hamcrest = "2.2"
    const val mockito = "3.2.4"
    const val jacoco = "0.8.2"
    const val junit5 = "5.6.0"
    const val logback = "1.2.3"
}

subprojects {

    apply(plugin = "idea")
    apply(plugin = "java")
    apply(plugin = "checkstyle")
    apply(plugin = "jacoco")
    apply(plugin = "eclipse")
    apply(plugin = "maven-publish")

    group = project.group
    version = project.version

    dependencies {
        testImplementation("org.hamcrest", "hamcrest", Versions.hamcrest)
        testImplementation("org.mockito", "mockito-junit-jupiter", Versions.mockito)
        testImplementation("org.junit.jupiter", "junit-jupiter-api", Versions.junit5)
        testImplementation("org.junit.jupiter", "junit-jupiter-params", Versions.junit5)
        testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", Versions.junit5)

        implementation("ch.qos.logback", "logback-classic", Versions.logback)

    }

    tasks.jar {
        enabled = true
    }

    checkstyle {
        toolVersion = Versions.checkstyle
        sourceSets = singletonList(project.sourceSets.main.get())
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
        finalizedBy("jacocoTestReport")
    }
    jacoco {
        toolVersion = Versions.jacoco
    }
    tasks.jacocoTestReport {
        reports {
            xml.isEnabled = true
        }
    }

    tasks.compileJava {
        options.encoding = "UTF-8"
        options.isDeprecation = true
    }
    tasks.compileTestJava {
        options.encoding = "UTF-8"
        options.isDeprecation = true
    }
    tasks.test {
        testLogging {
            showStandardStreams = true
            exceptionFormat = TestExceptionFormat.FULL
        }
    }

    tasks.javadoc {
        title = "<h1>RPN Calculator</h1>"
    }

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/stankevichevg/rpn-calculator")
                credentials {
                    username = project.gprUsername
                    password = project.gprPassword
                }
            }
        }
        publications {
            create<MavenPublication>("RpnCalculator") {
                from(components["java"])
                pom {
                    name.set("RPN Calculator")
                    description.set(
                        """
                            RPN Calculator
                        """
                    )
                    url.set("https://github.com/stankevichevg/rpn-calculator.git")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("stiachevg")
                            name.set("Evgenii Stankevich")
                            email.set("stankevich.evg@gmail.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/stankevichevg/rpn-calculator.git")
                        developerConnection.set("scm:git:ssh://github.com/stankevichevg/rpn-calculator.git")
                        url.set("https://github.com/stankevichevg/rpn-calculator")
                    }
                }
            }
        }
    }
}

val jacocoAggregateMerge by tasks.creating(JacocoMerge::class) {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    executionData(
        project(":rpn-calculator-engine").buildDir.absolutePath + "/jacoco/test.exec",
        project(":rpn-calculator-command-line").buildDir.absolutePath + "/jacoco/test.exec"
    )
    dependsOn(
        ":rpn-calculator-engine:test",
        ":rpn-calculator-command-line:test"
    )
}

@Suppress("UnstableApiUsage")
val jacocoAggregateReport by tasks.creating(JacocoReport::class) {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    executionData(jacocoAggregateMerge.destinationFile)
    reports {
        xml.isEnabled = true
    }
    additionalClassDirs(files(subprojects.flatMap { project ->
        listOf("java", "kotlin").map { project.buildDir.path + "/classes/$it/main" }
    }))
    additionalSourceDirs(files(subprojects.flatMap { project ->
        listOf("java", "kotlin").map { project.file("src/main/$it").absolutePath }
    }))
    dependsOn(jacocoAggregateMerge)
}

tasks {
    jacocoTestCoverageVerification {
        executionData.setFrom(jacocoAggregateMerge.destinationFile)
        violationRules {
            rule {
                limit {
                    minimum = valueOf(0.0)
                }
            }
        }
        additionalClassDirs(files(subprojects.flatMap { project ->
            listOf("java", "kotlin").map { project.buildDir.path + "/classes/$it/main" }
        }))
        additionalSourceDirs(files(subprojects.flatMap { project ->
            listOf("java", "kotlin").map { project.file("src/main/$it").absolutePath }
        }))
        dependsOn(jacocoAggregateReport)
    }
    check {
        finalizedBy(jacocoTestCoverageVerification)
    }
}

project(":rpn-calculator-engine") {
    apply(plugin = "java-library")
}

project(":rpn-calculator-command-line") {
    dependencies {
        implementation(project(":rpn-calculator-engine"))
    }
}

tasks.register<Copy>("copyTestLogs") {
    from(".")
    include("**/build/test-output/**")
    include("**/*.log")
    exclude("build")
    into("build/test_logs")
    includeEmptyDirs = false
}