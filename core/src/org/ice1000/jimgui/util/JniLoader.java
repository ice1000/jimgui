package org.ice1000.jimgui.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 */
@SuppressWarnings("WeakerAccess")
public final class JniLoader {
	private static boolean isLoaded = false;
	public static @NotNull String jniLibraryPathInJar = "/native/" + System.mapLibraryName("jimgui");

	@NotNull
	@Contract(pure = true)
	public static String libraryName(@NonNls @NotNull String libName) {
		return System.mapLibraryName(libName);
//		String ___ = System.getProperty("os.name");
//		String fileName;
//		if (___.contains("Mac")) fileName = libName + ".dylib";
//		else if (___.startsWith("Windows")) fileName = libName + ".dll";
//		else /* if (___.startsWith("Linux")) */ fileName = libName + ".so";
//		return fileName;
	}

	public static void load() {
		if (!isLoaded) {
			NativeUtil.loadLibraryFromJar(jniLibraryPathInJar);
			isLoaded = true;
		}
	}
}
