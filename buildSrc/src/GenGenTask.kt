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
			it.document = it.document ?: parser.map[it.name.capitalize()]
			genJavaFun(javaCode, it)
		}
	}

	override val `c++Expr` = "ImGui::"
	override fun `c++`(cppCode: StringBuilder) =
			trivialMethods.forEach { (name, type, params) -> `genC++Fun`(params, name, type, cppCode) }

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

			// Demo, Debug, Information
			Fun("showUserGuide"),
			Fun("showDemoWindow", pOpen),
			Fun("showMetricsWindow", pOpen),
			Fun("showFontSelector", label),
			Fun("showStyleSelector", label),
			Fun("showStyleEditor"),
			/*Fun("showStyleEditor", "boolean", label),*/

			// Windows Utilities
			Fun("isWindowAppearing", "boolean"),
			Fun("isWindowCollapsed", "boolean"),
			Fun("isWindowFocused", "boolean", flags(from = "Focused", default = "ChildWindows")),
			Fun("isWindowHovered", "boolean", flags(from = "Hovered", default = "Default")),
			Fun("getWindowWidth", "float"),
			Fun("getWindowHeight", "float"),
			Fun("getContentRegionAvailWidth", "float"),
			Fun("getWindowContentRegionWidth", "float"),

			Fun("setNextWindowPos", pos(), cond, pos("windowPosPivot", default = "0,0")),
			Fun("setNextWindowSize", size(), cond),
			Fun("setNextWindowSizeConstraints", size("Min"), size("Max")),
			Fun("setNextWindowContentSize", size()),
			Fun("setNextWindowCollapsed", bool("collapsed"), cond),
			Fun("setNextWindowFocus"),
			Fun("setNextWindowBgAlpha", float("alpha")),
			Fun("setWindowFontScale", float("scale")),
			Fun("setWindowPos", string("name"), pos("windowPos"), cond),
			Fun("setWindowSize", string("name"), size(), cond),
			Fun("setWindowCollapsed", string("name"), bool("collapsed"), cond),
			Fun("setWindowFocus", string("name")),

			// Inputs
			Fun("getKeyIndex", "int", int("imguiKey")),
			Fun("isKeyDown", "boolean", int("userKeyIndex")),
			Fun("isKeyPressed", "boolean", int("userKeyIndex"), bool("repeat", default = true)),
			Fun("isKeyReleased", "boolean", int("userKeyIndex")),
			Fun("getKeyPressedAmount", "int", int("keyIndex"), float("repeatDelay"), float("rate")),
			Fun("isMouseDown", "boolean", mouseButton),
			Fun("isAnyMouseDown", "boolean"),
			Fun("isMouseClicked", "boolean", mouseButton, bool("repeat", default = false)),
			Fun("isMouseDoubleClicked", "boolean", mouseButton),
			Fun("isMouseReleased", "boolean", mouseButton),
			Fun("isMouseDragging", "boolean", mouseButton, float("lockThreshold", default = -1)),
			Fun("isMouseHoveringRect", "boolean", size("RMin"), size("RMax"), bool("clip", default = true)),
			Fun("isMousePosValid", "boolean"),
			Fun("captureKeyboardFromApp", bool("capture", default = true)),
			Fun("captureMouseFromApp", bool("capture", default = true)),

			// Clipboard Utilities
			Fun("setClipboardText", string("text")),

			// ID stack/scopes
			Fun("popID"),
			Fun("pushID", int("intID")),
			Fun("pushID0", string("stringIDBegin"), string("stringIDEnd")),
			Fun("pushID1", stringID),
			Fun("getID", "int", stringID),
			Fun("getID0", "int", string("stringIDBegin"), string("stringIDEnd")),

			// Windows
			Fun("begin", "boolean", string("name"), pOpen, windowFlags),
			Fun("end"),
			Fun("beginChild", "boolean",
					int("id"),
					size(default = "0,0"),
					bool("border", default = false),
					windowFlags),
			Fun("beginChild0", "boolean",
					stringID,
					size(default = "0,0"),
					bool("border", default = false),
					windowFlags),
			Fun("endChild"),

			// Widgets: Text
			Fun("bulletText", text),
			Fun("labelText", label, text),
			Fun("textWrapped", text),

			// Widgets: Main
			Fun("button", "boolean", text, size(default = "0,0")),
			Fun("smallButton", "boolean", text),
			Fun("invisibleButton", "boolean",
					text,
					size(default = "0,0")),
			Fun("arrowButton", "boolean",
					text,
					int("direction", annotation = "@MagicConstant(valuesFromClass = JImDirection.class)")),
			Fun("checkbox", "boolean", label, boolPtr("v")),
			Fun("radioButton", "boolean", text, bool("active")),
			Fun("bullet"),
			Fun("progressBar",
					float("fraction"),
					size(default = "-1,0"),
					string("overlay", default = strNull)),

			// Widgets: Combo Box
			Fun("beginCombo", "boolean",
					label,
					string("previewValue"),
					flags(from = "Combo", default = "PopupAlignLeft")),
			Fun("endCombo"),

			// Widgets: Drags
			Fun("dragFloat", "boolean", label,
					floatPtr("value"),
					float("valueSpeed", default = 1),
					float("valueMin", default = 0),
					float("valueMax", default = 0),
					string("format", default = "\"%.3f\\0\".getBytes(StandardCharsets.UTF_8)"),
					float("power", default = 1)),
			Fun("dragFloatRange2", "boolean", label,
					floatPtr("valueCurrentMin"),
					floatPtr("valueCurrentMax"),
					float("valueSpeed", default = 1),
					float("valueMin", default = 0),
					float("valueMax", default = 0),
					string("format", default = "\"%.3f\\0\".getBytes(StandardCharsets.UTF_8)"),
					string("formatMax", default = strNull),
					float("power", default = 1)),
			Fun("dragInt", "boolean", label,
					intPtr("value"),
					float("valueSpeed", default = 1),
					int("valueMin", default = 0),
					int("valueMax", default = 0),
					string("format", default = "\"%d\\0\".getBytes(StandardCharsets.UTF_8)")),
			Fun("dragIntRange2", "boolean", label,
					intPtr("valueCurrentMin"),
					intPtr("valueCurrentMax"),
					float("valueSpeed", default = 1),
					int("valueMin", default = 0),
					int("valueMax", default = 0),
					string("format", default = "\"%d\\0\".getBytes(StandardCharsets.UTF_8)")),

			// Widgets: Trees
			Fun("treeNode", "boolean", label),
			Fun("treeNodeEx", "boolean", label, treeNodeFlags),
			Fun("treePush", stringID),
			Fun("treePop"),
			Fun("treeAdvanceToLabelPos"),
			Fun("getTreeNodeToLabelSpacing", "float"),
			Fun("setNextTreeNodeOpen", bool("isOpen"), cond),
			Fun("collapsingHeader", "boolean", label, pOpen, treeNodeFlags),

			// Widgets: Color Editor/Picker
			Fun("colorEdit3", "boolean", string("label"), vec4("color"), flags(from = "ColorEdit", default = "Nothing")),
			Fun("colorEdit4", "boolean", string("label"), vec4("color"), flags(from = "ColorEdit", default = "Nothing")),
			Fun("colorButton", "boolean",
					string("descriptionID"),
					vec4("color"),
					flags(from = "ColorEdit", default = "Nothing"),
					size(default = "0,0")),
			Fun("colorPicker3", "boolean", string("label"), vec4("color"), flags(from = "ColorEdit", default = "Nothing")),
			Fun("colorPicker4", "boolean", string("label"), vec4("color"), flags(from = "ColorEdit", default = "Nothing")),
			Fun("setColorEditOptions", flags()),

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
					string("shortcut", default = strNull),
					bool("selected", default = false),
					bool("enabled", default = true)),
			Fun("menuItem0", "boolean",
					label,
					string("shortcut", default = strNull),
					boolPtr("selected", nullable = true),
					bool("enabled", default = true)),

			// Popups
			Fun("openPopup", stringID),
			Fun("beginPopup", "boolean", stringID, windowFlags),
			Fun("beginPopupContextItem", "boolean", nStringID, int("mouseButton", default = 1)),
			Fun("beginPopupContextWindow", "boolean", nStringID, int("mouseButton", default = 1)),
			Fun("beginPopupContextVoid", "boolean", nStringID, int("mouseButton", default = 1)),
			Fun("beginPopupModal", "boolean", string("name"), pOpen, windowFlags),
			Fun("endPopup"),
			Fun("openPopupOnItemClick", "boolean", nStringID, int("mouseButton", default = 1)),
			Fun("isPopupOpen", "boolean", stringID),
			Fun("closeCurrentPopup"),

			// Columns
			Fun("columns",
					int("count", default = 1),
					nStringID,
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
					string("fileName", default = strNull)),
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
			Fun("pushStyleColor", int("index", annotation = "@MagicConstant(valuesFromClass = JImStyleColors.class)"), vec4("color")),
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
			Fun("getTime", "float"),
			Fun("getFrameCount", "int"),

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

// Channels
//Fun("channelsSplit", int("channelsCount")),
//Fun("channelsMerge"),
//Fun("channelsSetCurrent", int("channelsIndex"))
