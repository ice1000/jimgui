package org.ice1000.jimgui.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author ice1000
 */
@SuppressWarnings("WeakerAccess")
public final class JniLoader {
	private static boolean isLoaded = false;
	public static @Nullable String jniLibraryPath;
	public static @NotNull String jniLibraryPathInJar = "/";

	public static void load() {
		if (!isLoaded) {
			if (jniLibraryPath != null) System.load(jniLibraryPath);
			else NativeUtils.loadLibraryFromJar(jniLibraryPathInJar);
			isLoaded = true;
		}
	}
}
