package org.ice1000.jimgui.util;

import org.ice1000.jimgui.JImGui;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongSupplier;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImGuiUtil {
	/** defined in C++ float.h */
	float FLT_MAX = 3.402823466e+38F;

	/**
	 * Run a GUI in a limited time period.
	 *
	 * @param millis   millis seconds to run
	 * @param runnable the task executed in each refreshing
	 */
	@TestOnly
	static void runWithin(long millis, @NotNull Consumer<@NotNull JImGui> runnable) {
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

	static void run(@NotNull Consumer<@NotNull JImGui> runnable) {
		try (JImGui imGui = new JImGui()) {
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();
				runnable.accept(imGui);
				imGui.render();
			}
		}
	}

	static void runPer(long millis, @NotNull Consumer<@NotNull JImGui> runnable) {
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
	static void runWithinPer(long limit, long millis, @NotNull Consumer<@NotNull JImGui> runnable) {
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

	static void runPer(@NotNull LongSupplier millisSupplier, @NotNull Consumer<@NotNull JImGui> runnable) {
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

	/**
	 * Customize your string-to-bytes function.
	 * Because the default implementation is not very efficient.
	 *
	 * @param stringToBytes your conversion function.
	 */
	static void setStringToBytes(@NotNull Function<@Nullable String, byte @Nullable []> stringToBytes) {
		SharedState.STRING_TO_BYTES = stringToBytes;
	}

	@Contract(value = "!null -> !null; null -> null", pure = true)
	static byte @Nullable [] getBytes(@Nullable String text) {
		return SharedState.STRING_TO_BYTES != null ? SharedState.STRING_TO_BYTES.apply(text) : SharedState.getBytesDefaultImpl(text);
	}
}
