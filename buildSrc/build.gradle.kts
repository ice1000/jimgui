group = "org.ice1000.gradle"
version = "114514"

plugins { kotlin("jvm") version "1.4.20" }

kotlin.sourceSets["main"].kotlin.srcDir("src")
sourceSets.main {
  java.srcDir("src")
}

repositories { jcenter() }
dependencies { implementation(kotlin("stdlib-jdk8")) }
