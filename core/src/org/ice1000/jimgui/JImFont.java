package org.ice1000.jimgui;

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
    if (nativeObjectPtr == 0) throw new NullPointerException();
  }

  @Contract("->new") public @NotNull NativeString debugName() {
    return NativeString.fromRaw(getDebugName());
  }

  public @NotNull JImFontAtlas getContainerAtlas() {
    return new JImFontAtlas(getContainerFontAtlas(nativeObjectPtr));
  }

  public @NotNull JImFontConfig getConfigData() {
    return new JImFontConfig(getConfigData(nativeObjectPtr));
  }
  // public native void setDisplayOffset(float newX, float newY);

  private static native long getContainerFontAtlas(long nativeObjectPtr);

  private static native long getConfigData(long nativeObjectPtr);
}
