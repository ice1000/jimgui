package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;

import static org.ice1000.jimgui.util.JImGuiUtil.FLT_MAX;
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
	@Contract
	public void setBackground(@NotNull JImVec4 background) {
		if (this.background == background) return;
		this.background.close();
		this.background = background;
	}

	@Contract(" -> fail")
	private void alreadyDisposed() {
		throw new IllegalStateException("Native object is nullptr.");
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
	 * @return same as {@link #getWindowDrawList()}
	 */
	@Contract(pure = true)
	public @Nullable JImDrawList findWindowDrawList() {
		long drawListNativeObjectPtr = getWindowDrawListNativeObjectPtr();
		return drawListNativeObjectPtr == 0 ? null : new JImDrawList(drawListNativeObjectPtr);
	}

	@Contract(pure = true)
	public @NotNull JImDrawList getWindowDrawList() {
		@Nullable JImDrawList windowDrawList = findWindowDrawList();
		if (null == windowDrawList) alreadyDisposed();
		return windowDrawList;
	}

	/**
	 * Call this only if you expect a nullable result.
	 *
	 * @return same as {@link #getOverlayDrawList()}
	 */
	@Contract(pure = true)
	public @Nullable JImDrawList findOverlayDrawList() {
		long drawListNativeObjectPtr = getOverlayDrawListNativeObjectPtr();
		return drawListNativeObjectPtr == 0 ? null : new JImDrawList(drawListNativeObjectPtr);
	}

	@Contract(pure = true)
	public @NotNull JImDrawList getOverlayDrawList() {
		@Nullable JImDrawList windowDrawList = findOverlayDrawList();
		if (null == windowDrawList) alreadyDisposed();
		return windowDrawList;
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

	/** alias to {@link JImGuiGen#textUnformatted(String)} */
	public void text(@NotNull String text) {
		textUnformatted(text);
	}

	public void textColored(@NotNull JImVec4 color, @NotNull String text) {
		pushStyleColor(JImStyleColors.Text, color);
		textUnformatted(text);
		popStyleColor();
	}

	public void textDisabled(@NotNull String text) {
		if (style == null) alreadyDisposed();
		pushStyleColor(JImStyleColors.Text, style.getColor(JImStyleColors.TextDisabled));
		textUnformatted(text);
		popStyleColor();
	}

	/**
	 * @param label        label text
	 * @param values       plot values
	 * @param valuesOffset offset in [values]
	 * @param valuesLength length in [values]
	 */
	public void plotLines(@NotNull String label, @NotNull float[] values, int valuesOffset, int valuesLength) {
		plotLines(label.getBytes(StandardCharsets.UTF_8), values, valuesOffset, valuesLength, null, FLT_MAX, FLT_MAX, 0, 0);
	}

	/**
	 * @param label  label text
	 * @param values plot values
	 */
	public void plotLines(@NotNull String label, @NotNull float[] values) {
		plotLines(label, values, 0, values.length);
	}

	/**
	 * @param label        label text
	 * @param values       plot values
	 * @param valuesOffset offset in [values]
	 * @param valuesLength length in [values]
	 * @param overlayText  tooltip text when plot is hovered
	 */
	public void plotLines(@NotNull String label,
	                      @NotNull float[] values,
	                      int valuesOffset,
	                      int valuesLength,
	                      @NotNull String overlayText) {
		plotLines(label, values, valuesOffset, valuesLength, overlayText, FLT_MAX, FLT_MAX, 0, 0);
	}

	/**
	 * @param label       label text
	 * @param values      plot values
	 * @param overlayText tooltip text when plot is hovered
	 */
	public void plotLines(@NotNull String label, @NotNull float[] values, @NotNull String overlayText) {
		plotLines(label, values, 0, 0, overlayText);
	}

	/**
	 * @param label        label text
	 * @param values       plot values
	 * @param valuesOffset offset in [values]
	 * @param valuesLength length in [values]
	 * @param overlayText  tooltip text when plot is hovered
	 */
	public void plotLines(@NotNull String label,
	                      @NotNull float[] values,
	                      int valuesOffset,
	                      int valuesLength,
	                      @NotNull String overlayText,
	                      float graphWidth,
	                      float graphHeight) {
		plotLines(label, values, valuesOffset, valuesLength, overlayText, FLT_MAX, FLT_MAX, graphWidth, graphHeight);
	}

	/**
	 * @param label        label text
	 * @param values       plot values
	 * @param valuesOffset offset in [values]
	 * @param valuesLength length in [values]
	 * @param overlayText  tooltip text when plot is hovered
	 */
	public void plotLines(@NotNull String label,
	                      @NotNull float[] values,
	                      int valuesOffset,
	                      int valuesLength,
	                      @NotNull String overlayText,
	                      float scaleMin,
	                      float scaleMax,
	                      float graphWidth,
	                      float graphHeight) {
		plotLines(label.getBytes(StandardCharsets.UTF_8),
				values,
				valuesOffset,
				valuesLength,
				overlayText.getBytes(StandardCharsets.UTF_8),
				scaleMin,
				scaleMax,
				graphWidth,
				graphHeight);
	}

	/**
	 * @param label  label text
	 * @param values plot values
	 */
	public void plotHistogram(@NotNull String label, @NotNull float[] values) {
		plotHistogram(label.getBytes(StandardCharsets.UTF_8), values, 0, values.length, null, FLT_MAX, FLT_MAX, 0, 0);
	}

	/**
	 * @param label       label text
	 * @param values      plot values
	 * @param overlayText tooltip text when plot is hovered
	 */
	public void plotHistogram(@NotNull String label, @NotNull float[] values, @NotNull String overlayText) {
		plotHistogram(label, values, 0, values.length, overlayText);
	}

	/**
	 * @param label        label text
	 * @param values       plot values
	 * @param valuesOffset offset in [values]
	 * @param valuesLength length in [values]
	 * @param overlayText  tooltip text when plot is hovered
	 */
	public void plotHistogram(@NotNull String label,
	                          @NotNull float[] values,
	                          int valuesOffset,
	                          int valuesLength,
	                          @NotNull String overlayText) {
		plotHistogram(label, values, valuesOffset, valuesLength, overlayText, FLT_MAX, FLT_MAX);
	}

	/**
	 * @param label        label text
	 * @param values       plot values
	 * @param valuesOffset offset in [values]
	 * @param valuesLength length in [values]
	 * @param overlayText  tooltip text when plot is hovered
	 */
	public void plotHistogram(@NotNull String label,
	                          @NotNull float[] values,
	                          int valuesOffset,
	                          int valuesLength,
	                          @NotNull String overlayText,
	                          float scaleMin,
	                          float scaleMax) {
		plotHistogram(label, values, valuesOffset, valuesLength, overlayText, scaleMin, scaleMax, 0, 0);
	}

	// TODO doc
	public void plotHistogram(@NotNull String label,
	                          @NotNull float[] values,
	                          int valuesOffset,
	                          int valuesLength,
	                          @NotNull String overlayText,
	                          float scaleMin,
	                          float scaleMax,
	                          float graphWidth,
	                          float graphHeight) {
		plotHistogram(label.getBytes(StandardCharsets.UTF_8),
				values,
				valuesOffset,
				valuesLength,
				overlayText.getBytes(StandardCharsets.UTF_8),
				scaleMin,
				scaleMax,
				graphWidth,
				graphHeight);
	}

	public native void pushID(int intID);

	/**
	 * @param styleVar should be a value from {@link JImStyleVars}
	 * @param value    the value to set
	 */
	public void pushStyleVar(@NotNull JImStyleVar<@NotNull Float> styleVar,
	                         float value) {
		pushStyleVarFloat(styleVar.nativeValue, value);
	}

	/**
	 * @param styleVar should be a value from {@link JImStyleVars}
	 * @param valueX   the first value of ImVec2 to set
	 * @param valueY   the second value of ImVec2 to set
	 */
	public void pushStyleVar(@NotNull JImStyleVar<@NotNull Void> styleVar,
	                         float valueX,
	                         float valueY) {
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

	public @NotNull String getClipboardText() {
		return new String(getClipboardText0());
	}

	/**
	 * @param label    label text
	 * @param shortcut displayed for convenience but not processed by ImGui at the moment
	 * @param selected like checkbox
	 * @param enabled  if not, will be grey
	 * @return true when activated.
	 */
	public boolean menuItem(@NotNull String label,
	                        @Nullable String shortcut,
	                        boolean selected,
	                        boolean enabled) {
		return menuItem(label.getBytes(StandardCharsets.UTF_8),
				shortcut != null ? shortcut.getBytes(StandardCharsets.UTF_8) : null,
				selected,
				enabled);
	}

	/**
	 * @param label    label text
	 * @param shortcut displayed for convenience but not processed by ImGui at the moment
	 * @param selected like checkbox
	 * @return true when activated.
	 */
	public boolean menuItem(@NotNull String label, @Nullable String shortcut, boolean selected) {
		return menuItem(label, shortcut, selected, true);
	}

	/**
	 * @param label    label text
	 * @param selected like checkbox
	 * @return true when activated.
	 */
	public boolean menuItem(@NotNull String label, boolean selected) {
		return menuItem(label, null, selected);
	}

	//region Private native interfaces
	private static native long allocateNativeObjects(int width, int height, byte @NotNull [] title);
	private static native void deallocateNativeObjects(long nativeObjectPtr);
	private static native void initNewFrame(long nativeObjectPtr);
	private static native long getFontNativeObjectPtr();
	private static native boolean menuItem(byte[] label, byte[] shortcut, boolean selected, boolean enabled);
	private static native long getWindowDrawListNativeObjectPtr();
	private static native long getOverlayDrawListNativeObjectPtr();
	private static native boolean windowShouldClose(long nativeObjectPtr);
	private static native void render(long nativeObjectPtr, long colorPtr);
	private static native void loadIniSettingsFromMemory(byte @NotNull [] data);
	private static native byte @NotNull [] saveIniSettingsToMemory0();
	private static native byte @NotNull [] getClipboardText0();
	private static native void plotLines(byte @NotNull [] label,
	                                     float @NotNull [] values,
	                                     int valuesOffset,
	                                     int valuesLength,
	                                     byte[] overlayText,
	                                     float scaleMin,
	                                     float scaleMax,
	                                     float graphWidth,
	                                     float graphHeight);
	private static native void plotHistogram(byte @NotNull [] label,
	                                         float @NotNull [] values,
	                                         int valuesOffset,
	                                         int valuesLength,
	                                         byte[] overlayText,
	                                         float scaleMin,
	                                         float scaleMax,
	                                         float graphWidth,
	                                         float graphHeight);
	//endregion
}
