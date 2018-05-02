package org.ice1000.gradle

import org.gradle.api.tasks.Exec

/**
 * @author ice1000
 */
open class GenNativeHeaderTask : Exec() {
	init {
		group = "code generation"
		description = "Run javah to generate all native header files"
	}
}
