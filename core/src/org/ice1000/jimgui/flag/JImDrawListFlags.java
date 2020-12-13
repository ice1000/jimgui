package org.ice1000.jimgui.flag;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImDrawListFlags {
  int Nothing = 0;
  int AntiAliasedLines = 1;
  int AntiAliasedFill = 1 << 1;

  enum Type implements Flag {
    /**
     * Used for reverse lookup results and enum comparison.
     * Return the Nothing or Default flag to prevent errors.
     */
    NoSuchFlag(JImDrawListFlags.Nothing), Nothing(JImDrawListFlags.Nothing),
    /** @see JImDrawListFlags#AntiAliasedLines */
    AntiAliasedLines(JImDrawListFlags.AntiAliasedLines),
    /** @see JImDrawListFlags#AntiAliasedFill */
    AntiAliasedFill(JImDrawListFlags.AntiAliasedFill);
    public final int flag;

    Type(int flag) {
      this.flag = flag;
    }

    @Override public int get() {
      return flag;
    }
  }
}
