package org.ice1000.jimgui;

/**
 * @param <T> Corresponding style var type,
 *            {@link Float} or ImVec2(Use {@link Void} to represent)
 * @author ice1000
 * @since v0.1
 */
@SuppressWarnings({"unused"})
public final class JImStyleVar<T> {
  /** package-private by design */
  int nativeValue;

  /** package-private by design */
  JImStyleVar(int nativeValue) {
    this.nativeValue = nativeValue;
  }
}
