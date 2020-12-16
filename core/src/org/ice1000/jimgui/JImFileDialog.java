package org.ice1000.jimgui;

import org.ice1000.jimgui.flag.JImWindowFlags;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/aiekick/ImGuiFileDialog
 *
 * @author ice1000
 * @since v0.13.0
 */
public final class JImFileDialog {
  private JImFileDialog() {
  }

  private static native void openDialog(byte @NotNull [] key, byte @NotNull [] title, byte @NotNull [] filters);

  private static native boolean fileDialog(
      byte @NotNull [] key,
      @MagicConstant(flagsFromClass = JImWindowFlags.class) int flags,
      float minSizeX,
      float minSizeY,
      float maxSizeX,
      float maxSizeY);

  private static native boolean fileDialogP(
      long nativeStringPtrKey,
      @MagicConstant(flagsFromClass = JImWindowFlags.class) int flags,
      float minSizeX,
      float minSizeY,
      float maxSizeX,
      float maxSizeY);

  public static native void loadIcons();
}
