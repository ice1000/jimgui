package org.ice1000.jimgui.glfw;

import org.ice1000.jimgui.JImGui;
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
	public static long createWindowPointer() {
		return createWindowPointer(1280, 720, JImGui.DEFAULT_TITLE, 0);
	}

	/**
	 * Should be called before {@link JImGui#fromExistingPointer(long)}
	 *
	 * @return opengl version, usually useless
	 */
	public static native int gl3wInit();

	/**
	 * Will not initialize GLFW
	 *
	 * @param anotherWindow the pointer to the former-created window
	 * @return newly created window pointer, 0 if it's not windows
	 * @see org.ice1000.jimgui.JImGui#fromExistingPointer(long)
	 */
	public static long createWindowPointer(long anotherWindow) {
		return createWindowPointer(1280, 720, JImGui.DEFAULT_TITLE, anotherWindow);
	}

	/**
	 * Will not initialize GLFW
	 *
	 * @param width  window width
	 * @param height window height
	 * @return newly created window pointer, 0 if it's not windows
	 * @see org.ice1000.jimgui.JImGui#fromExistingPointer(long)
	 */
	public static long createWindowPointer(int width, int height, @NotNull String title) {
		return createWindowPointer(width, height, title, 0);
	}

	/**
	 * With another already created GLFW window
	 *
	 * @param anotherWindow the pointer to the former-created window
	 * @param width         window width
	 * @param height        window height
	 * @return newly created window pointer, 0 if it's not windows
	 * @see org.ice1000.jimgui.JImGui#fromExistingPointer(long)
	 */
	public static long createWindowPointer(int width, int height, @NotNull String title, long anotherWindow) {
		return createWindowPointer0(width, height, getBytes(title), anotherWindow);
	}

	private static native long createWindowPointer0(int w, int h, byte[] title, long anotherWindow);
}
