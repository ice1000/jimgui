@file:JvmName("GenerationUtil")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language
import org.intellij.lang.annotations.MagicConstant

const val strNull = "(byte[]) null"
fun p(name: String, type: String, default: Any? = null) = SimpleParam(name, type, default)
fun bool(name: String, default: Any? = null) = SimpleParam(name, "boolean", default)
fun configPtr(name: String, nullable: Boolean = false) = PointerParam(name, "JImFontConfig", "ImFontConfig", nullable)
fun fontPtr(name: String, nullable: Boolean = false) = PointerParam(name, "JImFont", "ImFont", nullable)
fun stylePtr(name: String, nullable: Boolean = false) = PointerParam(name, "JImStyle", "ImGuiStyle", nullable)
fun drawListPtr(name: String, nullable: Boolean = false) = PointerParam(name, "JImDrawList", "ImDrawList", nullable)
fun boolPtr(name: String, nullable: Boolean = false) = PointerParam(name, "NativeBool", "bool", nullable)
fun stringPtr(name: String, nullable: Boolean = false) = PointerParam(name, "NativeString", "std::string", nullable)
fun floatPtr(name: String, nullable: Boolean = false) = PointerParam(name, "NativeFloat", "float", nullable)
fun doublePtr(name: String, nullable: Boolean = false) = PointerParam(name, "NativeDouble", "double", nullable)

fun texture(name: String, nullable: Boolean = false) =
    PointerParam(name, "JImTextureID", "ImTextureID", if (nullable) "@Nullable" else "@NotNull")

fun intPtr(name: String, nullable: Boolean = false) =
    PointerParam(name, "NativeInt", "int", if (nullable) "@Nullable" else "@NotNull")

fun int(
    name: String,
    default: Any? = null,
    annotation: String? = null,
) = SimpleParam(name, "int", default, annotation.orEmpty())

fun double(
    name: String,
    default: Any? = null,
    annotation: String? = null,
) = SimpleParam(name, "double", default, annotation.orEmpty())

fun float(name: String, default: Any? = null) = SimpleParam(name, "float", default)
fun vec2(nameX: String, nameY: String, default: Any? = null) = ImVec2Param(nameX, nameY, default)
fun vec4(name: String, default: Any? = null) = ImVec4Param(name, default)
fun font(name: String = "font") = PointerParam(name, "JImFont", "ImFont")
fun vec4Ptr(name: String, default: Any? = null) = PointerParam(name, "JImVec4", "ImVec4", default = default)
fun size(name: String = "", default: Any? = null) = vec2("width$name", "height$name", default)
fun pos(name: String = "pos", default: Any? = null) = vec2("${name}X", "${name}Y", default)
fun string(
    name: String,
    default: String? = null,
) = StringParam(name, if (default != strNull) "@NotNull" else "@Nullable", default)

fun stringSized(
    name: String,
    default: String? = null,
) = SizedStringParam(name, if (default != strNull) "@NotNull" else "@Nullable", default)

fun numSegments(default: Int?) = int("numSegments", default = default)
val cond = int("condition", "JImCond.Always", "@MagicConstant(valuesFromClass = JImCond.class)")
val nativeObjectPtr = p("nativeObjectPtr", "long", default = "this.nativeObjectPtr")
val label = string("label")
val text = string("text")
val rightPopupFlags = flags(from = "Popup", default = "MouseButtonRight")
val windowFlags = flags(from = "Window", default = "None")
val sliderFlags = flags(from = "Slider", default = "None")
val treeNodeFlags = flags(from = "TreeNode", default = "Selected")
val mouseButton = int("button", default = "JImMouseButton.Left", annotation = "@MagicConstant(valuesFromClass = JImMouseButton.class)")
val pOpen = boolPtr("openPtr")
val pOpenNull = boolPtr("openPtr", true)
val u32 = int("u32Color")
val thickness = float("thickness", default = 1)
val roundingFlags = flags(name = "roundingCornersFlags", from = "DrawCorner", default = "All")
val rounding = float("rounding", default = 0)
val numSegments = numSegments(12)
val stringID = string("stringID")
val nStringID = string("stringID", default = strNull)
val pos = pos()
val userKeyIndex = int("userKeyIndex")
fun rawFlags(from: String, default: String? = null, name: String = "flags", isFlag: Boolean = true) = int(name,
    default = default?.let { "$from.$default" } ?: 0,
    annotation = "@MagicConstant(${if (isFlag) "flags" else "values"}FromClass = $from.class)")
fun flags(from: String, default: String? = null, name: String = "flags") =
    rawFlags("JIm${from}Flags", default, name)

/**
 * @property name String function name
 * @property type String? null -> void
 * @property param List<out Param>
 */
data class Fun(
    val name: String,
    val type: String?,
    val param: List<Param>,
    var visibility: String = "public",
    var document: String? = null,
) {
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
    fun private(name: String, vararg param: Param) = Fun(name, *param).apply { visibility = "private" }
    fun protected(
        name: String,
        type: String?,
        vararg param: Param,
    ) = Fun(name, type, *param).apply { visibility = "protected" }

    fun private(
        name: String,
        type: String?,
        vararg param: Param,
    ) = Fun(name, type, *param).apply { visibility = "private" }
  }
}

/** Property */
data class PPT(
    @MagicConstant(stringValues = ["int", "float", "double", "short", "byte", "char", "boolean"])
    val type: String,
    val name: String,
    val annotation: String = "",
    val isArray: Boolean = false,
    val jvmName: String = name.decapitalize().replace("$", ""),
    val `c++Name`: String = name.replace('$', 's'),
)

