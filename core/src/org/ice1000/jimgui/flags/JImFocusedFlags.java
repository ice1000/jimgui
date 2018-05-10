package org.ice1000.jimgui.flags;

/**
 * Flags for JImGui.isWindowFocused()
 *
 * @author ice1000
 * @since v0.1
 */
public interface JImFocusedFlags {
	/** isWindowFocused(): Return true if any children of the window is focused */
	int ChildWindows = 1 << 0;
	/** isWindowFocused(): Test from root window (top most parent of the current hierarchy) */
	int RootWindow = 1 << 1;
	/** isWindowFocused(): Return true if any window is focused */
	int AnyWindow = 1 << 2;
	int RootAndChildWindows = RootWindow | ChildWindows;
}
