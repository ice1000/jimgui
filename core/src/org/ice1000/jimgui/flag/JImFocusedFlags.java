package org.ice1000.jimgui.flag;

/**
 * Flags for {@link org.ice1000.jimgui.JImGuiGen#isWindowFocused(int)}
 *
 * @author ice1000
 * @since v0.1
 */
public interface JImFocusedFlags {
	int Nothing = 0;
	int Default = 0;
	/** {@link org.ice1000.jimgui.JImGuiGen#isWindowFocused}: Return true if any children of the window is focused */
	int ChildWindows = 1;
	/** {@link org.ice1000.jimgui.JImGuiGen#isWindowFocused}: Test from root window (top most parent of the current hierarchy) */
	int RootWindow = 1 << 1;
	/** {@link org.ice1000.jimgui.JImGuiGen#isWindowFocused}: Return true if any window is focused */
	int AnyWindow = 1 << 2;
	int RootAndChildWindows = RootWindow | ChildWindows;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImFocusedFlags.Nothing),
		Nothing(JImFocusedFlags.Nothing),
		/** @see JImFocusedFlags#Default */
		Default(JImFocusedFlags.Default),
		/** @see JImFocusedFlags#ChildWindows */
		ChildWindows(JImFocusedFlags.ChildWindows),
		/** @see JImFocusedFlags#RootWindow */
		RootWindow(JImFocusedFlags.RootWindow),
		/** @see JImFocusedFlags#AnyWindow */
		AnyWindow(JImFocusedFlags.AnyWindow),
		/** @see JImFocusedFlags#RootAndChildWindows */
		RootAndChildWindows(JImFocusedFlags.RootAndChildWindows);
		
		public final int flag;

		Type(int flag) {
			this.flag = flag;
		}

		@Override
		public int get() {
			return flag;
		}
	}
}
