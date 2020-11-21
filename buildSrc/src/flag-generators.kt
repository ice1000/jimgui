package org.ice1000.gradle

import org.gradle.api.tasks.TaskAction

abstract class GenFlagTask(className: String, private vararg val list: Pair<String, String>)
	: GenJavaTask(className, packageName = "org.ice1000.jimgui.flags"), Runnable {
	@TaskAction
	override fun run() = buildString {
		append(prefixInterfacedJava)
		list.forEach { (name, value) ->
			val keyName = "${className.replace("JIm", "imGui")}_$name"
			val comment = GenGenTask.parser.map[keyName]
			if (comment != null) append("\t/**").append(comment).appendln("*/")
			append('\t')
			genStatement(name, value)
			appendln(";")
		}
		appendln("\tenum Type implements Flag {")
		list.forEach { (name, _) ->
			append("\t\t").append(name).append("(").append(className).append(".").append(name).appendln("),")
		}
		appendln("\t\t;\n\t\tpublic final int flag;")
				.appendln("\t\tType(int flag) { this.flag = flag; }")
				.appendln("\t\t@Override public int get() { return flag; }")
		appendln("\t}").appendln('}')
	}.let { targetJavaFile.writeText(it) }

	private fun StringBuilder.genStatement(name: String, value: String) {
		append("int ")
		append(name)
		append(" = ")
		append(value)
	}
}

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

open class GenBackendFlags : GenFlagTask(
		"JImBackendFlags",
		"None" to "0",
		"HasGamepad" to "1 << 0",
		"HasMouseCursors" to "1 << 1",
		"HasSetMousePos" to "1 << 2",
		"RendererHasVtxOffset" to "1 << 3",
)
