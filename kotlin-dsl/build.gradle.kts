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
		resources.setSrcDirs(listOf("testRes"))
	}
}

repositories {
	jcenter()
	maven("https://dl.bintray.com/ice1000/Lice")
}

dependencies {
	compile(project(":core"))
	compile(kotlin("stdlib-jdk8"))
	testCompile(kotlin("test-junit"))
	testCompile(group = "junit", name = "junit", version = "4.12")
	testCompile("org.lice:lice:3.3.2")
}