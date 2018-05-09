@file:Suppress("unused")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

/**
 * @author ice1000
 */
open class GenFontTask : GenTask("JImGuiFontGen", "imgui_font") {
	init {
		description = "Generate binding for ImGui::GetFont"
	}

	@Language("JAVA")
	override val userCode = "@Contract(pure = true) public static @NotNull $className getInstance(@NotNull JImGui owner) { return owner.getFont(); }"

	override fun java(javaCode: StringBuilder) {
		primitiveMembers.joinLinesTo(javaCode) { (type, name) -> javaPrimitiveGetter(type, name) }
		booleanMembers.joinLinesTo(javaCode, transform = ::javaBooleanGetter)
		primitiveMembers.joinLinesTo(javaCode) { (type, name) -> javaPrimitiveSetter(type, name) }
		booleanMembers.joinLinesTo(javaCode, transform = ::javaBooleanSetter)
	}

	override fun `c++`(cppCode: StringBuilder) {
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveGetter`(type, name, "ImGui::GetIO().$name") }
		booleanMembers.joinLinesTo(cppCode) { `c++BooleanGetter`(it, `c++Expr`(it)) }
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveSetter`(type, name, "ImGui::GetIO().$name") }
		booleanMembers.joinLinesTo(cppCode) { `c++BooleanSetter`(it, `c++Expr`(it)) }
	}

	private fun `c++Expr`(it: String) = "ImGui::GetFont()->$it"
	private val booleanMembers = listOf("DirtyLookupTables")
	private val primitiveMembers = listOf(
			"float" to "FontSize",
			"float" to "Scale",
			"float" to "FallbackAdvanceX",
			"short" to "ConfigDataCount",
			"float" to "Ascent",
			"float" to "Descent",
			"int" to "MetricsTotalSurface")
}
