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
	override val userCode = """@Contract(pure = true)
	public static @NotNull $className getInstance(@NotNull JImGui owner) { return owner.getStyle(); }

	/** package-private by design */
	JImGuiFontAtlasGen(long nativeObjectPtr) {
		this.nativeObjectPtr = nativeObjectPtr;
	}

	protected long nativeObjectPtr;
"""

	override fun java(javaCode: StringBuilder) {
		GenGenTask.checkParserInitialized(project)
		imVec2Members.forEach { genJavaObjectiveXYAccessor(javaCode, it, "float") }
		primitiveMembers.forEach { (type, name) -> genSimpleJavaObjectivePrimitiveMembers(javaCode, type, name) }
		booleanMembers.forEach { genSimpleJavaObjectiveBooleanMember(javaCode, it) }
		functions.forEach { genJavaFun(javaCode, it) }
	}

	override fun `c++`(cppCode: StringBuilder) {
		imVec2Members.joinLinesTo(cppCode) { `c++XYAccessor`(it, "float", "jlong nativeObjectPtr") }
		booleanMembers.joinLinesTo(cppCode) { `c++BooleanAccessor`(it, "jlong nativeObjectPtr") }
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveAccessor`(type, name, "jlong nativeObjectPtr") }
		functions.forEach { (name, type, params) -> `genC++Fun`(params, name, type, cppCode) }
	}

	override val `c++Expr` = "PTR_J2C(ImStyle, nativeObjectPtr)->"
	private val booleanMembers = listOf("AntiAliasedLines", "AntiAliasedFill")
	private val functions = listOf(Fun("scaleAllSizes", float("scaleFactor")))
	private val primitiveMembers = listOf(
			"float" to "Alpha",
			"float" to "WindowRounding",
			"float" to "WindowBorderSize",
			"float" to "ChildRounding",
			"float" to "ChildBorderSize",
			"float" to "PopupRounding",
			"float" to "PopupBorderSize",
			"float" to "FrameRounding",
			"float" to "FrameBorderSize",
			"float" to "IndentSpacing",
			"float" to "ColumnsMinSpacing",
			"float" to "ScrollbarSize",
			"float" to "ScrollbarRounding",
			"float" to "GrabMinSize",
			"float" to "GrabRounding",
			"float" to "MouseCursorScale",
			"float" to "CurveTessellationTol")

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
