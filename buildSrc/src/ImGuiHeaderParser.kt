package org.ice1000.gradle

import java.io.File

/**
 * @author ice1000
 */
class ImGuiHeaderParser(private val imguiHeader: File) {
	val map: Map<String, String> = hashMapOf()

	init {
		imguiHeader.bufferedReader().use {
			it.lineSequence().dropWhile { it != "namespace ImGui" }
			TODO()
		}
	}
}