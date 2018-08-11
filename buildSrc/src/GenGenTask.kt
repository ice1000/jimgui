package org.ice1000.gradle

import org.gradle.api.Project

open class GenGenTask : GenTask("JImGuiGen", "imgui") {
	companion object {
		lateinit var parser: ImGuiHeaderParser
		private var isParserInitialized = false
		fun checkParserInitialized(project: Project) {
			if (isParserInitialized) return
			parser = ImGuiHeaderParser(project.projectDir.resolve("jni").resolve("imgui").resolve("imgui.h"))
			isParserInitialized = true
		}
	}

	init {
		description = "Generate binding for ImGui"
	}

	override val userCode = """
	/** %.3f */ public static final @NotNull byte[] FLOAT_FMT = new byte[]{${"%.3f".toList().joinToString { "${it.toInt()}" }}, 0};
	/** %.6f */ public static final @NotNull byte[] DOUBLE_FMT = new byte[]{${"%.6f".toList().joinToString { "${it.toInt()}" }}, 0};
	/** %d */ public static final @NotNull byte[] INT_FMT = new byte[]{${"%d".toList().joinToString { "${it.toInt()}" }}, 0};
"""

	override fun java(javaCode: StringBuilder) {
		checkParserInitialized(project)
		trivialMethods.forEach {
			it.document = it.document ?: parser.map[it.name.capitalize()]
			genJavaFun(javaCode, it)
		}
	}

	override val `c++Expr` = "ImGui::"
	override fun `c++`(cppCode: StringBuilder) =
			trivialMethods.forEach { (name, type, params) -> `genC++Fun`(params, name, type, cppCode) }

