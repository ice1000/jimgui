package org.ice1000.jimgui;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class JImDrawList extends JImGuiDrawListGen {
	/**
	 * package-private by design
	 *
	 * @param nativeObjectPtr native pointer ImDrawList *
	 */
	@Contract
	JImDrawList(long nativeObjectPtr) {
		super(nativeObjectPtr);
	}

	public void addText(float posX, float posY, int u32Color, @NotNull String text) {
		addText(0, posX, posY, u32Color, text);
	}

	public void addText(float fontSize, float posX, float posY, int u32Color, @NotNull String text) {
		addText(0, fontSize, posX, posY, u32Color, text.getBytes(StandardCharsets.UTF_8), 0, 0, nativeObjectPtr);
	}
}
