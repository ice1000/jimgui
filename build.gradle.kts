import org.ice1000.gradle.*

plugins {
	java
	maven
	`maven-publish`
	kotlin("jvm") version "1.2.41" apply false
	id("org.sonarqube") version "2.6.2"
}

var isCI: Boolean by extra
isCI = !System.getenv("CI").isNullOrBlank()

allprojects {
	group = "org.ice1000.jimgui"
	version = "v0.1"

	apply {
		plugin("java")
		plugin("maven")
	}

	repositories {
		mavenCentral()
		jcenter()
		maven("https://jitpack.io")
	}

	tasks.withType<JavaCompile> {
		sourceCompatibility = "1.8"
		targetCompatibility = "1.8"
		options.apply {
			isDeprecation = true
			isWarnings = true
			isDebug = !isCI
			compilerArgs.add("-Xlint:unchecked")
		}
	}

	val sourcesJar = task<Jar>("sourcesJar") {
		group = tasks["jar"].group
		from(java.sourceSets["main"].allSource)
		classifier = "sources"
	}

	artifacts { add("archives", sourcesJar) }
}
