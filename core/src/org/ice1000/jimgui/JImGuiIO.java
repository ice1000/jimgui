package org.ice1000.jimgui;

import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImGuiIO extends JImGuiIOGen {
	public native float getMousePosX();
	public native float getMousePosY();
	public @NotNull String getInputCharacters() {
		return new String(getInputCharacters0());
	}
	public void addInputCharacter(char character) {
		addInputCharacter((short) character);
	}

	private native byte[] getInputCharacters0();
}
