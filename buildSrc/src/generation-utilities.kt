@file:JvmName("GenerationUtil")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

data class Param(val name: String, val type: String)

//region Getter and setter
fun javaPrimitiveGetter(type: String, name: String) = "public native $type get$name();"

fun javaBooleanGetter(name: String) = "public native boolean is$name();"

fun `cppPrimitiveGetter`(className: String, type: String, name: String, `c++Expr`: String) =
		"JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_${className}_get$name(JNIEnv *, jobject) -> j$type { return static_cast<j$type> ($`c++Expr`); }"

fun `c++BooleanGetter`(className: String, name: String, `c++Expr`: String) =
		"JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_${className}_is$name(JNIEnv *, jobject) -> jboolean { return static_cast<jboolean> ($`c++Expr` ? JNI_TRUE : JNI_FALSE); }"

fun javaPrimitiveMemberGetter(type: String, name: String, ptrName: String = "nativeObjectPtr") =
		"""private static native $type get$name(long $ptrName);public $type get$name() { return get$name($ptrName); }"""

fun javaBooleanSetter(name: String) = javaPrimitiveSetter("boolean", name)
fun javaPrimitiveSetter(type: String, name: String) = "public native void set$name($type newValue);"

fun `c++BooleanSetter`(className: String, name: String, `c++Expr`: String) = `cppPrimitiveSetter`(className, "boolean", name, `c++Expr`)
fun `cppPrimitiveSetter`(className: String, type: String, name: String, `c++Expr`: String) =
		"JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_${className}_set$name(JNIEnv *, jobject, j$type newValue) -> void { $`c++Expr` = newValue; }"

fun javaPrimitiveMemberSetter(type: String, name: String, ptrName: String = "nativeObjectPtr") =
		"private static native void set$name(long $ptrName, $type newValue);public final void set$name($type newValue) { return set$name($ptrName, newValue); }"
//endregion

fun List<Param>.java() = joinToString { (name, type) -> "$name: $type" }
fun List<Param>.`c++`() = joinToString { (name, type) -> "$type $name" }

fun javaSimpleMethod(name: String, params: List<Param>, type: String) = "public native $type $name(${params.java()});"
fun `c++SimpleMethod`(className: String, name: String, params: List<Param>, type: String) =
		"auto Java_org_ice1000_jimgui_${className}_$name(JNIEnv *, jobject) -> j$type { ImGui::Separator(); }"

val eol: String = System.lineSeparator()

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
/// since: v0.1
///

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

#include <imgui.h>
"""

@Language("C++")
const val CXX_SUFFIX = "#pragma clang diagnostic pop"
