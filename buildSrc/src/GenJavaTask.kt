package org.ice1000.gradle

import org.gradle.api.DefaultTask
import org.intellij.lang.annotations.Language

open class GenJavaTask(val className: String) : DefaultTask() {
	@JvmField
	val targetJavaFile = project
			.projectDir
			.resolve("gen")
			.resolve("org")
			.resolve("ice1000")
			.resolve("jimgui")
			.resolve("$className.java")
			.absoluteFile

	@Language("Text")
	open val userCode = ""

	protected val prefixJava
		@Language("JAVA", suffix = "}")
		get() = """$CLASS_PREFIX
public class $className {
	$userCode

	/** package-private by design */
	$className() { }
"""

	protected val prefixInterfacedJava
		@Language("JAVA", suffix = "}")
		get() = """$CLASS_PREFIX
public interface $className {
"""

	val eol: String = System.lineSeparator()
}
