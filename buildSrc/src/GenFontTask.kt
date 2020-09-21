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

	@Language("JAVA", prefix = "class A{", suffix = "}")
	override val userCode = """@Contract(pure = true)
	public static @NotNull JImFont getInstance(@NotNull JImGui owner) {
		return owner.getFont();
	}

	/** package-private by design */
	protected long nativeObjectPtr;

	/** package-private by design */
	$className(long nativeObjectPtr) {
		this.nativeObjectPtr = nativeObjectPtr;
	}
"""

	override fun java(javaCode: StringBuilder) {
		GenGenTask.checkParserInitialized(project)
		// imVec2Members.forEach { genJavaObjectiveXYAccessor(javaCode, it, "float") }
		primitiveMembers.forEach { (type, name) -> genSimpleJavaObjectivePrimitiveMembers(javaCode, name, type) }
		booleanMembers.forEach { genSimpleJavaObjectiveBooleanMember(javaCode, it) }
		functions.forEach { genJavaFun(javaCode, it) }
	}

	override fun `c++`(cppCode: StringBuilder) {
		// imVec2Members.joinLinesTo(cppCode) { `c++XYAccessor`(it, "float", "jlong nativeObjectPtr") }
		booleanMembers.joinLinesTo(cppCode) { `c++BooleanAccessor`(it, "jlong nativeObjectPtr") }
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveAccessor`(type, name, "jlong nativeObjectPtr") }
		functions.forEach { (name, type, params) -> `genC++Fun`(params.dropLast(1), name, type, cppCode, "jlong nativeObjectPtr") }
	}

	override val `c++Expr` = "PTR_J2C(ImFont, nativeObjectPtr)->"
	private val booleanMembers = listOf("DirtyLookupTables")
	// private val imVec2Members = listOf<String>("DisplayOffset")
	private val functions = listOf(
			Fun.private("clearOutputData", nativeObjectPtr),
			Fun.private("setFallbackChar", p("wChar", "int"), nativeObjectPtr),
			Fun.private("isLoaded", "boolean", nativeObjectPtr),
			Fun.private("growIndex", int("newSize"), nativeObjectPtr),
			Fun.private("addRemapChar", int("dst"), int("src"), bool("overwriteDst", default = true), nativeObjectPtr),
			// TODO: add this back
			// Fun.private("addGlyph", p("wChar", "int"), float("x0"), float("y0"), float("x1"), float("y1"),
			// 		float("u0"), float("v0"), float("u1"), float("v1"), float("advanceX"), nativeObjectPtr),
			Fun.private("buildLookupTable", nativeObjectPtr),
			Fun.private("renderChar", drawListPtr("drawList"), float("size"), pos(), u32, p("c", "short"), nativeObjectPtr))

	private val primitiveMembers = listOf(
			"float" to "FontSize",
			"float" to "Scale",
			"float" to "FallbackAdvanceX",
			// "int" to "FallbackChar",
			"short" to "ConfigDataCount",
			"float" to "Ascent",
			"float" to "Descent",
			"int" to "MetricsTotalSurface")
}
