@file:Suppress("unused")

package org.ice1000.gradle

import org.gradle.api.tasks.Exec

/**
 * @author ice1000
 */
open class GenNativeHeaderTask : Exec() {
	private val target
		get() = project
				.rootProject
				.projectDir
				.resolve("jni")
				.resolve("javah")
				.absoluteFile
	private val classpath = project
			.buildDir
			.resolve("classes")
			.resolve("java")
			.resolve("main")
			.absoluteFile

	init {
		group = "code generation"
		description = "Run javah to generate all native header files"
	}

	fun classes(vararg classes: String) {
		commandLine("javah", "-d", target, "-classpath", classpath, *classes)
	}
}
