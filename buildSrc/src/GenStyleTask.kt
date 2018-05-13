@file:Suppress("unused")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

/**
 * @author ice1000
 */
open class GenStyleTask : GenTask("JImGuiStyleGen", "imgui_style") {
	init {
		description = "Generate binding for ImGui::GetStyle"
	}

	@Language("JAVA")
	//override val userCode = "@Contract(pure = true) public static @NotNull $className getInstance(@NotNull JImGui owner) { return owner.getStyle(); }"

	override fun java(javaCode: StringBuilder) {
		imVec2Members.forEach { genJavaXYAccessor(javaCode, it, "float") }
		primitiveMembers.forEach { (type, name, annotation, isArray, jvmName, `c++Name`) ->
			genJavaPrimitiveMember(javaCode, name, annotation, type, isArray, jvmName, `c++Name`)
		}
		booleanMembers.forEach {
			javaCode.javadoc(it).append("\tpublic native boolean is").append(it).appendln("();")
					.javadoc(it).append("\tpublic native void set").append(it).appendln("(boolean newValue);")
		}
		functions.forEach { genJavaFun(javaCode, it) }
	}

	override fun `c++`(cppCode: StringBuilder) {
		imVec2Members.joinLinesTo(cppCode) { `c++XYAccessor`(it, "float") }
		booleanMembers.joinLinesTo(cppCode, transform = ::`c++BooleanAccessor`)
		primitiveMembers.joinLinesTo(cppCode) { (type, name, _, isArray, jvmName, `c++Name`) ->
			if (isArray) `c++PrimitiveArrayAccessor`(type, name, jvmName, `c++Name`)
			else `c++PrimitiveAccessor`(type, name)
		}
		functions.forEach { (name, type, params) -> `genC++Fun`(params, name, type, cppCode) }
	}

	override val `c++Expr` = "ImGui::GetStyle()."
	private val booleanMembers = listOf("AntiAliasedLines", "AntiAliasedFill")
	private val functions = listOf(Fun("scaleAllSizes", float("scaleFactor")))
	private val primitiveMembers = listOf(
			PPT("float", "Alpha"),
			PPT("float", "WindowRounding"),
			PPT("float", "WindowBorderSize"),
			PPT("float", "ChildRounding"),
			PPT("float", "ChildBorderSize"),
			PPT("float", "PopupRounding"),
			PPT("float", "PopupBorderSize"),
			PPT("float", "FrameRounding"),
			PPT("float", "FrameBorderSize"),
			PPT("float", "IndentSpacing"),
			PPT("float", "ColumnsMinSpacing"),
			PPT("float", "ScrollbarSize"),
			PPT("float", "ScrollbarRounding"),
			PPT("float", "GrabMinSize"),
			PPT("float", "GrabRounding"),
			PPT("float", "MouseCursorScale"),
			PPT("float", "CurveTessellationTol"))

	private val imVec2Members = listOf(
			"WindowPadding",
			"WindowMinSize",
			"WindowTitleAlign",
			"FramePadding",
			"ItemSpacing",
			"ItemInnerSpacing",
			"TouchExtraPadding",
			"ButtonTextAlign",
			"DisplayWindowPadding",
			"DisplaySafeAreaPadding"
	)
}
