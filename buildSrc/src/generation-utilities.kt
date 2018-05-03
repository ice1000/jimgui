@file:JvmName("GenerationUtil")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

fun p(name: String, type: String) = SimpleParam(name, type)
fun bool(name: String) = SimpleParam(name, "boolean")
fun int(name: String) = SimpleParam(name, "int")
fun float(name: String) = SimpleParam(name, "float")
fun vec2(nameX: String, nameY: String) = ImVec2(nameX, nameY)

sealed class Param {
	abstract fun java(): String
	abstract fun `c++`(): String
	abstract fun `c++Expr`(): String
}

data class SimpleParam(val name: String, val type: String) : Param() {
	override fun java() = "$type $name"
	override fun `c++`() = "j$type $name"
	override fun `c++Expr`() = name
}

data class ImVec2(val nameX: String, val nameY: String) : Param() {
	override fun java() = "float $nameX, float $nameY"
	override fun `c++`() = "jfloat $nameX, jfloat $nameY"
	override fun `c++Expr`() = "ImVec2($nameX, $nameY)"
}

@Language("JAVA", suffix = "class A {}")
const val CLASS_PREFIX = """package org.ice1000.jimgui;

import org.jetbrains.annotations.*;

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
