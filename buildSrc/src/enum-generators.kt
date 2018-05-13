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

	abstract fun StringBuilder.genStatement(index: Int, element: T)
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
	override val list = listOf("Alpha", "WindowPadding", "WindowRounding",
			"WindowBorderSize", "WindowMinSize", "WindowTitleAlign", "ChildRounding", "ChildBorderSize",
			"PopupRounding", "PopupBorderSize", "FramePadding", "FrameRounding", "FrameBorderSize",
			"ItemSpacing", "ItemInnerSpacing", "IndentSpacing", "ScrollbarSize", "ScrollbarRounding",
			"GrabMinSize", "GrabRounding", "ButtonTextAlign")

	override fun StringBuilder.genStatement(index: Int, element: String) {
		append("int ")
		append(element)
		append(" = ")
		append(index)
	}
}
