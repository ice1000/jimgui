import com.jfrog.bintray.gradle.*

plugins {
	java
	`maven-publish`
	kotlin("jvm") version "1.3.60" apply false
	id("com.jfrog.bintray") version "1.8.4"
	id("de.undercouch.download") version "4.0.2" apply false
}

var isCI: Boolean by extra
isCI = !System.getenv("CI").isNullOrBlank()

allprojects {
	group = "org.ice1000.jimgui"
	version = "v0.10"

	apply { plugin("java") }

	repositories {
		mavenCentral()
		jcenter()
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
		from(sourceSets["main"].allJava)
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
			create<MavenPublication>("maven") {
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
