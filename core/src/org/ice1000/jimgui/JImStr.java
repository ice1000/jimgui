package org.ice1000.jimgui;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static org.ice1000.jimgui.util.JImGuiUtil.getBytes;

/**
 * An alternative to {@link String} used in
 * {@link JImGui} functions.
 * Reduces memory allocations.
 *
 * @author ice1000
 * @since 0.9
 */
public class JImStr {
  public static class Cached extends JImStr {
    public final @NotNull String source;

    public Cached(@NotNull String source) {
      super(source);
      this.source = source;
    }

    @Override public @NotNull String toString() {
      return source;
    }
  }

  public static final @NotNull JImStr EMPTY = new JImStr("");
  public final byte @NotNull [] bytes;

  private JImStr(byte @NotNull [] bytes) {
    this.bytes = bytes;
  }

  public JImStr(@NotNull String source) {
    this(getBytes(source));
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    JImStr imStr = (JImStr) o;
    return Arrays.equals(bytes, imStr.bytes);
  }

  @Override public int hashCode() {
    return Arrays.hashCode(bytes);
  }

  @Contract(pure = true) @Override public @NotNull String toString() {
    return new String(Arrays.copyOfRange(bytes, 0, bytes.length - 1));
  }
}
