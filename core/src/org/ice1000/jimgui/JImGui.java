package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.ice1000.jimgui.util.JImGuiUtil.getBytes;

/**
 * @author ice1000
 * @since v0.1
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class JImGui extends JImWidgets implements DeallocatableObject {
  public static final @NotNull String DEFAULT_TITLE = "ImGui window created by JImGui";
  /** package-private by design */
  long nativeObjectPtr;
  private @NotNull JImVec4 background;
  private @Nullable JImGuiIO io;

  //region Native-unrelated
  public JImGui() {
    this(1280, 720);
  }

  public JImGui(@NotNull String title) {
    this(1280, 720, title);
  }

  public JImGui(int width, int height, @NotNull String title) {
    this(allocateNativeObjects(width, height, 0, getBytes(title), 0));
    setupImguiSpecificObjects(nativeObjectPtr, 0);
  }

  public JImGui(int width, int height) {
    this(width, height, DEFAULT_TITLE);
  }

  public JImGui(int width, int height, @NotNull JImFontAtlas fontAtlas, @NotNull String title) {
    this(width, height, fontAtlas, title, 0);
  }

  public JImGui(int width, int height, @NotNull JImFontAtlas fontAtlas, @NotNull String title, long anotherWindow) {
    this(allocateNativeObjects(width, height, fontAtlas.nativeObjectPtr, getBytes(title), anotherWindow));
    setupImguiSpecificObjects(nativeObjectPtr, fontAtlas.nativeObjectPtr);
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
   * @param nativeObjectPtr usually a C++ pointer to {@code GLFWwindow} on Linux/OSX,
   *                        {@code NativeObject} (see dx9_impl.cpp) on Windows, but if you're using
   *                        {@code JniLoaderEx} (in {@code org.ice1000.jimgui:extension}), this can
   *                        a lot of things else.
   * @param fontAtlas       font related settings
   * @see org.ice1000.jimgui.util.JniLoader
   */
  public static @NotNull JImGui fromExistingPointer(long nativeObjectPtr, @NotNull JImFontAtlas fontAtlas) {
    JImGui imGui = new JImGui(nativeObjectPtr);
    setupImguiSpecificObjects(nativeObjectPtr, fontAtlas.nativeObjectPtr);
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
    setupImguiSpecificObjects(nativeObjectPtr, 0);
    return imGui;
  }

  @Override public final void deallocateNativeObject() {
    background.close();
    deallocateNativeObjects(nativeObjectPtr);
    io = null;
  }

  @Override public final void close() {
    deallocateNativeObject();
    deallocateGuiFramework(nativeObjectPtr);
  }

  /**
   * @param background shouldn't be closed, will close automatically
   */
  @Contract public void setBackground(@NotNull JImVec4 background) {
    if (this.background == background) return;
    this.background.close();
    this.background = background;
  }

  /**
   * Call this only if you expect a nullable result.
   *
   * @return same as {@link #getIO()}
   */
  @Contract(pure = true) public @Nullable JImGuiIO findIO() {
    return io;
  }

  @Contract(pure = true) public @NotNull JImGuiIO getIO() {
    return null == io ? alreadyDisposed() : io;
  }

  @Contract(pure = true) public @NotNull JImStyle getStyle() {
    JImStyle style = findStyle();
    return null == style ? alreadyDisposed() : style;
  }

  @Contract(pure = true) public @NotNull JImFont getFont() {
    JImFont font = findFont();
    return null == font ? alreadyDisposed() : font;
  }

  /**
   * Call this only if you expect a nullable result.
   *
   * @return same as {@link #getWindowDrawList()}
   */
  @Contract(pure = true) public @Nullable JImDrawList findWindowDrawList() {
    long drawListNativeObjectPtr = getWindowDrawListNativeObjectPtr();
    return drawListNativeObjectPtr == 0 ? null : new JImDrawList(drawListNativeObjectPtr);
  }

  @Contract(pure = true) public @NotNull JImDrawList getWindowDrawList() {
    @Nullable JImDrawList windowDrawList = findWindowDrawList();
    return null == windowDrawList ? alreadyDisposed() : windowDrawList;
  }

  /**
   * Call this only if you expect a nullable result.
   *
   * @return same as {@link #getForegroundDrawList()}
   */
  @Contract(pure = true) public @Nullable JImDrawList findForegroundDrawList() {
    long drawListNativeObjectPtr = getForegroundDrawListNativeObjectPtr();
    return drawListNativeObjectPtr == 0 ? null : new JImDrawList(drawListNativeObjectPtr);
  }

  @Contract(pure = true) public @NotNull JImDrawList getForegroundDrawList() {
    @Nullable JImDrawList windowDrawList = findForegroundDrawList();
    return null == windowDrawList ? alreadyDisposed() : windowDrawList;
  }

  /**
   * Call this only if you expect a nullable result.
   *
   * @return same as {@link #getStyle()}, don't call {@link JImStyle#deallocateNativeObject()}
   */
  @Contract(pure = true) public @Nullable JImStyle findStyle() {
    long styleNativeObjectPtr = getStyleNativeObjectPtr();
    return styleNativeObjectPtr == 0 ? null : new JImStyle(styleNativeObjectPtr);
  }

  /**
   * Call this only if you expect a nullable result.
   *
   * @return same as {@link #getFont()}, don't call {@link JImStyle#deallocateNativeObject()}
   */
  @Contract(pure = true) public @Nullable JImFont findFont() {
    long fontNativeObjectPtr = getFontNativeObjectPtr();
    return fontNativeObjectPtr == 0 ? null : new JImFont(fontNativeObjectPtr);
  }

  @Contract(pure = true) public boolean isDisposed() {
    return io == null;
  }

  /**
   * @return shouldn't be closed, will close automatically
   */
  @Contract(pure = true) public @NotNull JImVec4 getBackground() {
    return background;
  }
  //endregion

  /** Don't call it. */
  @Deprecated
  @ApiStatus.ScheduledForRemoval
  public void initBeforeMainLoop() {
  }

  public float getPlatformWindowSizeX() {
    return getPlatformWindowSizeX(nativeObjectPtr);
  }

  public float getPlatformWindowSizeY() {
    return getPlatformWindowSizeY(nativeObjectPtr);
  }

  public float getPlatformWindowPosX() {
    return getPlatformWindowPosX(nativeObjectPtr);
  }

  public float getPlatformWindowPosY() {
    return getPlatformWindowPosY(nativeObjectPtr);
  }

  public void setPlatformWindowSize(float newX, float newY) {
    setPlatformWindowSize(nativeObjectPtr, newX, newY);
  }

  public void setPlatformWindowPos(float newX, float newY) {
    setPlatformWindowPos(nativeObjectPtr, newX, newY);
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
   * the condition of the main loop
   *
   * @return should end the main loop or not
   */
  @Contract(pure = true) public boolean windowShouldClose() {
    return windowShouldClose(nativeObjectPtr);
  }

  /** Should be called after drawing all widgets */
  @Contract public void render() {
    render(nativeObjectPtr, background.nativeObjectPtr);
  }

  /** Should be called before drawing all widgets */
  @Contract public void initNewFrame() {
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

  public void windowDrawListAddImage(
      @NotNull JImTextureID id,
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

  //region Private native interfaces
  private static native long allocateNativeObjects(
      int width, int height, long fontAtlas, byte @NotNull [] title, long anotherWindow);

  protected static native void setupImguiSpecificObjects(long nativeObjectPtr, long fontAtlas);

  private static native void deallocateNativeObjects(long nativeObjectPtr);

  private static native void deallocateGuiFramework(long nativeObjectPtr);

  private static native void initNewFrame(long nativeObjectPtr);

  private static native long getFontNativeObjectPtr();

  private static native long getStyleNativeObjectPtr();

  private static native long getWindowDrawListNativeObjectPtr();

  private static native long getForegroundDrawListNativeObjectPtr();

  private static native boolean windowShouldClose(long nativeObjectPtr);

  private static native void render(long nativeObjectPtr, long colorPtr);

  private static native float getPlatformWindowSizeX(long nativeObjectPtr);

  private static native float getPlatformWindowSizeY(long nativeObjectPtr);

  private static native float getPlatformWindowPosX(long nativeObjectPtr);

  private static native float getPlatformWindowPosY(long nativeObjectPtr);

  private static native void setPlatformWindowSize(long nativeObjectPtr, float newX, float newY);

  private static native void setPlatformWindowPos(long nativeObjectPtr, float newX, float newY);

  private static native void loadIniSettingsFromMemory(final byte @NotNull [] data);

  private static native byte @NotNull [] saveIniSettingsToMemory0();

  private static native byte @NotNull [] getClipboardText0();
  //endregion
}
