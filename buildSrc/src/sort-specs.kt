package org.ice1000.gradle

import org.intellij.lang.annotations.Language

open class GenTableSortSpecsTask : GenTask("JImTableSortSpecsGen", "imgui_table_sort_specs_gen", since = "v0.18") {
  override val `c++Expr` = "PTR_J2C(ImGuiTableSortSpecs, nativeObjectPtr)->"

  @Language("JAVA")
  override val userCode = """  /** package-private by design */
  $className(long nativeObjectPtr) {
    this.nativeObjectPtr = nativeObjectPtr;
  }

  protected long nativeObjectPtr;
"""

  override fun `c++`(cppCode: StringBuilder) {
    cppCode.appendLine(`c++PrimitiveAccessor`("int", "SpecsCount", "jlong nativeObjectPtr"))
        .append(`c++BooleanAccessor`("SpecsDirty", "jlong nativeObjectPtr"))
  }

  override fun java(javaCode: StringBuilder) {
    genSimpleJavaObjectivePrimitiveMembers(javaCode, "SpecsCount", "int")
    genSimpleJavaObjectiveBooleanMember(javaCode, "SpecsDirty")
  }
}

open class GenColumnSortSpecsTask : GenTask("JImColumnSortSpecsGen", "imgui_column_sort_specs_gen", since = "v0.18") {
  override val `c++Expr` = "PTR_J2C(ImGuiTableColumnSortSpecs, nativeObjectPtr)->"
  @Language("JAVA")
  override val userCode = """  /** package-private by design */
  $className(long nativeObjectPtr) {
    this.nativeObjectPtr = nativeObjectPtr;
  }

  protected long nativeObjectPtr;
"""

  override fun `c++`(cppCode: StringBuilder) {
    primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveAccessor`(type, name, "jlong nativeObjectPtr") }
  }

  override fun java(javaCode: StringBuilder) {
    primitiveMembers.forEach { (type, name) -> genSimpleJavaObjectivePrimitiveMembers(javaCode, name, type) }
  }

  private val primitiveMembers = listOf(
      "int" to "ColumnUserID",
      "int" to "SortDirection",
      "short" to "ColumnIndex",
      "short" to "SortOrder",
  )
}
