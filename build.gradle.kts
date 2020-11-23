plugins {
	java
	`maven-publish`
	kotlin("jvm") version "1.4.20" apply false
	id("com.jfrog.bintray") version "1.8.5"
	id("de.undercouch.download") version "4.1.1" apply false
}

val isCI = !System.getenv("CI").isNullOrBlank()

allprojects {
	group = "org.ice1000.jimgui"
	version = "v0.11"

	apply { plugin("java") }

	repositories {
		mavenCentral()
		jcenter()
	}

	tasks.withType<JavaCompile>().configureEach {
		sourceCompatibility = "1.8"
		targetCompatibility = "1.8"
		options.apply {
			isDeprecation = true
			isWarnings = true
			isDebug = !isCI
			compilerArgs.add("-Xlint:unchecked")
		}
	}

	if (org.ice1000.gradle.isMac) tasks.withType<JavaExec>().configureEach {
		jvmArgs("-XstartOnFirstThread")
	}

	tasks.jar {
		manifest.attributes("Automatic-Module-Name" to "ice1000.jimgui")
	}

	val sourcesJar = tasks.register<Jar>("sourcesJar") {
		group = tasks.jar.get().group
		from(sourceSets["main"].allJava)
		archiveClassifier.set("sources")
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
			setLicenses("Apache-2.0")
			setLabels("binding", "imgui", "kotlin")
			version.apply {
				vcsTag = "${project.version}"
				name = vcsTag
				websiteUrl = "https://github.com/ice1000/jimgui/releases/tag/$vcsTag"
			}
		}
	}

	publishing.publications {
		create<MavenPublication>("maven") {
			from(components["java"])
			groupId = "${project.group}"
			artifactId = "${rootProject.name}-${project.name}"
			version = "${project.version}"
			artifact(tasks.named("sourcesJar"))
			pom {
				description.set("Pure Java binding for dear-imgui")
				name.set(project.name)
				url.set("https://github.com/ice1000/jimgui")
				licenses {
					license {
						name.set("The Apache License, Version 2.0")
						url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
					}
				}
				developers {
					developer {
						id.set("ice1000")
						name.set("Tesla Ice Zhang")
						email.set("ice1000kotlin@foxmail.com")
					}
				}
			}
		}
	}
}
