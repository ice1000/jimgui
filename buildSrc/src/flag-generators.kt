/*
 * Use http://62.171.186.248:8080
 */
package org.ice1000.gradle

import org.gradle.api.tasks.TaskAction

abstract class GenFlagTask(className: String, private vararg val list: Pair<String, String>)
  : GenJavaTask(className, packageName = "org.ice1000.jimgui.flag", since = "undecidable"), Runnable {
  init {
    GenGenTask.checkParserInitialized(project)
  }

  @TaskAction
  override fun run() = buildString {
    GenGenTask.checkParserInitialized(project)
    append(prefixInterfacedJava)
    list.forEach { (name, value) ->
      val keyName = "${className.replace("JIm", "imGui")}_$name"
      val comment = GenGenTask.parser.map[keyName]
      if (comment != null) append("  /**").append(comment).appendln("*/")
      append("  ")
      genStatement(name, value)
      appendln(";")
    }
    appendln("  enum Type implements Flag {")
    preType()
    list.forEach { (name, _) ->
      append("    ").append(name).append("(").append(className).append(".").append(name).appendln("),")
    }
    appendln("    ;\n    public final int flag;")
        .appendln("    Type(int flag) { this.flag = flag; }")
        .appendln("    @Override public int get() { return flag; }")
    appendln("  }").appendln('}')
  }.let { targetJavaFile.writeText(it) }

  open fun StringBuilder.preType() {
  }

  private fun StringBuilder.genStatement(name: String, value: String) {
    append("int ")
    append(name)
    append(" = ")
    append(value)
  }
}

// Internal
open class GenItemFlags : GenFlagTask(
    "JImItemFlags",
    "None" to "0",
    "NoTabStop" to "1 << 0",
    "ButtonRepeat" to "1 << 1",
    "Disabled" to "1 << 2",
    "NoNav" to "1 << 3",
    "NoNavDefaultFocus" to "1 << 4",
    "SelectableDontClosePopup" to "1 << 5",
    "MixedValue" to "1 << 6",
    "ReadOnly" to "1 << 7",
    "Default" to "0",
)

open class GenTabBarFlags : GenFlagTask(
    "JImTabBarFlags",
    "None" to "0",
    "Reorderable" to "1",
    "AutoSelectNewTabs" to "1 << 1",
    "TabListPopupButton" to "1 << 2",
    "NoCloseWithMiddleMouseButton" to "1 << 3",
    "NoTabListScrollingButtons" to "1 << 4",
    "NoTooltip" to "1 << 5",
    "FittingPolicyResizeDown" to "1 << 6",
    "FittingPolicyScroll" to "1 << 7",
    "FittingPolicyMask" to "FittingPolicyResizeDown | FittingPolicyScroll",
    "FittingPolicyDefault" to "FittingPolicyResizeDown",
)

open class GenColorEditFlags : GenFlagTask(
    "JImColorEditFlags",
    "None" to "0",
    "NoAlpha" to "1 << 1",
    "NoPicker" to "1 << 2",
    "NoOptions" to "1 << 3",
    "NoSmallPreview" to "1 << 4",
    "NoInputs" to "1 << 5",
    "NoTooltip" to "1 << 6",
    "NoLabel" to "1 << 7",
    "NoSidePreview" to "1 << 8",
    "NoDragDrop" to "1 << 9",
    "NoBorder" to "1 << 10",
    "AlphaBar" to "1 << 16",
    "AlphaPreview" to "1 << 17",
    "AlphaPreviewHalf" to "1 << 18",
    "HDR" to "1 << 19",
    "DisplayRGB" to "1 << 20",
    "DisplayHSV" to "1 << 21",
    "DisplayHex" to "1 << 22",
    "Uint8" to "1 << 23",
    "Float" to "1 << 24",
    "PickerHueBar" to "1 << 25",
    "PickerHueWheel" to "1 << 26",
    "InputRGB" to "1 << 27",
    "InputHSV" to "1 << 28",
    "OptionsDefault" to "Uint8 | InputRGB | PickerHueBar",
    "_DisplayMask" to "DisplayRGB | DisplayHSV | DisplayHex",
    "_DataTypeMask" to "Uint8 | Float",
    "_PickerMask" to "PickerHueWheel | PickerHueBar",
    "_InputMask" to "InputRGB | InputHSV",
)

