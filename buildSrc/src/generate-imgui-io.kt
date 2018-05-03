@file:Suppress("unused")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

/**
 * @author ice1000
 */
open class GenIOTask : GenTask() {
	init {
		description = "Generate binding for ImGui::GetIO"
	}

	final override val cppFileSuffix = "imgui_io"
	@Language("TEXT")
	final override val className = "JImGuiIO"
	@Language("JAVA")
	override val userCode = "@Contract(pure = true) public static @NotNull $className getInstance(@NotNull JImGui owner) { return owner.getIO(); }"

	override fun java(javaCode: StringBuilder) {
		primitiveMembers.joinTo(javaCode, eol, postfix = eol) { (type, name) -> javaPrimitiveGetter(type, name) }
		booleanMembers.joinTo(javaCode, eol, postfix = eol, transform = ::javaBooleanGetter)
		primitiveMembers.joinTo(javaCode, eol, postfix = eol) { (type, name) -> javaPrimitiveSetter(type, name) }
		booleanMembers.joinTo(javaCode, eol, postfix = eol, transform = ::javaBooleanSetter)
	}

	override fun cpp(cppCode: StringBuilder) {
		primitiveMembers.joinTo(cppCode, eol, postfix = eol) { (type, name) ->
			`c++PrimitiveGetter`(type, name, "ImGui::GetIO().$name")
		}
		booleanMembers.joinTo(cppCode, eol, postfix = eol) { `c++BooleanGetter`(it, "ImGui::GetIO().$it") }
		primitiveMembers.joinTo(cppCode, eol, postfix = eol) { (type, name) ->
			`c++PrimitiveSetter`(type, name, "ImGui::GetIO().$name")
		}
		booleanMembers.joinTo(cppCode, eol, postfix = eol) { `c++BooleanSetter`(it, "ImGui::GetIO().$it") }
	}

	private val booleanMembers = listOf(
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

	private val primitiveMembers = listOf(
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
