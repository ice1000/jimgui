import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

group = "org.ice1000.gradle"
version = "v0.1"

plugins { kotlin("jvm") version "1.4.10" }

sourceSets {
	main {
		java.setSrcDirs(listOf("src"))
		withConvention(KotlinSourceSet::class) { kotlin.setSrcDirs(listOf("src")) }
	}
}

repositories { jcenter() }
dependencies { implementation(kotlin("stdlib-jdk8")) }
