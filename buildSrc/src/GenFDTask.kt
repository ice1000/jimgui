package org.ice1000.gradle

open class GenFDTask : GenTask("JImFileDialogGen", "imgui_file_dialog") {
  override val `c++Expr` = "igfd::ImGuiFileDialog::Instance()->"
  override val `prefixC++`: String
    get() = "#include <ImGuiFileDialog.h>\n${super.`prefixC++`}"

  override fun `c++`(cppCode: StringBuilder) {
    functions.forEach { `genC++Fun`(it, cppCode) }
    cppCode.appendln(`c++PrimitiveAccessor`("boolean", "IsOk"))
  }

  override fun java(javaCode: StringBuilder) {
    functions.forEach { genJavaFun(javaCode, it) }
    genJavaPrimitiveMember(javaCode, "IsOk", "", "boolean", false, "", "114514")
  }

  private val filters = string("filters")
  private val key = string("key")
  private val functions = listOf(
      Fun("fileDialog", "boolean", key,
          windowFlags, size("Min", default = "0, 0"), size("Max", default = "FLT_MAX, FLT_MAX")),
      Fun("openDialog", key, string("title"),
          filters, string("basePath")),
      Fun("openModal", key, string("title"),
          filters, string("basePath")),
      Fun("closeDialog", key),
      Fun("setExtensionInfo", filters, vec4("color"), string("text")),
      Fun("getExtensionInfo", "boolean", filters, vec4Ptr("color"), stringPtr("text", nullable = true)),
      Fun("clearExtensionInfo"),
      Fun("drawBookmarkPane", size()),
  )
}