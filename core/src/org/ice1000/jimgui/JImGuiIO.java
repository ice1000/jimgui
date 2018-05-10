package org.ice1000.jimgui;

import org.ice1000.jimgui.flags.JImMouseIndexes;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImGuiIO extends JImGuiIOGen {
	public native float getMousePosX();
	public native float getMousePosY();
	public native float getMouseDeltaX();
	public native float getMouseDeltaY();
	public native float getMousePosPrevX();
	public native float getMousePosPrevY();
	public native float getDisplayFramebufferScaleX();
	public native float getDisplayFramebufferScaleY();
	public native float getDisplayVisibleMinX();
	public native float getDisplayVisibleMinY();
	public native float getDisplayVisibleMaxX();
	public native float getDisplayVisibleMaxY();
	public native float getDisplaySizeX();
	public native float getDisplaySizeY();
	public native float getMouseClickedPosX(@MagicConstant(valuesFromClass = JImMouseIndexes.class) int index);
	public native float getMouseClickedPosY(@MagicConstant(valuesFromClass = JImMouseIndexes.class) int index);
	public native float getMouseDragMaxDistanceAbsX(@MagicConstant(valuesFromClass = JImMouseIndexes.class) int index);
	public native float getMouseDragMaxDistanceAbsClickedPosY(@MagicConstant(valuesFromClass = JImMouseIndexes.class) int index);
	public @NotNull String getInputString() {
		return new String(getInputString0());
	}
	public void addInputCharacter(char character) {
		addInputCharacter((short) character);
	}

	private native byte[] getInputString0();
}
