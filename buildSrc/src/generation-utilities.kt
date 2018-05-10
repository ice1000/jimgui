@file:JvmName("GenerationUtil")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

fun p(name: String, type: String, default: Any? = null) = SimpleParam(name, type, default)
fun bool(name: String, default: Any? = null) = SimpleParam(name, "boolean", default)
fun int(name: String, default: Any? = null, annotation: String? = null) = SimpleParam(name, "int", default, annotation.orEmpty())
fun float(name: String, default: Any? = null) = SimpleParam(name, "float", default)
fun vec2(nameX: String, nameY: String, default: Any? = null) = ImVec2Param(nameX, nameY, default)
fun vec4(name: String, default: Any? = null) = ImVec4Param(name, default)
fun size(name: String = "", default: Any? = null) = vec2("width$name", "height$name", default)
fun pos(default: Any? = null) = vec2("posX", "posY", default)
fun string(name: String, default: String? = null) = StringParam(name, default)
val cond = SimpleParam("cond", "int", "JImCond.Always", "@MagicConstant(valuesFromClass = JImCond.class)")
val label = string("label")
val text = string("text")
fun flags(from: String? = null, default: String? = null) = int("flags",
		default = default?.let { from?.let { "JIm${from}Flags.$default" } } ?: 0,
		annotation = from?.let { "@MagicConstant(flagsFromClass = JIm${it}Flags.class)" })

/**
 * @property name String function name
 * @property type String? null -> void
 * @property param List<out Param>
 */
data class Fun(val name: String,
               val type: String?,
               val param: List<Param>,
               var visibility: String = "public",
               var document: String? = null) {
	constructor(name: String, type: String?, vararg param: Param) :
			this(name, type, param.toList())

	constructor(name: String, vararg param: Param) :
			this(name, null, param.toList())

	constructor(name: String, param: List<Param>) :
			this(name, null, param)

	constructor(name: String, type: String?, vararg param: Param, document: String) :
			this(name, type, param.toList(), document = document)

	companion object {
		fun protected(name: String, vararg param: Param) = Fun(name, *param).apply { visibility = "protected" }
	}
}

infix fun Fun.withDoc(javadoc: String) = apply { document = javadoc }

sealed class Param {
	abstract fun java(): String
	open fun javaDefault(): String = java()
	abstract fun javaExpr(): String
	abstract fun `c++`(): String
	abstract fun `c++Expr`(): String
	open fun surrounding(): Pair<String, String>? = null
	/** null refers to no default value. */
	open val default: Any? get() = null
}

data class SimpleParam(
		val name: String,
		val type: String,
		override val default: Any?,
		val annotation: String = "") : Param() {
	override fun java() = "$annotation$type $name"
	override fun javaExpr() = name
	override fun `c++`() = "j$type $name"
	override fun `c++Expr`() = name
}

data class StringParam(val name: String, override val default: Any?) : Param() {
	override fun java() = "byte[] $name"
	override fun javaDefault() = "@NotNull String $name"
	override fun javaExpr() = "getBytes($name)"
	override fun `c++`() = "jbyteArray _$name"
	override fun `c++Expr`() = "reinterpret_cast<const char *> ($name)"
	override fun surrounding() = "__get(Byte, $name)" to "__release(Byte, $name)"
}

data class ImVec4Param(val name: String, override val default: Any?) : Param() {
	override fun java() = "long $name"
	override fun javaDefault() = "@NotNull JImVec4 $name"
	override fun javaExpr() = "$name.nativeObjectPtr"
	override fun `c++`() = "jlong $name"
	override fun `c++Expr`() = "*reinterpret_cast<const ImVec4 *> ($name)"

}

/**
 * @property nameX String
 * @property nameY String
 * @property default Any? don't use ATM
 */
data class ImVec2Param(val nameX: String, val nameY: String, override val default: Any?) : Param() {
	override fun java() = "float $nameX, float $nameY"
	override fun javaExpr() = "$nameX, $nameY"
	override fun `c++`() = "jfloat $nameX, jfloat $nameY"
	override fun `c++Expr`() = "ImVec2($nameX, $nameY)"
}

fun StringBuilder.javadoc(name: String): StringBuilder {
	GenGenTask.parser.map[name]?.let { javadoc -> append("\t/**").append(javadoc).appendln("*/") }
	return this
}

@Language("JAVA", suffix = "class A {}")
const val CLASS_PREFIX = """package org.ice1000.jimgui;

import org.intellij.lang.annotations.*;
import org.jetbrains.annotations.*;
import static org.ice1000.jimgui.util.JImGuiUtil.*;

/**
 * @author ice1000
 * @since v0.1
 */
@SuppressWarnings("ALL")
"""

const val JNI_FUNCTION_INIT = "__JNI__FUNCTION__INIT__ "
const val JNI_FUNCTION_CLEAN = " __JNI__FUNCTION__CLEAN__"

@Language("C++")
const val CXX_PREFIX = """///
/// author: ice1000
/// generated code, edits are not expected.
///

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

#include <imgui.h>
#include "basics.hpp"
#include "overloads_helper.hpp"
#ifdef __cplusplus
extern "C" {
#endif
"""

@Language("C++")
const val CXX_SUFFIX = """
#pragma clang diagnostic pop
#ifdef __cplusplus
}
#endif
"""

@Language("C++", suffix = "(){}")
const val JNI_FUNC_PREFIX = "JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_"
