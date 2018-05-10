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
	override val `c++Prefix`: String get() = "ImGui::GetFont()->"

	override fun java(javaCode: StringBuilder) {
		primitiveMembers.forEach { (type, name) ->
			javaCode
					.javadoc(name)
					.append('\t')
					.appendln(javaPrimitiveGetter(type, name))
					.javadoc(name)
					.appendln(javaPrimitiveSetter(type, name))
		}
		booleanMembers.forEach {
			javaCode
					.javadoc(it)
					.append('\t')
					.appendln(javaBooleanGetter(it))
					.javadoc(it)
					.appendln(javaBooleanSetter(it))
		}
		functions.forEach { genFun(javaCode, it) }
	}

	override fun `c++`(cppCode: StringBuilder) {
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveGetter`(type, name, `c++Expr`(name)) }
		booleanMembers.joinLinesTo(cppCode) { `c++BooleanGetter`(it, `c++Expr`(it)) }
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveSetter`(type, name, `c++Expr`(name)) }
		booleanMembers.joinLinesTo(cppCode) { `c++BooleanSetter`(it, `c++Expr`(it)) }
		functions.forEach { (name, type, params) -> `genFunC++`(params, name, type, cppCode) }
	}

	private fun `c++Expr`(it: String) = "ImGui::GetFont()->$it"
	private val booleanMembers = listOf("DirtyLookupTables")
	private val functions = listOf(
			Fun("clearOutputData"),
			Fun("setFallbackChar", p("wChar", "short")),
			Fun("isLoaded", "boolean"),
			Fun("buildLookupTable"))

	private val primitiveMembers = listOf(
			"float" to "FontSize",
			"float" to "Scale",
			"float" to "FallbackAdvanceX",
			"short" to "ConfigDataCount",
			"float" to "Ascent",
			"float" to "Descent",
			"int" to "MetricsTotalSurface")
}
