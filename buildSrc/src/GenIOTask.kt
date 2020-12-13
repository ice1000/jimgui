@file:Suppress("unused")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

private const val MOUSE_INDICES = "@MagicConstant(valuesFromClass = JImMouseButton.class)"

/**
 * @author ice1000
 */
open class GenIOTask : GenTask("JImGuiIOGen", "imgui_io") {
  init {
    description = "Generate binding for ImGui::GetIO"
  }

  @Language("JAVA", prefix = "class A{", suffix = "}")
  override val userCode = """  @Contract(pure = true)
  public static @NotNull JImGuiIOGen getInstance(@NotNull JImGui owner) {
    return owner.getIO();
  }

  /** package-private by design */
  JImGuiIOGen() { }
"""

  override fun java(javaCode: StringBuilder) {
    imVec2Members.forEach { genJavaXYAccessor(javaCode, it, "float") }
    functions.forEach { genJavaFun(javaCode, it) }
    primitiveMembers.forEach { (type, name, annotation, isArray, jvmName, `c++Name`) ->
      genJavaPrimitiveMember(javaCode, name, annotation, type, isArray, jvmName, `c++Name`)
    }
    booleanMembers.forEach { (name, isArray, annotation, jvmName, `c++Name`) ->
      genJavaBooleanMember(javaCode, name, isArray, jvmName, annotation, `c++Name`)
    }
    stringMembers.forEach { name ->
      javaCode.append("  private static native void set").append(name).appendln("(byte[]newValue);")
          .javadoc(name)
          .append("  public void set").append(name).append("(@NotNull String newValue){set").append(name)
          .appendln("(getBytes(newValue));}")
    }
  }

  override fun `c++`(cppCode: StringBuilder) {
    imVec2Members.joinLinesTo(cppCode) { `c++XYAccessor`(it, "float") }
    primitiveMembers.joinLinesTo(cppCode) { (type, name, _, isArray, jvmName, `c++Name`) ->
      if (isArray) `c++PrimitiveArrayAccessor`(type, name, jvmName, `c++Name`)
      else `c++PrimitiveAccessor`(type, name)
    }
    booleanMembers.joinLinesTo(cppCode) { (name, isArray, _, jvmName, `c++Name`) ->
      if (isArray) `c++BooleanArrayAccessor`(name, jvmName, `c++Name`)
      else `c++BooleanAccessor`(name)
    }
    functions.forEach { (name, type, params) -> `genC++Fun`(params, name, type, cppCode) }
    stringMembers.joinLinesTo(cppCode) {
      val param = string(name = it.decapitalize(), default = "null")
      val (init, deinit) = param.surrounding()
      `c++StringedFunction`(
          name = "set$it",
          params = listOf(param),
          type = null,
          `c++Expr` = "$`c++Expr`$it = ${param.`c++Expr`()}",
          `c++CriticalExpr` = "$`c++Expr`$it = ${param.`c++CriticalExpr`()}",
          init = "$JNI_FUNCTION_INIT $init",
          deinit = "$deinit $JNI_FUNCTION_CLEAN")
    }
  }

  override val `c++Expr` = "ImGui::GetIO()."

  private val functions = listOf(
      Fun("addInputCharactersUTF8", string("characters")),
      Fun("clearInputCharacters"),
      Fun("addInputCharacter", int("character")))

  private val stringMembers = listOf("IniFilename", "LogFilename")

  private val booleanMembers = listOf(
      BPPT("Key\$Down", isArray = true),
      BPPT("MouseClicked", isArray = true, annotation = MOUSE_INDICES),
      BPPT("MouseDoubleClicked", isArray = true, annotation = MOUSE_INDICES),
      BPPT("MouseReleased", isArray = true, annotation = MOUSE_INDICES),
      BPPT("MouseDownOwned", isArray = true, annotation = MOUSE_INDICES),
      BPPT("FontAllowUserScaling"),
      BPPT("ConfigMacOSXBehaviors"),
      BPPT("ConfigInputTextCursorBlink"),
      BPPT("ConfigWindowsResizeFromEdges"),
      BPPT("ConfigWindowsMoveFromTitleBarOnly"),
      BPPT("MouseDrawCursor"),
      BPPT("KeyCtrl"),
      BPPT("KeyShift"),
      BPPT("KeyAlt"),
      BPPT("KeySuper"),
      BPPT("WantCaptureMouse"),
      BPPT("WantCaptureKeyboard"),
      BPPT("WantTextInput"),
      BPPT("WantSetMousePos"),
      BPPT("WantSaveIniSettings"),
      BPPT("NavActive"),
      BPPT("NavVisible"))

  private val primitiveMembers = listOf(
      PPT("int", "InputQueueCharacter$", isArray = true),
      PPT("float", "NavInput$", isArray = true),
      PPT("float", "MouseClickedTime", isArray = true, annotation = MOUSE_INDICES),
      PPT("float", "MouseDownDuration", isArray = true, annotation = MOUSE_INDICES),
      PPT("float", "MouseDownDurationPrev", isArray = true, annotation = MOUSE_INDICES),
      PPT("float", "MouseDragMaxDistanceSqr", isArray = true, annotation = MOUSE_INDICES),
      PPT("float", "KeysDownDuration", isArray = true),
      PPT("float", "KeysDownDurationPrev", isArray = true),
      PPT("float", "NavInputsDownDuration", isArray = true),
      PPT("float", "NavInputsDownDurationPrev", isArray = true),
      PPT("int", "MetricsRenderVertices"),
      PPT("int", "MetricsRenderIndices"),
      PPT("int", "MetricsActiveWindows"),
      PPT("int", "KeyMap", isArray = true),
      PPT("int", "ConfigFlags", "@MagicConstant(flagsFromClass = JImConfigFlags.class)"),
      PPT("int", "BackendFlags", "@MagicConstant(flagsFromClass = JImBackendFlags.class)"),
      PPT("float", "ConfigMemoryCompactTimer"),
      PPT("float", "MouseDoubleClickTime"),
      PPT("float", "MouseDoubleClickMaxDist"),
      PPT("float", "KeyRepeatDelay"),
      PPT("float", "KeyRepeatRate"),
      PPT("float", "FontGlobalScale"),
      PPT("float", "MouseWheel"),
      PPT("float", "MouseWheelH"),
      PPT("float", "Framerate"),
      PPT("float", "DeltaTime"),
      PPT("float", "IniSavingRate"))

  private val imVec2Members = listOf(
      "DisplayFramebufferScale",
      // "DisplayVisibleMin",
      // "DisplayVisibleMax",
      "DisplaySize",
      "MousePos",
      "MouseDelta",
      "MousePosPrev"
  )
}
