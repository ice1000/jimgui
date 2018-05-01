package org.ice1000.jimgui;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.nio.charset.StandardCharsets;

/**
 * @author ice1000
 * @since v0.1
 */
@SuppressWarnings("WeakerAccess")
public class JImGui implements AutoCloseable, Closeable {
	/** package-private by design */
	long nativeObjectPtr;
	private @NotNull JImVec4 background;

	public JImGui() {
		nativeObjectPtr = allocateNativeObjects();
		background = new JImVec4(1.0f, 0.55f, 0.60f, 1.00f);
	}

	@Override
	public void close() {
		deallocateNativeObjects(nativeObjectPtr);
	}

	public void demoMainLoop() {
		demoMainLoop(background.nativeObjectPtr);
	}

	/**
	 * Create {@link java.awt.Label} like text label
	 *
	 * @param text the text to display
	 */
	public void text(@NotNull String text) {
		text(text.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Create {@link java.awt.Button} like text button
	 *
	 * @param text the text to display
	 */
	public void button(@NotNull String text) {
		button(text.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Create {@link java.awt.Button} like text button
	 *
	 * @param text   the text to display
	 * @param height button height
	 * @param width  button width
	 */
	public void button(@NotNull String text, float width, float height) {
		button(text.getBytes(StandardCharsets.UTF_8), width, height);
	}

	public boolean windowShouldClose() {
		return windowShouldClose(nativeObjectPtr);
	}

	public void render() {
		render(nativeObjectPtr, background.nativeObjectPtr);
	}

	public native void initNewFrame();

	/** @return see {@link JImGui#nativeObjectPtr} */
	private static native long allocateNativeObjects();

	/** @param nativeObjectPtr see {@link JImGui#nativeObjectPtr} */
	private static native void deallocateNativeObjects(long nativeObjectPtr);

	private static native void demoMainLoop(long colorPtr);

	private static native boolean windowShouldClose(long nativeObjectPtr);

	private static native void render(long nativeObjectPtr, long colorPtr);

	private static native void text(byte[] text);

	private static native void button(byte[] text);

	private static native void button(byte[] text, float width, float height);
}
