package org.ice1000.jimgui;

import org.ice1000.jimgui.flag.JImMouseIndexes;
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

	public native float getMouseClickedPosX(@MagicConstant(valuesFromClass = JImMouseIndexes.class) int index);
	public native float getMouseClickedPosY(@MagicConstant(valuesFromClass = JImMouseIndexes.class) int index);
	public native float getMouseDragMaxDistanceAbsX(@MagicConstant(valuesFromClass = JImMouseIndexes.class) int index);
	public native float getMouseDragMaxDistanceAbsY(@MagicConstant(valuesFromClass = JImMouseIndexes.class) int index);
	public @NotNull String getInputString() {
		return new String(getInputString0());
	}
	public void addInputCharacter(char character) {
		addInputCharacter((short) character);
	}

	private native byte @NotNull [] getInputString0();
}
