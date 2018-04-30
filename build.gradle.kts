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

val javah = task<Exec>("javah") {
	val target = file("jni").resolve("javah").absolutePath
	val classpath = project.buildDir.absoluteFile.resolve("classes").resolve("java").resolve("main")
	val className = "org.ice1000.jimgui.JImGui"
	// TODO use javac -h (simply replacing javah with javac -h does not work)
	commandLine("javah", "-d", target, "-classpath", classpath, className)
	dependsOn(tasks["classes"])
}

java.sourceSets {
	"main" {
		java.setSrcDirs(listOf("src"))
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
	testCompile(group = "junit", name = "junit", version = "4.12")
}
