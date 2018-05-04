package org.ice1000.gradle

open class GenGenTask : GenTask("JImGuiGen", "imgui") {
	init {
		description = "Generate binding for ImGui"
	}

	override fun java(javaCode: StringBuilder) {
		trivialMethods.forEach outer@{ (name, type, params) ->
			javaCode.appendln(javaSimpleMethod(name, params, type))
			val defaults = ArrayList<String>(params.size)
			params.asReversed().forEachIndexed inner@{ index, param ->
				val default = param.default() ?: kotlin.run {
					defaults += param.javaExpr()
					return@inner
				}
				defaults += default
				javaCode.appendln(javaOverloadMethod(name, params.dropLast(index + 1), defaults, type))
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
			Fun("sameLine", float("posX", default = "0.0f"), float("spacingW", default = "-1.0f")),
			Fun("setCursorPosX", float("newValue")),
			Fun("setCursorPosY", float("newValue")),
			Fun("indent", float("indentW", default = "0.0f")),
			Fun("unindent", float("indentW", default = "0.0f")),
			Fun("beginMainMenuBar"),
			Fun("endMainMenuBar"),
			Fun("beginMenuBar"),
			Fun("endMenuBar"),
			Fun("separator"),
			Fun("newLine"),
			Fun("pushItemWidth", float("itemWidth")),
			Fun("popItemWidth"),
			Fun("pushTextWrapPos", float("wrapPosX", default = "0.0f")),
			Fun("popTextWrapPos"),
			Fun("pushAllowKeyboardFocus", bool("allowKeyboardFocus")),
			Fun("popAllowKeyboardFocus"),
			Fun("pushButtonRepeat", bool("repeat", default = "false")),
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