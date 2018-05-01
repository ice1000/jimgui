import org.gradle.internal.impldep.org.intellij.lang.annotations.Language

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

val javah = task<Exec>("javah") {
	group = init.group
	val target = file("jni").resolve("javah").absolutePath
	val classpath = project.buildDir.absoluteFile.resolve("classes").resolve("java").resolve("main")
	val className = arrayOf(
			"org.ice1000.jimgui.JImGui",
			"org.ice1000.jimgui.JImGuiIO",
			"org.ice1000.jimgui.JImVec4",
			"org.ice1000.jimgui.MutableJImVec4"
	)
	commandLine("javah", "-d", target, "-classpath", classpath, *className)
	dependsOn(classes)
}

// TODO move to buildSrc
val genBinding = task("genBinding") {
	group = init.group
	val codeGenTargetFile = file("gen/org/ice1000/jimgui")
	val className = "JImGuiIO"
	@Language("JAVA", suffix = "}")
	val prefix = """
package org.ice1000.jimgui;

import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.1
 */
public final class $className {
	private long nativeObjectPtr;
	public $className(@NotNull JImGui owner) { nativeObjectPtr = getNativeObjects(owner.nativeObjectPtr); }
"""
	@Language("JAVA", prefix = "class JImGuiIO {")
	val suffix = """
	private static native long getNativeObjects(long ownerPtr);
}
"""
	val members = listOf(
			"float" to "DeltaTime",
			"float" to "MouseDoubleClickTime",
			"float" to "MouseDoubleClickMaxDist",
			"float" to "MouseDoubleClickMaxDist",
			"float" to "KeyRepeatDelay",
			"float" to "KeyRepeatRate",
			"float" to "FontGlobalScale",
			"float" to "MouseWheel",
			"float" to "MouseWheelH",
			"float" to "Framerate",
			"float" to "IniSavingRate"
	)
	codeGenTargetFile.mkdirs()
	doFirst {
		val text = members.joinToString(System.lineSeparator(), prefix, suffix) { (type, name) ->
			"""
	private static native $type get$name(long nativeObjectPtr);
	public $type get$name() { return get$name(nativeObjectPtr); }
"""
		}
		codeGenTargetFile.resolve("$className.java").writeText(text)
	}
}

val clearBinding = task<Delete>("clearBinding") {
	group = clean.group
	doFirst { file("gen").deleteRecursively() }
}

classes.dependsOn(genBinding)
clean.dependsOn(clearBinding)

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
