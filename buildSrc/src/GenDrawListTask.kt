@file:Suppress("unused")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

/**
 * @author ice1000
 */
open class GenDrawListTask : GenTask("JImGuiDrawListGen", "imgui_draw_list") {
	init {
		description = "Generate binding for ImGui::GetDrawList"
	}

	@Language("JAVA", prefix = "class A{", suffix = "}")
	override val userCode = """/** package-private by design */
	protected long nativeObjectPtr;

	/** package-private by design */
	JImGuiDrawListGen(long nativeObjectPtr) {
		this.nativeObjectPtr = nativeObjectPtr;
	}
"""

	override fun java(javaCode: StringBuilder) {
		primitiveMembers.forEach { (type, name, annotation) ->
			javaCode.genJavaObjectiveMemberAccessor(name, annotation, type)
		}
		functions.forEach { genJavaFun(javaCode, it) }
	}

	override fun `c++`(cppCode: StringBuilder) {
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveAccessor`(type, name, ", jlong nativeObjectPtr") }
		functions.forEach { (name, type, params) -> `genC++Fun`(params.dropLast(1), name, type, cppCode, ", jlong nativeObjectPtr") }
	}

	override val `c++Expr` = "(reinterpret_cast<ImDrawList *> (nativeObjectPtr))->"
	private val functions = listOf(
			Fun.private("pathClear", nativeObjectPtr),
			Fun.protected("pathLineTo", pos(), nativeObjectPtr)
	)

	private val primitiveMembers = listOf(PPT("int", "Flags",
			annotation = "@MagicConstant(flagsFromClass = JImDrawListFlags.class)"))
}
