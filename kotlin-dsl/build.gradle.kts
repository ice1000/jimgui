import de.undercouch.gradle.tasks.download.Download
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
	id("de.undercouch.download")
	kotlin("jvm")
}

kotlin {
	sourceSets["main"].kotlin.srcDir("src")
	sourceSets["test"].kotlin.srcDir("test")
}

sourceSets {
	main {
		java.srcDir("src")
	}

	test {
		java.srcDir("test")
		resources.srcDir("testRes")
	}
}

val downloadIce1000 = tasks.register<Download>("downloadIce1000") {
	src("https://pic4.zhimg.com/61984a25d44df15b857475e7f7b1c7e3_xl.jpg")
	dest(file("testRes/pics/ice1000.png"))
	overwrite(false)
}

tasks.named("processTestResources") {
	dependsOn(downloadIce1000)
}
repositories { jcenter() }

dependencies {
	implementation(project(":core"))
	implementation(kotlin("stdlib-jdk8"))
	testImplementation(kotlin("test-junit"))
	testImplementation(group = "junit", name = "junit", version = "4.12")
	testImplementation("org.lice:lice:3.3.2")
}