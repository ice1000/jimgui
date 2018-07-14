package org.ice1000.jimgui.util;

import org.ice1000.jimgui.JImGui;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.function.LongSupplier;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImGuiUtil {
	/** defined in C++ float.h */
	public static final float FLT_MAX = 3.402823466e+38F;

	/**
	 * Run a GUI in a limited time period.
	 *
	 * @param millis   millis seconds to run
	 * @param runnable the task executed in each refreshing
	 */
	@TestOnly
	public static void runWithin(long millis, @NotNull Consumer<@NotNull JImGui> runnable) {
		try (JImGui imGui = new JImGui()) {
			long end = System.currentTimeMillis() + millis;
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose() && System.currentTimeMillis() < end) {
				imGui.initNewFrame();
				runnable.accept(imGui);
				imGui.render();
			}
		}
	}

	public static void run(@NotNull Consumer<@NotNull JImGui> runnable) {
		try (JImGui imGui = new JImGui()) {
			imGui.initBeforeMainLoop();
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
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				long currentTimeMillis = System.currentTimeMillis();
				long deltaTime = currentTimeMillis - latestRefresh;
				Thread.sleep(deltaTime / 2);
				if (deltaTime > millis) {
					imGui.initNewFrame();
					runnable.accept(imGui);
					imGui.render();
					latestRefresh = currentTimeMillis;
				}
			}
		} catch (@NotNull InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@TestOnly
	public static void runWithinPer(long limit, long millis, @NotNull Consumer<@NotNull JImGui> runnable) {
		try (JImGui imGui = new JImGui()) {
			long latestRefresh = System.currentTimeMillis();
			long end = System.currentTimeMillis() + limit;
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose() && System.currentTimeMillis() < end) {
				long currentTimeMillis = System.currentTimeMillis();
				long deltaTime = currentTimeMillis - latestRefresh;
				Thread.sleep(deltaTime * 2 / 3);
				if (deltaTime > millis) {
					imGui.initNewFrame();
					runnable.accept(imGui);
					imGui.render();
					latestRefresh = currentTimeMillis;
				}
			}
		} catch (@NotNull InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void runPer(@NotNull LongSupplier millisSupplier, @NotNull Consumer<@NotNull JImGui> runnable) {
		try (JImGui imGui = new JImGui()) {
			long latestRefresh = System.currentTimeMillis();
			long millis = millisSupplier.getAsLong();
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				long currentTimeMillis = System.currentTimeMillis();
				long deltaTime = currentTimeMillis - latestRefresh;
				Thread.sleep(deltaTime / 2);
				if (deltaTime > millis) {
					imGui.initNewFrame();
					runnable.accept(imGui);
					imGui.render();
					latestRefresh = currentTimeMillis;
					millis = millisSupplier.getAsLong();
				}
			}
		} catch (@NotNull InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Contract(value = "!null -> !null; null -> null", pure = true)
	public static @Nullable byte[] getBytes(@Nullable String text) {
		return text != null ? (text + '\0').getBytes(StandardCharsets.UTF_8) : null;
	}
}
