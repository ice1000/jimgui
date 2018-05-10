package org.ice1000.gradle

open class GenGenTask : GenTask("JImGuiGen", "imgui") {
	companion object {
		lateinit var parser: ImGuiHeaderParser
	}
	init {
		description = "Generate binding for ImGui"
	}

	override fun java(javaCode: StringBuilder) {
		parser = ImGuiHeaderParser(project.projectDir.resolve("jni").resolve("imgui").resolve("imgui.h"))
		trivialMethods.forEach {
			it.document = parser.map[it.name.capitalize()]
			genFun(javaCode, it)
		}
	}

	override fun `c++`(cppCode: StringBuilder) =
			trivialMethods.forEach { (name, type, params) -> `genFunC++`(params, name, type, cppCode) }

	override val `c++Prefix`: String get() = "ImGui::"
	private val trivialMethods = listOf(
			// Styles
			Fun("styleColorsDark"),
			Fun("styleColorsClassic"),
			Fun("styleColorsLight"),

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

			// Windows Utilities
			Fun("isWindowAppearing", "boolean"),
			Fun("isWindowCollapsed", "boolean"),
			Fun("isWindowFocused", "boolean", flags(from = "Focused", default = "ChildWindows")),
			Fun("isWindowHovered", "boolean", flags(from = "Hovered", default = "Default")),
			Fun("getWindowWidth", "float"),
			Fun("getWindowHeight", "float"),
			Fun("getContentRegionAvailWidth", "float"),
			Fun("getWindowContentRegionWidth", "float"),

			Fun("setNextWindowPos", pos(), cond),
			Fun("setNextWindowSize", size(), cond),
			Fun("setNextWindowSizeConstraints", size("Min"), size("Max")),
			Fun("setNextWindowContentSize", size()),
			Fun("setNextWindowCollapsed", bool("collapsed"), cond),
			Fun("setNextWindowFocus"),
			Fun("setNextWindowBgAlpha", float("alpha")),
			Fun("setWindowFontScale", float("scale")),
			Fun("setWindowPos", string("name"), pos(), cond),
			Fun("setWindowSize", string("name"), size(), cond),
			Fun("setWindowCollapsed", string("name"), bool("collapsed"), cond),
			Fun("setWindowFocus", string("name")),

			// Inputs
			Fun("getKeyIndex", "int", int("imguiKey")),
			Fun("isKeyDown", "boolean", int("userKeyIndex")),
			Fun("isKeyPressed", "boolean", int("userKeyIndex"), bool("repeat", default = true)),
			Fun("isKeyReleased", "boolean", int("userKeyIndex")),
			Fun("getKeyPressedAmount", "int", int("keyIndex"), float("repeatDelay"), float("rate")),
			Fun("isMouseDown", "boolean", int("button")),
			Fun("isAnyMouseDown", "boolean"),
			Fun("isMouseClicked", "boolean", int("button"), bool("repeat", default = false)),
			Fun("isMouseDoubleClicked", "boolean", int("button")),
			Fun("isMouseReleased", "boolean", int("button")),
			Fun("isMouseDragging", "boolean", int("button"), float("lockThreshold", default = -1)),
			Fun("isMouseHoveringRect", "boolean", size("RMin"), size("RMax"), bool("clip", default = true)),
			Fun("isMousePosValid", "boolean"),
			Fun("captureKeyboardFromApp", bool("capture", default = true)),
			Fun("captureMouseFromApp", bool("capture", default = true)),

			// ID stack/scopes
			Fun("popID"),
			Fun("pushID", int("intID")),
			Fun("pushID0", string("stringIDBegin"), string("stringIDEnd")),
			Fun("pushID1", string("stringID")),
			Fun("getID", "int", string("stringID")),
			Fun("getID0", "int", string("stringIDBegin"), string("stringIDEnd")),

			// Windows
			/*Fun("begin", "boolean"),*/ // this is hand-written
			Fun("end"),
			Fun("beginChild", "boolean",
					int("id"),
					size(default = "0,0"),
					bool("border", default = false),
					flags(from = "Window", default = "NoTitleBar")),
			Fun("beginChild0", "boolean",
					string("stringID"),
					size(default = "0,0"),
					bool("border", default = false),
					flags(from = "Window", default = "NoTitleBar")),
			Fun("endChild"),

			// Widgets: Text
			// Fun("text", text),
			Fun.protected("textUnformatted", text),
			Fun("textColored", vec4("color"), text),
			Fun("bulletText", text),
			Fun("labelText", label, text),
			Fun("textDisabled", text),
			Fun("textWrapped", text),

			// Widgets: Main
			Fun("button", "boolean", text, size(default = "0,0")),
			Fun("smallButton", "boolean", text),
			Fun("invisibleButton", "boolean",
					text,
					size(default = "0,0")),
			Fun("arrowButton", "boolean",
					text,
					int("direction", annotation = "@MagicConstant(valuesFromClass = JImDir.class)")),
			Fun("radioButton", "boolean", text, bool("active")),
			Fun("bullet"),
			Fun("progressBar",
					float("fraction"),
					size(default = "-1,0"),
					string("overlay", default = "(byte[])null")),

			// Widgets: Combo Box
			Fun("beginCombo", "boolean",
					label,
					string("previewValue"),
					flags(from = "Combo", default = "PopupAlignLeft")),
			Fun("endCombo"),

			// Widgets: Trees
			Fun("treeNode", "boolean", label),
			Fun("treeNodeEx", "boolean", label, flags(from = "TreeNode", default = "Selected")),
			Fun("treePush", string("stringID")),
			Fun("treePop"),
			Fun("treeAdvanceToLabelPos"),
			Fun("getTreeNodeToLabelSpacing", "float"),
			Fun("setNextTreeNodeOpen", bool("isOpen"), int("condition", default = 0)),
			Fun("collapsingHeader", "boolean", label, flags(from = "TreeNode", default = "Selected")),

			// Widgets: Selectable / Lists
			Fun("selectable", "boolean",
					string("label"),
					bool("selected", default = false),
					flags(from = "Selectable", default = "DontClosePopups"),
					size(default = "0,0")),
			Fun("listBoxHeader", "boolean", string("label"), size()),
			Fun("listBoxHeader0", "boolean",
					string("label"),
					int("itemsCount"),
					int("heightInItems", default = -1)),
			Fun("listBoxFooter"),

			// Tooltips
			Fun("setTooltip", text),
			Fun("beginTooltip"),
			Fun("endTooltip"),

			// Menus
			Fun("beginMainMenuBar", "boolean"),
			Fun("endMainMenuBar"),
			Fun("beginMenuBar", "boolean"),
			Fun("endMenuBar"),
			Fun("beginMenu", "boolean",
					label,
					bool("enabled", default = true)),
			Fun("endMenu"),
			Fun("menuItem", "boolean",
					label,
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
			Fun("logText", text),

			// Clipping
			Fun("pushClipRect",
					size("ClipRectMin"),
					size("ClipRectMax"),
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
			Fun("popFont"),
			Fun("pushStyleColor", int("index"), vec4("color")),
			Fun("popStyleColor", int("count", default = 1)),
			Fun.protected("pushStyleVarImVec2",
					int("styleVar"),
					vec2("valueX", "valueY")),
			Fun.protected("pushStyleVarFloat",
					int("styleVar"),
					float("value")),
			Fun("popStyleVar", int("count", default = 1)),

			// Focus, Activation
			Fun("setItemDefaultFocus"),
			Fun("setKeyboardFocusHere", int("offset")),

			// Settings/.Ini Utilities
			Fun("loadIniSettingsFromDisk", string("iniFileName")),
			Fun("saveIniSettingsToDisk", string("iniFileName")),

			// Utilities
			Fun("isItemHovered", "boolean", flags(from = "Hovered", default = "Default")),
			Fun("isItemActive", "boolean"),
			Fun("isItemFocused", "boolean"),
			Fun("isItemClicked", "boolean", int("mouseButton", default = 0)),
			Fun("isItemVisible", "boolean"),
			Fun("isAnyItemHovered", "boolean"),
			Fun("isAnyItemActive", "boolean"),
			Fun("isAnyItemFocused", "boolean"),
			Fun("setItemAllowOverlap"),
			Fun("isRectVisible", "boolean", size()),

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
