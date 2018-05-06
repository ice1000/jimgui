@file:JvmName("KotlinDsl")
@file:JvmMultifileClass

package org.ice1000.jimgui.dsl

inline fun JImGuiContext.mainMenuBar(block: JImGuiBlock) {
	if (beginMainMenuBar()) {
		block()
		endMainMenuBar()
	}
}

inline fun JImGuiContext.menuBar(block: JImGuiBlock) {
	if (beginMenuBar()) {
		block()
		endMenuBar()
	}
}

inline fun JImGuiContext.menu(label: String, enabled: Boolean = true, block: JImGuiBlock) {
	if (beginMenu(label, enabled)) {
		block()
		endMenu()
	}
}

inline fun JImGuiContext.tooltip(block: JImGuiBlock) {
	beginTooltip()
	block()
	endTooltip()
}

inline fun JImGuiContext.group(block: JImGuiBlock) {
	beginGroup()
	block()
	endGroup()
}

inline fun JImGuiContext.treeNode(label: String, block: JImGuiBlock) {
	if (treeNode(label)) {
		block()
		treePop()
	}
}

inline fun JImGuiContext.child(
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

inline fun JImGuiContext.child(
		id: IntID,
		width: Float = 0f,
		height: Float = 0f,
		border: Boolean = false,
		flags: Flags = 0,
		block: JImGuiBlock) {
	if (beginChild(id, width, height, border, flags)) {
		block()
		endChild()
	}
}
