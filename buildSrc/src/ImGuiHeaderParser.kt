package org.ice1000.gradle

import java.io.File

/**
 * @author ice1000
 */
class ImGuiHeaderParser(imguiHeader: File) {
	val map: MutableMap<String, String> = hashMapOf()

	init {
		imguiHeader.bufferedReader().use {
			it.lineSequence()
					.dropWhile { it != "namespace ImGui" }
					.map { it.trim() }
					.filter { it.startsWith("IMGUI_API") }
					.map { it.removePrefix("IMGUI_API ") }
					.filter { it.indexOf('(') > 0 }
					.filter { it.indexOf("//") > 0 }
					.map {
						val name = it.substring(0, it.indexOf('('))
						val javadoc = it.substring(it.indexOf("//") + 2)
						if (' ' in name)
							name.substring(name.lastIndexOf(' ')).trimStart().decapitalize() to javadoc
						else
							name.decapitalize() to javadoc
					}
					.toMap(map)
		}
	}
}