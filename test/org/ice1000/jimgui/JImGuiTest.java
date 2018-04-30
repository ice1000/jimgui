package org.ice1000.jimgui;

import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class JImGuiTest {
	@BeforeClass
	public static void useAlternativeJeniLib() {
		JniLoader.jniLibraryPath = Paths.get("jni", "cmake-build-debug", "libjimgui.so").toAbsolutePath().toString();
	}

	@Test
	public void jniInitialization() throws NoSuchFieldException, IllegalAccessException {
		try (JImGui imGui = new JImGui()) {
			long nativeObjectPtr = (long) JImGui.class.getDeclaredField("nativeObjectPtr").get(imGui);
			assertTrue(nativeObjectPtr != 0);
		}
	}
}