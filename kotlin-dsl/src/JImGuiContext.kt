package org.ice1000.jimgui.dsl

import org.ice1000.jimgui.JImGui
import org.ice1000.jimgui.NativeBool
import org.ice1000.jimgui.flag.JImWindowFlags
import org.intellij.lang.annotations.MagicConstant

class JImGuiContext(
		width: Int = 1280,
		height: Int = 720,
		title: String = "Window created by JImGui Kotlin DSL") : JImGui(width, height, title) {
	inline operator fun String.invoke(block: JImGuiBlock) {
		begin(this@invoke)
		block()
		end()
	}

	inline operator fun String.invoke(pOpen: NativeBool,
	                                  @MagicConstant(flagsFromClass = JImWindowFlags::class)
	                                  flags: Flags = JImWindowFlags.None,
	                                  block: JImGuiBlock) {
		begin(this@invoke, pOpen, flags)
		block()
		end()
	}
}