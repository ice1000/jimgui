package org.ice1000.jimgui;

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
public final class JImStr {
  public final byte @NotNull [] bytes;

  public JImStr(@NotNull String source) {
    bytes = getBytes(source);
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
}
