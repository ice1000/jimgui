@file:JvmName("KotlinDsl")
@file:JvmMultifileClass

package org.ice1000.jimgui.dsl

import org.ice1000.jimgui.JImGui

typealias JImGuiBlock = JImGui.() -> Unit
typealias StrID = String
typealias IntID = Int
