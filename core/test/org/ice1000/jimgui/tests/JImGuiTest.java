package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.nio.file.Paths;

import static org.ice1000.jimgui.util.JniLoader.libraryName;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

public class JImGuiTest {
	@BeforeClass
	@TestOnly
	public static void useAlternativeJeniLibAndCheckHeadless() {
		assumeFalse("true".equals(System.getenv("CI")));
		assumeFalse("true".equals(System.getProperty("java.awt.headless")));
		useAlternativeJeniLib();
	}

	public static void useAlternativeJeniLib() {
		JniLoader.jniLibraryPath = Paths.get("core","jni", "cmake-build-debug", libraryName("libjimgui")).toAbsolutePath().toString();
		JniLoader.load();
	}

	@Test
	public void jniInitialization() throws @NotNull NoSuchFieldException, @NotNull IllegalAccessException {
		try (JImGui imGui = new JImGui()) {
			Field nativeObjectPtr = JImGui.class.getDeclaredField("nativeObjectPtr");
			nativeObjectPtr.setAccessible(true);
			assertTrue((long) nativeObjectPtr.get(imGui) != 0);
			nativeObjectPtr.setAccessible(false);
		}
	}

	@Test(expected = IllegalStateException.class)
	public void jniDisposal() {
		JImGui imGui = new JImGui();
		imGui.close();
		assertTrue(imGui.isDisposed());
		imGui.getIO();
	}
}
