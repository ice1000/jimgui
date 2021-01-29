package org.ice1000.jimgui;

import org.ice1000.jimgui.flag.JImSortDirection;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

/**
 * No need to deallocate.
 *
 * @author ice1000
 * @since v0.18
 */
public class JImSortSpecs extends JImTableSortSpecsGen {
  /** package-private by design */
  JImSortSpecs(long nativeObjectPtr) {
    super(nativeObjectPtr);
  }
  public @NotNull Column columnSortSpecs(int index) {
    return new Column(columnSortSpecs(nativeObjectPtr, index));
  }
  private static native long columnSortSpecs(long nativeObjectPtr, int offset);
  @SuppressWarnings("MagicConstant")
  public static class Column extends JImColumnSortSpecsGen {
    /** package-private by design */
    Column(long nativeObjectPtr) {
      super(nativeObjectPtr);
    }
    @Override public @MagicConstant(valuesFromClass = JImSortDirection.class) int getSortDirection() {
      return super.getSortDirection();
    }
    @Override public void setSortDirection(@MagicConstant(valuesFromClass = JImSortDirection.class) int newValue) {
      super.setSortDirection(newValue);
    }
  }
}
