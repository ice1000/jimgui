package org.ice1000.jimgui;

import org.ice1000.jimgui.flag.JImWindowFlags;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

import static org.ice1000.jimgui.util.JImGuiUtil.FLT_MAX;

/**
 * https://github.com/aiekick/ImGuiFileDialog
 *
 * @author ice1000
 * @since v0.13.0
 */
public final class JImFileDialog extends JImFileDialogGen {
  public static final @NotNull JImFileDialog INSTANCE = new JImFileDialog();

  private JImFileDialog() {
  }

  public boolean fileDialog(
      @NotNull NativeString key,
      @MagicConstant(flagsFromClass = JImWindowFlags.class) int flags) {
    return fileDialog(key, flags, 0, 0, FLT_MAX, FLT_MAX);
  }

  public boolean fileDialog(
      @NotNull NativeString key,
      @MagicConstant(flagsFromClass = JImWindowFlags.class) int flags,
      float minSizeX,
      float minSizeY,
      float maxSizeX,
      float maxSizeY) {
    return fileDialogP(key.nativeObjectPtr, flags, minSizeX, minSizeY, maxSizeX, maxSizeY);
  }

  private static native boolean fileDialogP(
      long nativeStringPtrKey,
      @MagicConstant(flagsFromClass = JImWindowFlags.class) int flags,
      float minSizeX,
      float minSizeY,
      float maxSizeX,
      float maxSizeY);

  public static native void loadIcons(float fontSize);
}
