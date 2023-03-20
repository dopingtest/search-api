rootProject.name = "blog"

pluginManagement {
    val springBootVersion: String by settings
    val dependencyManagementVersion: String by settings
    val kotlinVersion: String by settings
    val asciiDoctorVersion: String by settings

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version dependencyManagementVersion
        id("org.asciidoctor.jvm.convert") version asciiDoctorVersion
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
    }
}

/** Structure */
include("core", "api", "domain", "infra", "batch")