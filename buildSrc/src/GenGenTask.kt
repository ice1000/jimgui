package org.ice1000.gradle

open class GenGenTask : GenTask("JImGuiGen", "imgui") {
	init {
		description = "Generate binding for ImGui"
	}

	override fun java(javaCode: StringBuilder) {
		trivialMethods.joinLinesTo(javaCode) { (name, type, params) -> javaSimpleMethod(name, params, type) }
		trivialVoidMethods.joinLinesTo(javaCode) { (name, params) -> javaSimpleMethod(name, params, "void") }
	}

	override fun cpp(cppCode: StringBuilder) {
		trivialMethods.joinLinesTo(cppCode) { (name, type, params) ->
			`c++SimpleMethod`(name, params, type, "return ImGui::${name[0].toUpperCase()}${name.drop(1)}(${params.joinToString { it.name }})")
		}
		trivialVoidMethods.joinLinesTo(cppCode) { (name, params) ->
			`c++SimpleMethod`(name, params, null, "ImGui::${name[0].toUpperCase()}${name.drop(1)}(${params.joinToString { it.name }})")
		}
	}

	private val trivialVoidMethods = listOf<Pair<String, List<Param>>>(
			Pair("separator", emptyList()),
			Pair("newLine", emptyList()),
			Pair("spacing", emptyList()),
			Pair("beginGroup", emptyList()),
			Pair("endGroup", emptyList()),
			Pair("bullet", emptyList()))

	private val trivialMethods = listOf<Triple<String, String, List<Param>>>(
			Triple("getTextLineHeight", "float", emptyList()),
			Triple("getTextLineHeightWithSpacing", "float", emptyList()),
			Triple("getFrameHeight", "float", emptyList()),
			Triple("getFrameHeightWithSpacing", "float", emptyList()),
			Triple("getCursorPosX", "float", emptyList()),
			Triple("getCursorPosY", "float", emptyList())
	)
}