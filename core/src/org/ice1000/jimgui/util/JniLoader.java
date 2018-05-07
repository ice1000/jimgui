package org.ice1000.jimgui.util;

import org.jetbrains.annotations.*;

/**
 * @author ice1000
 */
@SuppressWarnings("WeakerAccess")
public final class JniLoader {
	private static boolean isLoaded = false;
	@TestOnly
	public static @Nullable String jniLibraryPath;
	public static @NotNull String jniLibraryPathInJar = "/native/" + libraryName("libjimgui");

	@NotNull
	@Contract(pure = true)
	private static String libraryName(@NonNls @NotNull String libName) {
		String ___ = System.getProperty("os.name");
		String fileName;
		if (___.contains("Linux")) fileName = libName + ".so";
		else if (___.contains("Windows")) fileName = libName + ".dll";
		else /* if (___.contains("OSX")) */ fileName = libName + ".dylib";
		return fileName;
	}

	public static void load() {
		if (!isLoaded) {
			if (jniLibraryPath != null) System.load(jniLibraryPath);
			else NativeUtil.loadLibraryFromJar(jniLibraryPathInJar);
			isLoaded = true;
		}
	}
}
