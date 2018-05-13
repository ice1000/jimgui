package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;

import static org.ice1000.jimgui.util.JImGuiUtil.getBytes;

/**
 * @author ice1000
 * @since v0.1
 */
@SuppressWarnings("WeakerAccess")
public class JImGui extends JImGuiGen implements DeallocatableObject {
	/** package-private by design */
	long nativeObjectPtr;
	private @NotNull JImVec4 background;
	private @Nullable JImGuiIO io;
	private @Nullable JImStyle style;

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
		style = new JImStyle();
		background = new JImVec4(1.0f, 0.55f, 0.60f, 1.00f);
	}

	@Override
	public void deallocateNativeObject() {
		background.close();
		deallocateNativeObjects(nativeObjectPtr);
		io = null;
		style = null;
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
	@Contract(pure = true)
	public @Nullable JImGuiIO findIO() {
		return io;
	}

	@Contract(pure = true)
	public @NotNull JImGuiIO getIO() {
		if (null == io) alreadyDisposed();
		return io;
	}

	/**
	 * Call this only if you expect a nullable result.
	 *
	 * @return same as {@link #getFont()}
	 */
	@Contract(pure = true)
	public @Nullable JImFont findFont() {
		long fontNativeObjectPtr = getFontNativeObjectPtr();
		return fontNativeObjectPtr == 0 ? null : new JImFont(fontNativeObjectPtr);
	}

	@Contract(pure = true)
	public @NotNull JImFont getFont() {
		JImFont font = findFont();
		if (null == font) alreadyDisposed();
		return font;
	}

	/**
	 * Call this only if you expect a nullable result.
	 *
	 * @return same as {@link #getStyle()}
	 */
	@Contract(pure = true)
	public @Nullable JImStyle findStyle() {
		return style;
	}

	@Contract(pure = true)
	public @NotNull JImStyle getStyle() {
		if (null == style) alreadyDisposed();
		return style;
	}

	@Contract(pure = true)
	public boolean isDisposed() {
		return io == null;
	}

	/**
	 * @return shouldn't be closed, will close automatically
	 */
	@Contract(pure = true)
	public @NotNull JImVec4 getBackground() {
		return background;
	}
	//endregion

	/** draw plain text */
	public void text(@NotNull String text) {
		textUnformatted(text.getBytes(StandardCharsets.UTF_8));
	}

	public void textColored(@NotNull JImVec4 color, @NotNull String text) {
		pushStyleColor(JImStyleColors.Text, color);
		text(text);
		popStyleColor();
	}

	public void textDisabled(@NotNull String text) {
		if (style == null) alreadyDisposed();
		pushStyleColor(JImStyleColors.Text, style.getColor(JImStyleColors.TextDisabled));
		text(text);
		popStyleColor();
	}

	/**
	 * @param styleVar should be a value from {@link JImStyleVars}
	 * @param value    the value to set
	 */
	public void pushStyleVar(@MagicConstant(valuesFromClass = JImStyleVars.class) @NotNull JImStyleVar<@NotNull Float> styleVar, float value) {
		pushStyleVarFloat(styleVar.nativeValue, value);
	}

	/**
	 * @param styleVar should be a value from {@link JImStyleVars}
	 * @param valueX   the first value of ImVec2 to set
	 * @param valueY   the second value of ImVec2 to set
	 */
	public void pushStyleVar(@MagicConstant(valuesFromClass = JImStyleVars.class) @NotNull JImStyleVar<@NotNull Void> styleVar, float valueX, float valueY) {
		pushStyleVarImVec2(styleVar.nativeValue, valueX, valueY);
	}

	/**
	 * the condition of the main loop
	 *
	 * @return should end the main loop or not
	 */
	@Contract(pure = true)
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
	private static native long allocateNativeObjects(int width, int height, byte @NotNull [] title);
	private static native void deallocateNativeObjects(long nativeObjectPtr);
	private static native void initNewFrame(long nativeObjectPtr);
	private static native long getFontNativeObjectPtr();
	private static native boolean windowShouldClose(long nativeObjectPtr);
	private static native void render(long nativeObjectPtr, long colorPtr);
	private static native void loadIniSettingsFromMemory(byte @NotNull [] data);
	private static native void textUnformatted(byte @NotNull [] text);
	private static native byte @NotNull [] saveIniSettingsToMemory0();
	//endregion
}
