package org.ice1000.jimgui;

import org.ice1000.jimgui.flag.JImFontAtlasFlags;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.1
 */
public final class JImFont extends JImGuiFontGen {
  /**
   * {@inheritDoc}
   *
   * @param nativeObjectPtr native ImFont*
   */
  @Contract(pure = true) JImFont(long nativeObjectPtr) {
    super(nativeObjectPtr);
  }

  @Contract public @NotNull String getDebugName() {
    return new String(getDebugName0(nativeObjectPtr));
  }

  public void setFallbackChar(char wChar) {
    setFallbackChar((int) wChar);
  }

  public @NotNull JImFontAtlas getContainerAtlas() {
    return new JImFontAtlas(getContainerFontAtlas(nativeObjectPtr));
  }

  public @NotNull JImFontConfig getConfigData() {
    return new JImFontConfig(getConfigData(nativeObjectPtr));
  }
  // public native void setDisplayOffset(float newX, float newY);

  @Contract private static native byte[] getDebugName0(long nativeObjectPtr);

  private static native long getContainerFontAtlas(long nativeObjectPtr);

  private static native long getConfigData(long nativeObjectPtr);
}
