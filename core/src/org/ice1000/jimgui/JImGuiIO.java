package org.ice1000.jimgui;

import org.ice1000.jimgui.flag.JImMouseButton;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.1
 */
public final class JImGuiIO extends JImGuiIOGen {
	/** package-private by design */
	JImGuiIO() {
	}

	public native float getMouseClickedPosX(@MagicConstant(valuesFromClass = JImMouseButton.class) int index);
	public native float getMouseClickedPosY(@MagicConstant(valuesFromClass = JImMouseButton.class) int index);
	public native float getMouseDragMaxDistanceAbsX(@MagicConstant(valuesFromClass = JImMouseButton.class) int index);
	public native float getMouseDragMaxDistanceAbsY(@MagicConstant(valuesFromClass = JImMouseButton.class) int index);

	public @NotNull String getInputString() {
		return new String(getInputChars());
	}

	public void addInputCharacter(char character) {
		addInputCharacter((int) character);
	}

	public @NotNull JImFontAtlas getFonts() {
		return new JImFontAtlas(getFonts0());
	}

	public @NotNull JImFont getFontDefault() {
		return new JImFont(getFontDefault0());
	}

	public native char @NotNull [] getInputChars();
	private static native long getFonts0();
	private static native long getFontDefault0();
}
