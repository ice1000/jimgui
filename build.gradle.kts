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
  version = "v0.20.1"

  apply {
    plugin("java")
    plugin("signing")
    plugin("maven-publish")
  }

  repositories {
    mavenCentral()
    jcenter()
  }

  tasks.withType<JavaCompile>().configureEach {
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

  java {
    withSourcesJar()
    withJavadocJar()
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  tasks.withType<Javadoc>().configureEach {
    (options as StandardJavadocDocletOptions).tags(
        "apiNote:a:API Note:",
        "implSpec:a:Implementation Requirements:",
        "implNote:a:Implementation Note:")
  }

  tasks.withType<Jar>().configureEach {
    manifest.attributes("Automatic-Module-Name" to "ice1000.jimgui")
  }

  artifacts {
    add("archives", tasks["sourcesJar"])
    add("archives", tasks["javadocJar"])
  }

  publishing.repositories {
    maven("https://oss.sonatype.org/service/local/staging/deploy/maven2") {
      name = "MavenCentral"
      credentials {
        try {
          username = property("ossrhUsername").toString()
          password = property("ossrhPassword").toString()
        } catch (ignored: groovy.lang.MissingPropertyException) {
        }
      }
    }
  }

  publishing.publications {
    create<MavenPublication>("maven") {
      val githubUrl = "https://github.com/ice1000/jimgui"
      from(components["java"])
      groupId = "${project.group}"
      artifactId = "${rootProject.name}-${project.name}"
      version = "${project.version}"
      pom {
        description.set("Pure Java binding for dear-imgui")
        name.set(project.name)
        url.set(githubUrl)
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
        scm {
          connection.set("scm:git:$githubUrl")
          url.set(githubUrl)
        }
      }
    }
  }

  signing {
    sign(publishing.publications["maven"])
  }
}
