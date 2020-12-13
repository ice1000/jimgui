plugins { java; application }
sourceSets.main { java.srcDir("src") }
repositories.jcenter()
dependencies.implementation(project(":core"))
application.mainClass.set("dlparty.BigCollection")

tasks.register<Jar>("fatJar") {
  group = "build"
  manifest.attributes["Main-Class"] = application.mainClass.get()
  dependsOn(configurations.runtimeClasspath)
  from({ configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) } })
  from(sourceSets.main.get().output)
  archiveClassifier.set("full")
}
