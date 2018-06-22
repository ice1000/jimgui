import de.undercouch.gradle.tasks.download.Download
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
	id("de.undercouch.download")
	kotlin("jvm")
}

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

val downloadIce1000 = task<Download>("downloadIce1000") {
	src("https://pic4.zhimg.com/61984a25d44df15b857475e7f7b1c7e3_xl.jpg")
	dest(file("testRes/pics/ice1000.png"))
	overwrite(false)
}

tasks["processTestResources"].dependsOn(downloadIce1000)
repositories { jcenter() }

dependencies {
	compile(project(":core"))
	compile(kotlin("stdlib-jdk8"))
	testCompile(kotlin("test-junit"))
	testCompile(group = "junit", name = "junit", version = "4.12")
	testCompile("org.lice:lice:3.3.2")
}