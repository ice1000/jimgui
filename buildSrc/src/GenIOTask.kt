@file:Suppress("unused")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

/**
 * @author ice1000
 */
open class GenIOTask : GenTask("JImGuiIO", "imgui_io") {
	init {
		description = "Generate binding for ImGui::GetIO"
	}

	@Language("JAVA")
	override val userCode = "@Contract(pure = true) public static @NotNull $className getInstance(@NotNull JImGui owner) { return owner.getIO(); }"

	override fun java(javaCode: StringBuilder) {
		primitiveMembers.joinLinesTo(javaCode) { (type, name) -> javaPrimitiveGetter(type, name) }
		booleanMembers.joinLinesTo(javaCode, transform = ::javaBooleanGetter)
		primitiveMembers.joinLinesTo(javaCode) { (type, name) -> javaPrimitiveSetter(type, name) }
		booleanMembers.joinLinesTo(javaCode, transform = ::javaBooleanSetter)
	}

	override fun cpp(cppCode: StringBuilder) {
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveGetter`(type, name, "ImGui::GetIO().$name") }
		booleanMembers.joinLinesTo(cppCode) { `c++BooleanGetter`(it, "ImGui::GetIO().$it") }
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveSetter`(type, name, "ImGui::GetIO().$name") }
		booleanMembers.joinLinesTo(cppCode) { `c++BooleanSetter`(it, "ImGui::GetIO().$it") }
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
