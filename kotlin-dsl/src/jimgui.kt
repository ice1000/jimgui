@file:JvmName("KotlinDsl")
@file:JvmMultifileClass

package org.ice1000.jimgui.dsl

import org.ice1000.jimgui.JImGui

inline fun JImGui.mainMenuBar(block: JImGuiBlock) {
	if (beginMainMenuBar()) {
		block()
		endMainMenuBar()
	}
}

inline fun JImGui.menuBar(block: JImGuiBlock) {
	if (beginMenuBar()) {
		block()
		endMenuBar()
	}
}

inline fun JImGui.menu(label: String, block: JImGuiBlock) {
	if (beginMenu(label)) {
		block()
		endMenu()
	}
}

inline fun JImGui.treeNode(label: String, block: JImGuiBlock) {
	if (treeNode(label)) {
		block()
		treePop()
	}
}

inline fun JImGui.child(
		strId: StrID,
		width: Float = 0f,
		height: Float = 0f,
		border: Boolean = false,
		block: JImGuiBlock) {
	if (beginChild(strId, width, height, border)) {
		block()
		endChild()
	}
}

inline fun JImGui.child(
		id: IntID,
		width: Float = 0f,
		height: Float = 0f,
		border: Boolean = false,
		flags: Int = 0,
		block: JImGuiBlock) {
	if (beginChild(id, width, height, border, flags)) {
		block()
		endChild()
	}
}
