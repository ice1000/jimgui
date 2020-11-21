package org.ice1000.gradle

import java.io.File

/**
 * @author ice1000
 */
class ImGuiHeaderParser(imguiHeader: File) {
	val map: MutableMap<String, String> = hashMapOf()

	init {
		imguiHeader.bufferedReader().use { reader ->
			reader.lineSequence()
					.dropWhile { it != "namespace ImGui" }
					.map { it.trimStart() }
					.map { it.removePrefix("IMGUI_API ") }
					.filter { it.indexOf(';') > 0 }
					.filter { it.indexOf("//") > 0 }
					.map {
						val docStartIndex = it.indexOf("//")
						val parenthesesStartIndex = it.indexOf('(')
						val bracketsStartIndex = it.indexOf('[')
						val name = when {
							bracketsStartIndex in 0..docStartIndex -> it.substring(0, bracketsStartIndex)
							parenthesesStartIndex in 0..docStartIndex -> it.substring(0, parenthesesStartIndex)
							else -> it.substring(0, it.indexOf(';'))
						}.trimEnd()
						val javadoc = it.substring(docStartIndex).trim(' ', '/', '\n', '\r', '\t')
								.replace('/', '|')
						if (' ' in name)
							name.substring(name.lastIndexOf(' ')).trimStart() to javadoc
						else
							name.decapitalize() to javadoc
					}
					.forEach { (name, javadoc) ->
						val original = map[name]
						if (original != null) map[name] = "$original\n\t$javadoc"
						else map[name] = javadoc
					}
		}
	}
}