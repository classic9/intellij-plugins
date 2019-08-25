package tanvd.grazi

import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.exclude

fun ExternalModuleDependency.kotlinExcludes() {
    exclude("org.jetbrains.kotlin", "kotlin-stdlib")
    exclude("org.jetbrains.kotlin", "kotlin-stdlib-common")
}

fun ExternalModuleDependency.ltExcludes() {
    // already in project
    exclude("org.apache.commons", "commons-lang3")

    // already in IDEA
    exclude("org.slf4j", "slf4j-api")
    exclude("com.google.guava", "guava")
    exclude("com.intellij", "annotations")
    exclude("net.java.dev.jna", "jna")
    exclude("javax.xml.bind", "jaxb-api")
    exclude("org.glassfish.jaxb", "jaxb-runtime")
    exclude("com.fasterxml.jackson.core", "jackson-databind")
    exclude("commons-logging", "commons-logging")
    exclude("org.ow2.asm", "asm")

    // used only in languagetool-core tests
    exclude("org.apache.lucene", "lucene-core")
    exclude("org.apache.lucene", "lucene-backward-codecs")

    exclude("org.slf4j", "slf4j-api")
}

fun DependencyHandler.aetherDependencies() {
    _compile("org.eclipse.aether", "aether-connector-basic", "1.1.0")
    _compile("org.eclipse.aether", "aether-transport-file", "1.1.0")
    _compile("org.eclipse.aether", "aether-transport-http", "1.1.0") {
        exclude("org.slf4j", "slf4j-api")
        exclude("org.apache.httpcomponents", "httpclient")
    }
    _compile("org.apache.maven", "maven-aether-provider", "3.3.9") {
        exclude("org.slf4j", "slf4j-api")
        exclude("com.google.guava", "guava")
        exclude("org.apache.commons", "commons-lang3")
    }
}
