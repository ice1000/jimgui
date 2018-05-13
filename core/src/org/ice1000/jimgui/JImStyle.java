package org.ice1000.jimgui;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImStyle extends JImGuiStyleGen {
	/** package-private by design */
	JImStyle() {
	}

	public @NotNull JImVec4 getColor(@MagicConstant(valuesFromClass = JImStyleColors.class) int index) {
		return new JImVec4(getColor0(index));
	}

	private static native long getColor0(@MagicConstant(valuesFromClass = JImStyleColors.class) int index);
}
