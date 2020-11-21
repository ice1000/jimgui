package org.ice1000.gradle

import org.gradle.api.DefaultTask
import org.intellij.lang.annotations.Language

open class GenJavaTask(
		val className: String,
		val since: String = "v0.1",
		private val packageName: String = "org.ice1000.jimgui",
		relativePath: String = packageName.replace('.', '/')
) : DefaultTask() {
	val targetJavaFile = project
			.projectDir
			.resolve("gen")
			.resolve(relativePath)
			.resolve("$className.java")
			.absoluteFile

	init {
		group = "code generation"
		targetJavaFile.parentFile.mkdirs()
	}

	@Language("Text")
	open val userCode = """/** package-private by design */
	$className() { }
"""

	protected val prefixJava
		@Language("JAVA", suffix = "}")
		get() = """package $packageName;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.flag.*;
import org.ice1000.jimgui.cpp.*;
import org.intellij.lang.annotations.*;
import org.jetbrains.annotations.*;
import java.nio.charset.StandardCharsets;

import static org.ice1000.jimgui.util.JImGuiUtil.*;

/**
 * @author ice1000
 * @since $since
 */
@SuppressWarnings("ALL")

public class $className {
$userCode
"""

	protected val prefixInterfacedJava
		@Language("JAVA", suffix = "}")
		get() = """package $packageName;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.flag.*;
import org.ice1000.jimgui.cpp.*;
import org.intellij.lang.annotations.*;
import org.jetbrains.annotations.*;
import java.nio.charset.StandardCharsets;

import static org.ice1000.jimgui.util.JImGuiUtil.*;

/**
 * @author ice1000
 * @since $since
 */
@SuppressWarnings("ALL")

public interface $className {
"""

	val eol: String = System.lineSeparator()
}
