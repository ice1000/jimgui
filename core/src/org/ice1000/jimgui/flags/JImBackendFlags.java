package org.ice1000.jimgui.flags;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImBackendFlags {
	/** Back-end supports and has a connected gamepad. */
	int HasGamepad = 1 << 0;
	/** Back-end supports reading GetMouseCursor() to change the OS cursor shape. */
	int HasMouseCursors = 1 << 1;
	/**
	 * Back-end supports io.WantSetMousePos requests to reposition the OS mouse position
	 * (only used if {@link JImConfigFlags#NavEnableSetMousePos} is set).
	 */
	int HasSetMousePos = 1 << 2;
}
