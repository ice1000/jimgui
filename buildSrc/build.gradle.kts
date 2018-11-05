import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

group = "org.ice1000.gradle"
version = "v0.1"

plugins { kotlin("jvm") version "1.2.60" }

java.sourceSets {
	"main" {
		java.setSrcDirs(listOf("src"))
		withConvention(KotlinSourceSet::class) { kotlin.setSrcDirs(listOf("src")) }
		resources.setSrcDirs(emptyList<Any>())
	}

	"test" {
		java.setSrcDirs(emptyList<Any>())
		withConvention(KotlinSourceSet::class) { kotlin.setSrcDirs(emptyList<Any>()) }
		resources.setSrcDirs(emptyList<Any>())
	}
}

repositories { jcenter() }
dependencies { compile(kotlin("stdlib-jdk8")) }
