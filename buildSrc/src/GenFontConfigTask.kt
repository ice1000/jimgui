@file:Suppress("unused")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

/**
 * @author ice1000
 */
open class GenFontConfigTask : GenTask("JImGuiFontConfigGen", "imgui_font_config", since = "v0.4") {
  init {
    description = "Generate binding for ImGui::GetFont()->ConfigData"
  }

  @Language("JAVA", prefix = "class A{", suffix = "}")
  override val userCode = """protected long nativeObjectPtr;

  /** package-private by design */
  $className(long nativeObjectPtr) {
    this.nativeObjectPtr = nativeObjectPtr;
  }
"""

  override fun java(javaCode: StringBuilder) {
    imVec2Members.forEach { genJavaObjectiveXYAccessor(javaCode, it, "float") }
    primitiveMembers.forEach { (type, name) -> genSimpleJavaObjectivePrimitiveMembers(javaCode, name, type) }
    booleanMembers.forEach { genSimpleJavaObjectiveBooleanMember(javaCode, it) }
  }

  override fun `c++`(cppCode: StringBuilder) {
    imVec2Members.joinLinesTo(cppCode) { `c++XYAccessor`(it, "float", "jlong nativeObjectPtr") }
    primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveAccessor`(type, name, "jlong nativeObjectPtr") }
    booleanMembers.joinLinesTo(cppCode) { `c++BooleanAccessor`(it, "jlong nativeObjectPtr") }
  }

  override val `c++Expr` = "PTR_J2C(ImFontConfig, nativeObjectPtr)->"
  private val booleanMembers = listOf("FontDataOwnedByAtlas", "PixelSnapH", "MergeMode")
  private val imVec2Members = listOf("GlyphExtraSpacing", "GlyphOffset")
  private val primitiveMembers = listOf(
      "int" to "FontDataSize",
      "int" to "FontNo",
      "int" to "OversampleH",
      "int" to "OversampleV",
      "int" to "RasterizerFlags",
      "float" to "SizePixels",
      "float" to "RasterizerMultiply",
      "float" to "GlyphMinAdvanceX",
      "float" to "GlyphMaxAdvanceX"
  )
}
