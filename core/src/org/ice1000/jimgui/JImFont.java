package org.ice1000.jimgui;

import org.ice1000.jimgui.flag.JImFontAtlasFlags;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImFont extends JImGuiFontGen {
	/** package-private by design */
	JImFont() {
	}

	public @NotNull String getDebugName() {
		return new String(getDebugName0());
	}

	public void setFallbackChar(char wChar) {
		setFallbackChar((short) wChar);
	}

	public native @MagicConstant(flagsFromClass = JImFontAtlasFlags.class)
	int getFontAtlasFlags();
	public native void setFontAtlasFlags(
			@MagicConstant(flagsFromClass = JImFontAtlasFlags.class) int flags);

	public native void setDisplayOffset(float newX, float newY);
	private static native byte[] getDebugName0();
}
