package org.ice1000.jimgui.dsl

import org.ice1000.jimgui.JImGui

class JImGuiContext(
		width: Int = 1280,
		height: Int = 720,
		title: String = "Window created by JImGui Kotlin DSL") : JImGui(width, height, title) {
	inline operator fun String.invoke(block: JImGuiBlock) {
		begin(this)
		block()
		end()
	}
}