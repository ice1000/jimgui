package org.ice1000.gradle

import java.io.File

/**
 * @author ice1000
 */
class ImGuiHeaderParser {
  val map: MutableMap<String, String> = hashMapOf()

  fun parseHeader(imguiHeader: File) {
    imguiHeader.bufferedReader().use { reader ->
      reader.lineSequence()
          .dropWhile { it != "#pragma once" }
          .map { it.trimStart() }
          .map { it.removePrefix("IMGUI_API ") }
          .filter { it.indexOf(';') > 0 || it.indexOf('=') > 0 }
          .filter { it.indexOf("//") > 0 }
          .mapNotNull {
            val docStartIndex = it.indexOf("//")
            val parenthesesStartIndex = it.indexOf('(')
            val bracketsStartIndex = it.indexOf('[')
            val assignStartIndex = it.indexOf('=')
            val name = when {
              bracketsStartIndex in 0..docStartIndex -> it.substring(0, bracketsStartIndex)
              parenthesesStartIndex in 0..docStartIndex -> it.substring(0, parenthesesStartIndex)
              assignStartIndex > 0 -> {
                if (assignStartIndex in 0..docStartIndex) it.substring(0, assignStartIndex)
                else return@mapNotNull null
              }
              else -> it.substring(0, it.indexOf(';'))
            }.trimEnd()
            val javadoc = it.substring(docStartIndex).trim(' ', '/', '\n', '\r', '\t')
                .replace('/', '|')
            if (' ' in name)
              name.substring(name.lastIndexOf(' ')).trimStart() to javadoc
            else
              name.decapitalize() to javadoc
          }
          .filter { (name, _) -> name.isNotEmpty() }
          .filter { (name, _) -> !name[0].isDigit() }
          .forEach { (name, javadoc) ->
            val original = map[name]
            if (original != null) map[name] = "$original\n  $javadoc"
            else map[name] = javadoc
          }
    }
  }
}
