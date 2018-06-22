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
		if (JniLoader.Linux) JniLoader.load();
		else NativeUtil.loadLibraryFromJar("TODO", JniLoaderEx.class);
	}
}
