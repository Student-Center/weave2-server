import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    implementation(project(":support:common"))
    implementation(project(":domain"))

    implementation(libs.spring.tx)
    implementation(libs.spring.boot.core)

    testFixturesImplementation(testFixtures(project(":domain")))
}
