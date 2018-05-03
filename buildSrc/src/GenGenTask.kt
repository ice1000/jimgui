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

	private val trivialVoidMethods = listOf(
			Pair("sameLine", listOf(p("posX", "float"), p("spacingW", "float"))),
			Pair("setCursorPosX", listOf(p("newValue", "float"))),
			Pair("setCursorPosY", listOf(p("newValue", "float"))),
			Pair("indent", listOf(p("indentW", "float"))),
			Pair("unindent", listOf(p("indentW", "float"))),
			Pair("beginMainMenuBar", emptyList()),
			Pair("endMainMenuBar", emptyList()),
			Pair("beginMenuBar", emptyList()),
			Pair("endMenuBar", emptyList()),
			Pair("separator", emptyList()),
			Pair("newLine", emptyList()),
			Pair("pushItemWidth", listOf(p("itemWidth", "float"))),
			Pair("popItemWidth", emptyList()),
			Pair("pushTextWrapPos", listOf(p("wrapPosX", "float"))),
			Pair("popTextWrapPos", emptyList()),
			Pair("pushAllowKeyboardFocus", listOf(p("allowKeyboardFocus", "boolean"))),
			Pair("popAllowKeyboardFocus", emptyList()),
			Pair("pushButtonRepeat", listOf(p("repeat", "boolean"))),
			Pair("setItemDefaultFocus", emptyList()),
			Pair("setKeyboardFocusHere", listOf(p("offset", "int"))),
			Pair("spacing", emptyList()),
			Pair("beginGroup", emptyList()),
			Pair("endGroup", emptyList()),
			Pair("bullet", emptyList()))

	private val trivialMethods = listOf<Triple<String, String, List<Param>>>(
			Triple("getTextLineHeight", "float", emptyList()),
			Triple("getFontSize", "float", emptyList()),
			Triple("calcItemWidth", "float", emptyList()),
			Triple("getTextLineHeightWithSpacing", "float", emptyList()),
			Triple("getFrameHeight", "float", emptyList()),
			Triple("getFrameHeightWithSpacing", "float", emptyList()),
			Triple("getCursorPosX", "float", emptyList()),
			Triple("getCursorPosY", "float", emptyList())
	)
}