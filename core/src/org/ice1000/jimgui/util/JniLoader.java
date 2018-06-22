package org.ice1000.jimgui.util;

/**
 * @author ice1000
 */
@SuppressWarnings("WeakerAccess")
public final class JniLoader {
	public static final String OsName = System.getProperty("os.name");
	public static final String ArchitectureName = System.getProperty("os.arch");
	public static final boolean X86 = "x86".equals(ArchitectureName);
	public static final boolean Linux = "Linux".equals(OsName);
	// I want to use osName.startsWith("Windows 9")...
	public static final boolean Windows95 = "Windows 95".equals(OsName);
	public static final boolean Windows98 = "Windows 98".equals(OsName);
	public static final boolean Windows200X = OsName.startsWith("Windows 200");
	public static final boolean WindowsXP = "Windows XP".equals(OsName);
	public static final boolean WindowsVista = "Windows Vista".equals(OsName);
	public static final boolean Windows7 = "Windows 7".equals(OsName)
			|| OsName.startsWith("Windows Server 2008");
	public static final boolean Windows8 = "Windows 8".equals(OsName)
			|| "Windows 8.1".equals(OsName)
			|| OsName.startsWith("Windows Server 2012");
	public static final boolean Windows10 = "Windows 10".equals(OsName)
			|| OsName.startsWith("Windows Server 2016");
	public static final boolean OSX = "Mac OS X".equals(OsName);
	private static boolean isLoaded = false;

	public static void load() {
		if (isLoaded) return;
		String libraryName;
		// Supported OS
		if (Linux) libraryName = X86 ? "libjimgui32.so" : "libjimgui.so";
		else if (WindowsVista || WindowsXP || Windows7 || Windows8 || Windows10) libraryName = X86 ? "jimgui32.dll" : "jimgui.dll";
		else if (OSX) libraryName = "libjimgui.dylib";
			// Unsupported OS
		else if (Windows98 || Windows95 || Windows200X)
			throw new UnsupportedOperationException("Windows 98/95/2000/2003 are not supported and won't be supported.");
		else throw new UnsupportedOperationException("Unknown OS " + OsName +
					", please submit issue to https://github.com/ice1000/jimgui/issues");
		NativeUtil.loadLibraryFromJar(libraryName, NativeUtil.class);
		isLoaded = true;
	}
}
