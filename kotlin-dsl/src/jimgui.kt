@file:JvmName("KotlinDsl")
@file:JvmMultifileClass

package org.ice1000.jimgui.dsl

import org.ice1000.jimgui.JImGuiGen.*
import org.ice1000.jimgui.JImStyleVar
import org.ice1000.jimgui.NativeBool
import org.ice1000.jimgui.flag.JImTabBarFlags
import org.ice1000.jimgui.flag.JImTabItemFlags
import org.ice1000.jimgui.flag.JImTreeNodeFlags
import org.ice1000.jimgui.flag.JImWindowFlags
import org.intellij.lang.annotations.MagicConstant

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

inline fun JImGuiContext.tabBar(
		stringId: StrID,
		@MagicConstant(flagsFromClass = JImTabBarFlags::class)
		flags: Flags = 0,
		block: JImGuiBlock) {
	if (beginTabBar(stringId, flags)) {
		block()
		endTabBar()
	}
}

inline fun JImGuiContext.tabItem(
		label: String,
		pOpen: NativeBool,
		@MagicConstant(flagsFromClass = JImTabItemFlags::class)
		flags: Flags = 0,
		block: JImGuiBlock) {
	if (beginTabItem(label, pOpen, flags)) {
		block()
		endTabItem()
	}
}

inline fun JImGuiContext.tabItem(
		label: String,
		@MagicConstant(flagsFromClass = JImTabItemFlags::class)
		flags: Flags = 0,
		block: JImGuiBlock) {
	if (beginTabItem(label, flags)) {
		block()
		endTabItem()
	}
}

inline fun JImGuiContext.collapsingHeader(
		label: String,
		pOpen: NativeBool,
		@MagicConstant(flagsFromClass = JImTreeNodeFlags::class)
		flags: Flags = 0,
		block: JImGuiBlock) {
	if (collapsingHeader(label, pOpen, flags)) block()
}

inline fun JImGuiContext.collapsingHeader(label: String, pOpen: NativeBool, block: JImGuiBlock) {
	if (collapsingHeader(label, pOpen)) block()
}

inline fun JImGuiContext.button(
		text: String,
		width: Float = 0f,
		height: Float = 0f,
		block: JImGuiBlock) {
	if (button(text, width, height)) block()
}

inline fun JImGuiContext.menu(label: String, enabled: Boolean = true, block: JImGuiBlock) {
	if (beginMenu(label, enabled)) {
		block()
		endMenu()
	}
}

inline fun JImGuiContext.popup(
		id: StrID,
		@MagicConstant(flagsFromClass = JImWindowFlags::class)
		flags: Flags = JImWindowFlags.None,
		block: JImGuiBlock) {
	if (beginPopup(id, flags)) {
		block()
		endPopup()
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
	if (beginChild0(strId, width, height, border)) {
		block()
		endChild()
	}
}

inline fun JImGuiContext.child(
		id: IntID,
		width: Float = 0f,
		height: Float = 0f,
		border: Boolean = false,
		@MagicConstant(flagsFromClass = JImWindowFlags::class)
		flags: Flags = JImWindowFlags.None,
		block: JImGuiBlock) {
	if (beginChild(id, width, height, border, flags)) {
		block()
		endChild()
	}
}

inline fun JImGuiContext.withStyle(styleVar: JImStyleVar<Float>, value: Float, block: JImGuiBlock) {
	pushStyleVar(styleVar, value)
	block()
	popStyleVar()
}

inline fun JImGuiContext.withStyle(
		styleVar: JImStyleVar<Void>,
		valueA: Float,
		valueB: Float,
		block: JImGuiBlock) {
	pushStyleVar(styleVar, valueA, valueB)
	block()
	popStyleVar()
}
