package org.ice1000.gradle

open class GenGenTask : GenTask("JImGuiGen", "imgui") {
	init {
		description = "Generate binding for ImGui"
	}

	override fun java(javaCode: StringBuilder) {
		trivialMethods.forEach outer@{ (name, type, params) ->
			if (params.any { it is StringParam }) {
				javaCode.append("\tpublic final ")
						.append(type(type))
						.append(' ')
						.append(name)
						.append('(')
				params.forEachIndexed { index, param ->
					if (index != 0) javaCode.append(",")
					if (param is StringParam) javaCode.append("@NotNull String ")
							.append(param.name)
					else javaCode.append(param.java())
				}
				javaCode
						.append("){")
						.append(ret(type))
						.append(name)
						.append('(')
				params.forEachIndexed { index, param ->
					if (index != 0) javaCode.append(",")
					if (param is StringParam) javaCode.append("getBytes(")
							.append(param.name)
							.append(')')
					else javaCode.append(param.javaExpr())
				}
				javaCode
						.append(");}")
						.append(eol)
						.append("\tprotected static native ")
			} else javaCode.append("\tpublic final native ")
			javaCode.append(type(type))
					.append(' ')
					.append(name)
					.append('(')
			params.joinTo(javaCode) { it.java() }
			javaCode.append(");").append(eol)
			val defaults = ArrayList<String>(params.size)
			params.asReversed().forEachIndexed inner@{ index, param ->
				val default = param.default?.toString() ?: kotlin.run {
					defaults += param.javaExpr()
					return@inner
				}
				defaults += default
				javaCode.append("\tpublic ")
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
				defaults.asReversed().joinTo(javaCode)
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
			// Cursor / Layout
			Fun("separator"),
			Fun("sameLine", float("posX", default = "0.0f"), float("spacingW", default = "-1.0f")),
			Fun("newLine"),
			Fun("spacing"),
			Fun("dummy", vec2("width", "height")),
			Fun("indent", float("indentW", default = "0.0f")),
			Fun("unindent", float("indentW", default = "0.0f")),
			Fun("beginGroup"),
			Fun("endGroup"),
			Fun("getCursorPosX", "float"),
			Fun("getCursorPosY", "float"),
			Fun("setCursorPos", vec2("posX", "spacingW")),
			Fun("setCursorScreenPos", vec2("screenPosX", "screenPosY")),
			Fun("setCursorPosX", float("newValue")),
			Fun("setCursorPosY", float("newValue")),
			Fun("alignTextToFramePadding"),
			Fun("getTextLineHeight", "float"),
			Fun("getTextLineHeightWithSpacing", "float"),
			Fun("getFrameHeight", "float"),
			Fun("getFrameHeightWithSpacing", "float"),

			// Widgets: Text
			Fun("text", string("text")),
			Fun("bulletText", string("text")),
			Fun("labelText", string("label"), string("text")),
			Fun("textDisabled", string("text")),
			Fun("textWrapped", string("text")),

			// Widgets: Main
//			Fun("button", "boolean", string("text")),
			Fun("smallButton", "boolean", string("text")),
			Fun("arrowButton", "boolean", string("text"), int("direction")),
			Fun("bullet"),

			// Tooltips
			Fun("setTooltip", string("text")),
			Fun("beginTooltip"),
			Fun("endTooltip"),

			// Menus
			Fun("beginMainMenuBar", "boolean"),
			Fun("endMainMenuBar"),
			Fun("beginMenuBar", "boolean"),
			Fun("endMenuBar"),
			Fun("beginMenu", "boolean",
					string("label"),
					bool("enabled", default = true)),
			Fun("endMenu"),
			Fun("menuItem", "boolean",
					string("label"),
					string("shortcut" /*, default = "null"*/),
					bool("selected", default = false),
					bool("enabled", default = true)),

			// Parameters stacks (current window)
			Fun("pushItemWidth", float("itemWidth")),
			Fun("popItemWidth"),
			Fun("calcItemWidth", "float"),
			Fun("pushTextWrapPos", float("wrapPosX", default = "0.0f")),
			Fun("popTextWrapPos"),
			Fun("pushAllowKeyboardFocus", bool("allowKeyboardFocus")),
			Fun("popAllowKeyboardFocus"),
			Fun("pushButtonRepeat", bool("repeat", default = false)),
			Fun("popButtonRepeat"),

			// Parameters stacks (shared)
			Fun("getFontSize", "float"),

			// Focus, Activation
			Fun("setItemDefaultFocus"),
			Fun("setKeyboardFocusHere", int("offset")),

			// Windows Scrolling
			Fun("setScrollX", float("scrollX")),
			Fun("setScrollY", float("scrollY")),
			Fun("setScrollHere", float("centerYRatio")),
			Fun("setScrollFromPosY", float("posY"), float("centerYRatio")),
			Fun("getScrollX", "float"),
			Fun("getScrollY", "float"),
			Fun("getScrollMaxX", "float"),
			Fun("getScrollMaxY", "float")
	)
}