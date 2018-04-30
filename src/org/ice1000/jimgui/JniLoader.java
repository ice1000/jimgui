package org.ice1000.jimgui;

import java.nio.file.Paths;

/**
 * @author ice1000
 */
@SuppressWarnings("WeakerAccess")
public final class JniLoader {
	private static boolean isLoaded = false;
	public static String jniLibraryPath = Paths.get("libjimgui.so").toAbsolutePath().toString();

	public static void load() {
		if (!isLoaded) {
			System.load(jniLibraryPath);
			isLoaded = true;
		}
	}
}
