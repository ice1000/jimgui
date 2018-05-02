package org.ice1000.jimgui;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;

import static org.ice1000.jimgui.util.JImGuiUtil.getBytes;

/**
 * @author ice1000
 * @since v0.1
 */
@SuppressWarnings("WeakerAccess")
public class JImGui implements AutoCloseable, Closeable {
	/** package-private by design， */
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
		background.close();
		deallocateNativeObjects(nativeObjectPtr);
		io = null;
	}

	public void demoMainLoop() {
		demoMainLoop(background.nativeObjectPtr);
	}

	public void text(@NotNull String text) {
		text(getBytes(text));
	}

	public void bulletText(@NotNull String text) {
		bulletText(getBytes(text));
	}

	public void labelText(@NotNull String label, @NotNull String text) {
		labelText(getBytes(label), getBytes(text));
	}

	public void textDisabled(@NotNull String text) {
		textDisabled(getBytes(text));
	}

	public void textWrapped(@NotNull String text) {
		textWrapped(getBytes(text));
	}

	public void text(@NotNull JImVec4 color, @NotNull String text) {
		textColored(color.nativeObjectPtr, getBytes(text));
	}

	public void sameLine() {
		sameLine(0, -1);
	}

	public boolean button(@NotNull String text) {
		return button(getBytes(text));
	}

	/**
	 * Create {@link java.awt.Button} like text button
	 *
	 * @param text the text to display
	 * @return true if clicked
	 */
	public boolean smallButton(@NotNull String text) {
		return smallButton(getBytes(text));
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
		return button(getBytes(text), width, height);
	}

	public @Nullable JImGuiIO tryGetIO() {
		return io;
	}

	public @NotNull JImGuiIO getIO() {
		if (null == io) alreadyDisposed();
		return io;
	}

	@Contract(pure = true)
	public boolean isDisposed() {
		return io == null;
	}

	/** @return shouldn't be closed, will close automatically */
	public @NotNull JImVec4 getBackground() {
		return background;
	}

	/** @param background shouldn't be closed, will close automatically */
	public void setBackground(@NotNull JImVec4 background) {
		this.background.close();
		this.background = background;
	}

	public boolean windowShouldClose() {
		return windowShouldClose(nativeObjectPtr);
	}

	@Contract(" -> fail")
	private void alreadyDisposed() {
		throw new IllegalStateException("Window already disposed.");
	}

	public void render() {
		render(nativeObjectPtr, background.nativeObjectPtr);
	}

	//region Trivial native functions
	public native void sameLine(float posX, float spacingW);
	public native void separator();
	public native void newLine();
	public native void spacing();
	public native void beginGroup();
	public native void endGroup();
	public native void bullet();
	public native void initNewFrame();
	public native float getTextLineHeight();
	public native float getTextLineHeightWithSpacing();
	public native float getFrameHeight();
	public native float getFrameHeightWithSpacing();
	public native float getCursorPosX();
	public native float getCursorPosY();
	public native void setCursorPos(float newX, float newY);
	public native void setCursorPosX(float newValue);
	public native void setCursorPosY(float newValue);
	//endregion

	//region Private native interfaces
	private static native long allocateNativeObjects();
	private static native void deallocateNativeObjects(long nativeObjectPtr);
	private static native void demoMainLoop(long colorPtr);
	private static native boolean windowShouldClose(long nativeObjectPtr);
	private static native void render(long nativeObjectPtr, long colorPtr);
	private static native void text(byte[] text);
	private static native void bulletText(byte[] text);
	private static native void labelText(byte[] label, byte[] text);
	private static native void textDisabled(byte[] text);
	private static native void textWrapped(byte[] text);
	private static native void textColored(long colorPtr, byte[] text);
	private static native boolean button(byte[] text);
	private static native boolean smallButton(byte[] text);
	private static native boolean button(byte[] text, float width, float height);
	//endregion
}
