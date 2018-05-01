package org.ice1000.jimgui;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	private @Nullable JImGuiIO io;

	public JImGui() {
		nativeObjectPtr = allocateNativeObjects();
		io = new JImGuiIO();
		background = new JImVec4(1.0f, 0.55f, 0.60f, 1.00f);
	}

	@Override
	public void close() {
		deallocateNativeObjects(nativeObjectPtr);
		io = null;
	}

	public void demoMainLoop() {
		if (io == null) alreadyDisposed();
		demoMainLoop(background.nativeObjectPtr);
	}

	/**
	 * Create {@link java.awt.Label} like text label
	 *
	 * @param text the text to display
	 */
	public void text(@NotNull String text) {
		if (io == null) alreadyDisposed();
		text(text.getBytes(StandardCharsets.UTF_8));
	}

	public void sameLine() {
		sameLine(0, -1);
	}

	public native void sameLine(float posX, float spacingW);

	/**
	 * Create {@link java.awt.Button} like text button
	 *
	 * @param text the text to display
	 * @return true if clicked
	 */
	public boolean button(@NotNull String text) {
		if (io == null) alreadyDisposed();
		return button(text.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Create {@link java.awt.Button} like text button
	 *
	 * @param text   the text to display
	 * @param height button height
	 * @param width  button width
	 * @return true if clicked
	 */
	public boolean button(@NotNull String text, float width, float height) {
		if (io == null) alreadyDisposed();
		return button(text.getBytes(StandardCharsets.UTF_8), width, height);
	}

	public @Nullable JImGuiIO tryGetIO() {
		return io;
	}

	public @NotNull JImGuiIO getIO() {
		if (io == null) alreadyDisposed();
		return io;
	}

	public boolean windowShouldClose() {
		if (io == null) alreadyDisposed();
		return windowShouldClose(nativeObjectPtr);
	}

	@Contract(" -> fail")
	private void alreadyDisposed() {
		throw new IllegalStateException("Window already disposed.");
	}

	public void render() {
		if (io == null) alreadyDisposed();
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

	private static native boolean button(byte[] text);

	private static native boolean button(byte[] text, float width, float height);
}
