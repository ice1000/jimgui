package org.ice1000.jimgui.util;

/**
 * @author ice1000
 * @since v0.6
 */
public final class JniLoaderEx {
	private JniLoaderEx() {
	}

	/**
	 * Force loading GLFW
	 */
	public static void loadGlfw() {
		if (JniLoader.Linux || JniLoader.OSX) JniLoader.load();
		else if (JniLoader.X86)
			// TODO
			throw new UnsupportedOperationException("Windows X86 GLFW backend is unavailable yet.");
		else NativeUtil.loadLibraryFromJar("jimgui-glfw.dll", JniLoaderEx.class);
	}

	public static void loadDirectX9() {
		if (JniLoader.Linux || JniLoader.OSX)
			throw new UnsupportedOperationException("DirectX9 is not supported on " + JniLoader.OsName + ".");
		else JniLoader.load();
	}
}
