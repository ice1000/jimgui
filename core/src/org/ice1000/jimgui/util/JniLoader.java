package org.ice1000.jimgui.util;

/**
 * @author ice1000
 */
@SuppressWarnings("WeakerAccess")
public final class JniLoader {
	public static final String osName = System.getProperty("os.name");
	public static final String architectureName = System.getProperty("os.arch");
	public static final boolean x86 = "x86".equals(architectureName);
	public static final boolean linux = "Linux".equals(osName);
	// I want to use osName.startsWith("Windows 9")...
	public static final boolean windows95 = "Windows 95".equals(osName);
	public static final boolean windows98 = "Windows 98".equals(osName);
	public static final boolean windows200X = osName.startsWith("Windows 200");
	public static final boolean windowsXP = "Windows XP".equals(osName);
	public static final boolean windowsVista = "Windows Vista".equals(osName);
	public static final boolean windows7 = "Windows 7".equals(osName)
			|| osName.startsWith("Windows Server 2008");
	public static final boolean windows8 = "Windows 8".equals(osName)
			|| "Windows 8.1".equals(osName)
			|| osName.startsWith("Windows Server 2012");
	public static final boolean windows10 = "Windows 10".equals(osName)
			|| osName.startsWith("Windows Server 2016");
	public static final boolean osx = "Mac OS X".equals(osName);
	private static boolean isLoaded = false;

	public static void load() {
		if (isLoaded) return;
		String libraryName;
		// Supported OS
		if (linux) libraryName = x86 ? "libjimgui32.so" : "libjimgui.so";
		else if (windowsVista || windowsXP || windows7 || windows8 || windows10) libraryName = x86 ? "jimgui32.dll" : "jimgui.dll";
		else if (osx) libraryName = "libjimgui.dylib";
			// Unsupported OS
		else if (windows98 || windows95 || windows200X)
			throw new UnsupportedOperationException("Windows 98/95/2000/2003 are not supported and won't be supported.");
		else throw new UnsupportedOperationException("Unknown OS " + osName +
					", please submit issue to https://github.com/ice1000/jimgui/issues");
		NativeUtil.loadLibraryFromJar(libraryName, NativeUtil.class);
		isLoaded = true;
	}
}
