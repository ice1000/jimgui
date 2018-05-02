@file:Suppress("unused")

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

	@TaskAction
	override fun run() {
		val targetJavaFile = File("gen/org/ice1000/jimgui").resolve("$CLASS_NAME.java")
		val `targetC++File` = File("jni/generated.cpp")
		targetJavaFile.parentFile.mkdirs()
		// `targetC++File`.parentFile.mkdirs()
		val javaCode = StringBuilder(prefixJava)
		primitiveMembers.joinTo(javaCode, eol) { (type, name) ->
			javaPrimitiveGetter(type, name)
		}
		javaCode.append(eol)
		primitiveMembers.joinTo(javaCode, eol) { (type, name) ->
			javaPrimitiveSetter(type, name)
		}
		javaCode.append(eol).append('}')
		targetJavaFile.writeText("$javaCode")
		// ImGui::GetIO().$name
		val `c++Code` = StringBuilder(`prefixC++`)
		primitiveMembers.joinTo(`c++Code`, eol) { (type, name) ->
			`c++PrimitiveGetter`(CLASS_NAME, type, name, "ImGui::GetIO().$name")
		}
		`c++Code`.append(eol)
		primitiveMembers.joinTo(`c++Code`, eol) { (type, name) ->
			`c++PrimitiveSetter`(CLASS_NAME, type, name, "ImGui::GetIO().$name")
		}
		`c++Code`.append(CXX_SUFFIX)
		`targetC++File`.writeText("$`c++Code`")
	}

	companion object {
		@Language("Text")
		const val CLASS_NAME = "JImGuiIO"

		@Language("JAVA", suffix = "}")
		const val prefixJava = """$CLASS_PREFIX
public final class $CLASS_NAME {
	@Contract(pure = true)
	public static @NotNull $CLASS_NAME getInstance(@NotNull JImGui owner) {
		return owner.getIO();
	}

	/** package-private by design */
	$CLASS_NAME() { }
"""
		@Language("C++")
		const val `prefixC++` = """$CXX_PREFIX
#include <org_ice1000_jimgui_$CLASS_NAME.h>
"""

		val primitiveMembers = listOf(
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
	}
}