/** Boolean property */
data class BPPT(
    val name: String,
    val isArray: Boolean = false,
    val annotation: String = "",
    val jvmName: String = name.decapitalize().replace("$", ""),
    val `c++Name`: String = name.replace('$', 's'),
)

sealed class Param {
  abstract fun java(): String
  open fun javaDefault(): String = java()
  open fun javaDefaultStr(): String = javaDefault()
  abstract fun javaExpr(): String
  open fun javaExprStr(): String = javaExpr()
  abstract fun `c++`(): String
  open fun `c++Critical`(): String = `c++`()
  abstract fun `c++Expr`(): String
  open fun `c++CriticalExpr`(): String = `c++Expr`()
  open fun surrounding(): Pair<String, String>? = null

  /** null refers to no default value. */
  open val default: Any? get() = null
}

data class SimpleParam(
    val name: String,
    @MagicConstant(stringValues = ["int", "float", "double", "short", "byte", "char", "boolean"])
    val type: String,
    override val default: Any?,
    val annotation: String = "",
) : Param() {
  override fun java() = "$annotation$type $name"
  override fun javaExpr() = name
  override fun `c++`() = "j$type $name"
  override fun `c++Expr`() = name
}

open class StringParam(
    val name: String,
    val annotation: String = "@NotNull",
    override val default: Any?,
) : Param() {
  override fun java() = "byte[] $name"
  override fun javaDefault() = "$annotation String $name"
  override fun javaDefaultStr() = "$annotation JImStr $name"
  override fun javaExpr() = "getBytes($name)"
  override fun javaExprStr() = "$name.bytes"
  override fun `c++`() = "jbyteArray _$name"
  override fun `c++Critical`() = "jint ${name}Len, Ptr<jbyte> $name"
  override fun `c++Expr`() = "STR_J2C($name)"
  override fun surrounding() = "__get(Byte, $name)" to "__release(Byte, $name)"
}

class SizedStringParam(
    name: String,
    annotation: String = "@NotNull",
    default: Any?,
) : StringParam(name, annotation, default) {
  override fun javaExpr() = "$name.getBytes(StandardCharsets.UTF_8)"
  override fun `c++Expr`() = "STR_J2C($name), STR_J2C($name + __len($name))"
  override fun `c++CriticalExpr`() = "STR_J2C($name), STR_J2C($name + ${name}Len)"
}

data class ImVec4Param(val name: String, override val default: Any?) : Param() {
  override fun java() = "long $name"
  override fun javaDefault() = "@NotNull JImVec4 $name"
  override fun javaExpr() = "$name.nativeObjectPtr"
  override fun `c++`() = "jlong $name"
  override fun `c++Expr`() = "*PTR_J2C(ImVec4, $name)"
}

data class TmParam(val name: String, override val default: Any? = null) : Param() {
  override fun java() = "long $name"
  override fun javaDefault() = "@NotNull NativeTime $name"
  override fun javaExpr() = "$name.nativeObjectPtr"
  override fun `c++`() = "jlong $name"
  override fun `c++Expr`() = "*PTR_J2C(tm, $name)"
}

data class PointerParam(
    val name: String,
    val jvmType: String,
    val nativeType: String,
    val annotation: String = "@NotNull",
    override val default: Any? = null,
) : Param() {
  constructor(name: String, jvmType: String, nativeType: String, nullable: Boolean)
      : this(name, jvmType, nativeType, if (nullable) "@Nullable" else "@NotNull", if (nullable) 0 else null)

  override fun java() = "long $name"
  override fun javaDefault() = "@NotNull $jvmType $name"
  override fun javaExpr() = "$name.nativeObjectPtr"
  override fun `c++`() = "jlong $name"
  override fun `c++Expr`() = "PTR_J2C($nativeType, $name)"
}

data class ImVec2Param(val nameX: String, val nameY: String, override val default: Any?) : Param() {
  override fun java() = "float $nameX, float $nameY"
  override fun javaExpr() = "$nameX, $nameY"
  override fun `c++`() = "jfloat $nameX, jfloat $nameY"
  override fun `c++Expr`() = "ImVec2($nameX, $nameY)"
}

fun StringBuilder.javadoc(name: String, default: String? = null): StringBuilder {
  (default ?: GenGenTask.parser.map[name])?.let { append("  /**").append(it).appendln("*/") }
  return this
}

// const val JNI_FUNCTION_INIT = "__JNI__FUNCTION__INIT__ "
// const val JNI_FUNCTION_CLEAN = " __JNI__FUNCTION__CLEAN__"
const val JNI_FUNCTION_INIT = ""
const val JNI_FUNCTION_CLEAN = ""

@Language("C++")
const val CXX_PREFIX = """///
/// author: ice1000
/// generated code, edits are not expected.
///

#ifndef WIN32
#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"
#endif

#include <imgui.h>
#include <imgui_internal.h>
#include <imgui_stdlib.h>
#include <time.h>
#include <imgui_ext.h>
#include "basics.hpp"
#include "overloads_helper.hpp"
#ifdef __cplusplus
extern "C" {
#endif
"""

@Language("C++")
const val CXX_SUFFIX = """
#ifndef WIN32
#pragma clang diagnostic pop
#endif
#ifdef __cplusplus
}
#endif
"""

@Language("C++", suffix = "(){}")
const val JNI_FUNC_PREFIX = "JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_"

@Language("C++", suffix = "(){}")
const val JNI_C_FUNC_PREFIX = "JNIEXPORT auto JNICALL JavaCritical_org_ice1000_jimgui_"
