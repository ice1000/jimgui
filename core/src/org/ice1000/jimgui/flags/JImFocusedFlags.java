package org.ice1000.jimgui.flags;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImFocusedFlags {
	int ChildWindows = 1 << 0;
	int RootWindow = 1 << 1;
	int AnyWindow = 1 << 2;
	int RootAndChildWindows = RootWindow | ChildWindows;
}
