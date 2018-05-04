package org.ice1000.gradle

open class GenGenTask : GenTask("JImGuiGen", "imgui") {
	init {
		description = "Generate binding for ImGui"
	}

	override fun java(javaCode: StringBuilder) {
		trivialMethods.forEach { (name, type, params) ->
			javaCode.appendln(javaSimpleMethod(name, params, type ?: "void"))
			params.asReversed().forEach inner@{ param ->
				val default = param.default() ?: return@inner
			}
		}
	}

	fun String.capitalizeFirst() = "${first().toUpperCase()}${drop(1)}"

	override fun `c++`(cppCode: StringBuilder) {
		trivialMethods.joinLinesTo(cppCode) { (name, type, params) ->
			`c++SimpleMethod`(name, params, type, "ImGui::${name.capitalizeFirst()}(${params.`c++Expr`()})")
		}
	}

	private val trivialMethods = listOf(
			Fun("sameLine", float("posX"), float("spacingW")),
			Fun("setCursorPosX", float("newValue")),
			Fun("setCursorPosY", float("newValue")),
			Fun("indent", float("indentW")),
			Fun("unindent", float("indentW")),
			Fun("beginMainMenuBar"),
			Fun("endMainMenuBar"),
			Fun("beginMenuBar"),
			Fun("endMenuBar"),
			Fun("separator"),
			Fun("newLine"),
			Fun("pushItemWidth", float("itemWidth")),
			Fun("popItemWidth"),
			Fun("pushTextWrapPos", float("wrapPosX")),
			Fun("popTextWrapPos"),
			Fun("pushAllowKeyboardFocus", bool("allowKeyboardFocus")),
			Fun("popAllowKeyboardFocus"),
			Fun("pushButtonRepeat", bool("repeat")),
			Fun("setItemDefaultFocus"),
			Fun("setKeyboardFocusHere", int("offset")),
			Fun("dummy", vec2("width", "height")),
			Fun("spacing"),
			Fun("setCursorPos", vec2("posX", "spacingW")),
			Fun("setCursorScreenPos", vec2("screenPosX", "screenPosY")),
			Fun("setScrollX", float("scrollX")),
			Fun("setScrollY", float("scrollY")),
			Fun("setScrollHere", float("centerYRatio")),
			Fun("setScrollFromPosY", float("posY"), float("centerYRatio")),
			Fun("alignTextToFramePadding"),
			Fun("beginGroup"),
			Fun("endGroup"),
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
			Fun("getCursorPosY", "float"),
			Fun("bullet")
	)
}