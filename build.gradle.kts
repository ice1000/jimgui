import com.jfrog.bintray.gradle.*

plugins {
	java
	`maven-publish`
	kotlin("jvm") version "1.2.50" apply false
	id("com.jfrog.bintray") version "1.7.3"
	id("de.undercouch.download") version "3.4.2" apply false
	id("org.sonarqube") version "2.6.2"
}

var isCI: Boolean by extra
isCI = !System.getenv("CI").isNullOrBlank()

allprojects {
	group = "org.ice1000.jimgui"
	version = "v0.4-3"

	apply { plugin("java") }

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

subprojects {
	apply {
		plugin("maven")
		plugin("maven-publish")
		plugin("com.jfrog.bintray")
	}

	bintray {
		user = "ice1000"
		key = findProperty("key").toString()
		setConfigurations("archives")
		pkg.apply {
			name = rootProject.name
			repo = "ice1000"
			githubRepo = "ice1000/jimgui"
			publicDownloadNumbers = true
			vcsUrl = "https://github.com/ice1000/jimgui.git"
			version.apply {
				vcsTag = "${project.version}"
				name = vcsTag
				websiteUrl = "https://github.com/ice1000/jimgui/releases/tag/$vcsTag"
			}
		}
	}

	publishing {
		(publications) {
			"mavenJava"(MavenPublication::class) {
				from(components["java"])
				groupId = project.group.toString()
				artifactId = "${rootProject.name}-${project.name}"
				version = project.version.toString()
				artifact(tasks["sourcesJar"])
				pom.withXml {
					val root = asNode()
					root.appendNode("description", "Pure Java binding for dear-imgui")
					root.appendNode("name", project.name)
					root.appendNode("url", "https://github.com/ice1000/jimgui")
					root.children().last()
				}
			}
		}
	}
}
