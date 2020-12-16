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
public final class JImFileDialog extends JImFileDialogGen {
  public static final @NotNull JImFileDialog INSTANCE = new JImFileDialog();

  private JImFileDialog() {
  }

  private static native boolean fileDialogP(
      long nativeStringPtrKey,
      @MagicConstant(flagsFromClass = JImWindowFlags.class) int flags,
      float minSizeX,
      float minSizeY,
      float maxSizeX,
      float maxSizeY);

  public static native void loadIcons();
}
