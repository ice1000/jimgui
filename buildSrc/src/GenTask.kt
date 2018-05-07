package org.ice1000.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.intellij.lang.annotations.Language
import kotlin.math.exp

@Suppress("PrivatePropertyName", "LocalVariableName", "FunctionName")
abstract class GenTask(
		val className: String,
		private val `c++FileSuffix`: String
) : DefaultTask(), Runnable {
	init {
		group = "code generation"
	}

	@Language("Text")
	open val userCode = ""

	private val `prefixC++`
		@Language("C++")
		get() = """$CXX_PREFIX
#include <org_ice1000_jimgui_$className.h>
"""

	private val prefixJava
		@Language("JAVA", suffix = "}")
		get() = """$CLASS_PREFIX
public class $className {
	$userCode

	/** package-private by design */
	$className() { }
"""


	@TaskAction
	override fun run() {
		val targetJavaFile = project
				.projectDir
				.resolve("gen")
				.resolve("org")
				.resolve("ice1000")
				.resolve("jimgui")
				.resolve("$className.java")
				.absoluteFile
		val `targetC++File` = project
				.projectDir
				.resolve("jni")
				.resolve("generated_$`c++FileSuffix`.cpp")
				.absoluteFile
		targetJavaFile.parentFile.mkdirs()
		`targetC++File`.parentFile.mkdirs()
		val javaCode = StringBuilder(prefixJava)
		java(javaCode)
		javaCode.append(eol).append('}')
		targetJavaFile.writeText("$javaCode")
		val cppCode = StringBuilder(`prefixC++`)
		`c++`(cppCode)
		cppCode.append(CXX_SUFFIX)
		`targetC++File`.writeText("$cppCode")
	}

	abstract fun `c++`(cppCode: StringBuilder)
	abstract fun java(javaCode: StringBuilder)

	fun <T> List<T>.joinLinesTo(builder: StringBuilder, transform: (T) -> CharSequence) = joinTo(builder, eol, postfix = eol, transform = transform)

	//region Getter and setter
	fun javaPrimitiveGetter(type: String, name: String) = "public native $type get$name();"

	fun javaBooleanGetter(name: String) = "public native boolean is$name();"

	fun `c++PrimitiveGetter`(type: String, name: String, `c++Expr`: String) =
			"JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_${className}_get$name(JNIEnv *, jobject) -> j$type { return static_cast<j$type> ($`c++Expr`); }"

	fun `c++BooleanGetter`(name: String, `c++Expr`: String) =
			"JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_${className}_is$name(JNIEnv *, jobject) -> jboolean { return static_cast<jboolean> ($`c++Expr` ? JNI_TRUE : JNI_FALSE); }"

	fun javaPrimitiveMemberGetter(type: String, name: String, ptrName: String = "nativeObjectPtr") =
			"""private static native $type get$name(long $ptrName);public $type get$name() { return get$name($ptrName); }"""

	fun javaBooleanSetter(name: String) = javaPrimitiveSetter("boolean", name)
	fun javaPrimitiveSetter(type: String, name: String) = "public native void set$name($type newValue);"

	fun `c++BooleanSetter`(name: String, `c++Expr`: String) = `c++PrimitiveSetter`("boolean", name, `c++Expr`)
	fun `c++PrimitiveSetter`(type: String, name: String, `c++Expr`: String) =
			"JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_${className}_set$name(JNIEnv *, jobject, j$type newValue) -> void { $`c++Expr` = newValue; }"

	fun javaPrimitiveMemberSetter(type: String, name: String, ptrName: String = "nativeObjectPtr") =
			"private static native void set$name(long $ptrName, $type newValue);public final void set$name($type newValue) { return set$name($ptrName, newValue); }"
//endregion

	//region Trivial helpers
	fun List<Param>.`c++`() = joinToString { it.`c++`() }
	fun List<Param>.`c++`(builder: StringBuilder) = joinTo(builder) { it.`c++`() }
	fun List<Param>.`c++Expr`() = joinToString { it.`c++Expr`() }
	fun comma(params: List<Param>) = if (params.isNotEmpty()) ", " else ""
	fun boolean(type: String?) = if (type == "boolean") " ? JNI_TRUE : JNI_FALSE" else ""
	fun type(type: String?) = type ?: "void"
	fun ret(type: String?, expr: String = "", orElse: String = expr) = type?.let { "return static_cast<j$type> ($expr);" } ?: "$orElse;"
	fun auto(type: String?) = type?.let { "auto res = " }.orEmpty()
	fun orVoid(type: String?) = type?.let { "j$it" } ?: "void"
	val eol: String = System.lineSeparator()
	//endregion

}
