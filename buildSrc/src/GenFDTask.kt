package org.ice1000.gradle

open class GenFDTask : GenTask("JImFileDialogGen", "imgui_file_dialog") {
  override val `c++Expr` = "igfd::ImGuiFileDialog::Instance()->"
  override val `prefixC++`: String
    get() = "#include <ImGuiFileDialog.h>\n${super.`prefixC++`}"

  override fun `c++`(cppCode: StringBuilder) {
    functions.forEach { `genC++Fun`(it, cppCode) }
  }

  override fun java(javaCode: StringBuilder) {
    functions.forEach { genJavaFun(javaCode, it) }
  }

  private val functions = listOf(
      Fun("fileDialog", "boolean", string("key"),
          windowFlags, size("Min", default = "0, 0"), size("Max", default = "FLT_MAX, FLT_MAX")),
      Fun("openDialog", string("key"), string("title"),
          string("filters"), string("basePath")),
  )
}
