package org.ice1000.jimgui;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImGuiFont extends JImGuiFontGen {
	JImGuiFont() {
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

	public native void setDisplayOffsetX(float newX);
	public native void setDisplayOffsetY(float newY);
	public native void setDisplayOffset(float newX, float newY);
	public native float getDisplayOffsetX();
	public native float getDisplayOffsetY();
	private static native byte[] getDebugName0();
}
