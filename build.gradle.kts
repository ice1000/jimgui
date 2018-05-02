import org.ice1000.gradle.*

group = "org.ice1000.jimgui"
version = "v0.1"

plugins { java }

val isCI = !System.getenv("CI").isNullOrBlank()

tasks.withType<JavaCompile> {
	sourceCompatibility = "1.8"
	targetCompatibility = "1.8"
	options.apply {
		isDeprecation = true
		isWarnings = true
		isDebug = !isCI
		compilerArgs.add("-Xlint:unchecked")
	}
}

val classes = tasks["classes"]
val clean = tasks["clean"]
val init = tasks["init"]

val genBindings = task<GenIOTask>("genBindings")

val javah = task<GenNativeHeaderTask>("javah") {
	classes(
			"org.ice1000.jimgui.JImGui",
			"org.ice1000.jimgui.JImGuiIO",
			"org.ice1000.jimgui.JImVec4",
			"org.ice1000.jimgui.MutableJImVec4"
	)
	dependsOn(classes)
	dependsOn(genBindings)
}

val clearGenerated = task<Delete>("clearGenerated") {
	group = clean.group
	delete("gen", "jni/generated.cpp")
}

classes.dependsOn(genBindings)
clean.dependsOn(clearGenerated)

java.sourceSets {
	"main" {
		java.setSrcDirs(listOf("src", "gen"))
		resources.setSrcDirs(listOf("res"))
	}

	"test" {
		java.setSrcDirs(listOf("test"))
		resources.setSrcDirs(listOf("testRes"))
	}
}

val sourcesJar = task<Jar>("sourcesJar") {
	group = tasks["jar"].group
	from(java.sourceSets["main"].allSource)
	classifier = "sources"
}

repositories {
	mavenCentral()
	jcenter()
	maven("https://jitpack.io")
}

dependencies {
	compile(group = "org.jetbrains", name = "annotations", version = "16.0.1")
	testCompile(group = "junit", name = "junit", version = "4.12")
}
