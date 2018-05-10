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
		functions.forEach { genFun(javaCode, it) }
		primitiveMembers.forEach { (type, name, annotation, isArray, jvmName) ->
			javaCode.javadoc(name).append("\tpublic native ").append(annotation).append(type)
			if (isArray) javaCode.append(' ').append(jvmName).append("At(").append(annotation).appendln("int index);")
			else javaCode.append(" get").append(name).appendln("();")
			javaCode.javadoc(name).append("\tpublic native void ")
			if (isArray) javaCode.append(jvmName).append('(').append(annotation).append("int index,")
			else javaCode.append("set").append(name).append('(')
			javaCode.append(annotation).append(type).appendln(" newValue);")
		}
		booleanMembers.forEach { (name, isArray, annotation, jvmName) ->
			javaCode.javadoc(name).append("\tpublic native boolean ")
			if (isArray) javaCode.append(jvmName).append("At").append('(').append(annotation).appendln("int index);")
			else javaCode.append("is").append(name).appendln("();")
			javaCode.javadoc(name).append("\tpublic native void ")
			if (isArray) javaCode.append(jvmName).append('(').append(annotation).appendln("int index,boolean newValue);")
			else javaCode.append("set").append(name).appendln("(boolean newValue);")
		}
		stringMembers.forEach { name ->
			javaCode.append("\tprivate static native void set").append(name).appendln("(byte[]newValue);")
					.javadoc(name)
					.append("\tpublic void set").append(name).append("(@NotNull String newValue){set").append(name)
					.appendln("(getBytes(newValue));}")
		}
	}

	override fun `c++`(cppCode: StringBuilder) {
		primitiveMembers.joinLinesTo(cppCode) { (type, name, _, isArray, jvmName) ->
			if (isArray) `c++PrimitiveArrayAccessor`(type, name, jvmName)
			else `c++PrimitiveAccessor`(type, name)
		}
		booleanMembers.joinLinesTo(cppCode) { (name, isArray, _, jvmName) ->
			if (isArray) `c++BooleanArrayAccessor`(name, jvmName)
			else `c++BooleanAccessor`(name)
		}
		functions.forEach { (name, type, params) -> `genFunC++`(params, name, type, cppCode) }
		stringMembers.joinLinesTo(cppCode) {
			val param = string(name = it.decapitalize(), default = "null")
			val (init, deinit) = param.surrounding()
			`c++StringedFunction`(
					name = "set$it",
					params = listOf(param),
					type = null,
					`c++Expr` = "$`c++Expr`$it = ${param.`c++Expr`()}",
					init = "$JNI_FUNCTION_INIT $init",
					deinit = "$deinit $JNI_FUNCTION_CLEAN")
		}
	}

	override val `c++Prefix`: String get() = "ImGui::GetIO()."
	override val `c++Expr` = "ImGui::GetIO()."

	private val functions = listOf(
			Fun("addInputCharactersUTF8", string("characters")),
			Fun("clearInputCharacters"),
			Fun("addInputCharacter", p("character", "short")))

	private val stringMembers = listOf(
			"IniFilename",
			"LogFilename")

	private val booleanMembers = listOf(
			BPPT("Key\$Down", isArray = true),
			BPPT("MouseClicked", isArray = true, annotation = "@MagicConstant(valuesFromClass = JImMouseIndexes.class)"),
			BPPT("MouseDoubleClicked", isArray = true, annotation = "@MagicConstant(valuesFromClass = JImMouseIndexes.class)"),
			BPPT("MouseReleased", isArray = true, annotation = "@MagicConstant(valuesFromClass = JImMouseIndexes.class)"),
			BPPT("MouseDownOwned", isArray = true, annotation = "@MagicConstant(valuesFromClass = JImMouseIndexes.class)"),
			BPPT("FontAllowUserScaling"),
			BPPT("OptMacOSXBehaviors"),
			BPPT("OptCursorBlink"),
			BPPT("MouseDrawCursor"),
			BPPT("KeyCtrl"),
			BPPT("KeyShift"),
			BPPT("KeyAlt"),
			BPPT("KeySuper"),
			BPPT("WantCaptureMouse"),
			BPPT("WantCaptureKeyboard"),
			BPPT("WantTextInput"),
			BPPT("WantSetMousePos"),
			BPPT("WantSaveIniSettings"),
			BPPT("NavActive"),
			BPPT("NavVisible"))

	private val primitiveMembers = listOf(
			PPT("short", "InputCharacter$", isArray = true),
			PPT("float", "NavInput$", isArray = true),
			PPT("float", "MouseClickedTime", isArray = true, annotation = "@MagicConstant(valuesFromClass = JImMouseIndexes.class)"),
			PPT("float", "MouseDownDuration", isArray = true, annotation = "@MagicConstant(valuesFromClass = JImMouseIndexes.class)"),
			PPT("float", "MouseDownDurationPrev", isArray = true, annotation = "@MagicConstant(valuesFromClass = JImMouseIndexes.class)"),
			PPT("float", "MouseDragMaxDistanceSqr", isArray = true, annotation = "@MagicConstant(valuesFromClass = JImMouseIndexes.class)"),
			PPT("float", "KeysDownDuration", isArray = true),
			PPT("float", "KeysDownDurationPrev", isArray = true),
			PPT("float", "NavInputsDownDuration", isArray = true),
			PPT("float", "NavInputsDownDurationPrev", isArray = true),
			PPT("int", "MetricsRenderVertices"),
			PPT("int", "MetricsRenderIndices"),
			PPT("int", "MetricsActiveWindows"),
			PPT("int", "ConfigFlags", "@MagicConstant(flagsFromClass = JImConfigFlags.class)"),
			PPT("int", "BackendFlags", "@MagicConstant(flagsFromClass = JImBackendFlags.class)"),
			PPT("float", "MouseDoubleClickTime"),
			PPT("float", "MouseDoubleClickMaxDist"),
			PPT("float", "KeyRepeatDelay"),
			PPT("float", "KeyRepeatRate"),
			PPT("float", "FontGlobalScale"),
			PPT("float", "MouseWheel"),
			PPT("float", "MouseWheelH"),
			PPT("float", "Framerate"),
			PPT("float", "IniSavingRate"))
}
