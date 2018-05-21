package org.ice1000.jimgui.util;

/**
 * @author ice1000
 */
@SuppressWarnings("WeakerAccess")
public final class JniLoader {
	private static boolean isLoaded = false;

	public static void load() {
		if (!isLoaded) {
			String suffix;
			String osName = System.getProperty("os.name");
			String architectureName = System.getProperty("os.arch");
			boolean x86 = "x86".equals(architectureName);
			boolean linux = "Linux".equals(osName);
			boolean windows95 = "Windows 95".equals(osName);
			boolean windows98 = "Windows 98".equals(osName);
			boolean windows2000 = "Windows 2000".equals(osName);
			boolean windows2003 = "Windows 2003".equals(osName);
			boolean windowsXP = "Windows XP".equals(osName);
			boolean windowsVista = "Windows Vista".equals(osName);
			boolean windows7 = "Windows 7".equals(osName);
			boolean windows8 = "Windows 8".equals(osName) || "Windows 8.1".equals(osName);
			boolean windows10 = "Windows 10".equals(osName);
			boolean osx = "Mac OS X".equals(osName);
			if (windows98 || windows95 || windows2000 || windows2003)
				throw new UnsupportedOperationException("Windows 98/95/2000/2003 are not supported and won't be supported");
			else if (windowsXP)
				throw new UnsupportedOperationException(
						"Windows XP required DirectX9 implementation of imgui, which is not available yet.");
			else if (windowsVista)
				throw new UnsupportedOperationException(
						"Windows Vista required DirectX10 implementation of imgui, which is not available yet.");
			else if (linux || windows7 || windows8 || windows10 || osx) suffix = x86 ? "32" : "";
			else throw new UnsupportedOperationException("Unknown OS " + osName +
						", please submit issue to https://github.com/ice1000/jimgui/issues");
			NativeUtil.loadLibraryFromJar(System.mapLibraryName("jimgui" + suffix));
			isLoaded = true;
		}
	}
}
