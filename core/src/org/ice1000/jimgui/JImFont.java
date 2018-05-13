package org.ice1000.jimgui;

import org.ice1000.jimgui.flag.JImFontAtlasFlags;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImFont extends JImGuiFontGen {
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

	public void setFallbackChar(short wChar) {
		setFallbackChar(nativeObjectPtr, wChar);
	}

	public boolean isLoaded() {
		return isLoaded(nativeObjectPtr);
	}

	public void buildLookupTable() {
		buildLookupTable(nativeObjectPtr);
	}

	public void clearOutputData() {
		clearOutputData(nativeObjectPtr);
	}

	public @MagicConstant(flagsFromClass = JImFontAtlasFlags.class)
	int getFontAtlasFlags() {
		return getFontAtlasFlags(nativeObjectPtr);
	}

	public void setFontAtlasFlags(
			@MagicConstant(flagsFromClass = JImFontAtlasFlags.class) int flags) {
		setFontAtlasFlags(nativeObjectPtr, flags);
	}

	public native void setDisplayOffset(float newX, float newY);

	private static native void setFontAtlasFlags(
			long nativeObjectPtr,
			@MagicConstant(flagsFromClass = JImFontAtlasFlags.class) int flags);
	private static native byte[] getDebugName0(long nativeObjectPtr);
	private static native @MagicConstant(flagsFromClass = JImFontAtlasFlags.class)
	int getFontAtlasFlags(long nativeObjectPtr);
}
