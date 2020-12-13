plugins { java }
sourceSets {
  main {
    java.srcDir("src")
    resources.srcDir("res")
  }
  test { java.srcDir("test") }
}
repositories { jcenter() }
dependencies {
  implementation(project(":core"))
  testImplementation(group = "junit", name = "junit", version = "4.12")
}
