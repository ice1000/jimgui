package org.ice1000.jimgui.util;

import org.jetbrains.annotations.Contract;

/**
 * @author ice1000
 * @since v0.12
 */
public interface ColorUtil {
	int IM_COL32_R_SHIFT = 0;
	int IM_COL32_G_SHIFT = 8;
	int IM_COL32_B_SHIFT = 16;
	int IM_COL32_A_SHIFT = 24;
	int IM_COL32_A_MASK = 0xFF000000;

	@Contract(pure = true)
	static int colorU32(int red, int green, int blue, int alpha) {
		return alpha << IM_COL32_A_SHIFT
				| blue << IM_COL32_B_SHIFT
				| green << IM_COL32_G_SHIFT
				| red << IM_COL32_R_SHIFT;
	}
}