open class GenBackendFlags : GenFlagTask(
    "JImBackendFlags",
    "None" to "0",
    "HasGamepad" to "1 << 0",
    "HasMouseCursors" to "1 << 1",
    "HasSetMousePos" to "1 << 2",
    "RendererHasVtxOffset" to "1 << 3",
)

open class GenCond : GenFlagTask(
    "JImCond",
    "None" to "0",
    "Always" to "1 << 0",
    "Once" to "1 << 1",
    "FirstUseEver" to "1 << 2",
    "Appearing" to "1 << 3",
)

open class GenSliderFlags : GenFlagTask(
    "JImSliderFlags",
    "None" to "0",
    "AlwaysClamp" to "1 << 4",
    "Logarithmic" to "1 << 5",
    "NoRoundToFormat" to "1 << 6",
    "NoInput" to "1 << 7",
    "InvalidMask_" to "0x7000000F",
)

open class GenComboFlags : GenFlagTask(
    "JImComboFlags",
    "None" to "0",
    "PopupAlignLeft" to "1 << 0",
    "HeightSmall" to "1 << 1",
    "HeightRegular" to "1 << 2",
    "HeightLarge" to "1 << 3",
    "HeightLargest" to "1 << 4",
    "NoArrowButton" to "1 << 5",
    "NoPreview" to "1 << 6",
)

open class GenFontAtlasFlags : GenFlagTask(
    "JImFontAtlasFlags",
    "None" to "0",
    "NoPowerOfTwoHeight" to "1 << 0",
    "NoMouseCursors" to "1 << 1",
    "NoBakedLines" to "1 << 2",
)

open class GenTreeNodeFlags : GenFlagTask(
    "JImTreeNodeFlags",
    "None" to "0",
    "Selected" to "1 << 0",
    "Framed" to "1 << 1",
    "AllowItemOverlap" to "1 << 2",
    "NoTreePushOnOpen" to "1 << 3",
    "NoAutoOpenOnLog" to "1 << 4",
    "DefaultOpen" to "1 << 5",
    "OpenOnDoubleClick" to "1 << 6",
    "OpenOnArrow" to "1 << 7",
    "Leaf" to "1 << 8",
    "Bullet" to "1 << 9",
    "FramePadding" to "1 << 10",
    "SpanAvailWidth" to "1 << 11",
    "SpanFullWidth" to "1 << 12",
    "NavLeftJumpsBackHere" to "1 << 13",
    "CollapsingHeader" to "Framed | NoTreePushOnOpen | NoAutoOpenOnLog",
)

open class GenPopupFlags : GenFlagTask(
    "JImPopupFlags",
    "None" to "0",
    "MouseButtonLeft" to "0",
    "MouseButtonRight" to "1",
    "MouseButtonMiddle" to "2",
    "MouseButtonMask" to "0x1F",
    "MouseButtonDefault" to "1",
    "NoOpenOverExistingPopup" to "1 << 5",
    "NoOpenOverItems" to "1 << 6",
    "AnyPopupId" to "1 << 7",
    "AnyPopupLevel" to "1 << 8",
    "AnyPopup" to "AnyPopupId | AnyPopupLevel",
)

open class GenHoveredFlags : GenFlagTask(
    "JImHoveredFlags",
    "None" to "0",
    "ChildWindows" to "1 << 0",
    "RootWindow" to "1 << 1",
    "AnyWindow" to "1 << 2",
    "AllowWhenBlockedByPopup" to "1 << 3",
    "AllowWhenBlockedByActiveItem" to "1 << 5",
    "AllowWhenOverlapped" to "1 << 6",
    "AllowWhenDisabled" to "1 << 7",
    "RectOnly" to "AllowWhenBlockedByPopup | AllowWhenBlockedByActiveItem | AllowWhenOverlapped",
    "RootAndChildWindows" to "RootWindow | ChildWindows",
)

open class GenTabItemFlags : GenFlagTask(
    "JImTabItemFlags",
    "None" to "0",
    "UnsavedDocument" to "1 << 0",
    "SetSelected" to "1 << 1",
    "NoCloseWithMiddleMouseButton" to "1 << 2",
    "NoPushId" to "1 << 3",
    "NoTooltip" to "1 << 4",
    "NoReorder" to "1 << 5",
    "Leading" to "1 << 6",
    "Trailing" to "1 << 7",
)

