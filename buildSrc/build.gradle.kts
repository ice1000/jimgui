import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

group = "org.ice1000.gradle"
version = "v0.1"

plugins {
	java
	kotlin("jvm") version "1.2.41"
}

java.sourceSets {
	"main" {
		java.setSrcDirs(listOf("src"))
		withConvention(KotlinSourceSet::class) { kotlin.setSrcDirs(listOf("src")) }
		resources.setSrcDirs(listOf("res"))
	}

	"test" {
		java.setSrcDirs(listOf("test"))
		withConvention(KotlinSourceSet::class) { kotlin.setSrcDirs(listOf("test")) }
	}
}

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	compile(group = "org.jetbrains", name = "annotations", version = "16.0.1")
	compile(kotlin("stdlib-jdk8"))
	testCompile(group = "junit", name = "junit", version = "4.12")
}
