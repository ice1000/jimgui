package org.ice1000.gradle

open class GenGenTask : GenTask("JImGuiGen", "imgui") {
	init {
		description = "Generate binding for ImGui"
	}

	override fun java(javaCode: StringBuilder) {
		trivialMethods.forEach outer@{ (name, type, params) ->
			if (params.any { it is StringParam }) javaCode.append("protected static native ")
			else javaCode.append("public native ")
			javaCode.append(type(type))
					.append(' ')
					.append(name)
					.append('(')
			params.joinTo(javaCode) { it.java() }
			javaCode.append(");")
			val defaults = ArrayList<String>(params.size)
			params.asReversed().forEachIndexed inner@{ index, param ->
				val default = param.default() ?: kotlin.run {
					defaults += param.javaExpr()
					return@inner
				}
				defaults += default
				javaCode.append("\t\t")
						.append("public")
						.append(' ')
						.append(type(type))
						.append(' ')
						.append(name)
						.append('(')
				val newParams = params.dropLast(index + 1)
				newParams.joinTo(javaCode) { it.java() }
				javaCode.append("){")
						.append(ret(type))
						.append(name)
						.append('(')
				newParams.joinTo(javaCode) { it.javaExpr() }
				if (newParams.isNotEmpty()) javaCode.append(',')
				defaults.joinTo(javaCode)
				javaCode.append(");}").append(eol)
			}
		}
	}

	fun String.capitalizeFirst() = "${first().toUpperCase()}${drop(1)}"

	fun `c++SimpleMethod`(name: String, params: List<Param>, type: String?, `c++Expr`: String) =
			"$JNI_FUNC_PREFIX${className}_$name(JNIEnv *env, jobject${
			comma(params)}${params.`c++`()}) -> ${type?.let { "j$it" } ?: "void"} {$eol${ret(type)}$`c++Expr`; }"

	fun `c++StringedFunction`(name: String, params: List<Param>, type: String?, `c++Expr`: String, init: String = "", deinit: String = "") =
			"$JNI_FUNC_PREFIX${className}_$name(JNIEnv *env, jclass${
			comma(params)}${params.`c++`()}) -> ${type?.let { "j$it" }
					?: "void"} {$eol$init ${auto(type)}$`c++Expr`; $deinit ${ret(type, "res;")} }"

	override fun `c++`(cppCode: StringBuilder) {
		trivialMethods.joinLinesTo(cppCode) { (name, type, params) ->
			val initParams = params.mapNotNull { it.surrounding() }
			if (initParams.isNotEmpty()) {
				`c++StringedFunction`(name, params, type, "ImGui::${name.capitalizeFirst()}(${params.`c++Expr`()})",
						init = initParams.joinToString(" ", prefix = "__JNI__FUNCTION__INIT__ ") { it.first },
						deinit = initParams.joinToString(" ", postfix = " __JNI__FUNCTION__CLEAN__") { it.second })
			} else `c++SimpleMethod`(name, params, type, "ImGui::${name.capitalizeFirst()}(${params.`c++Expr`()})")
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
			Fun("text", string("text")),
			Fun("bulletText", string("text")),
			Fun("labelText", string("label"), string("text")),
			Fun("textDisabled", string("text")),
			Fun("textWrapped", string("text")),
//			Fun("button", "boolean", string("text")),
			Fun("smallButton", "boolean", string("text")),
			Fun("arrowButton", "boolean", string("text"), int("direction")),
			Fun("bullet")
	)
}