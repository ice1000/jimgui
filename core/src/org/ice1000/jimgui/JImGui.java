package org.ice1000.jimgui;

import org.intellij.lang.annotations.MagicConstant;
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
public class JImGui extends JImGuiGen implements AutoCloseable, Closeable {
	/**
	 * package-private by design，
	 */
	long nativeObjectPtr;
	private @NotNull JImVec4 background;
	private @Nullable JImGuiIO io;
	private @Nullable JImGuiFont font;

	//region Native-unrelated
	public JImGui() {
		this(1280, 720);
	}

	public JImGui(int width, int height) {
		this(width, height, "ImGui window created by JImGui");
	}

	public JImGui(int width, int height, @NotNull String title) {
		nativeObjectPtr = allocateNativeObjects(width, height, getBytes(title));
		if (nativeObjectPtr == 0) {
			System.err.println("Unknown error has happened during initialization!");
			System.exit(1);
		}
		io = new JImGuiIO();
		font = new JImGuiFont();
		background = new JImVec4(1.0f, 0.55f, 0.60f, 1.00f);
	}

	@Override
	public void close() {
		background.close();
		deallocateNativeObjects(nativeObjectPtr);
		io = null;
	}

	/**
	 * @param background shouldn't be closed, will close automatically
	 */
	public void setBackground(@NotNull JImVec4 background) {
		this.background.close();
		this.background = background;
	}

	@Contract(" -> fail")
	private void alreadyDisposed() {
		throw new IllegalStateException("Window already disposed.");
	}

	/**
	 * Call this only if you expect a nullable result.
	 *
	 * @return same as {@link #getIO()}
	 */
	public @Nullable JImGuiIO findIO() {
		return io;
	}

	public @NotNull JImGuiIO getIO() {
		if (null == io) alreadyDisposed();
		return io;
	}

	/**
	 * Call this only if you expect a nullable result.
	 *
	 * @return same as {@link #getFont()}
	 */
	public @Nullable JImGuiFont findFont() {
		return font;
	}

	public @NotNull JImGuiFont getFont() {
		if (null == font) alreadyDisposed();
		return font;
	}

	@Contract(pure = true)
	public boolean isDisposed() {
		return io == null;
	}

	/**
	 * @return shouldn't be closed, will close automatically
	 */
	public @NotNull JImVec4 getBackground() {
		return background;
	}
	//endregion

	public void begin(@NotNull String name, int flags) {
		begin(getBytes(name), flags);
	}

	public void begin(@NotNull String name) {
		begin(name, 0);
	}

	/** draw plain text */
	public void text(@NotNull String text) {
		textUnformatted(getBytes(text));
	}

	public void pushStyleVar(@NotNull JImStyleVar<@NotNull Float> styleVar, float value) {
		pushStyleVarFloat(styleVar.nativeValue, value);
	}

	public void pushStyleVar(@NotNull JImStyleVar<@NotNull Void> styleVar, float valueX, float valueY) {
		pushStyleVarImVec2(styleVar.nativeValue, valueX, valueY);
	}

	public boolean windowShouldClose() {
		return windowShouldClose(nativeObjectPtr);
	}

	/** Should be called after drawing all widgets */
	public void render() {
		render(nativeObjectPtr, background.nativeObjectPtr);
	}

	/** Should be called before drawing all widgets */
	public void initNewFrame() {
		initNewFrame(nativeObjectPtr);
	}

	public void loadIniSettingsFromMemory(@NotNull String data) {
		loadIniSettingsFromMemory(getBytes(data));
	}

	public @NotNull String saveIniSettingsToMemory() {
		return new String(saveIniSettingsToMemory0());
	}

	//region Private native interfaces
	private static native long allocateNativeObjects(int width, int height, byte[] title);
	private static native void deallocateNativeObjects(long nativeObjectPtr);
	private static native void initNewFrame(long nativeObjectPtr);
	private static native boolean windowShouldClose(long nativeObjectPtr);
	private static native void render(long nativeObjectPtr, long colorPtr);
	private static native void begin(byte[] name, int flags);
	private static native void loadIniSettingsFromMemory(byte[] data);
	private static native byte[] saveIniSettingsToMemory0();
	//endregion
}
