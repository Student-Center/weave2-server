import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("jacoco")
    id("jacoco-report-aggregation")
    id("java-test-fixtures")
    id(libs.plugins.sonarqube.get().pluginId) version libs.plugins.sonarqube.get().version.toString()
    id(libs.plugins.spring.boot.get().pluginId) version libs.plugins.spring.boot.get().version.toString()
    id(libs.plugins.spring.dependency.management.get().pluginId) version libs.plugins.spring.dependency.management.get().version.toString()
    id(libs.plugins.kotlin.jvm.get().pluginId) version libs.plugins.kotlin.jvm.get().version.toString()
    id(libs.plugins.kotlin.jpa.get().pluginId) version libs.plugins.kotlin.jpa.get().version.toString()
    id(libs.plugins.kotlin.spring.get().pluginId) version libs.plugins.kotlin.spring.get().version.toString()
}

allprojects {
    group = "com.sc.weave2"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "jacoco")
    apply(plugin = "java-test-fixtures")
    apply(plugin = "org.sonarqube")
    apply(plugin = "idea")
    apply(plugin = rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    apply(plugin = rootProject.libs.plugins.kotlin.jpa.get().pluginId)
    apply(plugin = rootProject.libs.plugins.kotlin.spring.get().pluginId)
    apply(plugin = rootProject.libs.plugins.kotlin.kapt.get().pluginId)
    apply(plugin = rootProject.libs.plugins.kotlin.noarg.get().pluginId)
    apply(plugin = rootProject.libs.plugins.kotlin.allopen.get().pluginId)
    apply(plugin = rootProject.libs.plugins.spring.boot.get().pluginId)
    apply(plugin = rootProject.libs.plugins.spring.dependency.management.get().pluginId)

    dependencies {

        testImplementation(kotlin("test"))
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(rootProject.libs.versions.java.get()))
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    tasks.withType<KotlinCompile> {
        compilerOptions {
            freeCompilerArgs.add("-Xjsr305=strict")
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
