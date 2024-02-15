plugins {
    id("java")
}

group = "uk.ac.york.student"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.9.1"
val lombokVersion = "1.18.30"

dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
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

// configure jar
tasks.jar {
    manifest {
        attributes["Main-Class"] = "uk.ac.york.student.Main"
    }
    from(sourceSets.main.get().output)
}

tasks.withType(JavaCompile::class).configureEach {
    options.encoding = "UTF-8"
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release = targetJavaVersion
    }
}

sourceSets.main {
    java {
        srcDir("src/main/java")
    }
    resources {
        srcDir("src/main/resources")
    }
}

tasks.test {
    useJUnitPlatform()
}