open class GenFocusedFlags : GenFlagTask(
    "JImFocusedFlags",
    "None" to "0",
    "ChildWindows" to "1 << 0",
    "RootWindow" to "1 << 1",
    "AnyWindow" to "1 << 2",
    "RootAndChildWindows" to "RootWindow | ChildWindows",
)

open class GenConfigFlags : GenFlagTask(
    "JImConfigFlags",
    "None" to "0",
    "NavEnableKeyboard" to "1 << 0",
    "NavEnableGamepad" to "1 << 1",
    "NavEnableSetMousePos" to "1 << 2",
    "NavNoCaptureKeyboard" to "1 << 3",
    "NoMouse" to "1 << 4",
    "NoMouseCursorChange" to "1 << 5",
    "IsSRGB" to "1 << 20",
    "IsTouchScreen" to "1 << 21",
)

open class GenButtonFlags : GenFlagTask(
    "JImButtonFlags",
    "None" to "0",
    "MouseButtonLeft" to "1 << 0",
    "MouseButtonRight" to "1 << 1",
    "MouseButtonMiddle" to "1 << 2",
    "MouseButtonMask_" to "MouseButtonLeft | MouseButtonRight | MouseButtonMiddle",
    "MouseButtonDefault_" to "MouseButtonLeft",
)

open class GenSelectableFlags : GenFlagTask(
    "JImSelectableFlags",
    "None" to "0",
    "DontClosePopups" to "1 << 0",
    "SpanAllColumns" to "1 << 1",
    "AllowDoubleClick" to "1 << 2",
    "Disabled" to "1 << 3",
    "AllowItemOverlap" to "1 << 4",
)

open class GenInputTextFlags : GenFlagTask(
    "JImInputTextFlags",
    "None" to "0",
    "CharsDecimal" to "1 << 0",
    "CharsHexadecimal" to "1 << 1",
    "CharsUppercase" to "1 << 2",
    "CharsNoBlank" to "1 << 3",
    "AutoSelectAll" to "1 << 4",
    "EnterReturnsTrue" to "1 << 5",
    "CallbackCompletion" to "1 << 6",
    "CallbackHistory" to "1 << 7",
    "CallbackAlways" to "1 << 8",
    "CallbackCharFilter" to "1 << 9",
    "AllowTabInput" to "1 << 10",
    "CtrlEnterForNewLine" to "1 << 11",
    "NoHorizontalScroll" to "1 << 12",
    "AlwaysInsertMode" to "1 << 13",
    "ReadOnly" to "1 << 14",
    "Password" to "1 << 15",
    "NoUndoRedo" to "1 << 16",
    "CharsScientific" to "1 << 17",
    "CallbackResize" to "1 << 18",
    "CallbackEdit" to "1 << 19",
    "Multiline" to "1 << 20",
    "NoMarkEdited" to "1 << 21",
)

open class GenWindowFlags : GenFlagTask(
    "JImWindowFlags",
    "None" to "0",
    "NoTitleBar" to "1 << 0",
    "NoResize" to "1 << 1",
    "NoMove" to "1 << 2",
    "NoScrollbar" to "1 << 3",
    "NoScrollWithMouse" to "1 << 4",
    "NoCollapse" to "1 << 5",
    "AlwaysAutoResize" to "1 << 6",
    "NoBackground" to "1 << 7",
    "NoSavedSettings" to "1 << 8",
    "NoMouseInputs" to "1 << 9",
    "MenuBar" to "1 << 10",
    "HorizontalScrollbar" to "1 << 11",
    "NoFocusOnAppearing" to "1 << 12",
    "NoBringToFrontOnFocus" to "1 << 13",
    "AlwaysVerticalScrollbar" to "1 << 14",
    "AlwaysHorizontalScrollbar" to "1<< 15",
    "AlwaysUseWindowPadding" to "1 << 16",
    "NoNavInputs" to "1 << 18",
    "NoNavFocus" to "1 << 19",
    "UnsavedDocument" to "1 << 20",
    "NoNav" to "NoNavInputs | NoNavFocus",
    "NoDecoration" to "NoTitleBar | NoResize | NoScrollbar | NoCollapse",
    "NoInputs" to "NoMouseInputs | NoNavInputs | NoNavFocus",
    "NavFlattened" to "1 << 23",
    "ChildWindow" to "1 << 24",
    "Tooltip" to "1 << 25",
    "Popup" to "1 << 26",
    "Modal" to "1 << 27",
    "ChildMenu" to "1 << 28",
)

