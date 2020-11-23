package org.ice1000.jimgui.util;

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

	static int colorU32(int R, int G, int B, int A) {
		return ((A << IM_COL32_A_SHIFT) | (B << IM_COL32_B_SHIFT) | (G << IM_COL32_G_SHIFT) | (R << IM_COL32_R_SHIFT));
	}
}
