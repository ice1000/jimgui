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
			javaCode.javadoc(name).append("\tpublic native ").append(type).append(" get").append(name).appendln("();")
					.javadoc(name).append("\tpublic native void set").append(name).append("(").append(type).appendln(" newValue);")
		}
		booleanMembers.forEach {
			javaCode.javadoc(it).append("\tpublic native boolean is").append(it).appendln("();")
					.javadoc(it).append("\tpublic native void set").append(it).appendln("(boolean newValue);")
		}
		functions.forEach { genFun(javaCode, it) }
	}

	override fun `c++`(cppCode: StringBuilder) {
		booleanMembers.joinLinesTo(cppCode, transform = ::`c++BooleanAccessor`)
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveAccessor`(type, name) }
		functions.forEach { (name, type, params) -> `genFunC++`(params, name, type, cppCode) }
	}

	override val `c++Expr` = "ImGui::GetFont()->"
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
