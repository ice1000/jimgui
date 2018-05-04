package org.ice1000.gradle

open class GenGenTask : GenTask("JImGuiGen", "imgui") {
	init {
		description = "Generate binding for ImGui"
	}

	override fun java(javaCode: StringBuilder) {
		trivialMethods.forEach { (name, type, params) ->
			javaCode.appendln(javaSimpleMethod(name, params, type ?: "void"))
		}
		trivialVoidMethods.forEach { (name, _, params) ->
			javaCode.appendln(javaSimpleMethod(name, params, "void"))
		}
	}

	fun String.capitalizeFirst() = "${first().toUpperCase()}${drop(1)}"

	override fun `c++`(cppCode: StringBuilder) {
		trivialMethods.joinLinesTo(cppCode) { (name, type, params) ->
			`c++SimpleMethod`(name, params, type, "return ImGui::${name.capitalizeFirst()}(${params.`c++Expr`()})")
		}
		trivialVoidMethods.joinLinesTo(cppCode) { (name, _, params) ->
			`c++SimpleMethod`(name, params, null, "ImGui::${name.capitalizeFirst()}(${params.`c++Expr`()})")
		}
	}

	private val trivialVoidMethods = listOf(
			Fun("sameLine", listOf(float("posX"), float("spacingW"))),
			Fun("setCursorPosX", listOf(float("newValue"))),
			Fun("setCursorPosY", listOf(float("newValue"))),
			Fun("indent", listOf(float("indentW"))),
			Fun("unindent", listOf(float("indentW"))),
			Fun("beginMainMenuBar"),
			Fun("endMainMenuBar"),
			Fun("beginMenuBar"),
			Fun("endMenuBar"),
			Fun("separator"),
			Fun("newLine"),
			Fun("pushItemWidth", listOf(float("itemWidth"))),
			Fun("popItemWidth"),
			Fun("pushTextWrapPos", listOf(float("wrapPosX"))),
			Fun("popTextWrapPos"),
			Fun("pushAllowKeyboardFocus", listOf(bool("allowKeyboardFocus"))),
			Fun("popAllowKeyboardFocus"),
			Fun("pushButtonRepeat", listOf(bool("repeat"))),
			Fun("setItemDefaultFocus"),
			Fun("setKeyboardFocusHere", listOf(int("offset"))),
			Fun("dummy", listOf(vec2("width", "height"))),
			Fun("spacing"),
			Fun("setCursorPos", listOf(vec2("posX", "spacingW"))),
			Fun("setCursorScreenPos", listOf(vec2("screenPosX", "screenPosY"))),
			Fun("setScrollX", listOf(float("scrollX"))),
			Fun("setScrollY", listOf(float("scrollY"))),
			Fun("setScrollHere", listOf(float("centerYRatio"))),
			Fun("setScrollFromPosY", listOf(float("posY"), float("centerYRatio"))),
			Fun("alignTextToFramePadding"),
			Fun("beginGroup"),
			Fun("endGroup"),
			Fun("bullet"))

	private val trivialMethods = listOf(
			Fun("getTextLineHeight", "float"),
			Fun("getFontSize", "float"),
			Fun("calcItemWidth", "float"),
			Fun("getTextLineHeightWithSpacing", "float"),
			Fun("getFrameHeight", "float"),
			Fun("getFrameHeightWithSpacing", "float"),
			Fun("getScrollX", "float"),
			Fun("getScrollY", "float"),
			Fun("getScrollMaxX", "float"),
			Fun("getScrollMaxY", "float"),
			Fun("getCursorPosX", "float"),
			Fun("getCursorPosY", "float")
	)
}