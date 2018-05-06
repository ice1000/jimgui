package org.ice1000.jimgui.util;

import org.ice1000.jimgui.JImGui;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.function.LongSupplier;

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
	public static void runWithin(long millis, @NotNull Consumer<@NotNull JImGui> runnable) {
		try (JImGui imGui = new JImGui()) {
			int i = 0;
			while (!imGui.windowShouldClose() && i++ < millis) {
				imGui.initNewFrame();
				runnable.accept(imGui);
				imGui.render();
			}
		}
	}

	public static void run(@NotNull Consumer<@NotNull JImGui> runnable) {
		try (JImGui imGui = new JImGui()) {
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();
				runnable.accept(imGui);
				imGui.render();
			}
		}
	}

	public static void runPer(long millis, @NotNull Consumer<@NotNull JImGui> runnable) {
		try (JImGui imGui = new JImGui()) {
			long latestRefresh = System.currentTimeMillis();
			while (!imGui.windowShouldClose()) {
				long currentTimeMillis = System.currentTimeMillis();
				if (currentTimeMillis - latestRefresh > millis) {
					imGui.initNewFrame();
					runnable.accept(imGui);
					imGui.render();
					latestRefresh = currentTimeMillis;
				}
			}
		}
	}

	public static void runPer(@NotNull LongSupplier millisSupplier, @NotNull Consumer<@NotNull JImGui> runnable) {
		try (JImGui imGui = new JImGui()) {
			long latestRefresh = System.currentTimeMillis();
			long millis = millisSupplier.getAsLong();
			while (!imGui.windowShouldClose()) {
				long currentTimeMillis = System.currentTimeMillis();
				if (currentTimeMillis - latestRefresh > millis) {
					imGui.initNewFrame();
					runnable.accept(imGui);
					imGui.render();
					latestRefresh = currentTimeMillis;
					millis = millisSupplier.getAsLong();
				}
			}
		}
	}

	@Contract(pure = true)
	public static @NotNull byte[] getBytes(@NotNull String text) {
		return (text + '\0').getBytes(StandardCharsets.UTF_8);
	}
}
