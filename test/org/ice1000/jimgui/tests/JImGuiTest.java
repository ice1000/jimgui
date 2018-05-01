package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class JImGuiTest {
	@BeforeClass
	public static void useAlternativeJeniLib() {
		JniLoader.jniLibraryPath = Paths.get("jni", "cmake-build-debug", "libjimgui.so").toAbsolutePath().toString();
		JniLoader.load();
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
			int i = 0;
			while (!imGui.windowShouldClose() && i++ < 1000) {
				JImGui.initNewFrame();
				imGui.demoMainLoop();
				imGui.render();
			}
		}
	}
}