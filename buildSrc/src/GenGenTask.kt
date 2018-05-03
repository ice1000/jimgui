package org.ice1000.gradle

open class GenGenTask : GenTask("JImGuiGen", "imgui") {
	init {
		description = "Generate binding for ImGui"
	}

	override fun java(javaCode: StringBuilder) {
		trivialMethods.joinLinesTo(javaCode) { (name, type, params) -> javaSimpleMethod(name, params, type) }
		trivialVoidMethods.joinLinesTo(javaCode) { (name, params) -> javaSimpleMethod(name, params, "void") }
	}

	fun String.capitalizeFirst() = "${first().toUpperCase()}${drop(1)}"

	override fun `c++`(cppCode: StringBuilder) {
		trivialMethods.joinLinesTo(cppCode) { (name, type, params) ->
			`c++SimpleMethod`(name, params, type, "return ImGui::${name.capitalizeFirst()}(${params.`c++Expr`()})")
		}
		trivialVoidMethods.joinLinesTo(cppCode) { (name, params) ->
			`c++SimpleMethod`(name, params, null, "ImGui::${name.capitalizeFirst()}(${params.`c++Expr`()})")
		}
	}

	private val trivialVoidMethods = listOf(
			Pair("sameLine", listOf(float("posX"), float("spacingW"))),
			Pair("setCursorPosX", listOf(float("newValue"))),
			Pair("setCursorPosY", listOf(float("newValue"))),
			Pair("indent", listOf(float("indentW"))),
			Pair("unindent", listOf(float("indentW"))),
			Pair("beginMainMenuBar", emptyList()),
			Pair("endMainMenuBar", emptyList()),
			Pair("beginMenuBar", emptyList()),
			Pair("endMenuBar", emptyList()),
			Pair("separator", emptyList()),
			Pair("newLine", emptyList()),
			Pair("pushItemWidth", listOf(float("itemWidth"))),
			Pair("popItemWidth", emptyList()),
			Pair("pushTextWrapPos", listOf(float("wrapPosX"))),
			Pair("popTextWrapPos", emptyList()),
			Pair("pushAllowKeyboardFocus", listOf(bool("allowKeyboardFocus"))),
			Pair("popAllowKeyboardFocus", emptyList()),
			Pair("pushButtonRepeat", listOf(bool("repeat"))),
			Pair("setItemDefaultFocus", emptyList()),
			Pair("setKeyboardFocusHere", listOf(int("offset"))),
			Pair("dummy", listOf(vec2("width", "height"))),
			Pair("spacing", emptyList()),
			Pair("setCursorPos", listOf(vec2("posX", "spacingW"))),
			Pair("setCursorScreenPos", listOf(vec2("screenPosX", "screenPosY"))),
			Pair("setScrollX", listOf(float("scrollX"))),
			Pair("setScrollY", listOf(float("scrollY"))),
			Pair("setScrollHere", listOf(float("centerYRatio"))),
			Pair("setScrollFromPosY", listOf(float("posY"), float("centerYRatio"))),
			Pair("alignTextToFramePadding", emptyList()),
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
			Triple("getScrollX", "float", emptyList()),
			Triple("getScrollY", "float", emptyList()),
			Triple("getScrollMaxX", "float", emptyList()),
			Triple("getScrollMaxY", "float", emptyList()),
			Triple("getCursorPosX", "float", emptyList()),
			Triple("getCursorPosY", "float", emptyList())
	)
}