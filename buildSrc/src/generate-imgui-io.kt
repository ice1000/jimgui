package org.ice1000.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.intellij.lang.annotations.Language
import java.io.File

/**
 * @author ice1000
 */
open class GenIOTask : DefaultTask(), Runnable {
	init {
		group = "code generation"
		description = "Generate binding for ImGui::GetIO"
	}

	fun javaPrimitiveGetter(type: String, name: String) =
			"public native $type get$name();"

	fun javaPrimitiveSetter(type: String, name: String) =
			"public native void set$name($type newValue);"

	private val eol = System.lineSeparator()

	@TaskAction
	override fun run() {
		val targetJavaFile = File("gen/org/ice1000/jimgui").resolve("$className.java")
		val `targetC++File` = File("jni/generated.cpp")
		targetJavaFile.parentFile.mkdirs()
		// `targetC++File`.parentFile.mkdirs()
		val javaCode = StringBuilder()
		javaCode.append(prefixJava)
		members.joinTo(javaCode, eol) { (type, name) ->
			javaPrimitiveGetter(type, name)
		}
		members.joinTo(javaCode, eol) { (type, name) ->
			javaPrimitiveSetter(type, name)
		}
		javaCode.append("\n}")
		targetJavaFile.writeText("$javaCode")
		val `c++Code` = members.joinToString(eol, `prefixC++`, "#pragma clang diagnostic pop") { (type, name) ->
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


@Language("Text")
private const val className = "JImGuiIO"

@Language("JAVA", suffix = "}")
const val prefixJava = """
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

	/** package-private by design */
	$className() { }
"""

@Language("C++")
const val `prefixC++` = """
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
