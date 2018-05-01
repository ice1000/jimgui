package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.function.Consumer;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

public class JImGuiTest {
	@BeforeClass
	public static void useAlternativeJeniLib() {
		// prevent auto-run
		// noinspection ConstantConditions
		assumeFalse(true);
		JniLoader.jniLibraryPath = Paths.get("jni", "cmake-build-debug", "libjimgui.so").toAbsolutePath().toString();
		JniLoader.load();
	}

	public static void testWithin(long millis, @NotNull Consumer<JImGui> runnable) {
		try (JImGui imGui = new JImGui()) {
			int i = 0;
			while (!imGui.windowShouldClose() && i++ < millis) {
				imGui.initNewFrame();
				runnable.accept(imGui);
				imGui.render();
			}
		}
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
		testWithin(1000, JImGui::demoMainLoop);
	}

	@Test
	public void text() {
		testWithin(30000, jImGui -> jImGui.text("Boy next door"));
	}

	@Test
	public void button() {
		testWithin(30000, jImGui -> jImGui.button("Boy next door"));
	}


	@Test
	public void sizedButton() {
		testWithin(30000, jImGui -> jImGui.button("Boy next door", 100, 100));
	}

	@Test
	public void textUnicode() {
		testWithin(30000, jImGui -> jImGui.text("Boy\u2642next\u26a8door\n就是邻\u26a2家男\u26a3孩"));
	}
}