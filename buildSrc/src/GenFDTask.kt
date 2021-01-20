package org.ice1000.gradle

import org.intellij.lang.annotations.Language

open class GenFDTask : GenTask("JImFileDialogGen", "imgui_file_dialog", since = "v0.13") {
  override val `c++Expr` = "PTR_J2C(IGFD::FileDialog, nativeObjectPtr)->"
  override val `prefixC++`: String
    get() = "#include <ImGuiFileDialog.h>\n${super.`prefixC++`}"

  @Language("JAVA")
  override val userCode = """  /** package-private by design */
  $className(long nativeObjectPtr) {
    this.nativeObjectPtr = nativeObjectPtr;
  }

  protected long nativeObjectPtr;
"""

  override fun `c++`(cppCode: StringBuilder) {
    functions.forEach { (name, type, params) -> `genC++Fun`(params.dropLast(1), name, type, cppCode, "jlong nativeObjectPtr") }
  }

  override fun java(javaCode: StringBuilder) {
    functions.forEach { genJavaFun(javaCode, it) }
  }

  private val filters = string("filters")
  private val key = string("key")
  private val functions = listOf(
      Fun("display", "boolean", key,
          windowFlags, size("Min", default = "0, 0"), size("Max", default = "FLT_MAX, FLT_MAX"),
          nativeObjectPtr),
      Fun("openDialog", key, string("title"),
          filters, string("basePath"),
          int("maxSelection", default = 1), nativeObjectPtr),
      Fun("openModal", key, string("title"),
          filters, string("basePath"),
          int("maxSelection", default = 1), nativeObjectPtr),
      Fun("close", nativeObjectPtr),
      Fun("isOk", "boolean", nativeObjectPtr),
      Fun("wasOpenedThisFrame", "boolean", key, nativeObjectPtr),
      Fun("isOpened", "boolean", string("text"), nativeObjectPtr),
      Fun("setExtensionInfo", filters, vec4("color"), string("text"), nativeObjectPtr),
      Fun("getExtensionInfo", "boolean",
          filters, vec4Ptr("color"), stringPtr("text"),
          nativeObjectPtr),
      Fun("clearExtensionInfo", nativeObjectPtr),
  )
}
