package org.ice1000.jimgui;

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
	JImFont(long nativeObjectPtr) {
		super(nativeObjectPtr);
	}

	public @NotNull String getDebugName() {
		return new String(getDebugName0(nativeObjectPtr));
	}

	public void setFallbackChar(char wChar) {
		setFallbackChar((short) wChar);
	}

	public @NotNull JImFontAtlas getContainerAtlas() {
		return new JImFontAtlas(getContainerFontAtlas(nativeObjectPtr));
	}

	public native void setDisplayOffset(float newX, float newY);

	private static native byte[] getDebugName0(long nativeObjectPtr);
	private static native long getContainerFontAtlas(long nativeObjectPtr);
}
