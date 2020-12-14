package org.ice1000.jimgui;

import org.ice1000.jimgui.flag.JImInputTextFlags;
import org.ice1000.jimgui.flag.JImTabItemFlags;
import org.ice1000.jimgui.flag.JImWindowFlags;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.ice1000.jimgui.util.JImGuiUtil.*;

/**
 * The class between JImGui and JImGuiGen.
 *
 * @author ice1000
 * @since v0.12
 */
public abstract class JImWidgets extends JImGuiGen {
  private static native void plotLines(
      final byte @NotNull [] label,
      final float @NotNull [] values,
      int valuesOffset,
      int valuesLength,
      final byte @Nullable [] overlayText,
      float scaleMin,
      float scaleMax,
      float graphWidth,
      float graphHeight);

  private static native void plotHistogram(
      final byte @NotNull [] label,
      final float @NotNull [] values,
      int valuesOffset,
      int valuesLength,
      final byte @Nullable [] overlayText,
      float scaleMin,
      float scaleMax,
      float graphWidth,
      float graphHeight);

  private static native boolean inputText(
      final byte @NotNull [] label, byte @NotNull [] buffer, final int bufferSize, int flags);

  private static native boolean menuItem(
      final byte @NotNull [] label, final byte @Nullable [] shortcut, boolean selected, boolean enabled);

  /** alias to {@link JImGuiGen#textUnformatted(String)} */
  public void text(@NotNull String text) {
    textUnformatted(text);
  }

  public void text(@NotNull JImStr text) {
    textUnformatted(text.bytes);
  }

  public boolean begin(
      @NotNull JImStr str, NativeBool openPtr, @MagicConstant(flagsFromClass = JImWindowFlags.class) int flags) {
    return begin(str.bytes, openPtr.nativeObjectPtr, flags);
  }

  public boolean begin(@NotNull JImStr str) {
    return begin(str.bytes, 0, JImWindowFlags.None);
  }

  public boolean begin(@NotNull JImStr str, @MagicConstant(flagsFromClass = JImWindowFlags.class) int flags) {
    return begin(str.bytes, 0, flags);
  }

  public void textColored(@NotNull JImVec4 color, @NotNull String text) {
    pushStyleColor(JImStyleColors.Text, color);
    textUnformatted(text);
    popStyleColor();
  }

  @Contract(value = " -> fail", pure = true) protected final void alreadyDisposed() {
    throw new IllegalStateException("Native object is nullptr.");
  }

  public final void progressBar(float fraction, @Nullable String overlay) {
    progressBar(fraction, -1, 0, getBytes(overlay));
  }

  public final void progressBar(float fraction, @Nullable JImStr overlay) {
    progressBar(fraction, -1, 0, overlay != null ? overlay.bytes : EMPTY_BYTES);
  }

  /**
   * @param label        label text
   * @param values       plot values
   * @param valuesOffset offset in [values]
   * @param valuesLength length in [values]
   */
  public void plotLines(@NotNull String label, float @NotNull [] values, int valuesOffset, int valuesLength) {
    plotLines(getBytes(label), values, valuesOffset, valuesLength, null, FLT_MAX, FLT_MAX, 0, 0);
  }

  /**
   * @param label  label text
   * @param values plot values
   */
  public void plotLines(@NotNull String label, float @NotNull [] values) {
    plotLines(label, values, 0, values.length);
  }

