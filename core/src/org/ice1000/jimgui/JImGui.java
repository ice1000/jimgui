package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.ice1000.jimgui.flag.JImTextEditFlags;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

	//region Native-unrelated
	public JImGui() {
		this(1280, 720);
	}

	public JImGui(int width, int height, @NotNull String title) {
		this(allocateNativeObjects(width, height, 0, getBytes(title)));
	}

	public JImGui(int width, int height) {
		this(width, height, "ImGui window created by JImGui");
	}

	public JImGui(int width, int height, @NotNull JImFontAtlas fontAtlas) {
		this(width, height, fontAtlas, "ImGui window created by JImGui");
	}

	public JImGui(int width, int height, @NotNull JImFontAtlas fontAtlas, @NotNull String title) {
		this(allocateNativeObjects(width, height, fontAtlas.nativeObjectPtr, getBytes(title)));
	}

	private JImGui(long nativeObjectPtr) {
		this.nativeObjectPtr = nativeObjectPtr;
		if (nativeObjectPtr == 0) {
			System.err.println("Unknown error has happened during initialization!");
			System.exit(1);
		}
		io = new JImGuiIO();
		background = new JImVec4(0.4f, 0.55f, 0.60f, 1.00f);
	}

	/**
	 * For hacking purpose, don't use this if you're not sure what you're doing
	 *
	 * @param nativeObjectPtr a C++ pointer to {@code GLFWwindow} on Linux/OSX,
	 *                        {@code NativeObject} (see dx9_impl.cpp) on Windows
	 * @param fontAtlas       font related settings
	 */
	public static @NotNull JImGui fromExistingPointer(long nativeObjectPtr, @NotNull JImFontAtlas fontAtlas) {
		JImGui imGui = new JImGui(nativeObjectPtr);
		setupImbueSpecificObjects(nativeObjectPtr, fontAtlas.nativeObjectPtr);
		return imGui;
	}

	/**
	 * For hacking purpose, don't use this if you're not sure what you're doing
	 *
	 * @param nativeObjectPtr a C++ pointer to {@code GLFWwindow} on Linux/OSX,
	 *                        {@code NativeObject} (see dx9_impl.cpp) on Windows
	 */
	public static @NotNull JImGui fromExistingPointer(long nativeObjectPtr) {
		JImGui imGui = new JImGui(nativeObjectPtr);
		setupImbueSpecificObjects(nativeObjectPtr, 0);
		return imGui;
	}

	@Override
	public final void deallocateNativeObject() {
		background.close();
		deallocateNativeObjects(nativeObjectPtr);
		io = null;
	}

	@Override
	public final void close() {
		deallocateNativeObject();
		deallocateGuiFramework(nativeObjectPtr);
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

	@Contract(pure = true)
	public @NotNull JImStyle getStyle() {
		JImStyle style = findStyle();
		if (null == style) alreadyDisposed();
		return style;
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
	 * @return same as {@link #getStyle()}, don't call {@link JImStyle#deallocateNativeObject()}
	 */
	@Contract(pure = true)
	public @Nullable JImStyle findStyle() {
		long styleNativeObjectPtr = getStyleNativeObjectPtr();
		return styleNativeObjectPtr == 0 ? null : new JImStyle(styleNativeObjectPtr);
	}

	/**
	 * Call this only if you expect a nullable result.
	 *
	 * @return same as {@link #getFont()}, don't call {@link JImStyle#deallocateNativeObject()}
	 */
	@Contract(pure = true)
	public @Nullable JImFont findFont() {
		long fontNativeObjectPtr = getFontNativeObjectPtr();
		return fontNativeObjectPtr == 0 ? null : new JImFont(fontNativeObjectPtr);
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
		long styleNativeObjectPtr = getStyleNativeObjectPtr();
		if (styleNativeObjectPtr == 0) alreadyDisposed();
		pushStyleColor(JImStyleColors.Text, JImStyle.getColor0(styleNativeObjectPtr, JImStyleColors.TextDisabled));
		textUnformatted(text);
		popStyleColor();
	}

	public final void progressBar(float fraction, @Nullable String overlay) {
		progressBar(fraction, -1, 0, getBytes(overlay));
	}

	/**
	 * @param label        label text
	 * @param values       plot values
	 * @param valuesOffset offset in [values]
	 * @param valuesLength length in [values]
	 */
	public void plotLines(@NotNull String label, @NotNull float[] values, int valuesOffset, int valuesLength) {
		plotLines(getBytes(label), values, valuesOffset, valuesLength, null, FLT_MAX, FLT_MAX, 0, 0);
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
		plotLines(getBytes(label),
				values,
				valuesOffset,
				valuesLength,
				getBytes(overlayText),
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
		plotHistogram(getBytes(label), values, 0, values.length, null, FLT_MAX, FLT_MAX, 0, 0);
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
		plotHistogram(getBytes(label),
				values,
				valuesOffset,
				valuesLength,
				getBytes(overlayText),
				scaleMin,
				scaleMax,
				graphWidth,
				graphHeight);
	}

	public static native void pushID(int intID);

	public static native float getWindowPosX();
	public static native float getWindowPosY();
	public static native float getContentRegionMaxX();
	public static native float getContentRegionMaxY();
	public static native float getWindowContentRegionMinX();
	public static native float getWindowContentRegionMinY();
	public static native float getWindowContentRegionMaxX();
	public static native float getWindowContentRegionMaxY();
	public static native float getFontTexUvWhitePixelX();
	public static native float getFontTexUvWhitePixelY();
	public static native float getItemRectMinX();
	public static native float getItemRectMinY();
	public static native float getItemRectMaxX();
	public static native float getItemRectMaxY();
	public static native float getItemRectSizeX();
	public static native float getItemRectSizeY();
	public static native float getMousePosOnOpeningCurrentPopupX();
	public static native float getMousePosOnOpeningCurrentPopupY();

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
		return menuItem(getBytes(label), getBytes(shortcut), selected, enabled);
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

	public void windowDrawListAddImage(@NotNull JImTextureID id,
	                                   float aX,
	                                   float aY,
	                                   float bX,
	                                   float bY,
	                                   float uvAX,
	                                   float uvAY,
	                                   float uvBX,
	                                   float uvBY,
	                                   int color) {
		long ptr = getWindowDrawListNativeObjectPtr();
		JImGuiDrawListGen.addImage(id.nativeObjectPtr, aX, aY, bX, bY, uvAX, uvAY, uvBX, uvBY, color, ptr);
	}

	public void windowDrawListAddLine(float aX, float aY, float bX, float bY, int u32Color, float thickness) {
		JImGuiDrawListGen.addLine(aX, aY, bX, bY, u32Color, thickness, getWindowDrawListNativeObjectPtr());
	}

	public void windowDrawListAddLine(float aX, float aY, float bX, float bY, int u32Color) {
		windowDrawListAddLine(aX, aY, bX, bY, u32Color, 1);
	}

	public void image(@NotNull JImTextureID id) {
		image(id, id.width, id.height);
	}

	/**
	 * @param label    label text
	 * @param selected like checkbox
	 * @return true when activated.
	 */
	public boolean menuItem(@NotNull String label, boolean selected) {
		return menuItem(label, null, selected);
	}

	public boolean inputText(@NotNull String label,
	                         @NotNull byte[] buffer,
	                         @MagicConstant(flagsFromClass = JImTextEditFlags.class) int flags) {
		return inputText(getBytes(label), buffer, buffer.length, flags);
	}

	public boolean inputText(@NotNull String label, @NotNull byte[] buffer) {
		return inputText(label, buffer, 0);
	}

	//region Private native interfaces
	private static native long allocateNativeObjects(int width, int height, long fontAtlas, byte @NotNull [] title);
	protected static native void setupImbueSpecificObjects(long nativeObjectPtr, long fontAtlas);
	private static native void deallocateNativeObjects(long nativeObjectPtr);
	private static native void deallocateGuiFramework(long nativeObjectPtr);
	private static native void initNewFrame(long nativeObjectPtr);
	private static native long getFontNativeObjectPtr();
	private static native long getStyleNativeObjectPtr();
	private static native boolean menuItem(final byte @NotNull [] label,
	                                       final byte @Nullable [] shortcut,
	                                       boolean selected,
	                                       boolean enabled);
	private static native long getWindowDrawListNativeObjectPtr();
	private static native long getOverlayDrawListNativeObjectPtr();
	private static native boolean windowShouldClose(long nativeObjectPtr);
	private static native void render(long nativeObjectPtr, long colorPtr);
	private static native void loadIniSettingsFromMemory(final byte @NotNull [] data);
	private static native byte @NotNull [] saveIniSettingsToMemory0();
	private static native byte @NotNull [] getClipboardText0();
	private static native void plotLines(final byte @NotNull [] label,
	                                     final float @NotNull [] values,
	                                     int valuesOffset,
	                                     int valuesLength,
	                                     final byte @Nullable [] overlayText,
	                                     float scaleMin,
	                                     float scaleMax,
	                                     float graphWidth,
	                                     float graphHeight);
	private static native void plotHistogram(final byte @NotNull [] label,
	                                         final float @NotNull [] values,
	                                         int valuesOffset,
	                                         int valuesLength,
	                                         final byte @Nullable [] overlayText,
	                                         float scaleMin,
	                                         float scaleMax,
	                                         float graphWidth,
	                                         float graphHeight);
	private static native boolean inputText(final byte @NotNull [] label,
	                                        byte @NotNull [] buffer,
	                                        final int bufferSize,
	                                        int flags);
	//endregion
}
