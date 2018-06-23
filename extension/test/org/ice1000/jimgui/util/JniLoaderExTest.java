package org.ice1000.jimgui.util;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assume.assumeFalse;

public class JniLoaderExTest {
	@BeforeClass
	public static void setup() {
		assumeFalse("true".equalsIgnoreCase(System.getenv("CI")));
		assumeFalse("true".equalsIgnoreCase(System.getenv("APPVEYOR")));
		assumeFalse("true".equalsIgnoreCase(System.getProperty("java.awt.headless")));
	}

	@Test
	public void testInit() throws InterruptedException {
		Thread hello_world = new Thread(() -> {
			JniLoaderEx.loadGlfw();
			JImGuiUtil.runWithinPer(8000, 15, imGui -> imGui.text("Hello World"));
		});
		hello_world.start();
		hello_world.join();
	}
}