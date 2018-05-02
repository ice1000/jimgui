@file:JvmName("GenerationUtil")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

fun javaPrimitiveGetter(type: String, name: String) =
		"public native $type get$name();"

fun javaPrimitiveSetter(type: String, name: String) =
		"public native void set$name($type newValue);"

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
