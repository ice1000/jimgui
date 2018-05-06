import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins { kotlin("jvm") }

java.sourceSets {
	"main" {
		java.setSrcDirs(listOf("src"))
		withConvention(KotlinSourceSet::class) { kotlin.setSrcDirs(listOf("src")) }
		resources.setSrcDirs(emptyList<Any>())
	}

	"test" {
		java.setSrcDirs(listOf("test"))
		withConvention(KotlinSourceSet::class) { kotlin.setSrcDirs(listOf("test")) }
		resources.setSrcDirs(emptyList<Any>())
	}
}

repositories { jcenter() }

dependencies {
	compile(project(":core"))
	compile(kotlin("stdlib-jdk8"))
}