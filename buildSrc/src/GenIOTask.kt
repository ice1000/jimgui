@file:Suppress("unused")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

/**
 * @author ice1000
 */
open class GenIOTask : GenTask("JImGuiIOGen", "imgui_io") {
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
		stringMembers.forEach {
			javaCode
					.append("\tprivate static native void set")
					.append(it)
					.appendln("(byte[]newValue);")
					.append("\tpublic void set")
					.append(it)
					.append("(@NotNull String newValue){set")
					.append(it)
					.appendln("(getBytes(newValue));}")
		}
	}

	override fun `c++`(cppCode: StringBuilder) {
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveGetter`(type, name, "ImGui::GetIO().$name") }
		booleanMembers.joinLinesTo(cppCode) { `c++BooleanGetter`(it, `c++Expr`(it)) }
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveSetter`(type, name, "ImGui::GetIO().$name") }
		booleanMembers.joinLinesTo(cppCode) { `c++BooleanSetter`(it, `c++Expr`(it)) }
		stringMembers.joinLinesTo(cppCode) {
			val param = string(name = it.decapitalizeFirst(), default = "null")
			val (init, deinit) = param.surrounding()
			`c++StringedFunction`(
					name = "set$it",
					params = listOf(param),
					type = null,
					`c++Expr` = `c++Expr`(it) + " = ${param.`c++Expr`()}",
					init = "$JNI_FUNCTION_INIT $init",
					deinit = "$deinit $JNI_FUNCTION_CLEAN")
		}
	}

	private fun `c++Expr`(it: String) = "ImGui::GetIO().$it"

	private val stringMembers = listOf(
			"IniFilename",
			"LogFilename"
	)

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
			"WantSaveIniSettings",
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
