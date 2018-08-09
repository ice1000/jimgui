package org.ice1000.gradle

import org.gradle.api.tasks.TaskAction

abstract class GenEnumTask<T>(className: String) : GenJavaTask(className), Runnable {
	abstract val list: List<T>

	@TaskAction
	override fun run() {
		buildString {
			append(prefixInterfacedJava)
			list.forEachIndexed { index, element ->
				append('\t')
				genStatement(index, element)
				appendln(";")
			}
			appendln('}')
		}.let { targetJavaFile.writeText(it) }
	}

	open fun StringBuilder.genStatement(index: Int, element: T) {
		append("int ")
		append(element)
		append(" = ")
		append(index)
	}
}

open class GenStyleVarsTask : GenEnumTask<Pair<String, String>>("JImStyleVars") {
	override val list = listOf("Alpha" to "Float",
			"WindowPadding" to "Void", "WindowRounding" to "Float", "WindowBorderSize" to "Float",
			"WindowMinSize" to "Void", "WindowTitleAlign" to "Void", "ChildRounding" to "Float",
			"ChildBorderSize" to "Float", "PopupRounding" to "Float", "PopupBorderSize" to "Float",
			"FramePadding" to "Void", "FrameRounding" to "Float", "FrameBorderSize" to "Float",
			"ItemSpacing" to "Void", "ItemInnerSpacing" to "Void", "IndentSpacing" to "Float",
			"ScrollbarSize" to "Float", "ScrollbarRounding" to "Float", "GrabMinSize" to "Float",
			"GrabRounding" to "Float", "ButtonTextAlign" to "Void")

	override fun StringBuilder.genStatement(index: Int, element: Pair<String, String>) {
		val (name, typeArg) = element
		append("@NotNull JImStyleVar<@NotNull ")
		append(typeArg)
		append("> ")
		append(name)
		append(" = new JImStyleVar<>(")
		append(index)
		append(')')
	}
}

open class GenStyleColorsTask : GenEnumTask<String>("JImStyleColors") {
	override val list = listOf(
			"Text", "TextDisabled", "WindowBg", "ChildBg", "PopupBg", "Border", "BorderShadow", "FrameBg", "FrameBgHovered",
			"FrameBgActive", "TitleBg", "TitleBgActive", "TitleBgCollapsed", "MenuBarBg", "ScrollbarBg", "ScrollbarGrab",
			"ScrollbarGrabHovered", "ScrollbarGrabActive", "CheckMark", "SliderGrab", "SliderGrabActive", "Button",
			"ButtonHovered", "ButtonActive", "Header", "HeaderHovered", "HeaderActive", "Separator", "SeparatorHovered",
			"SeparatorActive", "ResizeGrip", "ResizeGripHovered", "ResizeGripActive", "PlotLines", "PlotLinesHovered",
			"PlotHistogram", "PlotHistogramHovered", "TextSelectedBg", "DragDropTarget",
			"NavHighlight", "NavWindowListHighlight", "NavWindowListDimBg", "ModalWindowDimBg")
}

open class GenDefaultKeysTask : GenEnumTask<String>("JImDefaultKeys") {
	override val list = listOf(
			"Tab", "LeftArrow", "RightArrow", "UpArrow", "DownArrow", "PageUp", "PageDown",
			"Home", "End", "Insert", "Delete", "Backspace", "Space", "Enter", "Escape",
			"LeftShift", "RightShift", "LeftCtrl", "RightCtrl", "Ctrl", "LeftAlt", "RightAlt",
			"_0", "_1", "_2", "_3", "_4", "_5", "_6", "_7", "_8", "_9",
			"A", "B", "C", "D", "E", "F", "G", "Q", "R", "S", "V", "W", "X", "Y", "Z")
}
