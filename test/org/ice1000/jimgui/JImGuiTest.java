package org.ice1000.jimgui;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
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
			Field nativeObjectPtr = JImGui.class.getDeclaredField("nativeObjectPtr");
			nativeObjectPtr.setAccessible(true);
			assertTrue((long) nativeObjectPtr.get(imGui) != 0);
			nativeObjectPtr.setAccessible(false);
		}
	}

	@Test
	public void demoMainLoop() {
		try (JImGui imGui = new JImGui()) {
			while (!imGui.windowShouldClose()) {
				JImGui.initNewFrame();
				imGui.demoMainLoop();
				imGui.render();
			}
		}
	}
}