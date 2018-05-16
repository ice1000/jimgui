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
	open val userCode = """/** package-private by design */
	$className() { }
"""

	protected val prefixJava
		@Language("JAVA", suffix = "}")
		get() = """$CLASS_PREFIX
public class $className {
	$userCode
"""

	protected val prefixInterfacedJava
		@Language("JAVA", suffix = "}")
		get() = """$CLASS_PREFIX
public interface $className {
"""

	val eol: String = System.lineSeparator()
	fun StringBuilder.genJavaObjectiveMemberAccessor(name: String, annotation: String, type: String) {
		javadoc(name)
				.append("\tpublic ").append(annotation).append(type).append(" get").append(name).append("(){return get").append(name).appendln("(nativeObjectPtr);}")
				.append("\tprivate native ").append(annotation).append(type).append(" get").append(name).appendln("(long nativeObjectPtr);")
				.javadoc(name)
				.append("\tpublic void set").append(name).append('(').append(annotation).append(type).append(" newValue) {set").append(name).appendln("(nativeObjectPtr, newValue);}")
				.append("\tprivate native void set").append(name).append("(long nativeObjectPtr, ").append(annotation).append(type).appendln(" newValue);")
	}
}
