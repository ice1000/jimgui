package org.ice1000.jimgui.util;

import org.ice1000.jimgui.JImGui;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImGuiUtil {
	/**
	 * Run a GUI in a limited time period.
	 *
	 * @param millis   millis seconds to run
	 * @param runnable the task executed in each refreshing
	 */
	public static void runWithin(long millis, @NotNull Consumer<JImGui> runnable) {
		try (JImGui imGui = new JImGui()) {
			int i = 0;
			while (!imGui.windowShouldClose() && i++ < millis) {
				imGui.initNewFrame();
				runnable.accept(imGui);
				imGui.render();
			}
		}
	}

	public static void run(@NotNull Consumer<JImGui> runnable) {
		try (JImGui imGui = new JImGui()) {
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();
				runnable.accept(imGui);
				imGui.render();
			}
		}
	}
}