open class GenTableFlags : GenFlagTask(
    "JImTableFlags",
    "None" to "0",
    "Resizable" to "1 << 0",
    "Reorderable" to "1 << 1",
    "Hideable" to "1 << 2",
    "Sortable" to "1 << 3",
    "NoSavedSettings" to "1 << 4",
    "ContextMenuInBody" to "1 << 5",
    "RowBg" to "1 << 6",
    "BordersInnerH" to "1 << 7",
    "BordersOuterH" to "1 << 8",
    "BordersInnerV" to "1 << 9",
    "BordersOuterV" to "1 << 10",
    "BordersH" to "BordersInnerH | BordersOuterH",
    "BordersV" to "BordersInnerV | BordersOuterV",
    "BordersInner" to "BordersInnerV | BordersInnerH",
    "BordersOuter" to "BordersOuterV | BordersOuterH",
    "Borders" to "BordersInner | BordersOuter",
    "NoBordersInBody" to "1 << 11",
    "NoBordersInBodyUntilResize" to "1 << 12",
    "SizingFixedFit" to "1 << 13",
    "SizingFixedSame" to "2 << 13",
    "SizingStretchProp" to "3 << 13",
    "SizingStretchSame" to "4 << 13",
    "NoHostExtendY" to "1 << 16",
    "NoKeepColumnsVisible" to "1 << 17",
    "PreciseWidths" to "1 << 18",
    "NoClip" to "1 << 19",
    "PadOuterX" to "1 << 20",
    "NoPadOuterX" to "1 << 21",
    "NoPadInnerX" to "1 << 22",
    "ScrollX" to "1 << 23",
    "ScrollY" to "1 << 24",
    "SortMulti" to "1 << 25",
    "SortTristate" to "1 << 26",
    "SizingMask" to "SizingFixedFit | SizingFixedSame | SizingStretchProp | SizingStretchSame",
)

open class GenTableColumnFlags : GenFlagTask(
    "JImTableColumnFlags",
    "None" to "0",
    "DefaultHide" to "1 << 0",
    "DefaultSort" to "1 << 1",
    "WidthStretch" to "1 << 2",
    "WidthFixed" to "1 << 3",
    "NoResize" to "1 << 5",
    "NoReorder" to "1 << 6",
    "NoHide" to "1 << 7",
    "NoClip" to "1 << 8",
    "NoSort" to "1 << 9",
    "NoSortAscending" to "1 << 10",
    "NoSortDescending" to "1 << 11",
    "NoHeaderWidth" to "1 << 12",
    "PreferSortAscending" to "1 << 13",
    "PreferSortDescending" to "1 << 14",
    "IndentEnable" to "1 << 15",
    "IndentDisable" to "1 << 16",
    "IsEnabled" to "1 << 20",
    "IsVisible" to "1 << 21",
    "IsSorted" to "1 << 22",
    "IsHovered" to "1 << 23",
    "WidthMask" to "WidthStretch | WidthFixed",
    "IndentMask" to "IndentEnable | IndentDisable",
    "StatusMask" to "IsEnabled | IsVisible | IsSorted | IsHovered",
    "NoDirectResize" to "1 << 30",
)

open class GenTableRowFlags : GenFlagTask(
    "JImTableRowFlags",
    "None" to "0",
    "Headers" to "1 << 0",
)

open class GenMouseButton : GenFlagTask(
    "JImMouseButton",
    "Left" to "0",
    "Right" to "1",
    "Middle" to "2",
    "ExtraA" to "3",
    "ExtraB" to "4",
) {
  override fun StringBuilder.preType() {
    appendln("""		/**
     * Used for reverse lookup results and enum comparison.
     * Return the None or Default flag to prevent errors.
     */
    NoSuchFlag(-1),""")
  }
}

open class GenTableBgTarget : GenFlagTask(
    "JImTableBgTarget",
    "None" to "0",
    "RowBg0" to "1",
    "RowBg1" to "2",
    "CellBg" to "3",
)

open class GenSortDirection : GenFlagTask(
    "JImSortDirection",
    "None" to "0",
    "Ascending" to "1",
    "Descending" to "2",
)
