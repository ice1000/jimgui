package org.ice1000.jimgui;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImFocusedFlags {
	public static final int ChildWindows = 1 << 0;
	public static final int RootWindow = 1 << 1;
	public static final int AnyWindow = 1 << 2;
	public static final int RootAndChildWindows = RootWindow | ChildWindows;
}
