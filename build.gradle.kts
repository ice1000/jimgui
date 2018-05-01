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

// TODO move to buildSrc
val genBindings = task("genBindings") {
	group = init.group
	val className = "JImGuiIO"
	val targetJavaFile = file("gen/org/ice1000/jimgui").resolve("$className.java")
	val `targetC++File` = file("jni/generated.cpp")
	@Language("JAVA", suffix = "}")
	val prefixJava = """
package org.ice1000.jimgui;

import org.jetbrains.annotations.*;

/**
 * @author ice1000
 * @since v0.1
 */
@SuppressWarnings("ALL")
public final class $className {
	@Contract(pure = true)
	public static @NotNull JImGuiIO getInstance(@NotNull JImGui owner) {
		return owner.getIO();
	}
	$className() { }
"""
	@Language("C++")
	val `prefixC++` = """
///
/// author: ice1000
/// since: v0.1
///

#include <org_ice1000_jimgui_JImGuiIO.h>
#include <imgui.h>
#define boolean bool

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"
"""
	val members = listOf(
			"int" to "MetricsRenderVertices",
			"int" to "MetricsRenderIndices",
			"int" to "MetricsActiveWindows",
			"boolean" to "FontAllowUserScaling",
			"boolean" to "OptMacOSXBehaviors",
			"boolean" to "OptCursorBlink",
			"boolean" to "MouseDrawCursor",
			"boolean" to "KeyCtrl",
			"boolean" to "KeyShift",
			"boolean" to "KeyAlt",
			"boolean" to "KeySuper",
			"boolean" to "WantCaptureMouse",
			"boolean" to "WantCaptureKeyboard",
			"boolean" to "WantTextInput",
			"boolean" to "WantSetMousePos",
			"boolean" to "NavActive",
			"boolean" to "NavVisible",
			"float" to "MouseDoubleClickTime",
			"float" to "MouseDoubleClickMaxDist",
			"float" to "KeyRepeatDelay",
			"float" to "KeyRepeatRate",
			"float" to "FontGlobalScale",
			"float" to "MouseWheel",
			"float" to "MouseWheelH",
			"float" to "Framerate",
			"float" to "IniSavingRate"
	)
	targetJavaFile.parentFile.mkdirs()
	// `targetC++File`.parentFile.mkdirs()
	doFirst {
		val javaCode = members.joinToString(System.lineSeparator(), prefixJava, postfix = "\n}") { (type, name) ->
			"""		public native $type get$name();
				|		public native void set$name($type newValue);""".trimMargin()
		}
		targetJavaFile.writeText(javaCode)
		val `c++Code` = members.joinToString(System.lineSeparator(), `prefixC++`, "#pragma clang diagnostic pop") { (type, name) ->
			"""JNIEXPORT j$type JNICALL
Java_org_ice1000_jimgui_JImGuiIO_get$name(JNIEnv *, jobject) {
	return static_cast<j$type> (ImGui::GetIO().$name);
}
JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGuiIO_set$name(JNIEnv *, jobject, j$type newValue) {
	ImGui::GetIO().$name = newValue;
}
"""
		}
		`targetC++File`.writeText(`c++Code`)
	}
}

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
	dependsOn(genBindings)
}

val clearBindings = task<Delete>("clearBindings") {
	group = clean.group
	doFirst { file("gen").deleteRecursively() }
}

classes.dependsOn(genBindings)
clean.dependsOn(clearBindings)

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
