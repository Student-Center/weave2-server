plugins {
    id(libs.plugins.openapi.generator.get().pluginId) version libs.plugins.openapi.generator.get().version.toString()
}


dependencies {
    apply(plugin = rootProject.libs.plugins.openapi.generator.get().pluginId)

    implementation(project(":support:common"))

    implementation(project(":domain"))
    implementation(project(":application"))
    implementation(project(":infrastructure:jpa"))

    implementation(libs.spring.boot.starter.web)

    developmentOnly(libs.spring.boot.devtools)
    developmentOnly(libs.spring.boot.docker.compose)
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpecRootDirectory.set("$rootDir/openapi")
    outputDir.set("$rootDir/${layout.buildDirectory}/generated")
    apiPackage.set("com.sc.weave2.oas.api")
    modelPackage.set("com.sc.weave2.oas.model")
    configOptions.set(mapOf(
        "interfaceOnly" to "true",
        "useTags" to "true"
    ))
    openapiGeneratorIgnoreList.add("./.gitignore")
}
