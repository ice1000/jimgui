@file:JvmName("GenerationUtil")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

fun p(name: String, type: String, default: String? = null) = SimpleParam(name, type, default)
fun bool(name: String, default: String? = null) = SimpleParam(name, "boolean", default)
fun int(name: String, default: String? = null) = SimpleParam(name, "int", default)
fun float(name: String, default: String? = null) = SimpleParam(name, "float", default)
fun vec2(nameX: String, nameY: String) = ImVec2(nameX, nameY)

/**
 * @property name String function name
 * @property type String? null -> void
 * @property param List<out Param>
 */
data class Fun(val name: String, val type: String?, val param: List<Param>) {
	constructor(name: String, type: String?, vararg param: Param) :
			this(name, type, param.toList())

	constructor(name: String, vararg param: Param) :
			this(name, null, param.toList())

	constructor(name: String, param: List<Param>) :
			this(name, null, param)
}

sealed class Param {
	abstract fun java(): String
	abstract fun `c++`(): String
	abstract fun `c++Expr`(): String
	open fun init() = ""
	open fun release() = ""
	open fun default(): String? = null
}

data class SimpleParam(val name: String, val type: String, val default: String?) : Param() {
	override fun java() = "$type $name"
	override fun `c++`() = "j$type $name"
	override fun `c++Expr`() = name
	override fun default() = default
}

// data class StringParam(val name: String)

data class ImVec2(val nameX: String, val nameY: String) : Param() {
	override fun java() = "float $nameX, float $nameY"
	override fun `c++`() = "jfloat $nameX, jfloat $nameY"
	override fun `c++Expr`() = "ImVec2($nameX, $nameY)"
}

@Language("JAVA", suffix = "class A {}")
const val CLASS_PREFIX = """package org.ice1000.jimgui;

import org.jetbrains.annotations.*;
import static org.ice1000.jimgui.util.JImGuiUtil.*;

/**
 * @author ice1000
 * @since v0.1
 */
@SuppressWarnings("ALL")
"""

@Language("C++")
const val CXX_PREFIX = """///
/// author: ice1000
/// generated code, edits are not expected.
///

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

#include <imgui.h>
"""

@Language("C++")
const val CXX_SUFFIX = "#pragma clang diagnostic pop"
