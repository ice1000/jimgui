plugins {
  java
  `maven-publish`
  signing
  kotlin("jvm") version "1.4.30" apply false
  id("de.undercouch.download") version "4.1.1" apply false
}

val isCI = !System.getenv("CI").isNullOrBlank()

subprojects {
  group = "org.ice1000.jimgui"
  version = "v0.19.0"

  apply {
    plugin("java")
    plugin("maven-publish")
  }

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
      options.encoding = "UTF-8"
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

  tasks.withType<Jar>().configureEach {
    archiveBaseName.set("jimgui-" + archiveBaseName.get())
  }

  artifacts { add("archives", sourcesJar) }

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
            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
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
