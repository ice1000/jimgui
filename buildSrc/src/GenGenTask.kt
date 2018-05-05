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
				params.joinTo(javaCode) { it.javaExpr() }
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
				newParams.joinTo(javaCode) { it.javaDefault() }
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
			Fun("sameLine",
					float("posX", default = 0),
					float("spacingW", default = -1)),
			Fun("newLine"),
			Fun("spacing"),
			Fun("dummy", size()),
			Fun("indent", float("indentW", default = 0)),
			Fun("unindent", float("indentW", default = 0)),
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
			Fun("button", "boolean", string("text"), size("0,0")),
			Fun("smallButton", "boolean", string("text")),
			Fun("invisibleButton", "boolean",
					string("text"),
					size("0,0")),
			Fun("arrowButton", "boolean", string("text"), int("direction")),
			Fun("radioButton", "boolean", string("text"), bool("active")),
			Fun("bullet"),
			Fun("progressBar",
					float("fraction"),
					size("-1,0"),
					string("overlay", default = "(byte[])null")),

			// Widgets: Trees
			Fun("treeNode", "boolean", string("label")),
			Fun("treePush", string("stringID")),
			Fun("treePop"),
			Fun("treeAdvanceToLabelPos"),
			Fun("getTreeNodeToLabelSpacing", "float"),
			Fun("setNextTreeNodeOpen", bool("isOpen"), int("condition", default = 0)),
			Fun("collapsingHeader", "boolean",
					string("label"),
					int("flags", default = 0)),

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
					string("shortcut", default = "(byte[])null"),
					bool("selected", default = false),
					bool("enabled", default = true)),

			// Columns
			Fun("columns",
					int("count", default = 1),
					string("id", default = "(byte[])null"),
					bool("border", default = true)),
			Fun("nextColumn"),
			Fun("getColumnIndex", "int"),
			Fun("getColumnWidth", "float", int("columnIndex", default = -1)),
			Fun("getColumnOffset", "float", int("columnIndex", default = -1)),
			Fun("setColumnWidth", int("columnIndex"), float("width")),
			Fun("setColumnOffset", int("columnIndex"), float("offsetX")),
			Fun("getColumnsCount", "int"),

			// Logging/Capture: all text output from interface is captured to tty/file/clipboard.
			Fun("logToTTY", int("maxDepth", default = -1)),
			Fun("logToFile",
					int("maxDepth", default = -1),
					string("fileName", default = "(byte[])null")),
			Fun("logToClipboard", int("maxDepth", default = -1)),
			Fun("logFinish"),
			Fun("logButtons"),
			Fun("logText", string("text")),

			// Clipping
			Fun("pushClipRect",
					vec2("clipRectMinWidth", "clipRectMinHeight"),
					vec2("clipRectMaxWidth", "clipRectMaxHeight"),
					bool("intersectWithCurrentClipRect")),
			Fun("popClipRect"),

			// Parameters stacks (current window)
			Fun("pushItemWidth", float("itemWidth")),
			Fun("popItemWidth"),
			Fun("calcItemWidth", "float"),
			Fun("pushTextWrapPos", float("wrapPosX", default = 0)),
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