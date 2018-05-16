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
	JImGuiFontGen(long nativeObjectPtr) {
		this.nativeObjectPtr = nativeObjectPtr;
	}
"""

	override fun java(javaCode: StringBuilder) {
		imVec2Members.forEach { genJavaObjectiveXYAccessor(javaCode, it, "float") }
		primitiveMembers.forEach { (type, name) ->
			javaCode.javadoc(name)
					.append("\tpublic ").append(type).append(" get").append(name).append("(){return get").append(name).appendln("(nativeObjectPtr);}")
					.append("\tprivate native ").append(type).append(" get").append(name).appendln("(long nativeObjectPtr);")
					.javadoc(name)
					.append("\tpublic void set").append(name).append('(').append(type).append(" newValue) {set").append(name).appendln("(nativeObjectPtr, newValue);}")
					.append("\tprivate native void set").append(name).append("(long nativeObjectPtr, ").append(type).appendln(" newValue);")
		}
		booleanMembers.forEach {
			javaCode.javadoc(it).append("\tpublic native boolean is").append(it).appendln("(long nativeObjectPtr);")
					.javadoc(it).append("\tpublic native void set").append(it).appendln("(long nativeObjectPtr, boolean newValue);")
		}
		functions.forEach { genJavaFun(javaCode, it) }
	}

	override fun `c++`(cppCode: StringBuilder) {
		imVec2Members.joinLinesTo(cppCode) { `c++XYAccessor`(it, "float", ", jlong nativeObjectPtr") }
		booleanMembers.joinLinesTo(cppCode) { `c++BooleanAccessor`(it, ", jlong nativeObjectPtr") }
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveAccessor`(type, name, ", jlong nativeObjectPtr") }
		functions.forEach { (name, type, params) -> `genC++Fun`(params.dropLast(1), name, type, cppCode, ", jlong nativeObjectPtr") }
	}

	override val `c++Expr` = "reinterpret_cast<ImFont *> (nativeObjectPtr)->"
	private val booleanMembers = listOf("DirtyLookupTables")
	private val imVec2Members = listOf("DisplayOffset")
	private val functions = listOf(
			Fun.private("clearOutputData", nativeObjectPtr),
			Fun.private("setFallbackChar", p("wChar", "short"), nativeObjectPtr),
			Fun.private("isLoaded", "boolean", nativeObjectPtr),
			Fun.private("buildLookupTable", nativeObjectPtr))

	private val primitiveMembers = listOf(
			"float" to "FontSize",
			"float" to "Scale",
			"float" to "FallbackAdvanceX",
			"short" to "ConfigDataCount",
			"float" to "Ascent",
			"float" to "Descent",
			"int" to "MetricsTotalSurface")
}
