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
		val targetJavaFile = File("gen/org/ice1000/jimgui/$CLASS_NAME.java")
		val targetCppFile = File("jni/generated_imgui_io.cpp")
		targetJavaFile.parentFile.mkdirs()
		 targetCppFile.parentFile.mkdirs()
		val javaCode = StringBuilder(prefixJava)
		primitiveMembers.joinTo(javaCode, eol, postfix = eol) { (type, name) -> javaPrimitiveGetter(type, name) }
		booleanMembers.joinTo(javaCode, eol, postfix = eol, transform = ::javaBooleanGetter)
		primitiveMembers.joinTo(javaCode, eol, postfix = eol) { (type, name) -> javaPrimitiveSetter(type, name) }
		booleanMembers.joinTo(javaCode, eol, postfix = eol, transform = ::javaBooleanSetter)
		javaCode.append(eol).append('}')
		targetJavaFile.writeText("$javaCode")
		val cppCode = StringBuilder(`prefixC++`)
		primitiveMembers.joinTo(cppCode, eol, postfix = eol) { (type, name) ->
			cppPrimitiveGetter(CLASS_NAME, type, name, "ImGui::GetIO().$name")
		}
		booleanMembers.joinTo(cppCode, eol, postfix = eol) { `c++BooleanGetter`(CLASS_NAME, it, "ImGui::GetIO().$it") }
		primitiveMembers.joinTo(cppCode, eol, postfix = eol) { (type, name) ->
			cppPrimitiveSetter(CLASS_NAME, type, name, "ImGui::GetIO().$name")
		}
		booleanMembers.joinTo(cppCode, eol, postfix = eol) { `c++BooleanSetter`(CLASS_NAME, it, "ImGui::GetIO().$it") }
		cppCode.append(CXX_SUFFIX)
		targetCppFile.writeText("$cppCode")
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
		val booleanMembers = listOf(
				"FontAllowUserScaling",
				"OptMacOSXBehaviors",
				"OptCursorBlink",
				"MouseDrawCursor",
				"KeyCtrl",
				"KeyShift",
				"KeyAlt",
				"KeySuper",
				"WantCaptureMouse",
				"WantCaptureKeyboard",
				"WantTextInput",
				"WantSetMousePos",
				"NavActive",
				"NavVisible")

		val primitiveMembers = listOf(
				"int" to "MetricsRenderVertices",
				"int" to "MetricsRenderIndices",
				"int" to "MetricsActiveWindows",
				"float" to "MouseDoubleClickTime",
				"float" to "MouseDoubleClickMaxDist",
				"float" to "KeyRepeatDelay",
				"float" to "KeyRepeatRate",
				"float" to "FontGlobalScale",
				"float" to "MouseWheel",
				"float" to "MouseWheelH",
				"float" to "Framerate",
				"float" to "IniSavingRate")
	}
}
