// jar options
tasks.jar { enabled = false }
tasks.bootJar { enabled = false }
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    implementation {
        // 동일 모듈에 대한 버전 충돌시 즉시 오류 발생하고 실패
        resolutionStrategy.failOnVersionConflict()
        // 변하는 모듈(Changing Module)을 캐시하지 않음
        resolutionStrategy.cacheChangingModulesFor(0, "seconds")
    }
}

// Plugins - settings.gradle.kts
plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.asciidoctor.jvm.convert")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

// Define Submodules Variables
val apiModules = listOf(project("api"))
val domainModules = listOf(project("domain"))
val batchModules = listOf(project("batch"))
val infraModules = listOf(project("infra"))

allprojects {
    group = "com.blog.kiwi"
    version = "0.0.1-SNAPSHOT"

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

// Common
subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    allOpen{
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
        annotation("javax.persistence.Embeddable")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("javax.servlet:javax.servlet-api:4.0.1")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // logging
        implementation("net.logstash.logback:logstash-logback-encoder:6.6")

        implementation("org.apache.commons:commons-lang3:3.12.0")

        // Test
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }

        // Annotation Processing Tool
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    }

    // src/main/resources 디렉토리에 위치한 리소스 파일들을 빌드 아티팩트에 포함
    // 중복 발생시 덮어 쓰기
    tasks.processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}

configure(apiModules) {
    apply(plugin = "org.asciidoctor.jvm.convert")

    dependencies {
        testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    }

    // Asciidoctor
    tasks {
        val snippetsDir by extra { file("build/generated-snippets") }

        test {
            useJUnitPlatform()
            systemProperty("org.springframework.restdocs.outputDir", snippetsDir)
            outputs.dir(snippetsDir)
        }

        build {
            dependsOn("copyDocument")
        }

        asciidoctor {
            inputs.dir(snippetsDir)

            dependsOn(test)

            doFirst {
                delete("src/main/resources/static/docs")
            }
        }

        register<Copy>("copyDocument") {
            dependsOn(asciidoctor)

            destinationDir = file(".")
            from(asciidoctor.get().outputDir) {
                into("src/main/resources/static/docs")
            }
        }

        bootJar {
            dependsOn(asciidoctor)

            from(asciidoctor.get().outputDir) {
                into("BOOT-INF/classes/static/docs")
            }
        }
    }
}

configure(batchModules) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-batch")
        implementation("net.javacrumbs.shedlock:shedlock-spring:4.42.0")
        implementation("net.javacrumbs.shedlock:shedlock-provider-jdbc-template:4.42.0")
    }
}

configure(infraModules) {
    dependencies {
        // h2 Database
        implementation("com.h2database:h2")
    }
}

// Dependency Relation
project(":api") {
    dependencies {
        implementation(project(":core"))
        implementation(project(":domain"))
        implementation(project(":batch"))
        implementation(project(":infra"))
    }
}

project(":batch") {
    dependencies {
        implementation(project(":core"))
        implementation(project(":domain:"))
        implementation(project(":infra"))
    }
}

project(":domain") {
    dependencies {
        implementation(project(":core"))
    }
}

project(":infra") {
    dependencies {
        implementation(project(":core"))
        implementation(project(":domain"))
    }
}
