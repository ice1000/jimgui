package org.ice1000.gradle

import org.intellij.lang.annotations.Language

open class GenFDTask : GenTask("JImFileDialogGen", "imgui_file_dialog") {
  override val `c++Expr` = "PTR_J2C(IGFD::ImGuiFileDialog, nativeObjectPtr)->"
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
    cppCode.appendln(`c++PrimitiveAccessor`("boolean", "IsOk", "jlong nativeObjectPtr"))
  }

  override fun java(javaCode: StringBuilder) {
    functions.forEach { genJavaFun(javaCode, it) }
    genJavaPrimitiveObjectiveMember(javaCode, "boolean", "IsOk", "114514")
  }

  private val filters = string("filters")
  private val key = string("key")
  private val functions = listOf(
      Fun("fileDialog", "boolean", key,
          windowFlags, size("Min", default = "0, 0"), size("Max", default = "FLT_MAX, FLT_MAX"),
          nativeObjectPtr),
      Fun("openDialog", key, string("title"),
          filters, string("basePath"), nativeObjectPtr),
      Fun("openModal", key, string("title"),
          filters, string("basePath"), nativeObjectPtr),
      Fun("closeDialog", key, nativeObjectPtr),
      Fun("wasOpenedThisFrame", "boolean", key, nativeObjectPtr),
      Fun("isOpened", "boolean", stringPtr("text", nullable = true), nativeObjectPtr),
      Fun("setExtensionInfo", filters, vec4("color"), string("text"), nativeObjectPtr),
      Fun("getExtensionInfo", "boolean",
          filters, vec4Ptr("color"), stringPtr("text", nullable = true),
          nativeObjectPtr),
      Fun("clearExtensionInfo", nativeObjectPtr),
  )
}
