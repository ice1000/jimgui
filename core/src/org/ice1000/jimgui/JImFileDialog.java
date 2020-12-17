package org.ice1000.jimgui;

import org.ice1000.jimgui.flag.JImWindowFlags;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

import static org.ice1000.jimgui.util.JImGuiUtil.FLT_MAX;

/**
 * https://github.com/aiekick/ImGuiFileDialog
 *
 * @author ice1000
 * @since v0.13.0
 */
public final class JImFileDialog extends JImFileDialogGen {
  public JImFileDialog() {
    super(allocateNativeObject());
  }

  public void setExtensionInfo(@NotNull String filters, @NotNull JImVec4 color) {
    setExtensionInfo(filters, color, "");
  }

  public void setExtensionInfo(@NotNull JImStr filters, @NotNull JImVec4 color) {
    setExtensionInfo(filters, color, JImStr.EMPTY);
  }

  public boolean isOk() {
    return getIsOk();
  }

  public boolean fileDialog(
      @NotNull NativeString key, @MagicConstant(flagsFromClass = JImWindowFlags.class) int flags) {
    return fileDialog(key, flags, 0, 0, FLT_MAX, FLT_MAX);
  }

  public boolean fileDialog(
      @NotNull NativeString key,
      @MagicConstant(flagsFromClass = JImWindowFlags.class) int flags,
      float minSizeX,
      float minSizeY,
      float maxSizeX,
      float maxSizeY) {
    return fileDialogP(key.nativeObjectPtr, flags, minSizeX, minSizeY, maxSizeX, maxSizeY);
  }

  private static native long allocateNativeObject();
  private static native void deallocateNativeObject(long nativeObjectPtr);

  private static native long currentPath0();

  private static native long currentFileName0();

  private static native long filePathName0();

  public @NotNull NativeString currentPath() {
    return new NativeString(currentPath0());
  }

  public @NotNull NativeString currentFileName() {
    return new NativeString(currentFileName0());
  }

  public @NotNull NativeString filePathName() {
    return new NativeString(filePathName0());
  }

  private static native boolean fileDialogP(
      long nativeStringPtrKey,
      @MagicConstant(flagsFromClass = JImWindowFlags.class) int flags,
      float minSizeX,
      float minSizeY,
      float maxSizeX,
      float maxSizeY);

  public static native void loadIcons(float fontSize);

  public interface Icons {
    int MIN = 0xf002;
    int MAX = 0xf1c9;
    @NotNull JImStr ADD = new JImStr.Cached("\uf067");
    @NotNull JImStr BOOKMARK = new JImStr.Cached("\uf02e");
    @NotNull JImStr CANCEL = new JImStr.Cached("\uf00d");
    @NotNull JImStr DRIVES = new JImStr.Cached("\uf0a0");
    @NotNull JImStr EDIT = new JImStr.Cached("\uf040");
    @NotNull JImStr CHEVRON_DOWN = new JImStr.Cached("\uf078");
    @NotNull JImStr CHEVRON_UP = new JImStr.Cached("\uf077");
    @NotNull JImStr FILE = new JImStr.Cached("\uf15b");
    @NotNull JImStr FILE_PIC = new JImStr.Cached("\uf1c5");
    @NotNull JImStr FOLDER = new JImStr.Cached("\uf07b");
    @NotNull JImStr FOLDER_OPEN = new JImStr.Cached("\uf07c");
    @NotNull JImStr LINK = new JImStr.Cached("\uf1c9");
    @NotNull JImStr OK = new JImStr.Cached("\uf00c");
    @NotNull JImStr REFRESH = new JImStr.Cached("\uf021");
    @NotNull JImStr REMOVE = new JImStr.Cached("\uf068");
    @NotNull JImStr RESET = new JImStr.Cached("\uf064");
    @NotNull JImStr SAVE = new JImStr.Cached("\uf0c7");
    @NotNull JImStr SEARCH = new JImStr.Cached("\uf002");
  }
}