	private val trivialMethods = listOf<Fun>(
			// Styles
			Fun("styleColorsDark", stylePtr("style", true)),
			Fun("styleColorsClassic", stylePtr("style", true)),
			Fun("styleColorsLight", stylePtr("style", true)),
			Fun("styleColorsLightGreen", stylePtr("style", true)),
			Fun("styleColorsDarcula", bool("setupSpacing", true), stylePtr("style", true)),
			Fun("styleColorsWindows", bool("setupSpacing", true), stylePtr("style", true)),
			Fun("styleColorsHackEd", stylePtr("style", true)),

			// My own extensions
			Fun("emptyButton", "boolean", vec4("bounds")),
			Fun("setDisableHighlight", boolean("newValue")),
			Fun("getDisableHighlight", "boolean"),
			Fun("dragVec4", string("label"), vec4("bounds"),
					float("speed", 1), float("min", 0), float("max", 0)),
			Fun("sliderVec4", string("label"), vec4("bounds"),
					float("min", 0), float("max", 100)),
			Fun("lineTo", pos("delta"), vec4("color"), thickness),
			Fun("circle", float("radius"), vec4("color"), numSegments, thickness).apply {
				document = "@param thickness if < 0, circle will be filled"
			},
			Fun("rect", size(), vec4("color"), rounding, thickness, roundingFlags).apply {
				document = "@param thickness if < 0, circle will be filled"
			},
			Fun("dialogBox", string("title"), string("text"),
					size("Window"), pOpen, float("percentageOnScreen", "0.2f")),
			Fun("bufferingBar", float("value"), size(),
					vec4("backgroundColor"), vec4("foregroundColor")),
			Fun("spinner", float("radius"), thickness,
					numSegments(30), vec4("color")),

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
			/*Fun("showUserGuide"),*/
			/*Fun("showDemoWindow", pOpen),*/
			Fun("showMetricsWindow", pOpen),
			Fun("showFontSelector", label),
			Fun("showStyleSelector", label),
			Fun("showStyleEditor", stylePtr("ref", nullable = true)),

			// Windows Utilities
			Fun("isWindowAppearing", "boolean"),
			Fun("isWindowCollapsed", "boolean"),
			Fun("isWindowFocused", "boolean", flags(from = "Focused", default = "Default")),
			Fun("isWindowHovered", "boolean", flags(from = "Hovered", default = "Default")),
			Fun("getWindowWidth", "float"),
			Fun("getWindowHeight", "float"),
			Fun("getContentRegionAvailWidth", "float"),
			Fun("getWindowContentRegionWidth", "float"),

			Fun("setNextWindowPos", pos, cond, pos("windowPosPivot", default = "0,0")),
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
			Fun("isKeyDown", "boolean", userKeyIndex),
			Fun("isKeyPressed", "boolean", userKeyIndex, bool("repeat", default = true)),
			Fun("isKeyReleased", "boolean", userKeyIndex),
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
			Fun("pushID", stringSized("stringID")),
			Fun("getID", "int", stringSized("stringID")),

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
			Fun("textUnformatted", stringSized("text")),

			// Widgets: Main
			Fun("button", "boolean", text, size(default = "0,0")),
			Fun("smallButton", "boolean", text),
			Fun("invisibleButton", "boolean",
					text,
					size(default = "0,0")),
			Fun("arrowButton", "boolean",
					text,
					int("direction", annotation = "@MagicConstant(valuesFromClass = JImDirection.class)")),
			Fun("image", texture("userTextureID"), size(),
					pos("uv0", default = "0,0"), pos("uv1", default = "1,1")),
			Fun("imageButton", "boolean", texture("userTextureID"), size(),
					pos("uv0", default = "0,0"), pos("uv1", default = "1,1"),
					int("framePadding", default = -1)),
			Fun("checkbox", "boolean", label, boolPtr("v")),
			Fun("radioButton", "boolean", text, intPtr("v"), int("v_button")),
			Fun("radioButton0", "boolean", text, bool("active")),
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
			Fun("combo", label, intPtr("currentItem"),
					string("itemsSeparatedByZeros"),
					int("popupMaxHeightInItems", default = -1)),

			// Widgets: Drags
			Fun("dragFloat", "boolean", label,
					floatPtr("value"),
					float("valueSpeed", default = 1),
					float("valueMin", default = 0),
					float("valueMax", default = 0),
					string("format", default = "FLOAT_FMT"),
					float("power", default = 1)),
			Fun("dragFloatRange2", "boolean", label,
					floatPtr("valueCurrentMin"),
					floatPtr("valueCurrentMax"),
					float("valueSpeed", default = 1),
					float("valueMin", default = 0),
					float("valueMax", default = 0),
					string("format", default = "FLOAT_FMT"),
					string("formatMax", default = strNull),
					float("power", default = 1)),
			Fun("dragInt", "boolean", label,
					intPtr("value"),
					float("valueSpeed", default = 1),
					int("valueMin", default = 0),
					int("valueMax", default = 0),
					string("format", default = "INT_FMT")),
			Fun("dragIntRange2", "boolean", label,
					intPtr("valueCurrentMin"),
					intPtr("valueCurrentMax"),
					float("valueSpeed", default = 1),
					int("valueMin", default = 0),
					int("valueMax", default = 0),
					string("format", default = "INT_FMT")),

			// Widgets: Input with Keyboard
			Fun("inputFloat", "boolean", label,
					floatPtr("value"), float("step", default = 0),
					float("stepFast", default = 0),
					string("format", default = "FLOAT_FMT"),
					flags("InputText", default = "Nothing")),
			Fun("inputInt", "boolean", label,
					intPtr("value"), int("step", default = 1),
					int("stepFast", default = 100),
					flags("InputText", default = "Nothing")),
			Fun("inputDouble", "boolean", label,
					doublePtr("value"), double("step", default = 0),
					double("stepFast", default = 0),
					string("format", default = "DOUBLE_FMT"),
					flags("InputText", default = "Nothing")),

			// Widgets: Sliders
			Fun("sliderFloat", "boolean", label,
					floatPtr("value"),
					float("valueMin", default = 0),
					float("valueMax", default = 0),
					string("format", default = "FLOAT_FMT"),
					float("power", default = 1)),
			Fun("sliderAngle", "boolean", label,
					floatPtr("valueRad"),
					float("valueDegreeMin", default = -360),
					float("valueDegreeMax", default = 360)),
			Fun("sliderInt", "boolean", label,
					intPtr("value"),
					int("valueMin", default = 0),
					int("valueMax", default = 0),
					string("format", default = "FLOAT_FMT")),
			Fun("vSliderFloat", "boolean", label, size(),
					floatPtr("value"),
					float("valueMin", default = 0),
					float("valueMax", default = 0),
					string("format", default = "FLOAT_FMT"),
					float("power", default = 1)),
			Fun("vSliderInt", "boolean", label, size(),
					intPtr("value"),
					int("valueMin", default = 0),
					int("valueMax", default = 0),
					string("format", default = "FLOAT_FMT")),

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
			Fun("setColorEditOptions", flags(from = "ColorEdit")),

			// Widgets: Selectable / Lists
			Fun("selectable0", "boolean",
					string("label"),
					bool("selected", default = false),
					flags(from = "Selectable", default = "Nothing"),
					size(default = "0,0")),
			Fun("selectable", "boolean",
					string("label"),
					boolPtr("selected"),
					flags(from = "Selectable", default = "Nothing"),
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
			Fun("isItemDeactivated", "boolean"),
			Fun("isItemDeactivatedAfterChange", "boolean"),
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
