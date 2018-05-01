package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;

import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class Sandbox {
	public static void main(String[] args) {
		JniLoader.jniLibraryPath = Paths.get("jni", "cmake-build-debug", "libjimgui.so").toAbsolutePath().toString();
		JniLoader.load();
		AtomicInteger count = new AtomicInteger();
		JImGuiUtil.run(imGui -> {
			if (imGui.button("Click me!")) count.getAndIncrement();
			imGui.sameLine();
			imGui.text("Click count: " + count);
			imGui.text("fps: " + imGui.getIO().getFramerate());
		});
	}
}
