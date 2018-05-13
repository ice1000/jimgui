package org.ice1000.jimgui.flag;

/**
 * Flags for {@link org.ice1000.jimgui.JImGuiGen#isWindowFocused(int)}
 *
 * @author ice1000
 * @since v0.1
 */
public interface JImFocusedFlags {
	/** {@link org.ice1000.jimgui.JImGuiGen#isWindowFocused}: Return true if any children of the window is focused */
	int ChildWindows = 1;
	/** {@link org.ice1000.jimgui.JImGuiGen#isWindowFocused}: Test from root window (top most parent of the current hierarchy) */
	int RootWindow = 1 << 1;
	/** {@link org.ice1000.jimgui.JImGuiGen#isWindowFocused}: Return true if any window is focused */
	int AnyWindow = 1 << 2;
	int RootAndChildWindows = RootWindow | ChildWindows;
}
