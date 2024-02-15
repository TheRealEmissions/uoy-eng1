plugins {
    id("java")
}

group = "uk.ac.york.student"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

var targetJavaVersion = 11
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain {
            languageVersion = JavaLanguageVersion.of(targetJavaVersion)
        }
    }
}

tasks.withType(JavaCompile::class).configureEach {
    options.encoding = "UTF-8"
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release = targetJavaVersion
    }
}

tasks.test {
    useJUnitPlatform()
}