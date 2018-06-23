package org.ice1000.jimgui.glfw;

import org.jetbrains.annotations.NotNull;

import static org.ice1000.jimgui.util.JImGuiUtil.getBytes;

/**
 * Only work when using glfw
 *
 * @author ice1000
 * @since v0.5
 */
public class GlfwUtil {
	/**
	 * Will not initialize GLFW
	 *
	 * @return newly created window pointer, 0 if it's not windows
	 * @see org.ice1000.jimgui.JImGui#fromExistingPointer(long)
	 */
	public static long createWindowPointer(int w, int h, @NotNull String title) {
		return createWindowPointer0(w, h, getBytes(title));
	}

	public static native long createWindowPointer0(int w, int h, byte[] title);
}