  /**
   * @param label        label text
   * @param values       plot values
   * @param valuesOffset offset in [values]
   * @param valuesLength length in [values]
   * @param overlayText  tooltip text when plot is hovered
   */
  public void plotLines(
      @NotNull String label,
      float @NotNull [] values,
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
  public void plotLines(@NotNull String label, float @NotNull [] values, @NotNull String overlayText) {
    plotLines(label, values, 0, 0, overlayText);
  }

  /**
   * @param label        label text
   * @param values       plot values
   * @param valuesOffset offset in [values]
   * @param valuesLength length in [values]
   * @param overlayText  tooltip text when plot is hovered
   */
  public void plotLines(
      @NotNull String label,
      float @NotNull [] values,
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
  public void plotLines(
      @NotNull String label,
      float @NotNull [] values,
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
  public void plotHistogram(@NotNull String label, float @NotNull [] values) {
    plotHistogram(getBytes(label), values, 0, values.length, null, FLT_MAX, FLT_MAX, 0, 0);
  }

  /**
   * @param label       label text
   * @param values      plot values
   * @param overlayText tooltip text when plot is hovered
   */
  public void plotHistogram(@NotNull String label, float @NotNull [] values, @NotNull String overlayText) {
    plotHistogram(label, values, 0, values.length, overlayText);
  }

  /**
   * @param label        label text
   * @param values       plot values
   * @param valuesOffset offset in [values]
   * @param valuesLength length in [values]
   * @param overlayText  tooltip text when plot is hovered
   */
  public void plotHistogram(
      @NotNull String label,
      float @NotNull [] values,
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
  public void plotHistogram(
      @NotNull String label,
      float @NotNull [] values,
      int valuesOffset,
      int valuesLength,
      @NotNull String overlayText,
      float scaleMin,
      float scaleMax) {
    plotHistogram(label, values, valuesOffset, valuesLength, overlayText, scaleMin, scaleMax, 0, 0);
  }

  // TODO doc
  public void plotHistogram(
      @NotNull String label,
      float @NotNull [] values,
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
   * @param shortcut displayed for convenience but not processed by ImGui at the moment
   * @param selected like checkbox
   * @param enabled  if not, will be grey
   * @return true when activated.
   */
  public boolean menuItem(@NotNull String label, @Nullable String shortcut, boolean selected, boolean enabled) {
    return menuItem(getBytes(label), getBytes(shortcut), selected, enabled);
  }

  /**
   * @param label    label text
   * @param shortcut displayed for convenience but not processed by ImGui at the moment
   * @param selected like checkbox
   * @param enabled  if not, will be grey
   * @return true when activated.
   */
  public boolean menuItem(@NotNull JImStr label, @Nullable JImStr shortcut, boolean selected, boolean enabled) {
    return menuItem(label.bytes, shortcut != null ? shortcut.bytes : EMPTY_BYTES, selected, enabled);
  }

  public boolean beginTabItem(@NotNull String label, @MagicConstant(flagsFromClass = JImTabItemFlags.class) int flags) {
    return beginTabItem(getBytes(label), 0, flags);
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

  /**
   * @param label    label text
   * @param selected like checkbox
   * @return true when activated.
   */
  public boolean menuItem(@NotNull JImStr label, boolean selected) {
    return menuItem(label.bytes, null, selected, true);
  }

  public boolean inputText(
      @NotNull String label,
      byte @NotNull [] buffer,
      @MagicConstant(flagsFromClass = JImInputTextFlags.class) int flags) {
    return inputText(getBytes(label), buffer, buffer.length, flags);
  }

  public boolean inputText(
      @NotNull JImStr label,
      byte @NotNull [] buffer,
      @MagicConstant(flagsFromClass = JImInputTextFlags.class) int flags) {
    return inputText(label.bytes, buffer, buffer.length, flags);
  }

  public boolean inputText(@NotNull String label, byte @NotNull [] buffer) {
    return inputText(label, buffer, JImInputTextFlags.None);
  }

  public boolean inputText(@NotNull JImStr label, byte @NotNull [] buffer) {
    return inputText(label, buffer, JImInputTextFlags.None);
  }

  /**
   * @param styleVar should be a value from {@link JImStyleVars}
   * @param value    the value to set
   */
  public void pushStyleVar(@NotNull JImStyleVar<@NotNull Float> styleVar, float value) {
    pushStyleVarFloat(styleVar.nativeValue, value);
  }

  /**
   * @param styleVar should be a value from {@link JImStyleVars}
   * @param valueX   the first value of ImVec2 to set
   * @param valueY   the second value of ImVec2 to set
   */
  public void pushStyleVar(@NotNull JImStyleVar<@NotNull Void> styleVar, float valueX, float valueY) {
    pushStyleVarImVec2(styleVar.nativeValue, valueX, valueY);
  }
}
